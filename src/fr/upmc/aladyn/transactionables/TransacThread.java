package fr.upmc.aladyn.transactionables;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Gere les conetextes des methodes transactionables en les stockant successivement dans un pile
 * @author David Lecoconnier
 * @author Allan Mottier
 * 2013-10-05
 */

public class TransacThread {
	private Stack<TransacMethod> pile;
	private ReentrantLock mutexRestoring;
	
	/**
	 * Constructeur
	 */
	public TransacThread() {
		this.pile = new Stack<TransacMethod>();
		this.mutexRestoring = new ReentrantLock();
	}
	
	/**
	 * Lance la copie d'un objet dans le contexte courant de la methode transactionable
	 * @param o
	 */
	public void copyTransacObjectInSubTransacMethod(Object o) {
		if (isEmpty() || isRestoring())
			return;
		
		TransacMethod tm = this.pile.peek();
		tm.saveNewObject(o);
	}
	
	/**
	 * Cree un contexte pour l'appel d'un nouvelle methode transactionable
	 */
	public void createTransacMethodContext() {
		TransacMethod tm = new TransacMethod();
		this.pile.push(tm);
	}
	
	/**
	 * Restaure les objets transactionables utilises dans la methode, i.e. dans le sommet de pile
	 */
	public void restoreHeap() {
		if (isEmpty())
			throw new EmptyStackException();
		this.mutexRestoring.lock();
		TransacMethod tm = this.pile.peek();
		tm.restore();
		this.mutexRestoring.unlock();
	}
	
	/**
	 * Supprime le contexte de la methode transactionable courante.
	 * Le contexte disponible a la suite de cette suppression est le contexte de la methode transactionable a l'iniative de la methode courante.
	 * Il peut ne plus y avoir de contexte s'ils ont tous ete supprimes ou restaures.
	 */
	public void destroyHeap() {
		if (isEmpty())
			throw new EmptyStackException();
		this.pile.pop();
	}
	
	/**
	 * Indique si des contextes de methodes transactionables ont ete crees
	 * @return true s'il n'y a pas de contexte
	 */
	public boolean isEmpty() {
		return this.pile.isEmpty();
	}
	
	/**
	 * Indique l'etat de la restauration
	 * @return true lorsqu'une restauration est en cours
	 */
	public boolean isRestoring() {
		return this.mutexRestoring.isLocked();
	}
}
