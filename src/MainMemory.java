import javax.swing.*;
import java.awt.*;
import controleur.Controleur;
import controleur.ModeDeuxJoueurs;
import controleur.ModeSolo;
import controleur.commande.CommandeRedo;
import controleur.commande.CommandeUndo;
import controleur.commande.CommandeValider;
import controleur.commande.CommandeQuitter;
import controleur.evaluation.EvaluationStrategy;
import controleur.evaluation.EvaluationParSimilarite;
import controleur.evaluation.EvaluationParPrecision;
import controleur.ModeRandom;
import modele.Modele;
import vue.Vue;

/**
 * Classe principale du jeu de mémorisation
 * Elle initialise l'application, demande à l'utilisateur de choisir le mode de jeu
 * et la stratégie d'évaluation, configure le modèle, la vue et le controleur.
 */
public class MainMemory {

    /**
     * Méthode principale lançant l'application graphique.
     * Elle affiche deux menus :
     * - un pour choisir le mode de jeu 
     * - un pour choisir la stratégie d'évaluation 
     *
     * En plus  elle crée les composants MVC (Modele, Vue, Controleur) et lance le jeu
     */
    public static void main(String[] args) {
        //affichage du menu
        String[] options = {"Solo - Formes enregistrées", "Deux Joueurs", "Solo - Formes Aléatoires"};
        int choix = JOptionPane.showOptionDialog(
            null,
            "Choisissez un mode de jeu",
            "Sélection du mode",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        // on choisit la stratégie d'évaluation
        String[] optionsEval = {"Évaluation par Similarité", "Évaluation par Précision"};
        int choixEval = JOptionPane.showOptionDialog(
            null,
            "Choisissez la stratégie d'évaluation",
            "Stratégie",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            optionsEval,
            optionsEval[0]
        );

        EvaluationStrategy strategie;
        if (choixEval == 1) {
            strategie = new EvaluationParPrecision();
        } else {
            strategie = new EvaluationParSimilarite();
        }

        // Initialisation du modèle et de la vue
        Modele modele = new Modele();
        modele.setEvaluateur(strategie, (choixEval == 1) ? "Précision" : "Similarité");

        Vue vue = new Vue(modele);
        vue.setCommandeUndo(new CommandeUndo(modele));
        vue.setCommandeRedo(new CommandeRedo(modele));
        vue.setCommandeValider(new CommandeValider(modele));
        vue.setCommandeQuitter(new CommandeQuitter(modele));

        JFrame frame = new JFrame("Jeu de Mémorisation");
        frame.add(vue);
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //choix du controleur avec le patron Strategy
        Controleur gameMode;
        switch (choix) {
            case 1:
                gameMode = ModeDeuxJoueurs.getInstance();
                break;
            case 0:
                gameMode = ModeSolo.getInstance();
                break;
            default:
                gameMode = ModeRandom.getInstance();
                break;
        }

        gameMode.demarrer(modele);
    }
}