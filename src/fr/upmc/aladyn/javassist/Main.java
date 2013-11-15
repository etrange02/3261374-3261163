package fr.upmc.aladyn.javassist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.Loader;
import javassist.NotFoundException;
import javassist.Translator;

/**
 * Classe principale d'appel pour gerer la sauvegarde/restauration
 * 
 * @author David Lecoconnier
 * @author Allan Mottier
 * 2013-10-01
 */
public class Main {

	public static void main(String[] args) {
		Translator t = new MonTranslator(); 
		ClassPool pool = ClassPool.getDefault(); 
		Loader cl = new Loader();
		try {
			cl.addTranslator(pool,t);
			cl.run("fr.upmc.aladyn.testGeneral.Test",args); 

		} catch (NotFoundException | CannotCompileException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
