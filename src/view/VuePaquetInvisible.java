package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cartes.Carte;
import cartes.Couleur;
import cartes.Paquet;
import controller.Controller;
import game.Configuration;
import modelEcoutable.EcouteurModele;
import utils.ImageUtils;
import utils.MonRectangle;

/**
 * Cette classe affiche le paquet et l'interface du croupier
 */
public class VuePaquetInvisible extends VuePaquet implements EcouteurModele{
	private static final long serialVersionUID = 1L;
	private ArrayList<MonRectangle>	listeRectangle=new ArrayList<>();
	private Rectangle2D figCarteCachee, figMelanger, figCouper, figDistribuer, figRejouer;
	private String[] options = new String[] {"5", "10", "25"};
	private Configuration config;
	private Map<String, Object> dataImages;
	private Map<Couleur, Integer> indexCouleurs;
	private JFrame frameParent;
	
	/**
	 * Construit l'instance VuePaquetInvisible
	 * @param frameParent
	 * @param config
	 * @param dataImages
	 */
	public VuePaquetInvisible(JFrame frameParent, Configuration config, Map<String, Object> dataImages) {
		super(config.getPaquet());
		this.config = config;
		this.frameParent = frameParent;
		this.setPreferredSize(new Dimension(350,300));
		this.dataImages = dataImages;
		this.indexCouleurs = new ImageUtils().getIndexCouleurs();
		this.config.ajoutEcouteur(this);
		this.addMouseListener(new Controller(this));
	}

	/**
	 * Accesseur de la liste de rectangle
	 * @return ArrayList<MonRectangle>
	 */
	public ArrayList<MonRectangle> getListeRectangle() {
		return this.listeRectangle;
	}

	/**
	 * Cette fonction ajoute un rectangle dans la liste des rectangles
	 * @param rectangle de type MonRectangle
	 */
	public void addRectangle(MonRectangle rectangle) {
		listeRectangle.add(rectangle);
	}

	/**
	 * Mutateur sur la liste de rectangle
	 * @param listeRectangle
	 */
	public void setListeRectangle(ArrayList<MonRectangle> listeRectangle) {
		this.listeRectangle = listeRectangle;
	}

	/**
	 * Cette fonction design l'interface graphe
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		BufferedImage imageCachee = null;
		int widthImage = 0, heightImage = 0;
		try {
			BufferedImage backgroundImage = ImageIO.read(new File("assets/blackjack_background.jpg"));
			imageCachee = ImageIO.read(new File("assets/carteCachee.jpeg"));
			widthImage = imageCachee.getWidth();
			heightImage = imageCachee.getHeight();
			g2.drawImage(backgroundImage, 0, 0, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int i=10;
		MonRectangle rect = null;
		for(Carte carte:this.paquet.getCartes()) {
			rect = new MonRectangle(i, i, heightImage, widthImage, carte);
			g.drawRoundRect(rect.getAbs(), rect.getOrd(), rect.getLargeur(), rect.getHauteur(), 2, 2);
			g.setColor(Color.black);
			setBackground(Color.red);
			this.addRectangle(rect);
			i++;
		}
		if (imageCachee != null && rect != null) {
			g2.drawImage(imageCachee, rect.getAbs(), rect.getOrd(), null);
			this.figCarteCachee = new Rectangle2D.Double(rect.getAbs(), rect.getOrd(), widthImage, heightImage);
		}

		g2.draw3DRect(0, 0, this.getWidth()/5, this.getHeight() + 60, true);
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Roboto", Font.BOLD, 18));
		g2.drawString("Melanger", 10, this.getHeight()/2);
		this.figMelanger = g2.getFontMetrics().getStringBounds("Melanger", g2);
		this.figMelanger.setRect(10, this.getHeight()/2 - g2.getFontMetrics().getAscent(), this.figMelanger.getWidth(), this.figMelanger.getHeight());
		g2.drawString("Couper", 10, (this.getHeight()/2)+40);
		this.figCouper = g2.getFontMetrics().getStringBounds("Couper", g2);
		this.figCouper.setRect(10, (this.getHeight()/2)+40 - g2.getFontMetrics().getAscent(), this.figCouper.getWidth(), this.figCouper.getHeight());
		g2.drawString("Distribuer", 10, (this.getHeight()/2)+80);
		this.figDistribuer = g2.getFontMetrics().getStringBounds("Distribuer", g2);
		this.figDistribuer.setRect(10, (this.getHeight()/2)+80 - g2.getFontMetrics().getAscent(), this.figDistribuer.getWidth(), this.figDistribuer.getHeight());
		g2.drawString("Rejouer", 10, (this.getHeight()/2)+120);
		this.figRejouer = g2.getFontMetrics().getStringBounds("Rejouer", g2);
		this.figRejouer.setRect(10, (this.getHeight()/2)+120 - g2.getFontMetrics().getAscent(), this.figRejouer.getWidth(), this.figRejouer.getHeight());
		
		
		this.displayCroupier(g2);
		this.displayCardCroupier(g2, imageCachee);
	}

	/**
	 * Cette fonction dessine l'interface du croupier
	 * @param g2
	 */
	private void displayCroupier(Graphics2D g2) {
		g2.setFont(new Font("Roboto", Font.BOLD, 25));
		g2.drawString("Croupier: "+this.getCardValueCroupier(this.config.isCroupierWaitting()), this.getWidth()/2, this.getHeight()/20);
		g2.setFont(new Font("Roboto", Font.BOLD, 18));
	}
	
