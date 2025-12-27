package controleur.commande;


/**
 * Interface représentant une commande.
 * Chaque commande encapsule une action exécutable de manière indépendante.
 * Ce pattern permet de découpler l'émetteur de l'action de son exécution.
 */
public interface Commande {
    void execute();
}
