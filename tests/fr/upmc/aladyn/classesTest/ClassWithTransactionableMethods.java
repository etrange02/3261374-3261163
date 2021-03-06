package fr.upmc.aladyn.classesTest;

import fr.upmc.aladyn.transactionables.annotations.Transactionable;

/**
 * Classe avec des méthodes Transactionable pour les tests
 * @author David Lecoconnier
 * @author Allan Mottier
 * 
 */

public class ClassWithTransactionableMethods {
	
	protected ClasseTransactionnable ref;
	
	public ClassWithTransactionableMethods (ClasseTransactionnable ref) {
		this.ref = ref;
	}

	public void setReference(ClasseTransactionnable ref) {
		this.ref = ref;
	}

	@Transactionable
	public void transMethod1() {
		ref.setX(3);
		ref.setY(7);
	}
	
	@Transactionable
	public void transMethod2_callTransMethod1() {
		ref.setX(6);
		transMethod1();
	}
	
	@Transactionable
	public void transMethod3_call() {
		ref.setX(4);
		ref.setY(8);
	}
}
