package vue.state;

import java.awt.event.MouseEvent;
import modele.Modele;

/**
 * Classe qui gère dynamiquement l'état courant de la souris.
 */
public class GestionnaireEtat {
    private EtatSouris etatCourant;
    private final Modele modele;

    /**
     * Constructeur du gestionnaire d'états.
     * Initialise avec un état par défaut : dessin de rectangle.
     *
     * @param modele Le modèle utilisé pour l'interaction avec la vue
     */

    public GestionnaireEtat(Modele modele) {
        this.modele = modele;
        this.etatCourant = new EtatDessinRectangle(); //etat par défaut
    }

    /**
     * Définit un nouvel état pour la gestion des événements souris.
     *
     * @param nouvelEtat L'état à activer (ex: dessin cercle ou rectangle)
     */

    public void setEtat(EtatSouris nouvelEtat) {
        this.etatCourant = nouvelEtat;
    }

    /**
     * Appelé lors d’un clic souris : délègue à l’état courant
     *
     * @param e L'événement souris
     */

    public void gererClicSouris(MouseEvent e) {
        etatCourant.gererClic(e, modele);
    }

    /**
     * Appelé lors du relâchement de la souris : délègue à l’état courant
     *
     * @param e L'événement souris
     */

    public void gererRelachementSouris(MouseEvent e) {
        etatCourant.gererRelachement(e, modele);
    }
}
