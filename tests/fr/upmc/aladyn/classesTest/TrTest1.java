package fr.upmc.aladyn.classesTest;

import java.nio.file.NoSuchFileException;

import fr.upmc.aladyn.transactionables.annotations.Transactionable;

/**
 * Classe Transactionable héritant d'une classe Transactionable avec 
 * plusieurs méthodes Transactionables de plusieurs type pour les tests
 * @author David Lecoconnier
 * @author Allan Mottier
 * 
 */

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
	public void methodeTransacAvecSousMethodEtException(int x,int y) throws NoSuchFileException{
		setX(x);
		this.sousMethodeTransacAvecException(y);
	}

	@Transactionable
	public void sousMethodeTransacAvecException(int y) throws NoSuchFileException {
		setX(9999);
		setY(y);
		throw new NoSuchFileException("loupé");
	}

	@Transactionable
	public void plusieurSetDansUneTransacAvecExceptionCatchee(int x,int y){
		try{
			setX(x);
			setY(y);

			setX(2/0);
		}catch(Exception e){
			System.out.println("plusieurSetDansUneTransacAvecExceptionCatchee exception catchée et pas re-throw");
		}
	}

	@Transactionable
	public void plusieurSetDansUneTransacAvecExceptionCatcheeEtReThrow(int x) throws Exception{
		try{
			setX(x);
			setX(2/0);
		}catch(Exception e){
			System.out.println("plusieurSetDansUneTransacAvecExceptionCatcheeEtReThrow exception catchée et re-throw");
			setX(32);
			throw new Exception();
		}
	}
	
	@Transactionable
	public void appelSetDeLaClasseMereAvecException(int z) throws Exception{
		setZ(z);
		throw new Exception();
	}
	
	@Transactionable
	public void appelSetDeDeuxObjetDeLaMemeClasseAvecException(TrTest1 tt, TrTest1 tt2) throws Exception{
		tt.setX(12345);
		tt2.setX(23456);
		throw new Exception();
	}

}
