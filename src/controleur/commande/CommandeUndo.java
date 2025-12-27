package controleur.commande;

import modele.Modele;

/**
 * Commande concrète pour l'action Undo
 * 
 * Elle encapsule l'appel à la méthode {@code undo()} du modèle,
 * permettant d'annuler la dernière action effectuée par l'utilisateur.
 * Utilise le patron de conception Command
 */
public class CommandeUndo implements Commande {
    private Modele modele;

    /**
     * Constructeur de la commande Undo
     *
     * @param modele Le modèle sur lequel exécuter l'action undo
     */
    public CommandeUndo(Modele modele) {
        this.modele = modele;
    }

    /**
     * Exécute l'action d'annulation de la dernière forme dessinée
     */
    @Override
    public void execute() {
        modele.undo();
    }
}
