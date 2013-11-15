package test.transactionables;

import java.nio.file.NoSuchFileException;

import fr.upmc.aladyn.transactionables.annotations.Transactionable;

@Transactionable
public class TrTest1 extends TrTest2{

	private int x;
	private int y;

	public TrTest1(){
		super();
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
		setX(x);
		this.chose(y);
	}

	@Transactionable
	public void chose(int y) throws NoSuchFileException {
		setX(9999);
		setY(y);
		throw new NoSuchFileException("loupé");
	}

	@Transactionable
	public void machin(){
		try{
			setY(-1000);
			setX(-1);

			setX(2/0);
		}catch(Exception e){
			System.out.println("Deja dans le catch et x = "+this.x);
		}
	}

	@Transactionable
	public void machin3(){
		try{

			setX(-2);
			setY(-2000);
			setX(2/0);
		}catch(Exception e){
			System.out.println("Deja dans le catch et x = "+this.x);
		}
	}

	@Transactionable
	public void machin2() throws Exception{
		try{
			setX(-3);
			setX(2/0);
		}catch(Exception e){
			System.out.println("Deja dans le catch et x = "+this.x);
			setX(32);
			throw new Exception();
		}
	}
	
	@Transactionable
	public void blah() throws Exception{
		setZ(43);
		throw new Exception();
	}
	
	@Transactionable
	public void glagla(TrTest1 tt, TrTest1 tt2) throws Exception{
		tt.setX(12345);
		tt2.setX(23456);
		throw new Exception();
	}


}
