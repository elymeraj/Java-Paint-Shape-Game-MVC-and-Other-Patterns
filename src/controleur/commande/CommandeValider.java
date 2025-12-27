package controleur.commande;

import modele.Modele;

/**
 * Commande concrète pour l'action Valider
 * 
 * Elle encapsule la validation des formes dessinées par l'utilisateur,
 * déclenche leur évaluation, l'affichage du score et la transition
 * vers l'étape suivante du jeu 
 * Utilise le patron de conception Command
 */
public class CommandeValider implements Commande {
    private Modele modele;

    /**
     * Constructeur de la commande Valider
     *
     * @param modele Le modèle sur lequel exécuter l'action
     */
    public CommandeValider(Modele modele) {
        this.modele = modele;
    }

    /**
     * Exécute l'action de validation via le modèle
     * Déclenche l'évaluation et la transition d'étape
     */
    @Override
    public void execute() {
        modele.validerAction();
    }
}
