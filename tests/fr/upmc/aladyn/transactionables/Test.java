package fr.upmc.aladyn.transactionables;

public class Test { 
	protected int i ; 
	
	public Test () { 
		this.i = 0 ; 
	}


	public void m2(int i) throws Exception { // supposons une erreur d’exécution et lançons une exception 
		throw new Exception();
	}
	
	public void m1() { 
		try { 
			this.i = 10;
			System.out.println("dans m1: i = " + this.i); 
			this.m2(this.i);
		} catch( Exception e ) { // code palliant l’erreur ayant provoqué l’exception 

		} 
		System.out.println("catch de m1: i = " + this.i);
	}
	
	public void m1_transactionnee () { // copie de la valeur modifiée spéculativement 
		int ancien_i = this.i ; 
		try { 
			this.i = 10;
			System.out.println("dans m1_transactionnee : i = " + this.i);
			this.m2(this.i );
			} catch(Exception e) { // remise en palce de la valeur initiale après erreur 
				this.i = ancien_i ; // code palliant l’erreur ayant provoqué l’exception 
			} 
		System.out.println("catch m1_transactionnee : i = " + this.i);
	}

//	public static void main( String[] args )
//	{ 
//		System.out.println("Java standard: " );
//		(new Test()).m1();
//		System.out.println("version dans l’esprit transaction: ");
//		(new Test()).m1_transactionnee();
//	}
}

