package fr.upmc.aladyn.transactionables.tests_unitaires;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.upmc.aladyn.classesTest.ClassWithTransactionableMethods;
import fr.upmc.aladyn.classesTest.ClasseTransactionnable;
import fr.upmc.aladyn.transactionables.TransacPool;

public class TransacPoolTest {
		
	@Before
	public void beforeTest() {
		TransacPool.s_TransacInstance = null;
	}
	
	@Test
	public void testThread() {
		System.out.println("Restoring test - Threading 1...");
		
		Thread t1 = new Thread(
			new Runnable() {
				public void run() {
					TransacPoolTest.testWithEnvironment_2levels_ErrorSimulazedOn2();
				}
			}
		);
		
		Thread t2 = new Thread(
			new Runnable() {
				public void run() {
					TransacPoolTest.testWithEnvironment_2levels_WellBeenSimulazed();
				}
			}
		);

		t1.start();
		t2.start();
		
		try {
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {		
			try {
				t2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("Restoring test - Threading 1 ended");
			}
		}
	}
	
	public static void testWithEnvironment_2levels_ErrorSimulazedOn2() {
		System.out.println("Restoring test - error 2/2...");
		// init
		ClasseTransactionnable ct = new ClasseTransactionnable();
		ClassWithTransactionableMethods cwtm = new ClassWithTransactionableMethods(ct);
		
		// modifications
		TransacPool.Get().transactionableMethod(Thread.currentThread().getId());
		TransacPool.Get().saveObject(Thread.currentThread().getId(), ct);
		cwtm.transMethod1();
		TransacPool.Get().transactionableMethod(Thread.currentThread().getId());
		TransacPool.Get().saveObject(Thread.currentThread().getId(), ct);
		cwtm.transMethod3_call();
		
		// operations
		TransacPool.Get().restore(Thread.currentThread().getId());
		
		// oracle
		Assert.assertTrue("X must be 3", ct.getX() == 3);
		Assert.assertTrue("Y must be 7", ct.getY() == 7);
		Assert.assertTrue("Sum must be 10", ct.somme() == 10);
	}
	
	public static  void testWithEnvironment_2levels_WellBeenSimulazed() {
		System.out.println("Restoring test - all ok 2...");
		// init
		ClasseTransactionnable ct = new ClasseTransactionnable();
		ClassWithTransactionableMethods cwtm = new ClassWithTransactionableMethods(ct);
		
		// modifications
		TransacPool.Get().transactionableMethod(Thread.currentThread().getId());// first
		TransacPool.Get().saveObject(Thread.currentThread().getId(), ct);
		cwtm.transMethod1();
		TransacPool.Get().transactionableMethod(Thread.currentThread().getId());// second
		TransacPool.Get().saveObject(Thread.currentThread().getId(), ct);
		cwtm.transMethod3_call();
		TransacPool.Get().endMethod(Thread.currentThread().getId()); // second ends
		
		// operations
		TransacPool.Get().endMethod(Thread.currentThread().getId()); // first ends
		
		// oracle
		Assert.assertTrue("X must be 4", ct.getX() == 4);
		Assert.assertTrue("Y must be 8", ct.getY() == 8);
		Assert.assertTrue("Sum must be 12", ct.somme() == 12);
	}
}
