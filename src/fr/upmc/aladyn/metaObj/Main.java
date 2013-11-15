package fr.upmc.aladyn.metaObj;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.NotFoundException;
import javassist.Translator;
import javassist.tools.reflect.Loader;

/**
 * Classe principale d'appel pour gerer la sauvegarde/restauration
 * 
 * @author David Lecoconnier
 * @author Allan Mottier
 * 2013-11-01
 */
public class Main {

	public static void main(String[] args) throws CannotCompileException, NotFoundException {

		ClassPool pool = ClassPool.getDefault(); 
		Loader cl = new Loader();
		Translator t = new MonMetaTranslator(cl); 
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
