package fr.upmc.aladyn.classesTest;

/**
 * Classe héritant d'une classe Transactionable pour les tests
 * @author David Lecoconnier
 * @author Allan Mottier
 * 
 */

public class Etudiant extends AbstractPersonne {
	
	private int numeroEtu;
	
	public int getNumeroEtu() {
		return numeroEtu;
	}

	public void setNumeroEtu(int numeroEtu) {
		this.numeroEtu = numeroEtu;
	}

	public Etudiant (String nom, String prenom, int num) {
		super(nom, prenom);
		this.numeroEtu = num;
	}
	
	public Etudiant() {
		this("", "", 0);
	}
}
