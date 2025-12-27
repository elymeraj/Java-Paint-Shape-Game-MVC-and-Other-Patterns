package vue;

import javax.swing.*;
import controleur.*;
import controleur.commande.Commande;
import modele.*;
import vue.state.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import controleur.commande.*;

/**
 * Vue principale du programme de dessin
 * Elle gère l'affichage de la zone de dessin, des boutons de commande, 
 * des messages de statut et des scores
 */
public class Vue extends JPanel {
    private Modele modele;
    private EtatSouris etatCourant;
    private JTextArea zoneScores;
    private JLabel lblStatut;
    private JButton btnValider, btnUndo, btnRedo, btnQuitter;
    private Map<String, JButton> boutons = new HashMap<>();
    private Commande commandeUndo;
    private Commande commandeRedo;
    private Commande commandeValider;
    private Commande commandeQuitter;

    private static final int ICON_SIZE = 32;

    /**
     * Constructeur principal de la Vue
     * Initialise les composants graphiques, les boutons, et les écouteurs souris
     * 
     * @param modele le modèle associé à la vue
     */
    public Vue(Modele modele) {
        this.modele = modele;
        modele.ajouterObserver(this);
        setLayout(new BorderLayout(10, 10));
        etatCourant = new EtatDessinRectangle();

        JPanel panneauBoutons = new JPanel(new GridLayout(1, 6, 10, 10));
        panneauBoutons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addButton(panneauBoutons, "Cercle", "cercle.png", () -> etatCourant = new EtatDessinCercle());
        addButton(panneauBoutons, "Rectangle", "rectangle.png", () -> etatCourant = new EtatDessinRectangle());

        btnValider = addButton(panneauBoutons, "Valider", "check.png", () -> commandeValider.execute());
        btnUndo = addButton(panneauBoutons, "Undo", "undo.png", () -> commandeUndo.execute());
        btnRedo = addButton(panneauBoutons, "Redo", "redo.png", () -> commandeRedo.execute());
        btnQuitter = addButton(panneauBoutons, "Quitter", "quit.png", () -> commandeQuitter.execute());

        lblStatut = new JLabel("", SwingConstants.CENTER);
        lblStatut.setFont(new Font("Arial", Font.BOLD, 14));
        lblStatut.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        zoneScores = new JTextArea(4, 40);
        zoneScores.setEditable(false);
        zoneScores.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollScores = new JScrollPane(zoneScores);
        scrollScores.setBorder(BorderFactory.createTitledBorder("Scores"));

        JPanel panneauNord = new JPanel(new BorderLayout());
        panneauNord.add(panneauBoutons, BorderLayout.NORTH);
        panneauNord.add(lblStatut, BorderLayout.CENTER);

        add(panneauNord, BorderLayout.NORTH);
        add(Box.createRigidArea(new Dimension(800, 600)), BorderLayout.CENTER);
        add(scrollScores, BorderLayout.SOUTH);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                etatCourant.gererClic(e, modele);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                etatCourant.gererRelachement(e, modele);
            }
        });
    }

    /**
     * Retourne le bouton correspondant au nom donné.
     * 
     * @param nom Le nom du bouton tel qu'il a été défini lors de l'ajout (ex. "Cercle", "Rectangle", "Undo", etc.).
     * @return Le bouton associé au nom, ou null si aucun bouton ne correspond.
     */
    public JButton getBouton(String nom) {
        return boutons.get(nom);
    }

    /**
     * definit la commande Undo
     * @param c La commande à associer au bouton Undo.
     */
    public void setCommandeUndo(Commande c){
        commandeUndo = c; 
    }

    /**
     * definit la commande Redo
     * @param c La commande à associer au bouton Redo
     */
    public void setCommandeRedo(Commande c) { 
        commandeRedo = c;
    }

    /**
     * definit la commande Valider
     * @param c La commande à associer au bouton Valider
     */
    public void setCommandeValider(Commande c) {
        commandeValider = c;
    }

    /**
     * definit la commande Quitter
     * @param c La commande à associer au bouton Quitter
     */
    public void setCommandeQuitter(Commande c){
        commandeQuitter = c;
    }

    /**
     * Crée et ajoute un bouton au panneau avec une icône et une action associée
     * 
     * @param panel     Le panneau où ajouter le bouton
     * @param title     Le texte du bouton
     * @param iconFile  Le nom du fichier image
     * @param action    L'action à exécuter au clic
     * @return          Le bouton créé
     */
    private JButton addButton(JPanel panel, String title, String iconFile, Runnable action) {
        JButton btn = new JButton(title, chargerIcone(iconFile));
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.addActionListener(e -> action.run());
        boutons.put(title, btn);
        panel.add(btn);
        return btn;
    }

    /**
     * Charge et redimensionne une icône depuis les ressources
     * 
     * @param nomFichier Le nom du fichier image
     * @return L'icône chargée, ou une icône vide si absente
     */
    private ImageIcon chargerIcone(String nomFichier) {
        URL location = getClass().getResource("/vue/" + nomFichier);
        if (location == null) {
            System.err.println("Image manquante : " + nomFichier);
            return new ImageIcon(); 
        }
        Image image = new ImageIcon(location).getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    /**
     * Affiche un message de statut dans l'interface
     * @param message Le message à afficher
     */
    public void setStatut(String message) {
        lblStatut.setText(message);
    }

    /**
     * Active ou désactive le bouton Valider
     * @param active true pour activer, false pour désactiver.
     */
    public void setValidationActive(boolean active) {
        btnValider.setEnabled(active);
    }

    /**
     * Active ou désactive le bouton Undo
     * @param active true pour activer, false pour désactiver
     */
    public void setUndoActive(boolean active) {
        btnUndo.setEnabled(active);
    }

    /**
     * Active ou désactive le bouton Redo
     * @param active true pour activer, false pour désactiver
     */
    public void setRedoActive(boolean active) {
        btnRedo.setEnabled(active);
    }

    /**
     * Indique si le bouton Valider est activé
     * @return true si activé, false sinon
     */
    public boolean getValidationStatus() {
        return btnValider.isEnabled();
    }

    /**
     * Ajoute un score à la zone d'affichage des scores
     * @param score Le score à ajouter
     */
    public void ajouterScore(String score) {
        zoneScores.append(score + "\n");
        zoneScores.setCaretPosition(zoneScores.getDocument().getLength());
    }

    /**
     * Redessine toutes les formes présentes dans le modèle
     * @param g Le contexte graphique sur lequel dessiner
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        for (Forme f : modele.getFormes()) {
            f.dessiner(g);
        }
    }
}
