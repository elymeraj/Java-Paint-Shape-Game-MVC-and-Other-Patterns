package vue.state;

import modele.Cercle;
import modele.Modele;
import vue.Vue;

import java.awt.event.MouseEvent;

/**
 * État de la souris utilisé pour dessiner un cercle.
 * Implémente le comportement lors du relâchement de la souris.
 */

public class EtatDessinCercle extends EtatSouris {

    /**
     * Gère le relâchement de la souris pour créer un cercle à partir du point initial (débutX, débutY)
     * jusqu'au point final (coordonnées de la souris au moment du relâchement).
     *
     * @param e      L'événement de la souris déclenché au relâchement
     * @param modele Le modèle contenant les formes
     */
    @Override
    public void gererRelachement(MouseEvent e, Modele modele) {
        int finX = e.getX();
        int finY = e.getY();
        int rayon = (int) Math.sqrt(Math.pow(finX - debutX, 2) + Math.pow(finY - debutY, 2)) / 2;
        if (modele.getVue() != null) {
            Vue vue = modele.getVue();
            if(vue.getValidationStatus()){
                modele.ajouterForme(new Cercle(
                    Math.min(debutX, finX),
                    Math.min(debutY, finY),
                    rayon
                ));
                }
               }
    }
}
