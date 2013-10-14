package fr.upmc.aladyn.transactionables;

import java.lang.annotation.Annotation;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Gere une pile contenant les objets transactionnables utilises dans chaque methode.
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
	 * Ajoute a la pile l'ensemble des objets transactionnables utilises dans la methode
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
	 * Restaure les objets transactionnables utilises dans la methode, i.e. dans le sommet de pile
	 */
	public void restoreHeap() {
		if (this.pile.isEmpty())
			throw new EmptyStackException();
		Transac t = this.pile.peek();
		t.restore();
	}
	
	/**
	 * Supprime le sommet de pile, i.e. les copies des objets transactionnables utilises dans la methode
	 */
	public void destroyHeap() {
		if (this.pile.isEmpty())
			throw new EmptyStackException();
		this.pile.pop();
	}
	
	/**
	 * Indique la presence de "Transationable" dans le tableau
	 * @param a
	 * @return vrai si present
	 */
	private boolean isTransactionnable(Annotation[] a) {
		for (int i = 0; i<a.length; ++i) {
			if (a[i].toString().equals("@fr.upmc.aladyn.transactionables.annotations.Transactionable()")){
				return true;
			}
		}
		return false;
	}
}
