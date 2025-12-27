package controleur.evaluation;

import modele.Forme;
import modele.Cercle;
import modele.Rectangle;

import java.util.List;

/**
 * stratégie d'évaluation par similarité entre deux listes de formes
 * Cette classe compare une proposition à une référence en évaluant
 * la similarité des formes (type, position et taille)
 */

public class EvaluationParSimilarite implements EvaluationStrategy {

    /**
     * Évalue la similarité entre deux listes de formes : la référence (originale)
     * et la proposition (dessinée par l'utilisateur).
     *
     * @param reference   Liste de formes attendues (référence).
     * @param proposition Liste de formes dessinées par le joueur.
     * @return Un score sur 100 reflétant la qualité de la reproduction.
     */

    @Override
    public int evaluer(List<Forme> reference, List<Forme> proposition) {
        if (reference.isEmpty() || proposition.isEmpty()) return 0;

        int taille = Math.min(reference.size(), proposition.size());
        double totalScore = 0;

        for (int i = 0; i < taille; i++) {
            Forme f1 = reference.get(i);
            Forme f2 = proposition.get(i);
            double scoreForme = 0;

            if (f1.getClass() == f2.getClass()) {
                scoreForme += 30;

                if (f1 instanceof Cercle && f2 instanceof Cercle) {
                    Cercle c1 = (Cercle) f1;
                    Cercle c2 = (Cercle) f2;
                    scoreForme += calculerScorePosition(c1.getX(), c1.getY(), c2.getX(), c2.getY());
                    scoreForme += calculerScoreTaille(c1.getRayon(), c2.getRayon());
                }

                if (f1 instanceof Rectangle && f2 instanceof Rectangle) {
                    Rectangle r1 = (Rectangle) f1;
                    Rectangle r2 = (Rectangle) f2;
                    scoreForme += calculerScorePosition(r1.getX(), r1.getY(), r2.getX(), r2.getY());
                    scoreForme += calculerScoreTaille(r1.getLargeur(), r2.getLargeur());
                    scoreForme += calculerScoreTaille(r1.getHauteur(), r2.getHauteur());
                }
            }

            totalScore += scoreForme;
        }

        return (int) (totalScore / taille);
    }

    /**
     * Calcule un score en fonction de la distance entre deux points
     *
     * @param x1 Coordonnée X de la première forme
     * @param y1 Coordonnée Y de la première forme
     * @param x2 Coordonnée X de la seconde forme
     * @param y2 Coordonnée Y de la seconde forme
     * @return Un score basé sur la distance entre les deux positions (max 40)
     */

    private double calculerScorePosition(int x1, int y1, int x2, int y2) {
        double distance = Math.hypot(x1 - x2, y1 - y2);
        return Math.max(0, 40 - distance * 0.5); // au dela de 80px de distance, on a 0
    }

    /**
     * Calcule un score en fonction de la différence de taille entre deux formes
     *
     * @param t1 Taille de la forme de référence
     * @param t2 Taille de la forme proposée
     * @return Un score basé sur l'écart de taille (max 15)
     */
    private double calculerScoreTaille(int t1, int t2) {
        double diff = Math.abs(t1 - t2);
        return Math.max(0, 15 - diff * 0.5); // au dela de 30px d’ecart on a 0
    }
}
