package fr.upmc.aladyn.classesTest;

import fr.upmc.aladyn.transactionables.annotations.Transactionable;

/**
 * Classe Transactionable utilisant des références pour les tests
 * @author David Lecoconnier
 * @author Allan Mottier
 * 
 */

@Transactionable
public class Reference {
	private Object o;
	
	public Reference (Object o) {
		this.o = o;
	}

	public Object getO() {
		return o;
	}

	public void setO(Object o) {
		this.o = o;
	}
}
