package fr.upmc.aladyn.transactionables;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Copie {

	private Object save;
	private Object reference;
	
	public Copie(String nomClasse, Object o) {
		this.reference = o;
		this.save = null;
		Class<?> clazz;
		try {
			clazz = Class.forName(nomClasse);
			Constructor<?> ctor = clazz.getConstructor(String.class);
			this.save = ctor.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		// recopier chaque valeurs de o dans save
	}

	public Object getSave() {
		return save;
	}

	public Object getReference() {
		return reference;
	}
}
