package players;

import cartes.Paquet;

public abstract class Players {
	
	protected String name;
	protected Hand playerHand;
	
	/**
	 * Construit une instance de personne 
	 * @param name représente le nom du joueur 
	 * @param playerHand represente la main du joueur 
	 */
	public Players(String name) {
		super();
		this.name = name;
		this.playerHand = new Hand();
	}
	
	/**
	 * Est un acesseur au nom du joueur 
	 * @return une chaine qui est le nom diu joueur
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Est un accesseur sur la main du joueur .On va s'en servir afin de pouvoir evaluer 
	 * à tout de role la valeur de la main d'un joueur
	 * @return
	 */
	public Hand getPlayerHand() {
		return playerHand;
	}
	
	/**
	 * Est un mutateur du nom joueur 
	 * @param name est le nouvel nom du joueur
	 */
	public void setName(String name) {
		this.name = name; 
	}
	
	/**
	 * Cette méthode permet à chacun des joueur tour a tour de tirer une carte
	 * en se servant de la methode addCardToHand(ajouter une carte a la main courante)
	 * @param paquet est le nom du paquet dans lequel la carte va etre tirée
	 * @return
	 */
	public  void drawCard(Paquet paquet) {
		this.getPlayerHand().addCardToHand(paquet);
	}
	
	public abstract void displayHand();
	
}
