package fr.upmc.aladyn.transactionables.tests_unitaires;

import java.util.EmptyStackException;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import classes.ClassWithTransactionableMethods;
import classes.ClasseTransactionnable;
import fr.upmc.aladyn.transactionables.TransacThread;

public class TransacThreadTest {
	
	protected TransacThread tt = null;

	public TransacThread getTransacThread() {
		return this.tt;
	}
	
	public void setTransacThread(TransacThread tt) {
		this.tt = tt;
	}

	@After
	public void afterTest() {
		setTransacThread(null);
	}

	@Before
	public void beforeTest() {
		setTransacThread(new TransacThread());
	}

	@Test
	public void testWithoutEnvironment() throws Exception {
		System.out.println("Restoring test - no environment...");
		// init
		ClasseTransactionnable ct = new ClasseTransactionnable();
		ClassWithTransactionableMethods cwtm = new ClassWithTransactionableMethods(ct);
		
		// modifications
		getTransacThread().copyTransacObjectInSubTransacMethod(cwtm);
		cwtm.transMethod1();
		
		// operations
		try {
			getTransacThread().restoreHeap();
			throw new Exception("no error raised");
		} catch (EmptyStackException e) {
		}
		
		// oracle
		Assert.assertTrue("X must be 3", ct.getX() == 3);
		Assert.assertTrue("Y must be 7", ct.getY() == 7);
		Assert.assertTrue("Sum must be 10", ct.somme() == 10);
	}

	@Test
	public void testWithEnvironment_ErrorSimulazed() {
		System.out.println("Restoring test - error simulazed...");
		// init
		ClasseTransactionnable ct = new ClasseTransactionnable();
		ClassWithTransactionableMethods cwtm = new ClassWithTransactionableMethods(ct);
		
		// modifications
		getTransacThread().createTransacMethodContext();
		getTransacThread().copyTransacObjectInSubTransacMethod(ct);
		cwtm.transMethod1();
		
		// operations
		getTransacThread().restoreHeap();
		
		// oracle
		Assert.assertTrue("X must be 0", ct.getX() == 0);
		Assert.assertTrue("Y must be 2", ct.getY() == 2);
		Assert.assertTrue("Sum must be 2", ct.somme() == 2);
		Assert.assertTrue("Stack must not be empty", !getTransacThread().isEmpty());
	}

	@Test
	public void testWithEnvironment_WellBeenSimulazed() {
		System.out.println("Restoring test - all ok 1...");
		// init
		ClasseTransactionnable ct = new ClasseTransactionnable();
		ClassWithTransactionableMethods cwtm = new ClassWithTransactionableMethods(ct);
		
		// modifications
		getTransacThread().createTransacMethodContext();
		getTransacThread().copyTransacObjectInSubTransacMethod(ct);
		cwtm.transMethod1();
		
		// operations
		getTransacThread().destroyHeap();
		
		// oracle
		Assert.assertTrue("X must be 0", ct.getX() == 3);
		Assert.assertTrue("Y must be 2", ct.getY() == 7);
		Assert.assertTrue("Sum must be 2", ct.somme() == 10);
		Assert.assertTrue("Stack must be empty", getTransacThread().isEmpty());
	}

	@Test
	public void testWithEnvironment_2levels_ErrorSimulazedOn2() {
		System.out.println("Restoring test - error 2/2...");
		// init
		ClasseTransactionnable ct = new ClasseTransactionnable();
		ClassWithTransactionableMethods cwtm = new ClassWithTransactionableMethods(ct);
		
		// modifications
		getTransacThread().createTransacMethodContext();
		getTransacThread().copyTransacObjectInSubTransacMethod(ct);
		cwtm.transMethod1();
		getTransacThread().createTransacMethodContext();
		getTransacThread().copyTransacObjectInSubTransacMethod(ct);
		cwtm.transMethod3_call();
		
		// operations
		getTransacThread().restoreHeap();
		
		// oracle
		Assert.assertTrue("X must be 3", ct.getX() == 3);
		Assert.assertTrue("Y must be 7", ct.getY() == 7);
		Assert.assertTrue("Sum must be 10", ct.somme() == 10);
		Assert.assertTrue("Stack must not be empty", !getTransacThread().isEmpty());
	}

	@Test
	public void testWithEnvironment_2levels_ErrorSimulazedOn1() {
		System.out.println("Restoring test - error 1/2...");
		// init
		ClasseTransactionnable ct = new ClasseTransactionnable();
		ClassWithTransactionableMethods cwtm = new ClassWithTransactionableMethods(ct);
		
		// modifications
		getTransacThread().createTransacMethodContext();// first
		getTransacThread().copyTransacObjectInSubTransacMethod(ct);
		cwtm.transMethod1();
		getTransacThread().createTransacMethodContext();// second
		getTransacThread().copyTransacObjectInSubTransacMethod(ct);
		cwtm.transMethod3_call();
		getTransacThread().destroyHeap(); // second ends
		
		// operations
		getTransacThread().restoreHeap(); // first restores
		
		// oracle
		Assert.assertTrue("X must be 0", ct.getX() == 0);
		Assert.assertTrue("Y must be 2", ct.getY() == 2);
		Assert.assertTrue("Sum must be 2", ct.somme() == 2);
		Assert.assertTrue("Stack must not be empty", !getTransacThread().isEmpty());
	}

	@Test
	public void testWithEnvironment_2levels_WellBeenSimulazed() {
		System.out.println("Restoring test - all ok 2...");
		// init
		ClasseTransactionnable ct = new ClasseTransactionnable();
		ClassWithTransactionableMethods cwtm = new ClassWithTransactionableMethods(ct);
		
		// modifications
		getTransacThread().createTransacMethodContext();// first
		getTransacThread().copyTransacObjectInSubTransacMethod(ct);
		cwtm.transMethod1();
		getTransacThread().createTransacMethodContext();// second
		getTransacThread().copyTransacObjectInSubTransacMethod(ct);
		cwtm.transMethod3_call();
		getTransacThread().destroyHeap(); // second ends
		
		// operations
		getTransacThread().destroyHeap(); // first ends
		
		// oracle
		Assert.assertTrue("X must be 4", ct.getX() == 4);
		Assert.assertTrue("Y must be 8", ct.getY() == 8);
		Assert.assertTrue("Sum must be 12", ct.somme() == 12);
		Assert.assertTrue("Stack must be empty", getTransacThread().isEmpty());
	}
}
