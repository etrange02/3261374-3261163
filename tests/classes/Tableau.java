package classes;

import fr.upmc.aladyn.transactionables.annotations.Transactionable;

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
