package javassist;

import test.transactionables.TrTest1;

public class Test {
	public static void main(String[] args){
		System.out.println("debut main");
		TrTest1 tt = new TrTest1();
		tt.setY(14);
		try {
			tt.setZ(13);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("fin main");
	}
}
