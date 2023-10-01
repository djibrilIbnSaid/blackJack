package view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import cartes.Carte;
import cartes.Couleur;
import utils.ImageUtils;

/**
 * La classe qui affiche une carte
 *
 */
public class CarteView {
	protected Carte carte;
	protected boolean hidden;
	private Map<Couleur, Integer> indexCouleurs;

	public CarteView(Carte carte, boolean hidden, int height, int width) {
		this.carte = carte;
		this.hidden = hidden;
		this.indexCouleurs = new ImageUtils().getIndexCouleurs();
	}
	
	/**
	 * La fonction d'affiche d'une cache ou non, selon la variable booleen hidden
	 * @param g2 Graphics2D
	 */
	public void showCarte(Graphics2D g2) {
		if (hidden) {
			try {
				BufferedImage image = ImageIO.read(new File("assets/carteCachee.jpeg"));
				g2.drawImage(image, 500, 100, null);
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			try {
				BufferedImage image = ImageIO.read(new File("assets/cartes.jpeg"));
				int width = image.getWidth();
				int height = image.getHeight();
				BufferedImage[][] carteImage = new BufferedImage[4][13];
				for (int c = 0; c < 4; c++) {
					for (int h = 0; h < 13; h++) {
						carteImage[c][h] = image.getSubimage(h*width/13, c*height/4, width/13, height/4);
					}
				}
				g2.drawImage(carteImage[this.indexCouleurs.get(carte.getCouleur())][carte.getHauteur().getRang()-1], 500, 100, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public String toString() {
		return "CarteView [carte=" + carte + "]";
	}
	
	
}
