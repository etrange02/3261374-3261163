package fr.upmc.aladyn.transactionables;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class TransacAbstractTest {

	protected TransacAbstractTest() {
		//this.?? = null;
	}
	
	@After
	public void afterTest() {
		
	}
	
	@Before
	public abstract void beforeTest();
	
	@Test
	public void test1() {
		
	}
}
