package fr.upmc.aladyn.transactionables;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

/**
 * 24/09/2013
 * Représente la copie d'un objet : copie des valeurs des attributs de ce dernier
 * @author David Lecoconnier
 * @author Allan Mottier
 */

public class Copie {

	private Vector<Attribut> save;
	private Object reference;
	
	/**
	 * Constructeur
	 * @param o l'instance à copier
	 */
	public Copie(Object o) {
		this.reference = o;
		this.save = new Vector<Attribut>();
		copyFields(o);
	}
	
	/**
	 * Copie tous les attributs de l'instance dans une sauvegarde. Fait aussi l'héritage
	 * @param o l'instance à copier
	 */
	private void copyFields(Object o) {
		for (int i = 0; i < 20; i++) {
			//save.add(new Attribut(variString, boolValue));
		}
	}

	/**
	 * Renvoie l'instance de l'objet copié
	 * @return l'instance
	 */
	public Object getReference() {
		return reference;
	}
	
	/**
	 * Restaure les attributs de l'objet copié
	 */
	public void restoreInstance() {
		//this.reference;
		//Class<?> c = this.reference.getClass();
		System.out.println(this.reference.getClass());
		/*Method m = null;
		
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
		}*/
	}
}
