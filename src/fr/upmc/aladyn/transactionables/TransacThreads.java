package fr.upmc.aladyn.transactionables;

import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe gère la copie et la restauration des objects
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
	 * Gère la méthode transtionnable appelé dans le thread idThread
	 * @param idThread
	 * @param m
	 */
	public void save(long idThread, Object[] o) {
		TransacMethods tm = new TransacMethods();
		tm.copyTransacObjectsInMethod(o);
		this.store.put(idThread, tm);
	}
	
	/**
	 * Gère la restauration des objets transationnables de la méthode courante dans le thread idThread
	 * @param idThread
	 */
	public void restore(long idThread) {
		TransacMethods tm = this.store.get(idThread);
		tm.restoreHeap();
	}
	
	/**
	 * Gère la destructions des objets transationnables utilisés dans la méthode courante dans le thread idThread
	 * @param idThread
	 */
	public void endMethod(long idThread) {
		TransacMethods tm = this.store.get(idThread);
		tm.destroyHeap();
		this.store.remove(idThread);
	}
}
