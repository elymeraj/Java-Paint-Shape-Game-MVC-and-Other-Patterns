package controleur;

import modele.Modele;
import modele.Joueur;
import modele.Partie;
import modele.Forme;
import controleur.evaluation.EvaluationStrategy;
import controleur.evaluation.EvaluationParSimilarite;
import vue.Vue;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Contrôleur pour le mode de jeu à deux joueurs.
 * Chaque joueur crée successivement une séquence de 4 formes
 * que l'autre joueur doit mémoriser et reproduire.
 */
public class ModeDeuxJoueurs implements Controleur {
    private Joueur joueur1 = new Joueur("Joueur 1");
    private Joueur joueur2 = new Joueur("Joueur 2");
    private Modele modele;
    private List<Forme> formesOriginales;
    private Partie partie;
    private EvaluationStrategy evaluateur;
    private Joueur joueurActif;
    private Joueur joueurCreateur;
    private Phase phaseActuelle;
    private static ModeDeuxJoueurs instance;

    /**
     * Enumération représentant les différentes phases d’un tour de jeu :
     * CREATION : le joueur crée 4 formes,
     * ATTENTE : l'autre joueur observe et mémorise,
     * REPRODUCTION : l'autre joueur tente de les reproduire.
     */
    private enum Phase {
        CREATION,
        ATTENTE,
        REPRODUCTION
    }

    /**
     * Ajout du patron singleton pour éviter plusieurs instance du mode de jeu
     */
    public static ModeDeuxJoueurs getInstance(){
        if (instance==null)
            instance = new ModeDeuxJoueurs();
        return instance;
    }
    /**
     * Constructeur du mode deux joueurs.
     * Initialise la partie, la stratégie d'évaluation et la structure des formes.
     */
    public ModeDeuxJoueurs() {
        this.partie = Partie.getInstance();
        this.evaluateur = new EvaluationParSimilarite();
        this.formesOriginales = new ArrayList<>();
    }

    /**
     * Démarre le mode de jeu à deux joueurs en enregistrant ce contrôleur
     * comme actif et en lançant le premier tour.
     *
     * @param modele Le modèle principal de l'application.
     */
    @Override
    public void demarrer(Modele modele) {
        this.modele = modele;
        this.modele.setControleurActif(this);
        demarrerNouveauTour();
    }

    /**
     * Lance un nouveau tour : gère l'initialisation de la phase de création,
     * l'alternance des rôles entre les joueurs, et l'affichage du statut.
     */
    private void demarrerNouveauTour() {
        if (partie.estTerminee()) {
            afficherResultatsFinaux();
            return;
        }

        modele.effacerFormes();
        formesOriginales.clear();

        joueurCreateur = (partie.getNumeroTour() % 2 == 1) ? joueur1 : joueur2;
        joueurActif = joueurCreateur;
        phaseActuelle = Phase.CREATION;

        String message = String.format(
            "Tour %d/%d - %s, créez exactement 4 formes pour que %s les reproduise",
            partie.getNumeroTour(),
            Partie.getNombreTours(),
            joueurCreateur.getNom(),
            (joueurCreateur == joueur1) ? joueur2.getNom() : joueur1.getNom()
        );

        if (modele.getVue() != null) {
            Vue vue = modele.getVue();
            vue.setStatut(message);
            vue.setValidationActive(true);
            vue.setUndoActive(true);
            vue.setRedoActive(true);
        }
    }

    /**
     * Méthode appelée quand un joueur clique sur Valider
     * Son comportement dépend de la phase actuelle :
     * - CREATION : mémorise les formes créées et passe à ATTENTE
     * - ATTENTE : ignore le clic
     * - REPRODUCTION : évalue la reproduction et passe au tour suivant
     */
    public void joueurAFini() {
        Vue vue = modele.getVue();

        switch (phaseActuelle) {
            case CREATION:
                formesOriginales = new ArrayList<>(modele.getFormes());
                if (formesOriginales.size() != 4) {
                    JOptionPane.showMessageDialog(null, "Vous devez créer exactement 4 formes !");
                    return;
                }

                phaseActuelle = Phase.ATTENTE;
                joueurActif = (joueurCreateur == joueur1) ? joueur2 : joueur1;

                if (vue != null) {
                    vue.setValidationActive(false);
                    vue.setUndoActive(false);
                    vue.setRedoActive(false);
                    vue.setStatut("Mémorisation des formes... (10 secondes)");
                }

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        modele.effacerFormes();
                        SwingUtilities.invokeLater(() -> {
                            phaseActuelle = Phase.REPRODUCTION;
                            if (vue != null) {
                                vue.setValidationActive(true);
                                vue.setStatut(joueurActif.getNom() + ", reproduisez les formes !");
                            }
                        });
                    }
                }, 10000);
                break;

            case REPRODUCTION:
                List<Forme> reproduction = new ArrayList<>(modele.getFormes());
                int score = evaluateur.evaluer(formesOriginales, reproduction);

                if (joueurActif == joueur1) {
                    partie.ajouterScore(1, score);
                } else {
                    partie.ajouterScore(2, score);
                }

                String messageScore = String.format("Tour %d - %s : %d/100",
                    partie.getNumeroTour(), joueurActif.getNom(), score);

                if (vue != null) {
                    vue.ajouterScore(messageScore);
                }

                partie.tourSuivant();
                demarrerNouveauTour();
                break;

            case ATTENTE:
                JOptionPane.showMessageDialog(null, "Veuillez attendre la fin de la phase de mémorisation");
                break;
        }
    }

    /**
     * Gère la demande de quitter la partie par l'utilisateur.
     * Affiche un résumé des scores et quitte l'application si confirmé.
     */
    public void quitterPartie() {
        int reponse = JOptionPane.showConfirmDialog(
            null,
            "Voulez-vous quitter la partie ?",
            "Confirmation",
            JOptionPane.YES_NO_OPTION
        );

        if (reponse == JOptionPane.YES_OPTION) {
            afficherResultatsFinaux();
            System.exit(0);
        }
    }

    /**
     * Affiche une boîte de dialogue avec les scores finaux des deux joueurs.
     * Indique qui est le gagnant ou s'il y a égalité.
     */
    private void afficherResultatsFinaux() {
        double moyenneJ1 = partie.getScoreMoyen(1);
        double moyenneJ2 = partie.getScoreMoyen(2);

        StringBuilder message = new StringBuilder();
        message.append("Résultats finaux\n\n");
        message.append(String.format("Joueur 1 : %.2f points\n", moyenneJ1));
        message.append(String.format("Joueur 2 : %.2f points\n\n", moyenneJ2));

        if (moyenneJ1 > moyenneJ2) {
            message.append("Joueur 1 gagne la partie !");
        } else if (moyenneJ2 > moyenneJ1) {
            message.append("Joueur 2 gagne la partie !");
        } else {
            message.append("Match nul !");
        }

        JOptionPane.showMessageDialog(null, message.toString());
    }
}