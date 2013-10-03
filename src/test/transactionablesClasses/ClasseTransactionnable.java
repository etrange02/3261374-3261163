package test.transactionablesClasses;

import fr.upmc.aladyn.transactionables.annotations.Transactionable;


@Transactionable
public class ClasseTransactionnable {

	private int x;
	private Integer y;
	
	public ClasseTransactionnable() {
		this.x = 0;
		this.y = 2;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}
	
	public int somme() {
		return this.x + this.y;
	}
	
	public String toString() {
		return "CT: x=" + x + "; y=" + y + "; somme=" + this.somme();
	}
}
