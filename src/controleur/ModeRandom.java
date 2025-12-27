package controleur;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import modele.Modele;
import modele.Forme;
import modele.Cercle;
import modele.Rectangle;

/**
 * Cette classe gère le mode de jeu "Random" dans lequel des formes sont générées aléatoirement à chaque round.
 * L'objectif du joueur est de valider les formes générées avant qu'elles ne disparaissent.
 * Le niveau augmente à chaque round réussi.
 * 
 * Implémente le pattern Singleton pour garantir une seule instance du mode Random dans l'application.
 */
public class ModeRandom implements Controleur {

    /** Instance unique du Singleton */
    private static ModeRandom instance;

    private Modele modele;
    private Random rand;
    private int niveau;
    private static final int MAX_ROUNDS = 10;
    private boolean attenteValidation;
    private List<Forme> formesAttendu;

    /**
     * Constructeur privé pour empêcher l’instanciation externe (Singleton).
     */
    private ModeRandom() {
        this.rand = new Random();
        this.niveau = 1;
        this.attenteValidation = false;
    }

    /**
     * Retourne l'instance unique du mode Random.
     * Crée l'instance si elle n'existe pas encore.
     * 
     * @return L'instance unique de ModeRandom.
     */
    public static ModeRandom getInstance() {
        if (instance == null) {
            instance = new ModeRandom();
        }
        return instance;
    }

    /**
     * Démarre le mode de jeu Random en initialisant le modèle et lançant le premier round.
     * 
     * @param modele Le modèle de données du jeu.
     */
    @Override
    public void demarrer(Modele modele) {
        this.modele = modele;
        this.modele.setControleurActif(this);
        lancerRound();
    }

    /**
     * Lance un round de jeu en générant des formes aléatoires à afficher.
     * Les actions de l'utilisateur sont désactivées jusqu'à ce que les formes disparaissent.
     */
    private void lancerRound() {
        modele.effacerFormes();
        formesAttendu = new ArrayList<>();
        attenteValidation = false;

        //Désactiver les actions pendant affichage des formes
        modele.setActionsActives(false);

        int nbFormes = 4 + niveau;
        int marge = 30;
        int zone = 700;

        // Génération des formes aléatoires
        for (int i = 0; i < nbFormes; i++) {
            boolean isCircle = rand.nextBoolean();

            int size1 = rand.nextInt(Math.max(20, 100 - niveau * 10)) + 20;
            int size2 = rand.nextInt(Math.max(20, 100 - niveau * 10)) + 20;

            Forme forme;

            if (isCircle) {
                int maxX = zone - size1 - marge;
                int minX = size1 + marge;
                int x = rand.nextInt(maxX - minX) + minX;

                int maxY = zone - size1 - marge;
                int minY = size1 + marge;
                int y = rand.nextInt(maxY - minY) + minY;

                forme = new Cercle(x, y, size1);
            } else {
                int maxX = zone - size1 - marge;
                int x = rand.nextInt(maxX - marge) + marge;

                int maxY = zone - size2 - marge;
                int y = rand.nextInt(maxY - marge) + marge;

                forme = new Rectangle(x, y, size1, size2);
            }

            formesAttendu.add(forme);
            modele.ajouterForme(forme);
        }

        //Attente avant disparition des formes
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                modele.effacerFormes();
                attenteValidation = true;

                //Réactivation des actions utilisateur
                modele.setActionsActives(true);
            }
        }, Math.max(2000, 10000 - (niveau * 1000)));
    }

    /**
     * Passe au round suivant ou termine la partie si le maximum est atteint.
     */
    public void prochaineEtape() {
        if (niveau < MAX_ROUNDS) {
            niveau++;
            lancerRound();
        } else {
            modele.afficherResultatsSolo();
            JOptionPane.showMessageDialog(null, "Partie terminée !");
        }
    }

    /**
     * Permet de valider un round une fois les formes disparues.
     * Si la validation est prématurée, un message d'avertissement est affiché.
     */
    public void validerRound() {
        if (!attenteValidation) {
            JOptionPane.showMessageDialog(null, "Attends que les formes disparaissent avant de valider !");
            return;
        }

        attenteValidation = false;
        modele.validerForme(); // Appelle prochaineEtape() après traitement
    }

    /**
     * Permet à l'utilisateur de quitter la partie avec confirmation.
     * Affiche les résultats et ferme l'application si confirmé.
     */
    public void quitterPartie() {
        int reponse = JOptionPane.showConfirmDialog(
            null,
            "Voulez-vous quitter la partie ?",
            "Confirmation",
            JOptionPane.YES_NO_OPTION
        );

        if (reponse == JOptionPane.YES_OPTION) {
            modele.afficherResultatsSolo();
            System.exit(0);
        }
    }
}
