package fr.upmc.aladyn.transactionables;

import fr.upmc.aladyn.transactionables.annotations.Transactionable;

public class ConcatTest {

	public ConcatTest() {
	}
	
	/**
	 * Renvoie la concat�nation de csp1 et csp2
	 * @param csp1
	 * @param csp2
	 * @return "csp1 csp2" avec un espace
	 */
	@Transactionable
	public String concat_default(ConstructeurSans0Params csp1, ConstructeurSans0Params csp2) {
		return csp1.toString() + " " + csp2.toString();
	}
	
	/**
	 * Renvoie la concat�nation de "result" + s1 + s2
	 * @param s1
	 * @param s2
	 * @return "result=" + s1 + s2
	 */
	@Transactionable
	public String concat_withUselessBody(String s1, String s2) {
		ConstructeurSans0Params csp = new ConstructeurSans0Params("Hello World");
		
		csp.setChaine("result=");

		String res = csp.toString() + s1.toString() + " " + s2.toString();
		System.out.println("res:  " + res);
		return res;
	}
	
	/**
	 * Renvoie Hello World en modifiant les deux classes en param�tre
	 * @param csp1
	 * @param csp2
	 * @return "Hello World"
	 */
	@Transactionable
	public String concat_withTransacParams(ConstructeurSans0Params csp1, ConstructeurSans0Params csp2) {
		System.out.println("csp1: " + csp1);
		System.out.println("csp2: " + csp2);
		csp1.setChaine("Hello");
		csp2.setChaine("World");
		String res = csp1.toString() + " " + csp2.toString();
		System.out.println("res:  " + res);
		return res;
	}
}
