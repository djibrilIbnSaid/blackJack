package players;

import java.util.ArrayList;

import cartes.Carte;

import cartes.Paquet;


/**
 * Cette classe represente la main d'un joueur
 *
 */
public class Hand {
	
	private ArrayList<Carte> playersHandCards;

	/**
	 * Construit une instance de la classe 
	 */
	public Hand() {
		this.playersHandCards = new ArrayList<>();
	}
	
	/**
	 * Cette fonction renvoie la liste des cartes d'une main
	 * @return ArrayList<Carte>
	 */
	public ArrayList<Carte> getPlayerHandCardsList() {
		return playersHandCards; 
	}

	/**
	 * Cette focntion permet d'ajouter une carte à la main d'un joueur
	 * @param paquet est le nom du paquet
	 * @return Carte  une carte celle tirée par le joueur 
	 */
	public Carte addCardToHand(Paquet paquet) {
		Carte carte=paquet.getFirst();
		playersHandCards.add(carte);
		return carte;
	}
	
	/**
	 * Cette fonction permet d'évalue la main d'un joueur courant 
	 * @return int représentant la valeur de la main de ce joueur
	 */
	public int getHandValue() {
		int handValue=0;
		int nombreDeAs=0;
		
		for(Carte eachCard: playersHandCards) {
			handValue+=eachCard.getHauteur().getNbPoint();
			if( eachCard.getHauteur().getRang()==1) {
				nombreDeAs++;
			}
		}
		while( handValue > 21  && nombreDeAs > 0 ) {
			handValue-=10;
			nombreDeAs--;
		}
		return handValue;
	}

	
	@Override
	public String toString() {
		return "Hand [listOfCard=" + playersHandCards + "\n]"+"handValue ="+getHandValue();
	}
	
	
}
