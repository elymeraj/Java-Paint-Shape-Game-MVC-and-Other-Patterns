package controleur;

import modele.Modele;

/**
 * Interface représentant un contrôleur de jeu.
 * 
 * Cette interface permet de définir différents comportements
 * de démarrage selon le mode de jeu 
 * Utilise le patron Strategy pour encapsuler ces variations.
 */
public interface Controleur {

    /**
     * Démarre la logique du mode de jeu correspondant
     *
     * @param modele Le modèle de l'application à connecter au contrôleur
     */
    void demarrer(Modele modele);
}
