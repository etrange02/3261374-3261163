package fr.upmc.aladyn.transactionables.tests_unitaires;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import classes.EntierChaine;
import classes.Reference;
import classes.Tableau;
import fr.upmc.aladyn.transactionables.TransacObjectCopy;


@SuppressWarnings("unused")
public class TransacObjectCopyTest {
	
	protected TransacObjectCopy toc = null;
	
	public TransacObjectCopy getTransacObjectCopy() {
		return this.toc;
	}
	
	public void setTransacObjectCopy(TransacObjectCopy toc) {
		this.toc = toc;
	}
	
	@After
	public void afterTest() {
		setTransacObjectCopy(null);
	}
	
	/**
	 * Check the restoration of primitives types
	 */
	@Test
	public void test_restoreInstanceEntierChaine() {
		System.out.println("Restoring test - integer and string...");
		// init
		EntierChaine ec = new EntierChaine(3,  "test");
		setTransacObjectCopy(new TransacObjectCopy(ec));
		
		// modifications		
		ec.setEntier(5);
		ec.setChaine("en cours");

		// operation
		getTransacObjectCopy().restoreInstance();

		// oracle
		org.junit.Assert.assertTrue("Reference changed", getTransacObjectCopy().getReference() == ec);
		org.junit.Assert.assertTrue("Integer not restored", ec.getEntier() == 3);
		org.junit.Assert.assertTrue("String not restored", ec.getChaine() == "test");
	}

	/**
	 * Check the restoration of references
	 */
	@Test
	public void test_restoreInstanceReference() {
		System.out.println("Restoring test - references...");
		// init
		Boolean b = new Boolean(true);
		Reference ref = new Reference(b);
		setTransacObjectCopy(new TransacObjectCopy(ref));

		// modifications		
		ref.setO(new Vector<String>());

		// operation
		getTransacObjectCopy().restoreInstance();

		// oracle
		org.junit.Assert.assertTrue("Reference changed", getTransacObjectCopy().getReference() == ref);
		org.junit.Assert.assertTrue("Integer not restored", ref.getO() == b);
		org.junit.Assert.assertTrue("String not restored", ref.getO().toString().equals(Boolean.toString(true)));
	}

	/**
	 * Check the restoration of one table
	 */
	@Test
	public void test_restoreInstanceTableau() {
		System.out.println("Restoring test - array...");
		// init
		int[] tabInit = {1, 2, 3};
		Tableau t = new Tableau( tabInit );
		setTransacObjectCopy(new TransacObjectCopy(t));
		
		// modifications		
		int[] newTab = {2, 5};
		t.setTab( newTab );
		
		// operation
		getTransacObjectCopy().restoreInstance();
		 
		// oracle
		org.junit.Assert.assertTrue("Reference changed", getTransacObjectCopy().getReference() == t);
		org.junit.Assert.assertTrue("Lenfth not restored", t.getTab().length == 3);
		org.junit.Assert.assertTrue("Values not restored", t.getTab()[0] == 1 && t.getTab()[1] == 2 && t.getTab()[2] == 3);
	}
}
