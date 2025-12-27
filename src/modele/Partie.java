package modele;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton représentant une partie à deux joueurs.
 * Il conserve les scores de chaque joueur sur plusieurs tours,
 * ainsi que l'état d'avancement de la partie.
 */
public class Partie {
    private static Partie instance;

    private int numeroTour;
    private List<Integer> scoresJoueur1;
    private List<Integer> scoresJoueur2;
    private static final int NOMBRE_TOURS = 10;

    /**
     * Constructeur privé du singleton.
     * Initialise les scores des deux joueurs et le numéro de tour à 1
     */
    private Partie() {
        this.numeroTour = 1;
        this.scoresJoueur1 = new ArrayList<>();
        this.scoresJoueur2 = new ArrayList<>();
    }

    /**
     * Retourne l'unique instance de Partie 
     *
     * @return l'instance unique de la partie
     */
    public static Partie getInstance() {
        if (instance == null) {
            instance = new Partie();
        }
        return instance;
    }

    /**
     * Vérifie si la partie est terminée (tous les tours joués).
     *
     * @return true si la partie est finie, false sinon.
     */
    public boolean estTerminee() {
        return numeroTour > NOMBRE_TOURS;
    }

    /**
     * Ajoute un score au joueur spécifié.
     *
     * @param joueur Le numéro du joueur (1 ou 2).
     * @param score  Le score à ajouter.
     */
    public void ajouterScore(int joueur, int score) {
        if (joueur == 1) {
            scoresJoueur1.add(score);
        } else {
            scoresJoueur2.add(score);
        }
    }

    /**
     * Calcule le score total cumulé du joueur.
     *
     * @param joueur Le numéro du joueur (1 ou 2).
     * @return La somme de tous les scores du joueur.
     */
    public int getScoreTotal(int joueur) {
        List<Integer> scores = (joueur == 1) ? scoresJoueur1 : scoresJoueur2;
        return scores.stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Calcule la moyenne des scores du joueur.
     *
     * @param joueur Le numéro du joueur (1 ou 2).
     * @return La moyenne des scores ou 0 si aucun score.
     */
    public double getScoreMoyen(int joueur) {
        List<Integer> scores = (joueur == 1) ? scoresJoueur1 : scoresJoueur2;
        if (scores.isEmpty()) return 0;
        return scores.stream().mapToInt(Integer::intValue).average().orElse(0);
    }

    /**
     * Retourne le numéro du tour en cours.
     *
     * @return Le numéro du tour
     */
    public int getNumeroTour() {
        return numeroTour;
    }

    /**
     * Passe au tour suivant.
     */
    public void tourSuivant() {
        numeroTour++;
    }

    /**
     * Retourne le nombre total de tours prévus.
     *
     * @return Le nombre fixe de tours de la partie.
     */
    public static int getNombreTours() {
        return NOMBRE_TOURS;
    }
}
