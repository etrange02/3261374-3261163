package fr.upmc.aladyn.transactionables;

import java.lang.reflect.Method;
import java.util.Stack;

import fr.upmc.aladyn.transactionables.annotations.Transactionable;

public class TransacMethods {
	private Stack<Transac> pile;
	
	public TransacMethods() {
		this.pile = new Stack<Transac>();
	}
	
	public void copyTransacObjectsInMethod(Method m) {
		Transac t = new Transac();
		Class<?>[] c = m.getParameterTypes();
		c[0].isAnnotationPresent(Class<Transactionable>);
	}
	
	public void restoreHeap() {
		Transac t = this.pile.peek();
		t.restore();
	}
	
	public void destroyHeap() {
		this.pile.pop();
	}
}
