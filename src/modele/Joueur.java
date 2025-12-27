package modele;

import java.util.ArrayList;
import java.util.List;


/**
 * Classe représentant un joueur dans le jeu
 * Chaque joueur possède un nom et une liste de formes qu’il a dessinées
 */
public class Joueur {
    private String nom;
    private List<Forme> formesDessinees = new ArrayList<>();

    /**
     * Constructeur du joueur
     * 
     * @param nom Le nom du joueur
     */
    public Joueur(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne le nom du joueur
     * 
     * @return Le nom.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne la liste des formes dessinées par le joueur
     * 
     * @return Liste des formes
     */
    public List<Forme> getFormesDessinees() {
        return formesDessinees;
    }

    /**
     * Ajoute une forme à la liste des formes dessinées par le joueur
     * 
     * @param f La forme à ajouter
     */
    public void ajouterForme(Forme f) {
        formesDessinees.add(f);
    }

    /**
     * Réinitialise la liste des formes dessinees 
     */
    public void reset() {
        formesDessinees.clear();
    }
}
