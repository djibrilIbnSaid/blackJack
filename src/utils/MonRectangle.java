package utils;

import cartes.Carte;

/**
 * 
 *
 */
public class MonRectangle {
	
	private int abs,ord,hauteur,largeur;
	private Carte carte;
	public MonRectangle(int abs, int ord, int hauteur, int largeur,Carte carte) {
		super();
		this.abs = abs;
		this.ord = ord;
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.carte=carte;
	} 

	public int getAbs() {
		return abs;
	}

	public int getOrd() {
		return ord;
	}

	public int getHauteur() {
		return hauteur;
	}

	public int getLargeur() {
		return largeur;
	}

	public Carte getCarte() {
		return carte;
	}

	public void setCarte(Carte carte) {
		this.carte = carte;
	}

	@Override
	public String toString() {
		return "MonRectangle [abs=" + abs + ", ord=" + ord + ", hauteur=" + hauteur + ", largeur=" + largeur
				+ ", carte=" + carte + "]";
	}
	
	
	/*public void drawRect(Graphics g) {
		g.drawRoundRect(abs, ord, largeur, hauteur,20,20);
		
	}*/
	
	
}
