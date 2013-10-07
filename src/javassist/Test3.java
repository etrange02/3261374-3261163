package javassist;

import test.transactionables.TrTest1;

public class Test3 {
	public static void main(String[] args){
		System.out.println("debut main");
		TrTest1 tt = new TrTest1();
		tt.setY(14);
		System.out.println("fin main");
	}
}
