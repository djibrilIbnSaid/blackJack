package tests;

import cartes.FactoryPaquet;
import cartes.Paquet;
import junit.framework.TestCase;
import players.HumanPlayer;
import players.Players;

public class TestPlayer  extends TestCase{
	
	
	public void testDrawCard() {
		
		Players hplayer=new HumanPlayer("djiguiné", 5);
		Paquet paquet=FactoryPaquet.createdCarte52();
		hplayer.drawCard(paquet);
		assertTrue(hplayer.getPlayerHand().getHandValue() >= 1);
		assertTrue(paquet.getCartes().size()==51);
		assertFalse("la main du joueur n'est pas vide",hplayer.getPlayerHand().getPlayerHandCardsList().size()==0);
	}
	
	
	
	
}
