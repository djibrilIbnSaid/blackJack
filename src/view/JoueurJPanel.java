package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import cartes.Carte;
import cartes.Couleur;
import controller.JoueurController;
import game.Configuration;
import modelEcoutable.EcouteurModele;
import players.HumanPlayer;
import utils.ImageUtils;

/**
 * La classe qui affiche l'interface du joueur
 *
 */
public class JoueurJPanel extends VuePaquet implements EcouteurModele {
	private static final long serialVersionUID = 1L;
	private Rectangle2D figTirerCarte, figPasserMain;
	private HumanPlayer player;
	private Map<String, Object> dataImages;
	private Map<Couleur, Integer> indexCouleurs;
	private int paddingCards;
	private Configuration config;
	private int result = -2;
	private Graphics2D g2;
	

	/**
	 * Construit l'instance de JoueurJPanel
	 * @param player joueur
	 * @param dataImages tableau des images (carte)
	 * @param config le model configuration
	 */
	public JoueurJPanel(HumanPlayer player, Map<String, Object> dataImages, Configuration config) {
		super(config.getPaquet());
		this.player = player;
		this.dataImages = dataImages;
		this.indexCouleurs = new ImageUtils().getIndexCouleurs();
		this.paddingCards = config.getPlayers().size()*12;
		this.config = config;
		this.config.ajoutEcouteur(this);
		this.addMouseListener(new JoueurController(this));
	}

	@Override
	public void paintComponent(Graphics g) {
		this.g2 = (Graphics2D)g;
		g2.setColor(Color.WHITE);
		try {
			BufferedImage backgroundImage = ImageIO.read(new File("assets/blackjack_background.jpg"));
			g2.drawImage(backgroundImage, 0, 0, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (this.config.getPlayers().indexOf(this.player) == this.config.getPlayers().indexOf(this.config.getCurrentPlayer())) {
			g2.setFont(new Font("Roboto", Font.BOLD, 14));
			g2.drawString("Tirer une carte", 1, this.getHeight()-this.getHeight()/5);
			this.figTirerCarte = g2.getFontMetrics().getStringBounds("Tirer une carte", g2);
			this.figTirerCarte.setRect(1, (this.getHeight()-this.getHeight()/5) - g2.getFontMetrics().getAscent(), this.figTirerCarte.getWidth(), this.figTirerCarte.getHeight());
			g2.drawString("Passer la main", this.getWidth()/2, this.getHeight()-this.getHeight()/5);
			this.figPasserMain = g2.getFontMetrics().getStringBounds("Passer la main", g2);
			this.figPasserMain.setRect(this.getWidth()/2, (this.getHeight()-this.getHeight()/5) - g2.getFontMetrics().getAscent(), this.figPasserMain.getWidth(), this.figPasserMain.getHeight());
			g2.draw3DRect(0, 0, this.getWidth()-5, this.getHeight(), true);
		}
		g2.setFont(new Font("Roboto", Font.BOLD, 12));
		g2.drawString(this.player.getName()+": "+this.player.getPlayerHand().getHandValue(), 1, (this.getHeight()-this.getHeight()/50));
		g2.drawString("Mise: "+this.player.getBetAmount(), this.getWidth()/2, (this.getHeight()-this.getHeight()/50));
		g2.drawString("Bank: "+this.player.getBank(), this.getWidth()/2, (this.getHeight()-this.getHeight()/10));
		this.drawHandCards();
		this.showResult();
	}
	
	/**
	 * Cette fonction dessine les cartes du joueur
	 */
	public void drawHandCards() {
		int i = 0;
		int width = (int) this.dataImages.get("width");
		for(Carte carte: this.player.getPlayerHand().getPlayerHandCardsList()) {
			BufferedImage[][] carteImage = (BufferedImage[][]) this.dataImages.get("images");
			g2.drawImage(carteImage[this.indexCouleurs.get(carte.getCouleur())][carte.getHauteur().getRang()-1], i*width/paddingCards, 5, null);
			i++;
		}
	}
	
	/**
	 * Cette fonction affiche le resultat d'un joueur
	 */
	public void showResult() {
		g2.setFont(new Font("Roboto", Font.BOLD, 12));
		if (this.result == -1) {
			g2.setColor(Color.RED);
			g2.drawString("PERDU", this.getWidth()/2, this.getHeight()/2);
		} else if (this.result == 0) {
				g2.setColor(Color.YELLOW);
				g2.drawString("EGALITE", this.getWidth()/2, this.getHeight()/2);
		} else if (this.result == 1) {
			g2.setColor(Color.WHITE);
			g2.drawString("GAGNE", this.getWidth()/2, this.getHeight()/2);
		}
		g2.setColor(Color.WHITE);
	}

	@Override
	public void modeleMisAjour(Object arg0) {
		this.repaint();
	}

	public Map<String, Object> getDataImages() {
		return dataImages;
	}

	/**
	 * C'est un mutateur sur le tableau des images (cartes)
	 * @param dataImages Map<String, Object>
	 */
	public void setDataImages(Map<String, Object> dataImages) {
		this.dataImages = dataImages;
	}

	/**
	 * Accesseur sur la figure tirer une carte
	 * @return Rectangle2D
	 */
	public Rectangle2D getFigTirerCarte() {
		return figTirerCarte;
	}

	/**
	 * Accesseur sur la figure passer la main
	 * @return Rectangle2D
	 */
	public Rectangle2D getFigPasserMain() {
		return figPasserMain;
	}

	/**
	 * Accesseur sur le joueur
	 * @return HumanPlayer
	 */
	public HumanPlayer getPlayer() {
		return player;
	}

	/**
	 * Accesseur sur l'index de la couleur des cartes
	 * @return Map<Couleur, Integer>
	 */
	public Map<Couleur, Integer> getIndexCouleurs() {
		return indexCouleurs;
	}

	/**
	 * Accesseur de la taille de la carte afficher sur image
	 * @return int
	 */
	public int getPaddingCards() {
		return paddingCards;
	}

	/**
	 * Accesseur du model configuration
	 * @return Configuration
	 */
	public Configuration getConfig() {
		return config;
	}

	/**
	 * Accesseur sur le resultat
	 * @return int
	 */
	public int getResult() {
		return result;
	}

	/**
	 * Mutateur du resultat
	 * @param result
	 */
	public void setResult(int result) {
		this.result = result;
	}
	
	/**
	 * Cette fonctionne affiche une boite de dialogue
	 * @param message
	 */
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

}
