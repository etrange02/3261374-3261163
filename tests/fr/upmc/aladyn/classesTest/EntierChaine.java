package fr.upmc.aladyn.classesTest;

/**
 * Classe pour les tests
 * @author David Lecoconnier
 * @author Allan Mottier
 * 
 */

public class EntierChaine {
	private int entier;
	private String chaine;
	
	public EntierChaine(int entier, String chaine) {
		this.entier = entier;
		this.chaine = chaine;
	}
	
	public int getEntier() {
		return entier;
	}
	public void setEntier(int entier) {
		this.entier = entier;
	}
	public String getChaine() {
		return chaine;
	}
	public void setChaine(String chaine) {
		this.chaine = chaine;
	}
}
