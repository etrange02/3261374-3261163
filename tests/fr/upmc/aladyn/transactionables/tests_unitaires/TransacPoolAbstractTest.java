package fr.upmc.aladyn.transactionables.tests_unitaires;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class TransacPoolAbstractTest {
	protected TransacPoolAbstractTest() {
		//this.?? = null;
	}
	
	@After
	public void afterTest() {
	}
	
	@Before
	public abstract void beforeTest();
	
	@Test
	public void testCopie() {
		
	}
}
