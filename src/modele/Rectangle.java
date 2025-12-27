package modele;

import java.awt.Graphics;

/**
 * Classe représentant un rectangle
 */
public class Rectangle implements Forme {
    private int x, y, largeur, hauteur;

    /**
     * Constructeur du rectangle.
     *
     * @param x       Position X du coin supérieur gauche
     * @param y       Position Y du coin supérieur gauch
     * @param largeur Largeur du rectangle
     * @param hauteur Hauteur du rectangle
     */
    public Rectangle(int x, int y, int largeur, int hauteur) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    /**
     * Dessine le rectangle sur le contexte graphique fourni
     *
     * @param g Le contexte graphique sur lequel dessiner
     */
    @Override
    public void dessiner(Graphics g) {
        g.drawRect(x, y, largeur, hauteur);
    }

    /**
     * Retourne la position X du coin supérieur gauche.
     *
     * @return Coordonnée X.
     */
    public int getX() {
        return x;
    }

    /**
     * Retourne la position Y du coin supérieur gauche
     *
     * @return Coordonnée Y
     */
    public int getY() {
        return y;
    }

    /**
     * Retourne la largeur du rectangle
     *
     * @return Largeur.
     */
    public int getLargeur() {
        return largeur;
    }

    /**
     * Retourne la hauteur du rectangle.
     *
     * @return Hauteur.
     */
    public int getHauteur() {
        return hauteur;
    }
}