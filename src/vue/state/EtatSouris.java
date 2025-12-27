package vue.state;

import modele.Modele;
import java.awt.event.MouseEvent;

/**
 * Classe abstraite représentant un état de la souris.
 * Sert de base pour les états comme dessin de cercle, rectangle, etc.
 */
public abstract class EtatSouris {
    protected int debutX, debutY;

    public void gererClic(MouseEvent e, Modele modele) {
        debutX = e.getX();
        debutY = e.getY();
    }

    public abstract void gererRelachement(MouseEvent e, Modele modele);
}
