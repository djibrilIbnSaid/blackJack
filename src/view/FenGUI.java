package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Configuration;
import players.HumanPlayer;


/**
 * La fenetre principale du jeu
 *
 */
public class FenGUI  extends JFrame {
	private static final long serialVersionUID = 1L;
	Dimension tailleEcran=Toolkit.getDefaultToolkit().getScreenSize();
    int screenLength = tailleEcran.width*2/3; 
    int screenHeight = (tailleEcran.height*2/2)-100;
    
    private Configuration config;
    private JFrame frameSecond;

    /**
     * Construit une instance de FenGUI
     * @param configView la fenetre de configuration du jeu
     * @param dataImages tableau des images (carte)
     * @param config le model configuration
     * @param frameSecond La fenetre qui affiche le paquet
     */
	public FenGUI(JFrame configView, Map<String, Object> dataImages, Configuration config, JFrame frameSecond){
		configView.dispose();
		this.setTitle("BlackJack");
		this.config = config;
		this.frameSecond = frameSecond;
		VuePaquetInvisible panInvisible = new VuePaquetInvisible(this, config, dataImages);
		panInvisible.setPreferredSize(new Dimension(screenLength-(screenLength/3), screenHeight-(screenHeight/3)));
		this.setLayout(new BorderLayout());
		this.add(panInvisible, BorderLayout.CENTER);
		
		JPanel gridJoueur = new JPanel();
		gridJoueur.setPreferredSize(new Dimension(screenLength/3, screenHeight/3));
		
		for (HumanPlayer joueur : this.config.getPlayers()) {
			gridJoueur.add(new JoueurJPanel(joueur, dataImages, config));
		}
		
		gridJoueur.setLayout(new GridLayout());
		this.add(gridJoueur, BorderLayout.SOUTH);
		
		this.setSize(screenLength,screenHeight);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void dispose() {
		this.frameSecond.dispose();
		super.dispose();
	}
}
