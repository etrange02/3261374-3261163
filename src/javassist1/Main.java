package javassist1;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Loader;
import javassist.NotFoundException;
import javassist.Translator;
import javassist.expr.ExprEditor;
import javassist.expr.Handler;

/**
 * Classe principale d'appel pour gerer la sauvegarde/restauration
 * 
 * @author allan
 * @author david
 *
 */
public class Main {

	public static void main(String[] args) {
		Translator t = new MonTranslator(); 
		ClassPool pool = ClassPool.getDefault(); 
		Loader cl = new Loader();
		try {
			cl.addTranslator(pool,t);
			cl.run("javassist1.Test",args); 

		} catch (NotFoundException | CannotCompileException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
