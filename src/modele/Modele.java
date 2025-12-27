package modele;

import controleur.evaluation.EvaluationStrategy;
import controleur.evaluation.EvaluationParSimilarite;
import vue.Vue;
import controleur.ModeSolo;
import controleur.ModeDeuxJoueurs;
import controleur.ModeRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.*;

/**
 * Classe représentant le modèle principal de l'application
 * Ilgere la logique de stockage des formes, les actions undo/redo, 
 * l’évaluation des reproductions, et communique avec la vue
 */
public class Modele {
    private Object controleurActif;
    private List<Forme> formesProposition = new ArrayList<>();
    private EvaluationStrategy evaluateur;
    private List<Integer> tabScores = new ArrayList<>();
    private List<Forme> formes = new ArrayList<>();
    private Stack<Forme> undoStack = new Stack<>();
    private Stack<Forme> redoStack = new Stack<>();
    private List<Vue> observers = new ArrayList<>();
    private Vue vue;
    private Forme formeTemporaire;
    private ModeSolo control;
    private int compteurForme;
    private String nomStrategie = "Similarité";

    /**
     * definit le controleur actif et désactive les boutons d'action
     *
     * @param c Le contrôleur utilisé (ModeSolo, ModeDeuxJoueurs ou ModeRandom)
     */
    public void setControleurActif(Object c) {
        this.controleurActif = c;
        this.vue.setValidationActive(false);
        this.vue.setUndoActive(false);
        this.vue.setRedoActive(false);
    }

    /**
     * Active ou désactive les actions de l'utilisateur sur l'interface,
     * notamment les boutons de validation, undo, redo et création de formes (cercle et rectangle).
     * 
     * Cette méthode est utilisée notamment dans le mode de jeu Random pour empêcher
     * toute interaction pendant que les formes sont affichées à l'écran.
     * 
     * @param actives true pour activer les boutons, false pour les désactiver.
     */
    public void setActionsActives(boolean actives) {
    vue.setValidationActive(actives);
    vue.setUndoActive(actives);
    vue.setRedoActive(actives);

    vue.getBouton("Cercle").setEnabled(actives);
    vue.getBouton("Rectangle").setEnabled(actives);
    }

    /**
     * definit la stratégie d'évaluation ainsi que son nom 
     *
     * @param eval la stratégie à utiliser.
     * @param nom  le nom de la stratégie 
     */
    public void setEvaluateur(EvaluationStrategy eval, String nom) {
        this.evaluateur = eval;
        this.nomStrategie = nom;
    }

    /**
     * Retourne le nom de la stratégie d'évaluation actuellement utilisée.
     *
     * @return Le nom de la stratégie.
     */
    public String getNomStrategie() {
        return nomStrategie;
    }

    /**
     * Retourne le controleur actif
     *
     * @return Le controleur en cours
     */
    public Object getControleurActif() {
        return controleurActif;
    }

    /**
     * Définit la vue associée au modèle.
     *
     * @param vue La vue à associer.
     */
    public void setVue(Vue vue) {
        this.vue = vue;
    }

    /**
     * Retourne la vue associée.
     *
     * @return La vue courante.
     */
    public Vue getVue() {
        return vue;
    }

    /**
     * Ajoute une forme au modèle et l'empile pour pouvoir l'annuler
     *
     * @param f La forme à ajouter.
     */
    public void ajouterForme(Forme f) {
        formes.add(f);
        undoStack.push(f);
        redoStack.clear();
        notifierObservers();
    }

    /**
     * Efface les formes visibles, en les sauvegardant pour l'évaluation
     * Réactive les boutons Undo/Redo/Valider.
     */
    public void effacerFormes() {
        formesProposition = new ArrayList<>(formes);
        formes.clear();
        undoStack.clear();
        redoStack.clear();
        vue.setValidationActive(true);
        vue.setUndoActive(true);
        vue.setRedoActive(true);
        notifierObservers();
    }

