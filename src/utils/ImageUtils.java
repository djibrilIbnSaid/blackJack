package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import cartes.Couleur;

public class ImageUtils {
	private Map<Couleur, Integer> indexCouleurs = new HashMap<>();
	
	public ImageUtils() {
		this.indexCouleurs.put(Couleur.TREFLE, 0);
		this.indexCouleurs.put(Couleur.CARREAU, 1);
		this.indexCouleurs.put(Couleur.COEUR, 2);
		this.indexCouleurs.put(Couleur.PIQUE, 3);
	}

	public Map<Couleur, Integer> getIndexCouleurs() {
		return indexCouleurs;
	}
	
	public static Map<String, Object> subImages() {
		Map<String, Object> imagesCartes = new HashMap<>();
		try {
			BufferedImage image = ImageIO.read(new File("assets/cartes.jpeg"));
			int width = image.getWidth();
			int height = image.getHeight();
			imagesCartes.put("width", width);
			imagesCartes.put("height", height);
			BufferedImage[][] carteImages = new BufferedImage[4][13];
			for (int c = 0; c < 4; c++) {
				for (int h = 0; h < 13; h++) {
					carteImages[c][h] = image.getSubimage(h*width/13, c*height/4, width/13, height/4);
				}
			}
			imagesCartes.put("images", carteImages);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imagesCartes;
	}
}
