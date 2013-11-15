package fr.upmc.aladyn.transactionables.tests_unitaires;

import java.util.EmptyStackException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.upmc.aladyn.classesTest.ClassWithTransactionableMethods;
import fr.upmc.aladyn.classesTest.ClasseTransactionnable;
import fr.upmc.aladyn.classesTest.Etudiant;
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

	/**
	 * Check the saving of an object when it is not used by a transactionable method
	 * Must raise an exception
	 * @throws Exception
	 */
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
		org.junit.Assert.assertTrue("X must be 3", ct.getX() == 3);
		org.junit.Assert.assertTrue("Y must be 7", ct.getY() == 7);
		org.junit.Assert.assertTrue("Sum must be 10", ct.somme() == 10);
	}

	/**
	 * Check a well-been when an exception is raised
	 */
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
		org.junit.Assert.assertTrue("X must be 0", ct.getX() == 0);
		org.junit.Assert.assertTrue("Y must be 2", ct.getY() == 2);
		org.junit.Assert.assertTrue("Sum must be 2", ct.somme() == 2);
		org.junit.Assert.assertTrue("Stack must not be empty", !getTransacThread().isEmpty());
	}

	/**
	 * Check a good computing
	 */
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
		org.junit.Assert.assertTrue("X must be 0", ct.getX() == 3);
		org.junit.Assert.assertTrue("Y must be 2", ct.getY() == 7);
		org.junit.Assert.assertTrue("Sum must be 2", ct.somme() == 10);
		org.junit.Assert.assertTrue("Stack must be empty", getTransacThread().isEmpty());
	}

	/**
	 * Check a restoration of 2 interlocked methods when the second one fails
	 */
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
		org.junit.Assert.assertTrue("X must be 3", ct.getX() == 3);
		org.junit.Assert.assertTrue("Y must be 7", ct.getY() == 7);
		org.junit.Assert.assertTrue("Sum must be 10", ct.somme() == 10);
		org.junit.Assert.assertTrue("Stack must not be empty", !getTransacThread().isEmpty());
	}

	/**
	 * Check a restoration of 2 interlocked methods when the first called one fails at end
	 */
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
		org.junit.Assert.assertTrue("X must be 0", ct.getX() == 0);
		org.junit.Assert.assertTrue("Y must be 2", ct.getY() == 2);
		org.junit.Assert.assertTrue("Sum must be 2", ct.somme() == 2);
		org.junit.Assert.assertTrue("Stack must not be empty", !getTransacThread().isEmpty());
	}

	/**
	 * Check a well-been computing of 2 interlocked methods
	 */
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
		org.junit.Assert.assertTrue("X must be 4", ct.getX() == 4);
		org.junit.Assert.assertTrue("Y must be 8", ct.getY() == 8);
		org.junit.Assert.assertTrue("Sum must be 12", ct.somme() == 12);
		org.junit.Assert.assertTrue("Stack must be empty", getTransacThread().isEmpty());
	}

	/**
	 * Check the saving and the restoring of an object whose mother class is transactionable
	 */
	@Test
	public void testTransactionableMotherClass() {
		System.out.println("Restoring test - transactionable mother class...");
		// init
		Etudiant etu = new Etudiant("nom", "prenom", 5);

		// modifications
		getTransacThread().createTransacMethodContext();
		getTransacThread().copyTransacObjectInSubTransacMethod(etu);
		etu.setNom("test");
		etu.setNumeroEtu(10);

		// operations
		getTransacThread().restoreHeap();

		// oracle
		org.junit.Assert.assertTrue("Nom must be 'nom'", etu.getNom().equals("nom"));
		org.junit.Assert.assertTrue("Prenom must be 'prenom'", etu.getPrenom().equals("prenom"));
		org.junit.Assert.assertTrue("Num must be 5", etu.getNumeroEtu() == 5);
	}
}
