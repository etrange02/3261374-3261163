package fr.upmc.aladyn.javassist;

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
 * Gere la mise en place de la sauvegarde et de la restauration pour tout ce qui est Transactionable
 * 
 * @author David Lecoconnier
 * @author Allan Mottier
 * 2013-10-04
 */
public class MonTranslator implements Translator {

	@Override
	public void onLoad(ClassPool pool, String classname) throws NotFoundException,CannotCompileException {
		CtClass cc = pool.get(classname);

		boolean classTr = false;
		if(cc.hasAnnotation(fr.upmc.aladyn.transactionables.annotations.Transactionable.class))
			classTr = true;

		CtMethod[] tabM = cc.getMethods();
		for(CtMethod m : tabM){
			if( classTr && m.getName().startsWith("set")){
				// On sauvegarde l'objet dans le cas d'un appel à une méthode set d'une classe Transactionable
				m.insertBefore("fr.upmc.aladyn.transactionables.TransacPool.Get().saveObject(Thread.currentThread().getId(),this);");
			}
			if(m.hasAnnotation(fr.upmc.aladyn.transactionables.annotations.Transactionable.class)){
				changeMethodeTr(m);
			}
		}
	}

	@Override
	public void start(ClassPool pool) throws NotFoundException,CannotCompileException {}

	/**
	 * Modifie une méthode Transactionable pour y inserer le système de sauvegarde/restauration
	 * 
	 * @param cm La methode a modifier
	 * @throws CannotCompileException 
	 * @throws NotFoundException 
	 */
	public static void changeMethodeTr(CtMethod cm) throws CannotCompileException, NotFoundException{
		// on cree un contexte
		cm.insertBefore("{fr.upmc.aladyn.transactionables.TransacPool.Get().transactionableMethod(Thread.currentThread().getId());}");

		// insert la restauration des objet transactionnable au debut de tout les catch de l'utilisateur
		cm.instrument(new ExprEditor() {
			public void edit(Handler ha) throws CannotCompileException{
				ha.insertBefore("fr.upmc.aladyn.transactionables.TransacPool.Get().restore(Thread.currentThread().getId());");
			}
		});
		
		// ajoute une try/catch englobant
		cm.addCatch("fr.upmc.aladyn.transactionables.TransacPool.Get().restore(Thread.currentThread().getId()); fr.upmc.aladyn.transactionables.TransacPool.Get().endMethod(Thread.currentThread().getId()); throw $e;",ClassPool.getDefault().get("java.lang.Exception"));
	}

}
