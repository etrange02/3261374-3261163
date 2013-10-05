package fr.upmc.aladyn.transactionables;

import fr.upmc.aladyn.transactionables.annotations.Transactionable;

@Transactionable
public class TrTest1 {

	private int x;
	private int y;
	
	public TrTest1(){
		x=1;
		y=0;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}


}
