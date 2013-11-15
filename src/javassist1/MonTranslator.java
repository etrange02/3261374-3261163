package javassist1;

import java.io.IOException;

import fr.upmc.aladyn.transactionables.annotations.Transactionable;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.Translator;
import javassist.expr.ExprEditor;
import javassist.expr.Handler;

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

		boolean classTr = false;
		if(cc.hasAnnotation(fr.upmc.aladyn.transactionables.annotations.Transactionable.class))
			classTr = true;

		CtMethod[] tabM = cc.getMethods();
		for(CtMethod m : tabM){
			if( classTr && m.getName().startsWith("set")){
				m.insertBefore("fr.upmc.aladyn.transactionables.TransacPool.Get().saveObject(Thread.currentThread().getId(),this);");
			}
			else {
				if(m.hasAnnotation(fr.upmc.aladyn.transactionables.annotations.Transactionable.class)){
					changeMethodeTr(cc, m);
				}
			}
		}

	}

	@Override
	public void start(ClassPool pool) throws NotFoundException,CannotCompileException {}

	/**
	 * Modifie une méthode transactionnable pour y inserer le system de sauvegarde/restauration
	 * 
	 * @param cc CtClass dont on veut modifier la methode
	 * @param cm La methode a modifier
	 * @throws CannotCompileException 
	 * @throws NotFoundException 
	 */
	public static void changeMethodeTr(CtClass cc,CtMethod cm) throws CannotCompileException, NotFoundException{
		// on cree un contexte
		cm.insertBefore("{fr.upmc.aladyn.transactionables.TransacPool.Get().transactionableMethod(Thread.currentThread().getId());}");

		// insert la restauration des objet transactionnable au debut de tout les catch de l'utilisateur
		cm.instrument(new ExprEditor() {
			public void edit(Handler ha) throws CannotCompileException{
				ha.insertBefore("fr.upmc.aladyn.transactionables.TransacPool.Get().restore(Thread.currentThread().getId());");
			}
		});
		cm.addCatch("fr.upmc.aladyn.transactionables.TransacPool.Get().restore(Thread.currentThread().getId()); fr.upmc.aladyn.transactionables.TransacPool.Get().endMethod(Thread.currentThread().getId()); throw $e;",ClassPool.getDefault().get("java.lang.Exception"));
	}

}
