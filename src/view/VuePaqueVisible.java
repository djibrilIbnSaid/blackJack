package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import cartes.Carte;
import cartes.Couleur;
import cartes.Paquet;
import modelEcoutable.EcouteurModele;
import utils.ImageUtils;

/**
 * Cette classe affiche toutes les cartes du paquet dans une fenetre
 *
 */
public class VuePaqueVisible extends VuePaquet implements EcouteurModele{
	private static final long serialVersionUID = 1L;
	private Map<Couleur, Integer> indexCouleurs;
	Map<String, Object> dataImages;

	/**
	 * Construit une instance de VuePaqueVisible
	 * @param paquet
	 * @param dataImages
	 */
	public VuePaqueVisible(Paquet paquet, Map<String, Object> dataImages) {
		super(paquet);
		this.dataImages = dataImages;
		this.indexCouleurs = new ImageUtils().getIndexCouleurs();
		this.paquet.ajoutEcouteur(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		int i = 0;
		int width = (int) this.dataImages.get("width");
		for(Carte carte: this.paquet.getCartes()) {
			BufferedImage[][] carteImage = (BufferedImage[][]) this.dataImages.get("images");
			g2.drawImage(carteImage[this.indexCouleurs.get(carte.getCouleur())][carte.getHauteur().getRang()-1], i*width/12, 0, null);
			i++;
		}
	}

	@Override
	public void modeleMisAjour(Object arg0) {
		this.repaint();
	}
}
