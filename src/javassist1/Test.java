package javassist1;

import fr.upmc.aladyn.transactionables.annotations.Transactionable;
import test.transactionables.TrTest1;

public class Test {
	public static void main(String[] args){
		System.out.println("debut main");
		TrTest1 tt = new TrTest1();
		TrTest1 tt2 = new TrTest1();
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
		System.out.println("avant3: "+tt.getX()+";"+tt.getY());
		tt.machin();
		System.out.println("apres3: "+tt.getX()+";"+tt.getY());
		System.out.println("avant4: "+tt.getX()+";"+tt.getY());
		tt.machin3();
		System.out.println("apres4: "+tt.getX()+";"+tt.getY());
		System.out.println("avant5: "+tt.getX());
		try {
			tt.machin2();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("apres5: "+tt.getX());
		
		
		tt.setX(0);
		tt2.setX(1);
		System.out.println("avant6: "+tt.getX()+";"+tt2.getX());
		try {
			tt.glagla(tt, tt2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("aprés6: "+tt.getX()+";"+tt2.getX());
		
		System.out.println("avant7: "+tt.getX()+";"+tt.getZ());
		try {
			tt.blah();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("aprés7: "+tt.getX()+";"+tt.getZ());
		System.out.println("fin main");
	}
	
}
