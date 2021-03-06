package fr.upmc.aladyn.transactionables;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Represente la copie d'un objet : copie des valeurs des attributs de ce dernier
 * @author David Lecoconnier
 * @author Allan Mottier
 * 2013-10-29
 */

public class TransacObjectCopy {

	private Map<String, Object> save;
	private Object reference;
	
	/**
	 * Constructeur
	 * @param o l'instance a copier
	 */
	public TransacObjectCopy(Object o) {
		this.reference = o;
		this.save = new HashMap<String, Object>();
		copyFields();
	}
	
	/**
	 * Copie tous les attributs de l'instance dans une sauvegarde. Fait aussi l'heritage
	 */
	private void copyFields() {
		Class<?> c = this.reference.getClass();
		Field[] f = null;
		Method m = null;
		Field field = null;
		
		while (c != null) {
			f = c.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				try {
					field = f[i];
					if (field.getClass().isArray()) { // type tableau
						copyArray(field);
					} else { // type normal
						if(field.getName()=="_metaobject" || field.getName()=="_classobject")
							continue;
						m = c.getMethod("get" + capitalize(field.getName()), null);
						save.put(field.getName(), m.invoke(this.reference));
					}
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
	
	/**
	 * Copie le contenu d'un tableau associe a un champ
	 * @param f
	 */
	private void copyArray(Field f) {		
		try {
			if(f.getName()=="_metaobject" || f.getName()=="_classobject")
				return;
			Method m = this.reference.getClass().getMethod("get" + capitalize(f.getName()), null);
			Object[] res = (Object[]) m.invoke(this.reference);
			res.getClass().cast(f.getClass());
			save.put(f.getName(), Arrays.copyOf(res, res.length));
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Renvoie l'instance de l'objet copie
	 * @return l'instance
	 */
	public Object getReference() {
		return this.reference;
	}
	
	/**
	 * Restaure les attributs de l'objet copie
	 * /!\ A tester avec un iterator sur la map /!\
	 */
	public void restoreInstance() {
		Class<?> c = this.reference.getClass();		
		Field[] f = null;
		Method m = null;
		Field field = null;
		
		while (c != null) {
			f = c.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				try {
					field = f[i];
					if(field.getName()=="_metaobject" || field.getName()=="_classobject")
						continue;
					m = c.getMethod("set" + capitalize(field.getName()), field.getType());
					m.invoke(this.reference, save.get(field.getName()));
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
	
	/**
	 * Met la premiere lettre en majuscule
	 * @param ligne
	 * @return une chaine commençant par une majuscule
	 */
	private String capitalize(String ligne) {
		return Character.toUpperCase(ligne.charAt(0)) + ligne.substring(1);
	}
	
	public Object sauveObjet(Object o1)
	{
		Class<?> c = o1.getClass();
		Object o2=null;

		try {
			o2 = c.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}

		return sauveField(c,o2);
	}

	public Object sauveField(Class<?> c, Object o)
	{
		Field[] tabF = c.getDeclaredFields();
		for(int i=0;i<tabF.length;i++){
			Field f;
			try {
				f = o.getClass().getDeclaredField(tabF[i].getName());
				if (f.isAccessible()) {
					f.set(o, tabF[0].get(c));
				} else {
					tabF[0].setAccessible(true);
					f.setAccessible(true);
					f.set(o, tabF[0].get(c));
					f.setAccessible(false);
					tabF[0].setAccessible(false);
				}
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		if(c.getName()=="Object")
			return o;
		else
			return sauveField(c.getSuperclass(), o);
	}

	/**** restauration    *********/
	public Object restaureObjet(Object o1)
	{
		Class<?> c = o1.getClass();
		Object o2=null;

		try {
			o2 = c.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}

		return restaureField(c,o2);
	}

	public Object restaureField(Class<?> c, Object o)
	{
		Field[] tabF = c.getDeclaredFields();
		for(int i=0;i<tabF.length;i++){
			Field f;
			try {
				f = o.getClass().getDeclaredField(tabF[i].getName());
				tabF[0].setAccessible(true);
				f.setAccessible(true);
				f.set(o, tabF[0].get(c));
				f.setAccessible(false);
				tabF[0].setAccessible(false);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		if(c.getName()=="Object")
			return o;
		else
			return restaureField(c.getSuperclass(), o);
	}
}
