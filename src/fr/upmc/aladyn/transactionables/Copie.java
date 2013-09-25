package fr.upmc.aladyn.transactionables;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 24/09/2013
 * Représente la copie d'un objet : copie des valeurs des attributs de ce dernier
 * @author David Lecoconnier
 * @author Allan Mottier
 */

public class Copie {

	private Map<String, Attribut> save;
	private Object reference;
	
	/**
	 * Constructeur
	 * @param o l'instance à copier
	 */
	public Copie(Object o) {
		this.reference = o;
		this.save = new HashMap<String, Attribut>();
		copyFields();
	}
	
	/**
	 * Copie tous les attributs de l'instance dans une sauvegarde. Fait aussi l'héritage
	 * @param o l'instance à copier
	 */
	private void copyFields() {
		Class<?> c = this.reference.getClass();
		Field[] f = c.getDeclaredFields();
		Method m = null;
		Field field = null;
		
		while (c != null) {
			for (int i = 0; i < f.length; i++) {
				try {
					field = f[i];
					m = c.getMethod("get" + capitalize(field.getName()), field.getType());
					save.put(field.getName(), new Attribut(field.getName(), m.invoke(this.reference), field.getType().getName().length() == 1));
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
			c = c.getSuperclass();
		}

		// voir setAccessible
		/*switch (f[i].getType().getName()) {
			case "I"://int
				save.add(new Attribut(f[i].getName(), f[i].getInt(obj)));
				break;
			case "B"://int
				break;
			case "Z"://int
				break;
			case "C"://int
				break;
			case "F"://int
				break;
			case "D"://int
				break;
			default:
		}*/ 
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
		Class<?> c = this.reference.getClass();		
		Field[] f = c.getDeclaredFields();
		Method m = null;
		Field field = null;
		
		while (c != null) {
			for (int i = 0; i < f.length; i++) {
				try {
					field = f[i];
					m = c.getMethod("set" + capitalize(field.getName()), field.getType());
					m.invoke(this.reference, save.get(field.getName()).getValue());
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
			c = c.getSuperclass();
		}
	}
	
	private String capitalize(String ligne) {
		return Character.toUpperCase(ligne.charAt(0)) + ligne.substring(1);
	}
}
