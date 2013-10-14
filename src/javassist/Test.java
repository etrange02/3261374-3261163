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
		System.out.println("avant2: "+tt.getX()+";"+tt.getY());
		try {
			tt.bidule(42,65);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("apres2: "+tt.getX()+";"+tt.getY());
		System.out.println("avant3: "+tt.getX());
		tt.machin();
		System.out.println("apres3: "+tt.getX());
		System.out.println("avant4: "+tt.getX());
		tt.machin();
		System.out.println("apres4: "+tt.getX());
		System.out.println("fin main");
	}
}
