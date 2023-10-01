package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game.Configuration;
import players.HumanPlayer;
import utils.ChampConfigJoueur;

/**
 * La fenetre de la configuration du jeu en interface graphique
 *
 */
public class ConfigurationGame extends JFrame {

	private static final long serialVersionUID = 1L;
	Dimension tailleEcran=Toolkit.getDefaultToolkit().getScreenSize();
    int screenLength = tailleEcran.width/2; 
    int screenHeight = tailleEcran.height/2;
    
    JFormattedTextField numberOfPlayersText;
    JPanel configurationPanel, joueursPanel, validationPanel;
    JButton bValidGame, bRemoveAllJoueur;
    boolean rejoue = false;
    
    ArrayList<ChampConfigJoueur> champConfigJoueurs = new ArrayList<>();
    
    int numberOfPlayers = 0;
    private Configuration config;
    private Map<String, Object> dataImages;
    
    /**
     * Construit l'instance de ConfigurationGame
     * @param config le model configuration 
     * @param dataImages le tableau des images (cartes)
     */
	public ConfigurationGame(Configuration config, Map<String, Object> dataImages) {
		this.setTitle("Configuration du jeu");
		this.dataImages = dataImages;
		this.config = config;
		configurationPanel = new JPanel();
		configurationPanel.setLayout(new GridLayout(1,3,5,5));
		
		joueursPanel = new JPanel();
		joueursPanel.setLayout(new GridLayout(this.numberOfPlayers,4,4,4));
		
		validationPanel = new JPanel();
		validationPanel.setLayout(new GridLayout(1,3,5,5));
		
		NumberFormat integerNumberInstance = NumberFormat.getIntegerInstance();
		numberOfPlayersText = new JFormattedTextField(integerNumberInstance);
		numberOfPlayersText.setColumns(5);
		
		JButton bValideNumber = new JButton("Valider");
		bValideNumber.addActionListener(this::valideSize);
		
		configurationPanel.add(new JLabel("Nombre de joueurs: 1-4"));
		configurationPanel.add(numberOfPlayersText);
		configurationPanel.add(bValideNumber);
		
		this.setLayout(new BorderLayout());
		this.add(configurationPanel, BorderLayout.NORTH);
		
		this.setSize(screenLength, screenHeight);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getOldPlayers();
	}
	
	/**
	 * La fonction qui recuperer les joueurs pour une nouvelle partie
	 */
	private void getOldPlayers() {
		this.setVisible(false);
		this.joueursPanel.removeAll();
		this.remove(this.joueursPanel);
		this.champConfigJoueurs.clear();
		this.numberOfPlayers = this.config.getPlayers().size();
		if (this.numberOfPlayers > 0) {
			this.rejoue = true;
			this.numberOfPlayersText.setValue(this.config.getPlayers().size());
			this.joueursPanel.setLayout(new GridLayout(this.numberOfPlayers,3,1,1));
			int i = 0;
			for (HumanPlayer player : this.config.getPlayers()) {
				System.out.println(player);
				this.champConfigJoueurs.add(
						new ChampConfigJoueur(
								new JLabel("Nom du joueur "+(i+1)+": "),
								new JTextField(player.getName()),
								new JLabel("la mise: 5-100"),
								new JFormattedTextField(NumberFormat.getIntegerInstance())));
				this.joueursPanel.add(this.champConfigJoueurs.get(i).getNameLable());
				this.joueursPanel.add(this.champConfigJoueurs.get(i).getName());
				this.joueursPanel.add(this.champConfigJoueurs.get(i).getBetLable());
				this.joueursPanel.add(this.champConfigJoueurs.get(i).getBettingAmount());
				i++;
			}
			this.add(this.joueursPanel, BorderLayout.CENTER);
			this.bRemoveAllJoueur = new JButton("Annuler");
			this.bRemoveAllJoueur.addActionListener(this::annuler);
			this.bValidGame = new JButton("Confirmer");
			this.bValidGame.addActionListener(this::lancerGame);
			this.validationPanel.removeAll();
			this.validationPanel.add(this.bRemoveAllJoueur);
			this.validationPanel.add(this.bValidGame);
			this.add(this.validationPanel, BorderLayout.SOUTH);
		}
		this.setVisible(true);
	}
	
