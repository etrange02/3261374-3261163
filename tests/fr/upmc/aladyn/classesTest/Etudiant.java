package fr.upmc.aladyn.classesTest;

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
