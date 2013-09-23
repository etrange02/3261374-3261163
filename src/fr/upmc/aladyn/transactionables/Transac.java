package fr.upmc.aladyn.transactionables;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import test.transactionables.TrTest1;

/**
 * Gère les transactions des différents threads
 * @author David Lecoconnier
 * @author Allan Mottier
 * 2013-09-18
 */

public class Transac {
	public static Transac s_TransacInstance = null;
	private Map<String, Stack<Copie>> store;
	
	/**
	 * Constructeur
	 */
	private Transac() {
		this.store = new HashMap<String, Stack<Copie>>();
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
		Method m = null;
		
		Field[] tabF = c.getDeclaredFields();
		
		for (int i = 0; i < tabF.length; ++i) {
			System.out.println("tabF[" + i + "]: name-" + tabF[i].getName() + " type-" + tabF[i].getType());
			try {
				m = c.getMethod("set" + tabF[i].getName().toUpperCase(), tabF[i].getType());
				m.invoke(t1, 3);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		/*try {
			tabF[0].setInt(c, 2);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}*/
		System.out.println("x="+t1.getX());
	}
	
	public void save(String nomClasse, Object o) {
		/*
		 * 1. Créer une nouvelle instance s ayant le même type
		 * 2. Recopier chaque attribut dans s
		 * 3. Générer la clef 'instance.nomclasse.thread'
		 * 4. ajouter la copie de l'objet dans le store
		 */
	}
	
	public void restore() {
		
	}
	
}
