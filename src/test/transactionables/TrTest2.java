package test.transactionables;

import fr.upmc.aladyn.transactionables.annotations.Transactionable;

@Transactionable
public class TrTest2 {
	private int z;
	
	public TrTest2(){
		z=-1;
	}
	
	public int getZ(){
		return z;
	}
	
	public void setZ(int z){
		this.z=z;
	}
}
