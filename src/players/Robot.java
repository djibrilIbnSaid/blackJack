package players;

import cartes.Carte;
import cartes.Paquet;

/**
 * Cette classe represente le croupier
 *
 */
public class Robot extends Players {

	/**
	 * construit une instance de la classe 
	 * @param name est le nom du dealer
	 */
	public Robot(String name) {
		super(name);
	
	}
	
	@Override
	public void displayHand() {
		System.out.println("[-----------------LA MAIN DU DEALER-----------]");
		for (Carte c : playerHand.getPlayerHandCardsList()) {
				System.out.println(c.toString());
		}
		System.out.println("LE NOMBRE DE POINTS :"+this.playerHand.getHandValue());
		System.out.println("[-----------------------------------------------------]");
	}	
	
	/**
	 * Cette méthode affiche les deux prémières carte d'un joueur tout en cachant la prémière
	 */
	public void printDealerFirstHand() {
		int i=0;
		System.out.println("[----LA MAIN DU DEALER----------]");
		for (Carte c : playerHand.getPlayerHandCardsList()) {
			if(i==0) {
				System.out.println("X");
				//System.out.println(this.playerHand.getPlayerHandCardsList().toString());
			}else {
				System.out.println(this.playerHand.getPlayerHandCardsList().get(i));
			}
			i++;
		}
	}
	
	/**
	 * Cette fonction permet au dealer du joueur a son tour 
	 * @param paquet est le nom du paquet
	 */
	public void robotPlaying(Paquet paquet) {
		while(this.getPlayerHand().getHandValue()<= 16) {
			this.drawCard(paquet);
			this.displayHand();
		}
		if(this.getPlayerHand().getHandValue() > 21) {
			this.displayHand();
			System.out.println("Le Dealer a depassé "+this.getPlayerHand().getHandValue());
		}else {
			System.out.println("[=====Le dealer s'est arrété =====]");
		}
		
	}
}