    /**
     * Efface toutes les formes (originales et reproduites), utilisé après validation
     */
    public void effacerFormesAll() {
        formesProposition.clear();
        formes.clear();
        undoStack.clear();
        redoStack.clear();
        notifierObservers();
    }

    /**
     * Annule la dernière forme ajoutée 
     */
    public void undo() {
        if (!undoStack.isEmpty()) {
            Forme derniereForme = undoStack.pop();
            redoStack.push(derniereForme);
            formes.remove(derniereForme);
            notifierObservers();
        }
    }

    /**
     * Rétablit la dernière forme annulée 
     */
    public void redo() {
        if (!redoStack.isEmpty()) {
            Forme derniereForme = redoStack.pop();
            formes.add(derniereForme);
            undoStack.push(derniereForme);
            notifierObservers();
        }
    }

    /**
     * Valide la reproduction du joueur, calcule le score avec la stratégie actuelle,
     * affiche le score, nettoie les formes, et passe à l'étape suivante selon le mode.
     */
    public void validerForme() {
        if (!formes.isEmpty()) {
            int score = evaluateur.evaluer(formesProposition, formes);
            tabScores.add(score);
            effacerFormesAll();
            compteurForme++;

            String messageScore = String.format("Tour %d : %d/100", compteurForme, score);
            if (vue != null) {
                vue.ajouterScore(messageScore);
            }

            vue.setValidationActive(false);
            vue.setUndoActive(false);
            vue.setRedoActive(false);

            if (controleurActif instanceof ModeSolo) {
                ((ModeSolo) controleurActif).prochaineEtape(compteurForme);
            } else if (controleurActif instanceof ModeRandom) {
                ((ModeRandom) controleurActif).prochaineEtape();
            }
        }
    }

    /**
     * Affiche un résumé final des scores dans le mode solo
     */
    public void afficherResultatsSolo() {
        StringBuilder message = new StringBuilder();
        message.append("Résultats finaux\n\n");
        if (tabScores.isEmpty()) {
        message.append(String.format("Votre moyenne : 0 point\n"));
        }else{
        message.append(String.format("Votre moyenne : %.2f points\n",tabScores.stream().mapToInt(Integer::intValue).average().orElse(0)));
        }
        JOptionPane.showMessageDialog(null, message.toString());
    }

    /**
     * Retourne la liste actuelle des formes visibles.
     *
     * @return Liste des formes affichées.
     */
    public List<Forme> getFormes() {
        return new ArrayList<>(formes);
    }

    /**
     * Ajoute un observateur (la vue) qui sera notifié lors de modifications.
     *
     * @param v La vue à enregistrer comme observateur.
     */
    public void ajouterObserver(Vue v) {
        observers.add(v);
        this.vue = v;
    }

    /**
     * Notifie tous les observateurs pour qu'ils redessinent les formes.
     */
    private void notifierObservers() {
        for (Vue v : observers) {
            v.repaint();
        }
    }

    /**
     * Gère l'action "Quitter" selon le contrôleur actif.
     */
    public void quitterAction() {
        if (controleurActif instanceof ModeDeuxJoueurs) {
            ModeDeuxJoueurs m2j = (ModeDeuxJoueurs) controleurActif;
            m2j.quitterPartie();
        }
        else if (controleurActif instanceof ModeSolo) {
            ModeSolo ms = (ModeSolo) controleurActif;
            ms.quitterPartie();
        }
        else if (controleurActif instanceof ModeRandom) {
            ModeRandom mr = (ModeRandom) controleurActif;
            mr.quitterPartie();
        }
    }


    /**
     * Gère l'action "Valider" selon le contrôleur actif.
     */
    public void validerAction() {
        if (controleurActif instanceof ModeDeuxJoueurs) {
            ModeDeuxJoueurs m2j = (ModeDeuxJoueurs) controleurActif;
            m2j.joueurAFini();
        } else if (controleurActif instanceof ModeRandom) {
            ModeRandom mr = (ModeRandom) controleurActif;
            mr.validerRound();
        } else {
            validerForme();
        }
    }

}