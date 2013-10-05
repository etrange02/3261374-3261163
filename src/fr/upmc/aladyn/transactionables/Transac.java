package fr.upmc.aladyn.transactionables;

import java.util.Iterator;
import java.util.Vector;

/**
 * Gère les transactions des différents threads
 * @author David Lecoconnier
 * @author Allan Mottier
 * 2013-09-18
 */

public class Transac {
	private Vector<Copie> copies;
	
	/**
	 * Constructeur
	 */
	public Transac() {
		this.copies = new Vector<Copie>();
	}
	
	/**
	 * Copie une instance d'objet et la conserve en mémoire pour une future restauration
	 * @param o
	 */
	public void saveNewObject(Object o) {
		this.copies.add(new Copie(o));
	}
	
	/**
	 * Appelé pour remettre en l'état les valeurs des attributs du dernier objet copié
	 */
	public void restore() {
		Iterator<Copie> iter = this.copies.iterator();
		
		while (iter.hasNext()) {
			((Copie) iter.next()).restoreInstance();
		}
	}
	
	/**
	 * Appelé pour signifier qu'aucune erreur n'a été trouvé.
	 */
	public void noErrorFound() {
		this.copies.removeAllElements();
	}
	
}
