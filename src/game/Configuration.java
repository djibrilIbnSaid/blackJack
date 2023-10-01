package game;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import cartes.FactoryPaquet;
import cartes.Paquet;
import modelEcoutable.AbstractModeleEcoutable;
import players.HumanPlayer;
import players.Robot;

/**
 * Cette classe représente une configuration du blackjack
 *
 */
public class Configuration extends AbstractModeleEcoutable {
	
	private ArrayList<HumanPlayer> players;
	private Robot robot;
	private Paquet paquet;
	Scanner sc = new Scanner(System.in);
	private HumanPlayer currentPlayer=null;
	private boolean croupierWaitting = true;
	
	/**
	 * Construit une instance de la classe 
	 * @param robot represente le dealer 
	 * @param p représente le paquet
	 */
	public Configuration(Robot robot,Paquet p) {
		super();
		//this.player = player;
		this.robot = robot;
		this.players=new ArrayList<>();
		this.paquet=p;
		this.croupierWaitting = true;
	}
	
	public ArrayList<HumanPlayer> getPlayers() {
		return this.players;
	}
	
	public void setPlayers(ArrayList<HumanPlayer> players) {
		this.players = players;
	}
	
	/**
	 * Cette fonction permet de mélander le paquet
	 */
	public void shuffleCards() {
		this.paquet.shuffle();
		this.currentPlayer = this.currentPlayer != null ? this.currentPlayer:this.getPlayers().get(0);
		fireChangement();
	}
	
	/**
	 * Cette fonction permet d'initialiser le jeu
	 * C'est une configuration de base en mode console
	 */
	public void initGame() {
		
		paquet.shuffle();
		
		int nbPlayers=0;
		float betAmount=0;
		do {
			System.out.println("[========Renseignez  le nombre de joeurs svp===  [1..4] ============]");
			try {
				nbPlayers=sc.nextInt();
			}catch (InputMismatchException e) {
				nbPlayers=sc.nextInt();
			}
			
		}while(nbPlayers > 4 || nbPlayers <0 );
		
		for(int i=0 ; i < nbPlayers ; i++) {
			System.out.println("[---------Renseignez le nom du joeur ["+(i+1)+"]-------]");
			String name=sc.next();
			
			do {
				System.out.println("[--JOUEUR : "+name.toUpperCase());
				System.out.println("[----COMBIEN VOULEZ-VOUS MISER-----]");
				betAmount=sc.nextFloat();
			}while(!(betAmount > 0 && betAmount > 4 && betAmount <= 100));
			addPlayer(name, betAmount);
			System.out.println();
			System.out.println("JOUEUR :  "+name.toUpperCase()+ "  crée avec succès");
			
		}
	}
	
	public void addPlayer(String name,float bet) {
		HumanPlayer hpayer=new HumanPlayer(name, bet);
		this.players.add(hpayer);
	}
	
	/**
	 * Cette fonction permet à tout joueur dejà ajouté de parier 
	 * un montant entre 5 et 100 .
	 */
	public void betOn() {
		float betAmount;
		for(HumanPlayer player: this.players) {
			if(player.getBank() > 0 && player.getBetAmount() <= 0 ) {
				do {
					System.out.println("Joueur : "+player.getName().toUpperCase());
					System.out.println("[======Combien voulez-vous miser==========]");
					betAmount=sc.nextFloat();
					player.setBetAmount(betAmount);
				}while(!(betAmount > 0 && betAmount > 4 && betAmount <= player.getBank()));
			}
		}
		fireChangement();
	}
	
	/**
	 * Cette fonction permet à un courant de tirer une carte s'il le souhaite
	 */
	public void hitCard() {
		this.currentPlayer.drawCard(paquet);
		this.fireChangement();
	}
	
	/**
	 * Cette méthode gère la decision d'un joueur selon qu'il souaite continuer de tirer une carte 
	 * ou de passer la main au prochain suivant la valeur de sa main
	 */
	public void playerDecision() {
		for(int i=0 ;i < this.players.size() ; i++ ) {
			if(players.get(i).getBetAmount() > 0) {
				String reponse;
				do {
					System.out.println();
					System.out.println("Player : "+this.players.get(i).getName()+", valeur: "+ this.players.get(i).getPlayerHand().getHandValue() +"\nà pour main"+this.players.get(i).
							getPlayerHand().getPlayerHandCardsList());
					do {
						System.out.println("[==== Souhaitez-vous continuer tapez O Oui ou N pour Non ====]");
						reponse=sc.next();
						
					}while(!reponse.matches("[ON]"));
					
					if(reponse.matches("[O]")) {
						this.players.get(i).drawCard(paquet);
						System.out.println("Player : "+this.players.get(i).getName()+"  à pour main "+this.players.get(i).
								getPlayerHand().getPlayerHandCardsList());
						System.out.println("Nouvelle valeur de la main: "+this.players.get(i).getPlayerHand().getHandValue());
						fireChangement();
					}

				}while(!reponse.matches("[N]") && this.players.get(i).getPlayerHand().getHandValue() <=  21);
			}
		}
		
		
	}
	
