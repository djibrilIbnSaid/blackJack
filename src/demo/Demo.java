package demo;

import java.util.Scanner;

/**
 * La classe principale du projet
 *
 */
public class Demo {

	/**
	 * La fonction d'entree du projet
	 */
	public static void main(String[] args) {
		try (Scanner scan = new Scanner(System.in)) {
			System.out.println("+---------------- MENU -------------+");
			System.out.println("+                                   +");
			System.out.println("+ 1 - pour jouer en mode graphique  +");
			System.out.println("+-----------------------------------+");
			System.out.println("+ 2 - pour jouer en mode console    +");
			System.out.println("+-----------------------------------+");
			System.out.println("+ 3 - pour quitter                  +");
			System.out.println("+-----------------------------------+");

			String key = "";
			
			do {
				System.out.println("Veuillez entrer votre choix(1..3)  ");
				key = scan.nextLine();
			} while(!key.equals("1") && !key.equals("2") && !key.equals("3"));
			
			switch (key) {
				case "1": {
					new DemoGraphique();
					break;
				}
				case "2": {
					new DemoConsole();
					break;
				}
				default:
					System.out.println("Bye Bye!");
					break;
			}
		}
	}

}
