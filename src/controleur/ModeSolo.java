package controleur;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import modele.Modele;
import modele.Joueur;
import modele.Partie;
import modele.Forme;
import controleur.evaluation.EvaluationStrategy;
import controleur.evaluation.EvaluationParSimilarite;
import vue.Vue;
import modele.Cercle;
import modele.Modele;
import modele.Rectangle;

/**
 * Mode "classique" du jeu de mémorisation
 * Dix dessins ont été conçus avec des niveaux de difficultés variables
 */
public class ModeSolo implements Controleur {
    private Modele modele;
    private Timer timer;
    private static ModeSolo instance;
    private ModeSolo() {
        this.timer = new Timer();
    }
    
    /**
     * Ajout du patron singleton pour éviter plusieurs instance du mode de jeu
     */
    public static ModeSolo getInstance(){
        if (instance==null)
            instance = new ModeSolo();
        return instance;
    }
    /**
    * Démarre le jeu avec la méthode de l'interface Controleur
    * Créer prémièrement le bonhomme de neige vu en TP
    * 
    * @param modele le modèle du jeu
	*/
    @Override
    public void demarrer(Modele modele) {
        // Création du bonhomme de neige avec un chapeau
        this.modele = modele;
        this.modele.setControleurActif(this);
        modele.ajouterForme(new Cercle(200, 300, 100)); // Corps
        modele.ajouterForme(new Cercle(240, 180, 60)); // Tête
        modele.ajouterForme(new Rectangle(260, 160, 80, 20)); // Chapeau

        // Suppression après 10 secondes
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                modele.effacerFormes();
            }
        }, 10000);
    }
    /**
    * Affiche les neuf autres dessins du jeu pendant dix seconde
    * 
    * @param compteurForme le nombre de dessins déjà réalisés par le joueur
	*/
    public void prochaineEtape(int compteurForme) {
        // Générer la nouvelle forme après validation
        switch(compteurForme){
            case 1:
            	modele.ajouterForme(new Cercle(260, 100, 60)); // Tête
                modele.ajouterForme(new Cercle(280, 120, 10)); // Oeil Gauche
                modele.ajouterForme(new Cercle(340, 120, 10)); // Oeil droit
                modele.ajouterForme(new Rectangle(315, 150, 10, 30)); // Nez
                modele.ajouterForme(new Rectangle(300, 190, 40, 15)); // Bouche
                modele.ajouterForme(new Rectangle(260, 220, 130, 150)); // Corps
                modele.ajouterForme(new Rectangle(160, 220, 100, 25)); // Bras gauche
                modele.ajouterForme(new Rectangle(390, 220, 100, 25)); // Bras droit
                modele.ajouterForme(new Rectangle(260, 370, 40, 160)); // Jambe gauche
                modele.ajouterForme(new Rectangle(350, 370, 40, 160)); // Jambe droite
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        modele.effacerFormes();
                    }
                }, 10000);
                break;
            case 2:
            	modele.ajouterForme(new Cercle(50, 300, 100));
                modele.ajouterForme(new Cercle(250, 300, 100));
                modele.ajouterForme(new Rectangle(110, 150, 300, 150));
                modele.ajouterForme(new Rectangle(410, 150, 150, 70));
                modele.ajouterForme(new Cercle(520, 150, 20));
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        modele.effacerFormes();
                    }
                }, 10000);
                break;
            case 3:
                modele.ajouterForme(new Rectangle(150, 300, 300, 100)); // Corps
                modele.ajouterForme(new Rectangle(90, 270, 60, 60)); // Tête
                modele.ajouterForme(new Cercle(95, 270, 15)); // Oeil
                modele.ajouterForme(new Cercle(180, 400, 30)); // Patte avant gauche
                modele.ajouterForme(new Cercle(320, 400, 30)); // Patte avant droite
                modele.ajouterForme(new Cercle(220, 400, 30)); // Patte arrière gauche
                modele.ajouterForme(new Cercle(360, 400, 30)); // Patte arrière droite
                modele.ajouterForme(new Rectangle(450, 320, 40, 20)); // Queue
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        modele.effacerFormes();
                    }
                }, 10000);
                break;
            case 4:
                modele.ajouterForme(new Rectangle(150, 300, 300, 100)); // Corps
                modele.ajouterForme(new Rectangle(200, 250, 200, 50)); // Toit
                modele.ajouterForme(new Cercle(180, 400, 30)); // Roue gauche
                modele.ajouterForme(new Cercle(390, 400, 30)); // Roue droite
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        modele.effacerFormes();
                    }
                }, 10000);
                break;
            case 5:
                modele.ajouterForme(new Rectangle(150, 250, 300, 300)); // Maison 
                modele.ajouterForme(new Rectangle(180, 300, 60, 60)); // Fenêtre gauche
                modele.ajouterForme(new Rectangle(360, 300, 60, 60)); // Fenêtre droite
                modele.ajouterForme(new Rectangle(250, 420, 80, 130)); // Porte
                modele.ajouterForme(new Rectangle(330, 150, 30, 80)); // Cheminée
                modele.ajouterForme(new Rectangle(140, 230, 320, 20));
                modele.ajouterForme(new Rectangle(160, 210, 280, 20));
                modele.ajouterForme(new Rectangle(180, 190, 240, 20));
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        modele.effacerFormes();
                    }
                }, 10000);
                break;
            case 6:
                modele.ajouterForme(new Rectangle(270, 200, 60, 200)); // Corps
                modele.ajouterForme(new Cercle(270, 140, 30)); // Hublot
                modele.ajouterForme(new Rectangle(230, 250, 40, 100)); // Aile gauche
                modele.ajouterForme(new Rectangle(330, 250, 40, 100)); // Aile droite
                modele.ajouterForme(new Rectangle(270, 400, 60, 30)); // Flammes (partie basse)
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        modele.effacerFormes();
                    }
                }, 10000);
                break;
            case 7:
                modele.ajouterForme(new Cercle(220, 190, 100)); // Crâne
                modele.ajouterForme(new Cercle(270, 230, 20)); // Œil gauche
                modele.ajouterForme(new Cercle(330, 230, 20)); // Œil droit
                modele.ajouterForme(new Rectangle(185, 290, 130, 10)); // Nez
                modele.ajouterForme(new Rectangle(275, 340, 80, 20)); // Bouche
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        modele.effacerFormes();
                    }
                }, 10000);
                break;
            case 8:
                modele.ajouterForme(new Cercle(240, 250, 80)); // Corps
                modele.ajouterForme(new Cercle(260, 220, 20)); // Œil gauche
                modele.ajouterForme(new Cercle(340, 220, 20)); // Œil droit
                modele.ajouterForme(new Rectangle(315, 260, 10, 20)); // Bec
                modele.ajouterForme(new Rectangle(250, 390, 20, 30)); // Patte gauche
                modele.ajouterForme(new Rectangle(370, 390, 20, 30)); // Patte droite
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        modele.effacerFormes();
                    }
                }, 10000);
                break;
            case 9:
                modele.ajouterForme(new Rectangle(180, 300, 200, 40)); // Base
                modele.ajouterForme(new Cercle(195, 155, 80)); // Dôme
                modele.ajouterForme(new Rectangle(220, 340, 10, 30)); // Pied gauche
                modele.ajouterForme(new Rectangle(280, 340, 10, 30)); // Pied centre
                modele.ajouterForme(new Rectangle(340, 340, 10, 30)); // Pied droit
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        modele.effacerFormes();
                    }
                }, 10000);
                break;
            default:
                modele.afficherResultatsSolo();
                System.exit(0);
            }

    }
    /**
    * Lorsque le joueur clique sur le bouton "quitter"
    * On lui demande confirmation et si c'est le cas, on lui affiche son score selon son nombre de dessins réalisés
    * 
	*/
    public void quitterPartie() {
        int reponse = JOptionPane.showConfirmDialog(
            null,
            "Voulez-vous quitter la partie ?",
            "Confirmation",
            JOptionPane.YES_NO_OPTION
        );

        if (reponse == JOptionPane.YES_OPTION) {
            modele.afficherResultatsSolo();
            System.exit(0);
        }
    }

    
}