package utils;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ChampConfigJoueur {
	
	private JLabel nameLable;
	private JTextField name;
	private JLabel betLable;
	private JFormattedTextField bettingAmount;
	
	public ChampConfigJoueur(JLabel nameLable, JTextField name, JLabel betLable, JFormattedTextField bettingAmount) {
		this.nameLable = nameLable;
		this.name = name;
		this.betLable = betLable;
		this.bettingAmount = bettingAmount;
	}

	public JTextField getName() {
		return name;
	}

	public JFormattedTextField getBettingAmount() {
		return bettingAmount;
	}

	public JLabel getNameLable() {
		return nameLable;
	}

	public JLabel getBetLable() {
		return betLable;
	}

	public void setNameLable(JLabel nameLable) {
		this.nameLable = nameLable;
	}

	public void setName(JTextField name) {
		this.name = name;
	}

	public void setBetLable(JLabel betLable) {
		this.betLable = betLable;
	}

	public void setBettingAmount(JFormattedTextField bettingAmount) {
		this.bettingAmount = bettingAmount;
	}

	@Override
	public String toString() {
		return "ChampConfigJoueur [name=" + name.getText() + ", bettingAmount=" + bettingAmount.getText() + "]";
	}
	
	

}
