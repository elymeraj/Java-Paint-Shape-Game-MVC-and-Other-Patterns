package controleur.evaluation;

import modele.Forme;
import modele.Cercle;
import modele.Rectangle;

import java.util.List;

/**
 * Stratégie d'évaluation stricte basée sur la précision de reproduction :
 * type de forme, position et dimensions. 
 * 
 * Cette stratégie est plus exigeante que la similarité classique :
 * - Si les types ne correspondent pas : score nul
 * - Si la position ou la taille est trop éloignée : pénalité forte
 */
public class EvaluationParPrecision implements EvaluationStrategy {

    private static final int SEUIL_DISTANCE = 40;  // seuil max pour que la position soit considérée correcte
    private static final int SEUIL_TAILLE = 15;    // seuil max pour les écarts de taille acceptables

    /**
     * Évalue la qualité d'une proposition par rapport à une référence.
     * L'évaluation est faite forme par forme (dans l'ordre), 
     * en comparant type, position et taille.
     *
     * @param reference   Liste des formes de référence (à mémoriser).
     * @param proposition Liste des formes dessinées par le joueur.
     * @return Un score global entre 0 et 100.
     */
    @Override
    public int evaluer(List<Forme> reference, List<Forme> proposition) {
        if (reference.isEmpty() || proposition.isEmpty()) return 0;

        int scoreTotal = 0;
        int nbComparables = Math.min(reference.size(), proposition.size());

        for (int i = 0; i < nbComparables; i++) {
            Forme f1 = reference.get(i);
            Forme f2 = proposition.get(i);

            // Type différent --> score nul pour cette forme
            if (!f1.getClass().equals(f2.getClass())) continue;

            int scoreForme = 0;

            if (f1 instanceof Cercle && f2 instanceof Cercle) {
		    Cercle c1 = (Cercle) f1;
		    Cercle c2 = (Cercle) f2;
		    double dist = distance(c1.getX(), c1.getY(), c2.getX(), c2.getY());
		    double diffRayon = Math.abs(c1.getRayon() - c2.getRayon());

		    if (dist < SEUIL_DISTANCE) scoreForme += 40;
		    if (diffRayon < SEUIL_TAILLE) scoreForme += 30;
		    scoreForme += 30;
		}

		if (f1 instanceof Rectangle && f2 instanceof Rectangle) {
		    Rectangle r1 = (Rectangle) f1;
		    Rectangle r2 = (Rectangle) f2;
		    double dist = distance(r1.getX(), r1.getY(), r2.getX(), r2.getY());
		    double diffL = Math.abs(r1.getLargeur() - r2.getLargeur());
		    double diffH = Math.abs(r1.getHauteur() - r2.getHauteur());

		    if (dist < SEUIL_DISTANCE) scoreForme += 30;
		    if (diffL < SEUIL_TAILLE) scoreForme += 20;
		    if (diffH < SEUIL_TAILLE) scoreForme += 20;
		    scoreForme += 30;
		}

            scoreTotal += scoreForme;
        }

        return scoreTotal / nbComparables;
    }

    /**
     * Calcule la distance euclidienne entre deux points
     *
     * @param x1 Coordonnée X du point 1.
     * @param y1 Coordonnée Y du point 1.
     * @param x2 Coordonnée X du point 2.
     * @param y2 Coordonnée Y du point 2.
     * @return Distance euclidienne.
     */
    private double distance(int x1, int y1, int x2, int y2) {
        return Math.hypot(x1 - x2, y1 - y2);
    }
}