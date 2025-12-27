package vue.state;

import modele.Rectangle;
import modele.Modele;
import vue.Vue;
import java.awt.event.MouseEvent;

/**
 * État de la souris utilisé pour dessiner un rectangle.
 * Ce comportement est déclenché au relâchement de la souris.
 */
public class EtatDessinRectangle extends EtatSouris {

    /**
     * Gère le relâchement de la souris pour créer un rectangle à partir du point initial
     * (débutX, débutY) jusqu'au point final (position de la souris au relâchement).
     *
     * @param e      L'événement souris contenant la position finale
     * @param modele Le modèle dans lequel ajouter la forme
     */
    @Override
    public void gererRelachement(MouseEvent e, Modele modele) {
        int finX = e.getX();
        int finY = e.getY();
        int largeur = Math.abs(finX - debutX);
        int hauteur = Math.abs(finY - debutY);
        if (modele.getVue() != null) {
            Vue vue = modele.getVue();
            if(vue.getValidationStatus()){
                modele.ajouterForme(new Rectangle(
                    Math.min(debutX, finX),
                    Math.min(debutY, finY),
                    largeur,
                    hauteur
                ));
               }
               }
    }
}
