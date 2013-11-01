package fr.upmc.aladyn.transactionables.tests_unitaires;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import classes.ClasseTransactionnable;
import classes.EntierChaine;
import classes.Reference;
import classes.Tableau;
import classes.useless.ConstructeurSans0Params;
import fr.upmc.aladyn.transactionables.TransacMethod;

public class TransacMethodTest {
	
	protected TransacMethod tm = null;
	
	
	public TransacMethod getTransacMethod() {
		return this.tm;
	}
	
	public void setTransacMethod(TransacMethod tm) {
		this.tm = tm;
	}
	
	@After
	public void afterTest() {
		setTransacMethod(null);
	}
	
	@Before
	public void beforeTest() {
		setTransacMethod(new TransacMethod());
	}
	
	@Test
	public void test_restoreClasseTransactionnable() {
		System.out.println("Restoring object - ClasseTransactionnable...");
		// init
		ClasseTransactionnable ct = new ClasseTransactionnable();
		ct.setX(0);
		ct.setY(3);
		getTransacMethod().saveNewObject(ct);
		
		// modifications		
		ct.setX(25);
		ct.setY(45);
		
		// operation
		getTransacMethod().restore();
		
		// oracle
		Assert.assertTrue("X must be 0", ct.getX() == 0);
		Assert.assertTrue("Y must be 3", ct.getY() == 3);
		Assert.assertTrue("Sum must be 3", ct.somme() == 3);
	}
	
	@Test
	public void test_restoreConstructeurSans0Params() {
		System.out.println("Restoring object - ConstructeurSans0Params...");
		// init
		ConstructeurSans0Params cs0p = new ConstructeurSans0Params("Hello!");
		getTransacMethod().saveNewObject(cs0p);
		
		// modifications		
		cs0p.setChaine("world");
		
		// operation
		getTransacMethod().restore();
		
		// oracle
		Assert.assertTrue("Must be 'Hello!'", cs0p.getChaine().equals("Hello!"));
	}
	
	/**
	 * This test confirm the recognition of annotation
	 * The object must not be restored
	 */
	@Test
	public void test_restoreEntierChaine() {
		System.out.println("Restoring object - EntierChaine...");
		// init
		EntierChaine ec = new EntierChaine(new Integer(0), "");
		getTransacMethod().saveNewObject(ec);
		
		// modifications		
		ec.setChaine("world");
		ec.setEntier(new Integer(3));
		
		// operation
		getTransacMethod().restore();
		
		// oracle
		Assert.assertTrue("Must be 3", ec.getEntier() == 3);
		Assert.assertTrue("Must be world", ec.getChaine().equals("world"));
	}
	
	@Test
	public void test_restoreReference() {
		System.out.println("Restoring object - Reference...");
		// init
		Reference r = new Reference(new Integer(5));
		getTransacMethod().saveNewObject(r);
		
		// modifications		
		r.setO(new Boolean(true));
		
		// operation
		getTransacMethod().restore();
		
		// oracle
		Assert.assertTrue("Must be 5", Integer.parseInt(r.getO().toString()) == 5);
	}
	
	@Test
	public void test_restoreTableau() {
		System.out.println("Restoring object - Tableau...");
		// init
		int[] tabInit = {1, 2, 3};
		Tableau t = new Tableau(tabInit);
		getTransacMethod().saveNewObject(t);
		
		// modifications		
		t.setTab(new int[]{2});
		
		// operation
		getTransacMethod().restore();
		
		// oracle
		Assert.assertTrue("Length must be 3", t.getTab().length == 3);
		Assert.assertTrue("Lenfth not restored", t.getTab().length == 3);
		Assert.assertTrue("Values not restored", t.getTab()[0] == 1 && t.getTab()[1] == 2 && t.getTab()[2] == 3);
	}
	
	@Test
	public void test_restoreManyClasses() {
		System.out.println("Restoring object - Tableau, Reference, EntierChaine...");
		// init
		int[] tabInit = {1, 2, 3};
		Tableau t = new Tableau(tabInit);
		Reference r = new Reference(new Integer(5));
		EntierChaine ec = new EntierChaine(new Integer(0), "");
		getTransacMethod().saveNewObject(t);
		getTransacMethod().saveNewObject(r);
		getTransacMethod().saveNewObject(ec);
		
		// modifications		
		t.setTab(new int[]{2});
		r.setO(new Boolean(true));
		ec.setChaine("world");
		ec.setEntier(new Integer(3));
		
		// operation
		getTransacMethod().restore();
		
		// oracle
		Assert.assertTrue("Length must be 3", t.getTab().length == 3);
		Assert.assertTrue("Lenfth not restored", t.getTab().length == 3);
		Assert.assertTrue("Values not restored", t.getTab()[0] == 1 && t.getTab()[1] == 2 && t.getTab()[2] == 3);
		Assert.assertTrue("Must be 5", Integer.parseInt(r.getO().toString()) == 5);
		Assert.assertTrue("Must be 3", ec.getEntier() == 3);
		Assert.assertTrue("Must be world", ec.getChaine().equals("world"));
	}
}
