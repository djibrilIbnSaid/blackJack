package demo;




import cartes.FactoryPaquet;
import game.Configuration;
import players.Robot;

/**
 * La classe de la demonstration console
 *
 */
public class DemoConsole {
	
	public DemoConsole() {
		this.init();
	}

	/**
	 * La fonction d'initialisation de la demo console
	 */
	public void init() {
		Robot robot=new Robot("Dealer");  
		Configuration config=new Configuration(robot, FactoryPaquet.createdCarte52());
		config.initGame();
		config.shuffleCards();
		do {
			config.betOn();
			config.getPlayersBankState();
			config.shareFirstTwoCard();
			config.showFirstTwoHands();
			config.playerDecision();
			System.out.println("[============Les mains======================]");
			config.dealerTurnToPlay();
			config.showHands();
			config.getPlayerOutcomes();
			config.clearHands();
		}while(config.continuePlaying());
	}

}
