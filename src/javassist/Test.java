package javassist;

import test.transactionables.TrTest1;

public class Test {
	public static void main(String[] args){
		System.out.println("debut main");
		TrTest1 tt = new TrTest1();
		Object o = new Object();
		System.out.println("avant: "+tt.getY());
		tt.setY(14);
		System.out.println("apres: "+tt.getY());
		System.out.println("avant2: "+tt.getY());
		try {
			tt.setZ(99);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("apres2: "+tt.getY());
		System.out.println("fin main");
	}
}
