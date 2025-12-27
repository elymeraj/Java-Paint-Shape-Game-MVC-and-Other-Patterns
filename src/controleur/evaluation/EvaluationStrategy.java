package controleur.evaluation;

import modele.Forme;
import java.util.List;

/**
 * Interface définissant la stratégie d'évaluation utilisée
 * pour comparer une proposition de formes avec une référence.
 * 
 * Ce patron Strategy permet de choisir dynamiquement différentes
 * méthodes d'évaluation (par similarité, par précision, etc.).
 */
public interface EvaluationStrategy {

    /**
     * Évalue une proposition du joueur par rapport à une référence.
     *
     * @param reference   Liste des formes attendues (modèle à reproduire).
     * @param proposition Liste des formes dessinées par le joueur.
     * @return Un score global entre 0 et 100, représentant la fidélité de la reproduction.
     */
    int evaluer(List<Forme> reference, List<Forme> proposition);
}
