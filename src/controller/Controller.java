package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.ConfigurationGame;
import view.VuePaquetInvisible;

/**
 * Cette classe controle les evenements de la classe VuePaquetInvisible
 *
 */
public class Controller implements MouseListener {
	private VuePaquetInvisible vueDuJeu;
	
	/**
	 * Construit l'instance de la classe
	 * @param vueDuJeu
	 */
	public Controller(VuePaquetInvisible vueDuJeu) {
		this.vueDuJeu = vueDuJeu;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (this.vueDuJeu.getFigCarteCachee().contains(e.getPoint())) {
			if (this.vueDuJeu.getPaquet().getCartes().size() > 0) {
				this.vueDuJeu.getPaquet().getFirst();
			}
		} else if (this.vueDuJeu.getFigMelanger().contains(e.getPoint())) {
			this.vueDuJeu.getPaquet().shuffle();
		} else if (this.vueDuJeu.getFigCouper().contains(e.getPoint())) {
			int result = this.vueDuJeu.showDialog();
			if (result > 0) {
				int quantite = Integer.parseInt(this.vueDuJeu.getOptions()[result]);
				this.vueDuJeu.getPaquet().cut(quantite);
			}
		} else if (this.vueDuJeu.getFigDistribuer().contains(e.getPoint())) {
			this.vueDuJeu.getConfig().setCroupierWaitting(true);
			this.vueDuJeu.getConfig().shuffleCards();
			this.vueDuJeu.getConfig().shareFirstTwoCard();
		} else if (this.vueDuJeu.getFigRejouer().contains(e.getPoint())) {
			if (this.vueDuJeu.getConfig().continuePlaying(true)) {
				this.vueDuJeu.getFrameParent().dispose();
				new ConfigurationGame(this.vueDuJeu.getConfig(), this.vueDuJeu.getDataImages());
			} else {
				this.vueDuJeu.showMessage("Game over! il n'y a plus de joueurs qui a un montant dans sa banque");
				this.vueDuJeu.getFrameParent().dispose();
			}
		}
	}

	

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
