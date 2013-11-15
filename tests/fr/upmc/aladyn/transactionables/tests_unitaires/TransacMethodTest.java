package fr.upmc.aladyn.transactionables.tests_unitaires;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.upmc.aladyn.classesTest.ClasseTransactionnable;
import fr.upmc.aladyn.classesTest.ConstructeurSans0Params;
import fr.upmc.aladyn.classesTest.EntierChaine;
import fr.upmc.aladyn.classesTest.Reference;
import fr.upmc.aladyn.classesTest.Tableau;
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
	
	/**
	 * Check the restoration of a transactionable class where attributes are simple types
	 */
	@Test
	public void test_restoreClasseTransactionable() {
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
		org.junit.Assert.assertTrue("X must be 0", ct.getX() == 0);
		org.junit.Assert.assertTrue("Y must be 3", ct.getY() == 3);
		org.junit.Assert.assertTrue("Sum must be 3", ct.somme() == 3);
	}
	
	/**
	 * Check the restoration of a class which constructor is not 0 parameter
	 */
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
		org.junit.Assert.assertTrue("Must be 'Hello!'", cs0p.getChaine().equals("Hello!"));
	}
	
	/**
	 * Check the recognition of annotation
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
		org.junit.Assert.assertTrue("Must be 3", ec.getEntier() == 3);
		org.junit.Assert.assertTrue("Must be world", ec.getChaine().equals("world"));
	}
	
	/**
	 * Check the restoration of a transactionable class which contains a reference to another object
	 */
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
		org.junit.Assert.assertTrue("Must be 5", Integer.parseInt(r.getO().toString()) == 5);
	}
	
	/**
	 * Check the restoration of values in a table
	 */
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
		org.junit.Assert.assertTrue("Length must be 3", t.getTab().length == 3);
		org.junit.Assert.assertTrue("Lenfth not restored", t.getTab().length == 3);
		org.junit.Assert.assertTrue("Values not restored", t.getTab()[0] == 1 && t.getTab()[1] == 2 && t.getTab()[2] == 3);
	}
	
	/**
	 * Check the restoration of many classes at the same time
	 */
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
		org.junit.Assert.assertTrue("Length must be 3", t.getTab().length == 3);
		org.junit.Assert.assertTrue("Lenfth not restored", t.getTab().length == 3);
		org.junit.Assert.assertTrue("Values not restored", t.getTab()[0] == 1 && t.getTab()[1] == 2 && t.getTab()[2] == 3);
		org.junit.Assert.assertTrue("Must be 5", Integer.parseInt(r.getO().toString()) == 5);
		org.junit.Assert.assertTrue("Must be 3", ec.getEntier() == 3);
		org.junit.Assert.assertTrue("Must be world", ec.getChaine().equals("world"));
	}
}
