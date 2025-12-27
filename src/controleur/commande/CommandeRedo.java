package controleur.commande;
import modele.Modele;

/**
 * Commande concrète pour l'action Redo
 * 
 * Elle encapsule l'appel à la méthode {@code redo()} du modèle,
 * permettant de réappliquer la dernière action qui avait été annulée.
 * Implémente le patron de conception Command.
 */
public class CommandeRedo implements Commande {
    private Modele modele;

    /**
     * Constructeur de la commande Redo
     *
     * @param modele Le modèle sur lequel exécuter l'action redo.
     */
    public CommandeRedo(Modele modele) {
        this.modele = modele;
    }

    /**
     * Exécute l'action de rétablissement d'une forme précédemment annulée
     */
    @Override
    public void execute() {
        modele.redo();
    }
}
