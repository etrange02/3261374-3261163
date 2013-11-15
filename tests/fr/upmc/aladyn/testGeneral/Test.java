package fr.upmc.aladyn.testGeneral;

import fr.upmc.aladyn.classesTest.TrTest1;

public class Test {
	public static void main(String[] args){
		System.out.println("Debut Main");
		
		TrTest1 tt = new TrTest1();
		TrTest1 tt2 = new TrTest1();
		
		System.out.println("\navant setY(2): y="+tt.getY());
		tt.setY(2);
		System.out.println("apres setY(2): y="+tt.getY());
		
		System.out.println("\navant methodeTransacAvecSousMethodEtException(42,65): x="+tt.getX()+"; y="+tt.getY());
		try {
			tt.methodeTransacAvecSousMethodEtException(42,65);
		} catch (Exception e) {
			System.out.println("Retour de l'exception dans le Main");
		}
		System.out.println("apres methodeTransacAvecSousMethodEtException(42,65): x="+tt.getX()+"; y="+tt.getY());
		
		System.out.println("\navant plusieurSetDansUneTransacAvecExceptionCatchee(-1,-2): x="+tt.getX()+"; y="+tt.getY());
		tt.plusieurSetDansUneTransacAvecExceptionCatchee(-1,-2);
		System.out.println("apres plusieurSetDansUneTransacAvecExceptionCatchee(-1,-2): x="+tt.getX()+"; y="+tt.getY());
		
		tt.setX(0);
		
		System.out.println("\navant plusieurSetDansUneTransacAvecExceptionCatcheeEtReThrow(-1): x="+tt.getX());
		try {
			tt.plusieurSetDansUneTransacAvecExceptionCatcheeEtReThrow(-1);
		} catch (Exception e) {
			System.out.println("Retour de l'exception dans le Main");
		}
		System.out.println("apres plusieurSetDansUneTransacAvecExceptionCatcheeEtReThrow(-1): x="+tt.getX());
		
		tt.setX(0);
		tt2.setX(1);
		
		System.out.println("\navant appelSetDeDeuxObjetDeLaMemeClasseAvecException(): obj1: x="+tt.getX()+"; obj2: x="+tt2.getX());
		try {
			tt.appelSetDeDeuxObjetDeLaMemeClasseAvecException(tt, tt2);
		} catch (Exception e) {
			System.out.println("Retour de l'exception dans le Main");
		}
		System.out.println("apres appelSetDeDeuxObjetDeLaMemeClasseAvecException(): obj1: x="+tt.getX()+"; obj2: x="+tt2.getX());
		
		System.out.println("\navant appelSetDeLaClasseMereAvecException(-1): z="+tt.getZ());
		try {
			tt.appelSetDeLaClasseMereAvecException(-1);
		} catch (Exception e) {
			System.out.println("Retour de l'exception dans le Main");
		}
		System.out.println("apres appelSetDeLaClasseMereAvecException(-1): z="+tt.getZ());
		
		System.out.println("\nFin Main");
	}
	
}
