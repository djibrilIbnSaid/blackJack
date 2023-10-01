package players;

import cartes.Carte;

/**
 * Cette classe represente un joueur
 *
 */
public class HumanPlayer extends Players {
	
	protected float bank;
	protected float bettingAmount;
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RESET = "\u001B[0m";
	
	/**
	 * Construit une instance de la classe 
	 * @param name représente le nom du joueur 
	 * @param betAmount représentent son pari
	 */
	public HumanPlayer(String name,float betAmount) {
		super(name);
		this.bank=100;
		this.bettingAmount=betAmount;
		
	
	}
	
	/**
	 * Accesseur sur la banque du joueur
	 * @return float
	 */
	public float getBank() {
		return this.bank;
	}
	
	/**
	 * Accesseur sur le pari du joueur
	 * @return
	 */
	public float getBetAmount() {
		return this.bettingAmount;
	}
	
	/**
	 * Mutateur sur le pari
	 * @param mtt
	 */
	public void setBetAmount(float mtt) {
		this.bettingAmount=mtt;
		
	}
	
	/**
	 * Mutateur sur la banque du joueur
	 * @param amount
	 */
	public void setBank(float amount) {
		this.bank=amount;
		
	}
	
	/**
	 * Cette méthode augmentant la bank d'un joueur ayant gagné avec blackjack
	 */
	public void winWithBlackJack() {
		this.bank+=this.bettingAmount*1.5f;
		this.bettingAmount=0;
		
	}
	
	/**
	 * Cette méthode retire le montant de parié par un joueur de sa bank  une fois qu'il a perdu
	 */
	public void loss() {
		this.bank-=this.bettingAmount;
		this.bettingAmount=0;
		
	}
	
	/**
	 * fonctionne comme loss pour des cas ou la main d'un joueur dépasse 21
	 */
	public void bust() {
		this.bank-=this.bettingAmount;
		this.bettingAmount=0;
		
	}
	
	/**
	 * cette méthode augmente le gain d'un joueur ayant gagné sans blackjack
	 */
	public void win() {
		this.bank+=this.bettingAmount;
		this.bettingAmount=0;
		
	}
	
	/**
	 * Cette méthode permet remettre à 0 la bank d'un joueur
	 */
	public void resetBank() {
		this.setBank(0);
		
	}
	
	
	@Override
	public void displayHand() {
		System.out.println("[-------------------INFORMATION SUR LA MAIN -------------]");
		System.out.println();
		System.out.println("[---DU JOUEUR : "+this.getName()+ "     -----]");
		for(Carte c: this.playerHand.getPlayerHandCardsList()) {
			System.out.println("carte ="+c.getHauteur() +" valeur="+c.getHauteur().getNbPoint());
		}
		System.out.println("LES CARTES DE SA MAIN: "+this.playerHand.getPlayerHandCardsList()+"\n");
		System.out.println(ANSI_GREEN+"[--NOMBRE DE POINTS : "+this.getPlayerHand().getHandValue()+ANSI_RESET+"--]");
		System.out.println("[----------------------------------------------------------------]");
		System.out.println();
		
	}

	@Override
	public String toString() {
		return "HumanPlayer [name: "+ this.getName()+ " bank=" + bank + ", bettingAmount=" + bettingAmount + "]";
	}
	
	

}
