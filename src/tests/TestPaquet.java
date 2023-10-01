package tests;

import cartes.Carte;
import cartes.Couleur;
import cartes.FactoryPaquet;
import cartes.Hauteur;
import cartes.Paquet;
import junit.framework.TestCase;

public class TestPaquet  extends TestCase {
	
	public void testFactory() {
		Paquet paquet1=FactoryPaquet.createdCarte52();
		Paquet paquet2=FactoryPaquet.createdCarte32();
		paquet1.shuffle();
		assertNotSame("les deux paquets ne sont pas egale",paquet1, paquet2);
	}
	
	public void testCut() {
		Paquet paquet1=FactoryPaquet.createdCarte52();
		Paquet paquet2=FactoryPaquet.createdCarte52();
		paquet1.cut(20);
		assertNotNull("Le paquet est vide ",paquet1);
		assertTrue(paquet1.getCartes().size()!=paquet2.getCartes().size());
	}
	
	public void testGetFirst() {
		Paquet paquet1=FactoryPaquet.createdCarte52();
		Paquet paquet2=FactoryPaquet.createdCarte52();
		assertTrue("les deux première cartes sont les memes",paquet1.getFirst().getHauteur().getNbPoint( )== paquet2.getFirst().getHauteur().getNbPoint());
		assertTrue("les deux premières carte  sont plus les memes",paquet1.getFirst().getHauteur().getNbPoint( )== paquet2.getFirst().getHauteur().getNbPoint());
	}
	
	public void testGetLast() {
		Paquet paquet1=FactoryPaquet.createdCarte52();
		Paquet paquet2=FactoryPaquet.createdCarte52();
		assertTrue("les deux première cartes sont les memes",paquet1.getLast().getHauteur().getNbPoint( )== paquet2.getLast().getHauteur().getNbPoint());
		assertTrue("les deux premières carte ne sont plus les memes",paquet1.getLast().getHauteur().getNbPoint( )== paquet2.getLast().getHauteur().getNbPoint());
	}
	
	public void testGetByIndex() {
		Paquet paquet1=FactoryPaquet.createdCarte52();
		assertNotNull("retourne une carte",paquet1.getByIndex(2));
		assertNotNull("retourne une carte",paquet1.getByIndex(0));
		//assertNull("retourne une carte",paquet1.getByIndex(-2));
	}
	public void testAddCarte() {
		Paquet p=FactoryPaquet.createdCarte52();
		Carte c = p.getFirst();
		assertTrue("Le paquet contient bien une carte ", p.getCartes().size()==51);
		Carte carte=new Carte(Hauteur.DIX, Couleur.CARREAU);
		p.addCarte(carte);
		assertTrue("Le paquet contient bien une carte ", p.getCartes().size()==52);
	}
	
	
}
