package fr.upmc.aladyn.transactionables;

import java.lang.annotation.Annotation;
import java.util.Stack;

/**
 * Gère une pile contenant les objets transationnables utilisés dans chaque méthode.
 * @author David Lecoconnier
 * @author Allan Mottier
 * 2013-10-05
 */

public class TransacMethods {
	private Stack<Transac> pile;
	
	/**
	 * Constructeur
	 */
	public TransacMethods() {
		this.pile = new Stack<Transac>();
	}
	
	/**
	 * Ajoute à la pile l'ensemble des objets transactionnables utilisés dans la méthode
	 * @param tab
	 */
	public void copyTransacObjectsInMethod(Object[] tab) {
		Transac t = new Transac();
		Class<?> c = null;
		
		for (int i = 0; i<tab.length; ++i) {
			c = tab[i].getClass();
			if (isTransactionnable(c.getAnnotations())) {
				t.saveNewObject(tab[i]);
			}
		}
		this.pile.push(t);
	}
	
	/**
	 * Restaure les objets transactionnables utilisés dans la méthode, i.e. dans le sommet de pile
	 */
	public void restoreHeap() {
		Transac t = this.pile.peek();
		t.restore();
	}
	
	/**
	 * Supprime le sommet de pile, i.e. les copies des objets transactionnables utilisés dans la méthode
	 */
	public void destroyHeap() {
		this.pile.pop();
	}
	
	/**
	 * Indique la présence de "Transationable" dans le tableau
	 * @param a
	 * @return vrai si présent
	 */
	private boolean isTransactionnable(Annotation[] a) {
		for (int i = 0; i<a.length; ++i) {
			if (a[0].toString() == "fr.upmc.aladyn.transactionables.annotations.Transactionable")
				return true;
		}
		return false;
	}
}
