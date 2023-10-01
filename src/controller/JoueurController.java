package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import players.HumanPlayer;
import view.JoueurJPanel;

/**
 * Cette classe controle les evenements de la classe JoueurJPanel
 *
 */
public class JoueurController implements MouseListener {
	private JoueurJPanel joueurJPanel;
	
	/**
	 * Construit l'instance de la classe
	 * @param joueurJPanel
	 */
	public JoueurController(JoueurJPanel joueurJPanel) {
		this.joueurJPanel = joueurJPanel;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (this.joueurJPanel.getPlayer().equals(this.joueurJPanel.getConfig().getCurrentPlayer())) {
			if (this.joueurJPanel.getFigPasserMain().contains(e.getPoint())) {
				this.skip();
			} else if (this.joueurJPanel.getFigTirerCarte().contains(e.getPoint())) {
				this.joueurJPanel.getConfig().hitCard();
				if (this.joueurJPanel.getConfig().getCurrentPlayer().getPlayerHand().getHandValue() > 21) {
					this.joueurJPanel.setResult(-1);
					this.skip();
				}
			}
		}
	}
	
	/**
	 * Cette methode passe la main d'un joueur a un autre 
	 */
	public void skip() {
		this.joueurJPanel.getConfig().nextPlayer();
		if (this.joueurJPanel.getConfig().getCurrentPlayer() == null) {
			this.joueurJPanel.getConfig().dealerTurnToPlay();
			this.joueurJPanel.getConfig().setCroupierWaitting(false);
			String message = "";
			for (HumanPlayer ply : this.joueurJPanel.getConfig().getPlayers()) {
				if (this.joueurJPanel.getConfig().getSpecificPlayerState(ply) == 1) {
					message += ply.getName() + " a gagne \n";
				} else if (this.joueurJPanel.getConfig().getSpecificPlayerState(ply) == 0) {
					message += ply.getName() + " a rembourse \n";
				} else if (this.joueurJPanel.getConfig().getSpecificPlayerState(ply) == -1) {
					message += ply.getName() + " a perdu \n";
				}
			} 
			this.joueurJPanel.showMessage(message);
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