	/**
	 * cette méthode permet au dealer de jouer après que tous les joueur aient pris 
	 * leurs decisions
	 */
	public void dealerTurnToPlay() {
		boolean isStillAnyPlayerIn=false;
		int i=0;
		while(i < this.players.size() && isStillAnyPlayerIn==false ) {
			if(this.players.get(i).getBetAmount() > 0 && this.players.get(i).getPlayerHand().getHandValue()<=21) {
				isStillAnyPlayerIn=true;
			}
			i++;
		}
		if(isStillAnyPlayerIn) {
			this.robot.robotPlaying(this.paquet);
			fireChangement();
		}
	}
	
	/**
	 * cette méthode determine l'etat de tous les joueurs selon qu'ils ont gagné ou perdu 
	 * apres l'annonce du resultat par le croupier
	 */
	public void getPlayerOutcomes() {
		for(int i=0 ; i < this.players.size() ;i++) {
			if(this.players.get(i).getBetAmount() > 0 ) {
				if (this.players.get(i).getPlayerHand().getHandValue() > 21) {
					System.out.println("Le joueur "+this.players.get(i).getName()+" a depassé");
					this.players.get(i).bust();
					
				}else if(this.players.get(i).getPlayerHand().getHandValue()==this.robot.getPlayerHand().getHandValue()) {
					System.out.println("Egalité avec le joueur "+this.players.get(i).getName());
					this.players.get(i).setBetAmount(0);
				}else if (this.players.get(i).getPlayerHand().getHandValue() < this.robot.getPlayerHand().getHandValue() && this.robot.getPlayerHand(). 
						getHandValue() <=  21) {
					System.out.println("Le joueur :"+this.players.get(i).getName()+" a perdu");
					this.players.get(i).loss();
				}else if (this.players.get(i).getPlayerHand().getHandValue()==21 ) {
					System.out.println("Le joueur "+this.players.get(i).getName()+"a gagné avec du blackjack");
					this.players.get(i).winWithBlackJack();
				}else {
					System.out.println("Le joueur "+this.players.get(i).getName()+"a gagné");
					this.players.get(i).win();
				}
			}
			
		}
		fireChangement();
	}
	
	/**
	 * cette méthode determine l'etat d'un joueur selon qu'il a gagné ou perdu 
	 * à l'nnonce du resultat 
	 */
	public int getSpecificPlayerState(HumanPlayer hplayer) {
		if (hplayer.getPlayerHand().getHandValue() > 21) {
			System.out.println("Le joueur "+hplayer.getName()+"a depassé");
			hplayer.bust();
			return -1;
		}else if(hplayer.getPlayerHand().getHandValue()==this.robot.getPlayerHand().getHandValue()) {
			System.out.println("Egalité avec le joueur"+hplayer.getName());
			hplayer.setBetAmount(0);
			return 0;
		}else if (hplayer.getPlayerHand().getHandValue() < this.robot.getPlayerHand().getHandValue() && this.robot.getPlayerHand(). 
				getHandValue() <=  21) {
			System.out.println("Le joueur :"+hplayer.getName()+" a perdu");
			hplayer.loss();
			return -1;
		}else if (hplayer.getPlayerHand().getHandValue()==21 ) {
			System.out.println("Le joueur "+hplayer.getName()+"a gagné avec du blackjack");
			hplayer.winWithBlackJack();
			return 1;
		}else {
			System.out.println("Le joueur "+hplayer.getName()+"a gagné");
			hplayer.win();
			return 1;
		}
	}
	
	/**
	 * Cette fonction permet de distribuer le deux prémière cartes à chacun des joueur y 
	 * compris le dealer 
	 */
	public void shareFirstTwoCard() {
		for(int i=0 ; i < 2 ; i++) {
			for( int j=0; j < this.players.size() ;j++) {
				if(this.players.get(j).getBank()  > 0 && this.players.get(j).getBetAmount() > 0) {
					this.players.get(j).drawCard(paquet);
				}
			}
			this.robot.drawCard(paquet);
		}
	}
	
	/**
	 * Cette fonction permet d'afficher la main de tous les joueur 
	 */
	public void showHands() {
		
		for(HumanPlayer hplayer:players) {
			System.out.println("La main du joueur :"+hplayer.getName().toUpperCase());
			hplayer.displayHand();
		}
		
		this.robot.displayHand();
	}
	
