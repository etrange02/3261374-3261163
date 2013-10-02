package test.transactionablesClasses;

import fr.upmc.aladyn.transactionables.annotations.Transactionable;

@Transactionable
public class ConstructeurSans0Params {

	private String chaine;
	
	public ConstructeurSans0Params(String message) {
		this.chaine = message;
	}
	
	public String getChaine() {
		return chaine;
	}

	public void setChaine(String chaine) {
		this.chaine = chaine;
	}

	public String toString() {
		return this.chaine;
	}
}