	/**
	 * Cette fonction affiche les cartes du croupier
	 * @param g2
	 * @param imageCachee
	 */
	private void displayCardCroupier(Graphics2D g2, BufferedImage imageCachee) {
		int i = 0;
		int width = (int) this.dataImages.get("width");
		for(Carte carte: this.config.getRobot().getPlayerHand().getPlayerHandCardsList()) {
			if (i == 0 && this.config.isCroupierWaitting()) {
				g2.drawImage(imageCachee, (i*width/12)+this.getWidth()/3, this.getHeight()/3, null);
			} else {
				BufferedImage[][] carteImage = (BufferedImage[][]) this.dataImages.get("images");
				g2.drawImage(carteImage[this.indexCouleurs.get(carte.getCouleur())][carte.getHauteur().getRang()-1], (i*width/12)+this.getWidth()/3, this.getHeight()/3, null);
			}
			i++;
		}
	}
	
	/**
	 * Cette fonction renvoie la valeur total des cartes du croupier si hideen = true, sinon 
	 * renvoie le total moins la premiere carte
	 * @param hiddeen
	 * @return int
	 */
	private int getCardValueCroupier(boolean hiddeen) {
		if (hiddeen && this.config.getRobot().getPlayerHand().getPlayerHandCardsList().size() > 0) {
			return this.config.getRobot().getPlayerHand().getHandValue() - this.config.getRobot().getPlayerHand().getPlayerHandCardsList().get(0).getHauteur().getNbPoint();
		} else if (!hiddeen) {
			return this.config.getRobot().getPlayerHand().getHandValue();
		}
		return 0;
	}

	/**
	 * Accesseur sur la figure "carte cachee"
	 * @return Rectangle2D
	 */
	public Rectangle2D getFigCarteCachee() {
		return figCarteCachee;
	}

	/**
	 * Accesseur sur la figure "melanger"
	 * @return Rectangle2D
	 */
	public Rectangle2D getFigMelanger() {
		return figMelanger;
	}

	/**
	 * Accesseur sur la figure "couper"
	 * @return Rectangle2D
	 */
	public Rectangle2D getFigCouper() {
		return figCouper;
	}
	
	/**
	 * Accesseur sur la figure "distribuer"
	 * @return Rectangle2D
	 */
	public Rectangle2D getFigDistribuer() {
		return figDistribuer;
	}
	
	/**
	 * Accesseur sur les options
	 * @return String[]
	 */
	public String[] getOptions() {
		return options;
	}

	/**
	 * Accesseur sur le model configuration
	 * @return Configuration
	 */
	public Configuration getConfig() {
		return config;
	}
	
	/**
	 * Accesseur sur la figure "rejouer"
	 * @return Rectangle2D
	 */
	public Rectangle2D getFigRejouer() {
		return figRejouer;
	}
	
	/**
	 * Accesseur sur le frameParent
	 * @return
	 */
	public JFrame getFrameParent() {
		return frameParent;
	}
	
	/**
	 * Accesseur sur le tableau d'images (cartes)
	 * @return
	 */
	public Map<String, Object> getDataImages() {
		return dataImages;
	}

	/**
	 * Cette fonction affiche une fenetre de dialogue
	 * @return
	 */
	public int showDialog() {
		return JOptionPane.showOptionDialog(null, "Le nombre de cartes a coupe", "COUPER", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, this.options, this.options[0]);
	}
	
	/**
	 * Cette fonction affiche un message d'alert
	 * @param message
	 */
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	@Override
	public void modeleMisAjour(Object e) {
		this.repaint();
	}
	
}
