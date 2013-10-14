package test.transactionables;

import java.nio.file.NoSuchFileException;

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
	
	@Transactionable
	public void bidule(int x,int y) throws NoSuchFileException{
		this.x=x;
		this.chose(y);
	}
	
	@Transactionable
	public void chose(int y) throws NoSuchFileException {
		this.y = y;
		throw new NoSuchFileException("loupé");
	}
	
	@Transactionable
	public void machin(){
		try{
			this.x=-1;
			this.x=2/0;
	}catch(Exception e){
		System.out.println("Deja dans le catch et x = "+this.x);
		this.x=0;
	}
	}
	
	@Transactionable
	public void machin2() throws Exception{
		try{
			this.x=-1;
			this.x=2/0;
	}catch(Exception e){
		System.out.println("Deja dans le catch et x = "+this.x);
		this.x=32;
		throw new Exception();
	}
	}


}
