package fr.upmc.aladyn.transactionables;

import java.util.Vector;

import test.transactionables.TrTest1;

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
		/*
		 * 1. Créer une nouvelle Copie puis recopier chaque attribut dans s
		 * 2. Générer la clef 'instance.nomclasse.thread'
		 * 3. ajouter la copie de l'objet dans le store
		 */
		Copie c = new Copie(o);
		Thread.currentThread().getId();
	}
	
	/**
	 * Appelé pour remettre en l'état les valeurs des attributs du dernier objet copié
	 */
	public void restore() {
		
	}
	
	/**
	 * Appelé pour signifier qu'aucune erreur n'a été trouvé.
	 */
	public void noErrorFound() {
		// détruit le sommet de pile
	}
	
}
