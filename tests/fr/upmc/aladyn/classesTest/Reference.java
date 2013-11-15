package fr.upmc.aladyn.classesTest;

import fr.upmc.aladyn.transactionables.annotations.Transactionable;

@Transactionable
public class Reference {
	private Object o;
	
	public Reference (Object o) {
		this.o = o;
	}

	public Object getO() {
		return o;
	}

	public void setO(Object o) {
		this.o = o;
	}
}
