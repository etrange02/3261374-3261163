package javassist;

import java.io.IOException;

import fr.upmc.aladyn.transactionables.annotations.Transactionable;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.Translator;

/**
 * Implémentation spécifique de la classe Translator
 * 
 * @author allan
 * @author david
 */
public class MonTranslator implements Translator {

	/**
	 * surcharge de la méthode onLoad
	 */
	@Override
	public void onLoad(ClassPool pool, String classname) throws NotFoundException,CannotCompileException {
		CtClass cc = pool.get(classname);
		
		CtMethod[] tabM = cc.getDeclaredMethods();
		for(CtMethod m : tabM){
			Object[] tabA = null;
			try {
				tabA = m.getAnnotations();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			for(int i=0;i<tabA.length;i++){
				if(tabA[i] instanceof Transactionable){
					javassist.Main.changeMethode(cc, m);
					break;
				}
			}
		}

		try {
			cc.writeFile(Main.getDirectoryName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(ClassPool pool) throws NotFoundException,CannotCompileException {}

}
