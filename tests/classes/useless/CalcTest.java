package classes.useless;

import fr.upmc.aladyn.transactionables.annotations.Transactionable;

public class CalcTest {
	private boolean first;

	public CalcTest() {
		first = true;
	}
	
	/**
	 * Somme par d�faut de deux entiers
	 * @param i1
	 * @param i2
	 * @return i1 + i2
	 */
	@Transactionable
	public Integer somme_default(Integer i1, Integer i2) {
		return i1 + i2;
	}
	
	/**
	 * Somme de deux entiers.
	 * @param i1
	 * @param i2
	 * @return i1 + i2
	 */
	@Transactionable
	public Integer somme_withUselessBody(Integer i1, Integer i2) {
		ClasseTransactionnable ct = new ClasseTransactionnable();
		ct.setX(0);
		ct.setY(5);
		ct.somme();

		System.out.println("i1=" + i1);
		System.out.println("i2=" + i2);
		System.out.println(ct.toString());
		
		return i1 + i2;
	}
	
	/**
	 * Renvoie la somme de ct et z. ct est mis � 0.
	 * @param ct
	 * @param z
	 * @return la valeur de z.
	 */
	@Transactionable
	public Integer somme_withTransacParam(ClasseTransactionnable ct, int z) {
		System.out.println("origin: " + ct.toString());
		ct.setX(0);
		ct.setY(0);
		System.out.println("modify: " + ct.toString());
		
		Integer sum = new Integer(ct.somme() + z);
		System.out.println("z=" + z + " (valeur de la somme)");
		System.out.println("Sum is : " + sum);
		return sum;
	}
	
	/**
	 * Renvoie la somme de 2 ClasseTransactionnable
	 * @param ct1
	 * @param ct2
	 * @return 2
	 */
	@Transactionable
	public Integer somme_withTransacParams(ClasseTransactionnable ct1, ClasseTransactionnable ct2) {
		System.out.println("ct1 origin: " + ct1.toString());
		ct1.setX(2);
		ct1.setY(0);
		System.out.println("ct1 modify: " + ct1.toString());
		System.out.println();
		System.out.println("ct2 origin: " + ct2.toString());
		ct2.setX(0);
		ct2.setY(0);
		System.out.println("ct2 modify: " + ct2.toString());
		
		Integer sum = new Integer(ct1.somme() + ct2.somme());
		System.out.println("Sum ct1 + ct2= " + sum);
		return sum;
	}
	
	/**
	 * Do x/y with y=0. Raise exception
	 * @param ct1
	 * @return exception
	 */
	@Transactionable
	public Integer divide_byZero(ClasseTransactionnable ct1) {
		System.out.println(ct1.toString());
		ct1.setY(0);
		System.out.println(ct1.toString());
		
		Integer res = ct1.getX()/ct1.getY();
		return res;
	}
	
	/**
	 * Lance une exception au premier appel puis renvoie la somme de ct1
	 * @param ct1
	 * @return exception puis somme
	 */
	@Transactionable
	public Integer somme_firstExecRaiseException(ClasseTransactionnable ct1) {
		if (this.first) {
			this.first = false;
			
			try {
				throw new Exception("first call");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			this.first = true;
		}
		return ct1.somme();
	}
}
