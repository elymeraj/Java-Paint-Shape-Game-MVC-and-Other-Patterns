package controleur.commande;
import modele.Modele;
/**
 * Commande concrète pour l'action "Quitter".
 * 
 * Elle encapsule la logique de sortie du jeu en fonction du mode actif,
 * en appelant la méthode {@code quitterAction()} du modèle
 * Utilise le patron de conception Command pour isoler les actions utilisateur
 */
public class CommandeQuitter implements Commande {
    private Modele modele;

    /**
     * Constructeur de la commande "Quitter".
     *
     * @param modele Le modèle sur lequel exécuter l'action
     */
    public CommandeQuitter(Modele modele) {
        this.modele = modele;
    }

    /**
     * Exécute l'action de quitter la partie via le modèle
     */
    @Override
    public void execute() {
        modele.quitterAction();
    }
}
