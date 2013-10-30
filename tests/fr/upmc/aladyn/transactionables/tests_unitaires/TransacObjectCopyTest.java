package fr.upmc.aladyn.transactionables.tests_unitaires;

import java.util.Vector;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import classes.EntierChaine;
import classes.Reference;
import classes.Tableau;
import fr.upmc.aladyn.transactionables.TransacObjectCopy;


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
		this.toc = null;
	}
	
	@Before
	public void beforeTest() {
		return;
	}
	
	@Test
	public void test_restoreInstanceEntierChaine() {
		System.out.println("Restoring test - integer and string...");
		// init
		EntierChaine ec = new EntierChaine(3,  "test");
		
		// modifications		
		ec.setEntier(5);
		ec.setChaine("en cours");

		// operation
		getTransacObjectCopy().restoreInstance();

		// oracle
		Assert.assertTrue("Reference changed", getTransacObjectCopy().getReference() == ec);
		Assert.assertTrue("Integer not restored", ec.getEntier() == 3);
		Assert.assertTrue("String not restored", ec.getChaine() == "test");
	}

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
		Assert.assertTrue("Reference changed", getTransacObjectCopy().getReference() == ref);
		Assert.assertTrue("Integer not restored", ref.getO() == b);
		Assert.assertTrue("String not restored", ref.getO().toString().equals(Boolean.toString(true)));
	}

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
		Assert.assertTrue("Reference changed", getTransacObjectCopy().getReference() == t);
		Assert.assertTrue("Lenfth not restored", t.getTab().length == 3);
		Assert.assertTrue("Values not restored", t.getTab()[0] == 1 && t.getTab()[1] == 2 && t.getTab()[2] == 3);
	}
}