	/**
	 * Cette fonction permet d'afficher les deux premières carte distribuées en debut du jeu 
	 * à chacun  des joueurs
	 *  
	 */
	public void showFirstTwoHands() {
		for(HumanPlayer hplayer:players) {
			hplayer.displayHand();
		}
		this.robot.printDealerFirstHand();
	}
	
	/**
	 * Affiche la situation de la bank des joueurs et supprime celui ou ceux qui ont
	 * un compte en dessous de 0
	 */
	public void getPlayersBankState() {
		ArrayList<HumanPlayer> deletedPlayers = new ArrayList<>();
		for(HumanPlayer player: players) {
			System.out.println("===> "+player);
			if(player.getBank() > 0) {
				
				System.out.println(" COMPTE DU JOUEUR : "+player.getName().toUpperCase()+"\n dispose d'un montant de "+player.getBank()+"  dans sa bank");
			}else {
				System.out.println(" Le joueur : "+player.getName().toUpperCase()+"\n dispose d'un montant de "+player.getBank()+"  dans sa bank");
				deletedPlayers.add(player);
			}
		}
		players.removeAll(deletedPlayers);
		fireChangement();
	}
	
	public String getPlayersFeedBack(String answer) {
		do {
			System.out.println("vouslez-vous continuer O/oui et N/non de jouer ");
			answer=sc.nextLine();
		}while(!answer.matches("[ON]"));
		
		return answer;
	}
	/**
	 * Cette méthode teste si les joueurs decident de rejouer ou pas tout en 
	 * testant la possibilité de continuer le jeu  selon que les joueur dispose de l'argent dans 
	 * leurs banks respectives. 
	 * @return true ou false selon que l'on peut rejouer ou pas
	 */
	public boolean continuePlaying() {
		boolean wishToContinue=true;
		String answer;
		if(checkEndOfGame()) {
			wishToContinue=false;
		}else {
			do {
				System.out.println("voulez-vous continuer O/oui et N/non de jouer");
				answer=sc.next();
			}while(!answer.matches("[ON]"));
			if(answer.matches("[N]")) {
				wishToContinue=false;
			}
		}
		this.getPlayersBankState();
		this.clearHands();
		this.paquet.getCartes().clear();
		this.paquet = FactoryPaquet.createdCarte52();
		return wishToContinue;
	}
	
	/**
	 * Est une rédifinition par polymorphisme afin d'avoir le meme comportement en mode graphique
	 * @param feedback est un booléen 
	 * @return true ou false selon que l'on peut rejouer ou pas
	 */
	public boolean continuePlaying(boolean feedback) {
			boolean wishToContinue=true;
			if(checkEndOfGame()) {
				wishToContinue=false;
			}
			if (!feedback) {
				wishToContinue=false;
			}
			this.getPlayersBankState();
			this.clearHands();
			this.paquet.getCartes().clear();
			this.paquet = FactoryPaquet.createdCarte52();
			return wishToContinue;
	}
	
	/**
	 * Cette méthode teste si le jeu est arrivé à la fin ou pas selon qu'il y ait de joueurs
	 * restant dans la partie et disposant de montant dans leur bank leurs permattant de rejouer 
	 * ou non
	 * @return
	 */
	public boolean checkEndOfGame() {
		boolean hasToBeEnd=false;
		int compteur=0;
		for(HumanPlayer hplayer:players) {
			if(hplayer.getBank() <= 0) {
				compteur++;
			}
		}
		if(compteur == players.size()) {
			hasToBeEnd=true;
		}
		
		if(hasToBeEnd) {
			System.out.println("Tous les joueurs ont perdu");
		}
		return hasToBeEnd;
	}
	
	/**
	 * Cette fonction permet de nettoyer la main de tous les joueurs
	 */
	public void clearHands() {
		for(HumanPlayer hplayer:players) {
			hplayer.getPlayerHand().getPlayerHandCardsList().clear();
		}
		this.robot.getPlayerHand().getPlayerHandCardsList().clear();
		fireChangement();
	}
	
	/**
	 * Cette fonction permet de gérer une transition : passage de main d'un joueur à un autre 
	 * 
	 */
	public void nextPlayer() {
		int indexCurrent = this.players.indexOf(this.currentPlayer);
		if (++indexCurrent  < this.players.size() ) {
			this.currentPlayer=this.players.get(indexCurrent);
		}else {
			this.currentPlayer=null;
		}
		fireChangement();
	}

	public Robot getRobot() {
		return robot;
	}

	public Paquet getPaquet() {
		return paquet;
	}

	public HumanPlayer getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean isCroupierWaitting() {
		return croupierWaitting;
	}

	public void setCroupierWaitting(boolean croupierWaitting) {
		this.croupierWaitting = croupierWaitting;
		this.fireChangement();
	}
	
}
