package fr.upmc.aladyn.transactionables;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Gere la copie et la restauration des objets transactionnables utilises dans la methode transactionnable
 * @author David Lecoconnier
 * @author Allan Mottier
 * 2013-09-18
 */

public class TransacMethod {
	private HashMap<Integer,TransacObjectCopy> copies;

	/**
	 * Constructeur
	 */
	public TransacMethod() {
		this.copies = new HashMap<Integer,TransacObjectCopy>();
	}

	/**
	 * Copie une instance d'objet et la conserve en memoire pour une future restauration si elle n'est pas déjà présente
	 * On empeche ainsi les modifications successives d'un meme objet dans une methode transationnable
	 * La copie se fait si l'objet est transactionnable
	 * @param o l'objet a copier
	 */
	public void saveNewObject(Object o) {
		if (this.copies.containsKey(o.hashCode())){
			return;
		}

		Class<?> c = o.getClass();

		if (isTransactionable(c)) {
			this.copies.put(o.hashCode(),new TransacObjectCopy(o));
		}
	}

	/**
	 * Remet en l'etat tous les objets transactionnables modifies pendant l'appel de la methode
	 */
	public void restore() {
		Iterator<TransacObjectCopy> iter = this.copies.values().iterator();
		while (iter.hasNext()) {
			((TransacObjectCopy) iter.next()).restoreInstance();
		}
	}

	/**
	 * Indique la presence de "Transationable" dans le tableau
	 * @param a un tableau d'annotations
	 * @return vrai si present
	 */
	private boolean isTransactionable(Class<?> c) {
		Annotation[] a = null;
		while (c != null) {
			a = c.getAnnotations();
			for (int i = 0; i<a.length; ++i) {
				if (a[i].toString().equals("@fr.upmc.aladyn.transactionables.annotations.Transactionable()")){
					return true;
				}
			}
			c = c.getSuperclass();
		}
		return false;
	}
}
