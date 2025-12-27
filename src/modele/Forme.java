package modele;

import java.awt.Graphics;

/**
 * Interface représentant une forme géométrique
 */
public interface Forme {
    /**
     * Dessine la forme sur le contexte graphique fourni.
     * @param g Le contexte graphique sur lequel dessiner
     */
    void dessiner(Graphics g);
}