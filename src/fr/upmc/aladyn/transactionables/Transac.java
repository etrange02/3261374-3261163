package fr.upmc.aladyn.transactionables;

import java.lang.reflect.Field;

import test.transactionables.TrTest1;

/**
 * Gère les transactions des différents threads
 * @author David Lecoconnier
 * @author Allan Mottier
 * 2013-09-18
 */

public class Transac {
	public static Transac s_TransacInstance = null;
	
	/**
	 * Constructeur
	 */
	private Transac() {
		
	}
	
	/**
	 * Singleton
	 * @return l'instance de la classe
	 */
	public static Transac Get() {
		if (null == s_TransacInstance)
			s_TransacInstance = new Transac();
		return s_TransacInstance;
	}
	
	public static void main(String[] args) {
		TrTest1 t1 = new TrTest1();
		
		Class<?> c = t1.getClass();
		
		Field[] tabF = c.getDeclaredFields();
		
		System.out.println("tabF[0]: name-"+tabF[0].getName()+"  type-"+tabF[0].getType());
		tabF[0].
		try {
			tabF[0].setInt(c, 2);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("x="+t1.getX());
	}
	
}