	/**
	 * la fonction qui valide le nombre de joueurs
	 * @param arg ActionEvent
	 */
	private void valideSize(ActionEvent arg) {
		if (!this.numberOfPlayersText.getText().equals("")) {
			this.setVisible(false);
			int value = Integer.parseInt(this.numberOfPlayersText.getText());
			this.joueursPanel.removeAll();
			this.remove(this.joueursPanel);
			if (value > 0 && value <= 4) {
				this.numberOfPlayers = value;
				this.joueursPanel.setLayout(new GridLayout(this.numberOfPlayers,3,1,1));
				for (int i=0; i<this.numberOfPlayers; i++) {
					this.champConfigJoueurs.add(
							new ChampConfigJoueur(
									new JLabel("Nom du joueur "+(i+1)+": "),
									new JTextField(),
									new JLabel("la mise: 5-100"),
									new JFormattedTextField(NumberFormat.getIntegerInstance())));
					this.joueursPanel.add(this.champConfigJoueurs.get(i).getNameLable());
					this.joueursPanel.add(this.champConfigJoueurs.get(i).getName());
					this.joueursPanel.add(this.champConfigJoueurs.get(i).getBetLable());
					this.joueursPanel.add(this.champConfigJoueurs.get(i).getBettingAmount());
				}
			}
			this.add(this.joueursPanel, BorderLayout.CENTER);
			this.bRemoveAllJoueur = new JButton("Annuler");
			this.bRemoveAllJoueur.addActionListener(this::annuler);
			this.bValidGame = new JButton("Confirmer");
			this.bValidGame.addActionListener(this::lancerGame);
			this.validationPanel.removeAll();
			this.validationPanel.add(this.bRemoveAllJoueur);
			this.validationPanel.add(this.bValidGame);
			this.add(this.validationPanel, BorderLayout.SOUTH);
			this.setVisible(true);
		}
	}
	
	/**
	 * La fonction qui annulle les champs
	 * @param arg ActionEvent
	 */
	private void annuler(ActionEvent arg) {
		this.setVisible(false);
		this.numberOfPlayersText.setValue(0);
		this.joueursPanel.removeAll();
		this.remove(this.joueursPanel);
		this.validationPanel.removeAll();
		this.champConfigJoueurs.clear();
		this.setVisible(true);
	}
	
	/**
	 * Cette fonction la lance le jeu
	 * @param arg
	 */
	private void lancerGame(ActionEvent arg) {
		if (this.verifChamps()) {
			if (this.rejoue) {
				int i = 0;
				for (HumanPlayer ply : this.config.getPlayers()) {
					ply.setBetAmount(Float.parseFloat(champConfigJoueurs.get(i++).getBettingAmount().getValue().toString()));
				}
			} else {
				for (ChampConfigJoueur champConfigJoueur : champConfigJoueurs) {
					this.config.addPlayer(champConfigJoueur.getName().getText(), Float.parseFloat(champConfigJoueur.getBettingAmount().getValue().toString()));
				}
			}
			new FenGUI(this, this.dataImages, this.config, new FenHideenGUI(this.config.getPaquet(), dataImages));
		} else {
			JOptionPane.showMessageDialog(null, "Verifiez les champs contiennent les bonnes donnees");
		}
	}
	
	/**
	 * Cette fonction verifie la validite des champs
	 * @return boolean, true si les champs sont valides, false sinon
	 */
	private boolean verifChamps() {
		System.out.println(champConfigJoueurs.size());
		if (this.champConfigJoueurs.size() == 0) {
			System.out.println("tab vide");
			return false;
		} else {
			for (ChampConfigJoueur champConfigJoueur : champConfigJoueurs) {
				System.out.println(champConfigJoueur);
				if (champConfigJoueur.getName().getText() == null || champConfigJoueur.getName().getText().equals("")) {
					System.out.println("text null");
					return false;
				}
				if (champConfigJoueur.getBettingAmount().getText().equals("")) {
					System.out.println("montant vide");
					return false;
				}
				if (!champConfigJoueur.getBettingAmount().getText().matches("[0-9]+") || Integer.parseInt(champConfigJoueur.getBettingAmount().getText()) < 5 || Integer.parseInt(champConfigJoueur.getBettingAmount().getText()) > 100) {
					System.out.println("montant inf");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Un accesseur sur les images de carte
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getDataImages() {
		return dataImages;
	}
	
	

}
