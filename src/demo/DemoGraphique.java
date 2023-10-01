package demo;

import java.util.Map;

import cartes.FactoryPaquet;
import cartes.Paquet;
import game.Configuration;
import players.Robot;
import utils.ImageUtils;
import view.ConfigurationGame;

/**
 * La classe de demonstration graphique
 *
 */
public class DemoGraphique {
	
	public DemoGraphique() {
		this.init();
	}
	
	/**
	 * La fonction d'initialisation de la demo graphique
	 */
	public void init() {
		Paquet paquet = FactoryPaquet.createdCarte52();
		Map<String, Object> dataImages = ImageUtils.subImages();
		Robot croupier = new Robot("Croupier");
		Configuration config = new Configuration(croupier, paquet);
		new ConfigurationGame(config, dataImages);
	}
}
