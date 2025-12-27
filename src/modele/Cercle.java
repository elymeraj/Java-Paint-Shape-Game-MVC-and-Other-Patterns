package modele;

import java.awt.Graphics;

/**
 * Classe représentant un cercle
 */
public class Cercle implements Forme {
    private int x, y, rayon;

    /**
     * Constructeur du cercle.
     *
     * @param x     Position X du centre du cercle
     * @param y     Position Y du centre du cercle
     * @param rayon Rayon du cercle.
     */
    public Cercle(int x, int y, int rayon) {
        this.x = x;
        this.y = y;
        this.rayon = rayon;
    }

    /**
     * Dessine le cercle sur le contexte graphique fourni
     *
     * @param g Le contexte graphique sur lequel dessiner
     */
    @Override
    public void dessiner(Graphics g) {
        g.drawOval(x, y, rayon * 2, rayon * 2);
    }

    /**
     * Retourne la coordonnée X du centre du cercle
     *
     * @return Position X
     */
    public int getX() {
        return x;
    }

    /**
     * Retourne la coordonnée Y du centre du cercle
     *
     * @return Position Y
     */
    public int getY() {
        return y;
    }

    /**
     * Retourne le rayon du cercle
     *
     * @return Rayon
     */
    public int getRayon() {
        return rayon;
    }
}