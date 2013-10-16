package fr.upmc.aladyn.transactionables;

import java.util.HashMap;
import java.util.Map;

/**
 * Gere les copies et les restaurations des objets transactionables dans les methodes transactionables dans plusieurs threads indépendants
 * @author David Lecoconnier
 * @author Allan Mottier
 * 2013-10-05
 */

public class TransacPool {
	public static TransacPool s_TransacInstance = null;
	private Map<Long, TransacThread> store;
	
	/**
	 * Constructeur
	 */
	private TransacPool() {
		this.store = new HashMap<Long, TransacThread>();
	}
	
	/**
	 * Singleton
	 * @return l'instance de la classe
	 */
	public static TransacPool Get() {
		if (null == s_TransacInstance)
			s_TransacInstance = new TransacPool();
		return s_TransacInstance;
	}
	
	/**
	 * Initie la copie d'un objet. La reel copie depend de plusieurs parametres : objet transactionable, contexte de methode transactionable
	 * @param idThread l'identifiant du thread appelant
	 * @param o un objet (pas necessairement transactionable)
	 */
	public synchronized void saveObject(long idThread, Object o) {
		if (this.store.containsKey(idThread)) {
			TransacThread tt = this.store.get(idThread);
			tt.copyTransacObjectInSubTransacMethod(o);
		}
	}
	
	/**
	 * Cree un contexte pour une methode transactionable
	 * @param idThread l'identifiant du thread appelant
	 */
	public synchronized void transactionableMethod(long idThread) {
		TransacThread tt = null;
		if (this.store.containsKey(idThread))
			tt = this.store.get(idThread);
		else {
			tt = new TransacThread();
			this.store.put(idThread, tt);
		}
		tt.createTransacMethodContext();
	}
	
	/**
	 * Indique si l'on est dans l'appel d'une methode transactionable et qu'aucune restauration n'est en cours
	 * @param idThread l'identifiant du thread appelant
	 * @return
	 */
	public boolean isTransactionableMethodAndNoRestoring(long idThread) {
		if (this.store.containsKey(idThread)) {
			return !this.store.get(idThread).isEmpty() && !this.store.get(idThread).isRestoring();
		}
		return false;
	}
	
	/**
	 * Gere la restauration des objets transactionables de la methode courante dans le thread idThread
	 * @param idThread l'identifiant du thread appelant
	 */
	public synchronized void restore(long idThread) {
		TransacThread tm = this.store.get(idThread);
		tm.restoreHeap();
	}
	
	/**
	 * Gere la destruction des objets transactionables utilises dans la methode courante dans le thread idThread
	 * @param idThread l'identifiant du thread appelant
	 */
	public synchronized void endMethod(long idThread) {
		TransacThread tm = this.store.get(idThread);
		tm.destroyHeap();
	}
}
