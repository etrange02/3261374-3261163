package classes;

import fr.upmc.aladyn.transactionables.annotations.Transactionable;

@Transactionable
public abstract class AbstractPersonne {
	
	private String nom;
	private String prenom;
	
	public AbstractPersonne (String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

}
