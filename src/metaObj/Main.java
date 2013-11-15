package metaObj;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.Translator;
import javassist.expr.ExprEditor;
import javassist.expr.Handler;
import javassist.tools.reflect.Loader;

/**
 * Classe principale d'appel pour gerer la sauvegarde/restauration
 * 
 * @author allan
 * @author david
 *
 */
public class Main {

	public static void main(String[] args) throws CannotCompileException, NotFoundException {
		
		ClassPool pool = ClassPool.getDefault(); 
		Loader cl = new Loader();
		Translator t = new MonMetaTranslator(cl); 
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
