package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import cartes.Paquet;


/**
 * La fenetre qui affiche le paquet
 *
 */
public class FenHideenGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	Dimension tailleEcran=Toolkit.getDefaultToolkit().getScreenSize();
    int screenLength=tailleEcran.width; 
    int screenHeight=(tailleEcran.height/5);

    /**
     * Construit une instance de FenHideenGUI
     * @param paquet le paquet des cartes
     * @param dataImages tableau des images (carte)
     */
	public FenHideenGUI(Paquet paquet, Map<String, Object> dataImages){
		this.setTitle("A ne pas montrer aux joueurs");
		VuePaqueVisible panVisible = new VuePaqueVisible(paquet, dataImages);
		panVisible.setPreferredSize(new Dimension(screenLength*10, screenHeight));
		JScrollPane scrollPane = new JScrollPane(panVisible);
		scrollPane.setPreferredSize(new Dimension(screenLength, screenHeight));
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		this.add(scrollPane);
		this.setSize(screenLength, screenHeight);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
	}
	
	
}
