package fr.upmc.aladyn.transactionables;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class CopieAbstractTest {

	protected CopieAbstractTest() {
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
