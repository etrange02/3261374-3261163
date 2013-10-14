package fr.upmc.aladyn.transactionables;

import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe gere la copie et la restauration des objets transationnables presents dans les differents threads
 * @author David Lecoconnier
 * @author Allan Mottier
 * 2013-10-05
 */

public class TransacThreads {
	public static TransacThreads s_TransacInstance = null;
	private Map<Long, TransacMethods> store;
	
	private TransacThreads() {
		this.store = new HashMap<Long, TransacMethods>();
	}
	
	/**
	 * Singleton
	 * @return l'instance de la classe
	 */
	public static TransacThreads Get() {
		if (null == s_TransacInstance)
			s_TransacInstance = new TransacThreads();
		return s_TransacInstance;
	}
	
	/**
	 * Gere la methode transtionnable appele dans le thread idThread
	 * @param idThread l'identifiant du thread appelant
	 * @param o un tableau d'objet (pas necessairement transactionable)
	 */
	public synchronized void save(long idThread, Object[] o) {
		TransacMethods tm = null;
		if (this.store.containsKey(idThread))
			tm = this.store.get(idThread);
		else
			tm = new TransacMethods();
		tm.copyTransacObjectsInMethod(o);
		this.store.put(idThread, tm);
	}
	
	/**
	 * Gere la restauration des objets transationnables de la methode courante dans le thread idThread
	 * @param idThread l'identifiant du thread appelant
	 */
	public synchronized void restore(long idThread) {
		TransacMethods tm = this.store.get(idThread);
		tm.restoreHeap();
	}
	
	/**
	 * Gere la destructions des objets transationnables utilises dans la methode courante dans le thread idThread
	 * @param idThread l'identifiant du thread appelant
	 */
	public synchronized void endMethod(long idThread) {
		TransacMethods tm = this.store.get(idThread);
		tm.destroyHeap();
	}
}
