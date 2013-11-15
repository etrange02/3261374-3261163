package fr.upmc.aladyn.classesTest;

import fr.upmc.aladyn.transactionables.annotations.Transactionable;

/**
 * Classe Transactionable utilisant un tableau pour les tests
 * @author David Lecoconnier
 * @author Allan Mottier
 * 
 */

@Transactionable
public class Tableau {
	private int[] tab;
	
	public Tableau(int[] tab) {
		this.tab = tab;
	}

	public int[] getTab() {
		return tab;
	}

	public void setTab(int[] tab) {
		this.tab = tab;
	}

}
