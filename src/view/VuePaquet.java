package view;

import java.awt.Graphics;

import javax.swing.JPanel;

import cartes.Paquet;

/**
 * cette classe permet de definir des vues utilisqnt le meme paquet selon qu,il soit visible ou pas
 *
 */
public abstract class VuePaquet extends JPanel {
	private static final long serialVersionUID = -1400530306642417963L;
	protected Paquet paquet;
	
	public VuePaquet(Paquet paquet) {
		this.paquet = paquet;
	}
	
	/**
	 * Accesseur sur le paquet
	 * @return Paquet
	 */
	public Paquet getPaquet() {
		return paquet;
	}
	
	/**
	 * Cette fonction dessine le comportement de l'interface graphique
	 */
	public abstract void paintComponent(Graphics g);

}
