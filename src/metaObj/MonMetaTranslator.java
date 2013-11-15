package metaObj;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import fr.upmc.aladyn.transactionables.annotations.Transactionable;
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
 * Implémentation spécifique de la classe Translator
 * 
 * @author allan
 * @author david
 */
public class MonMetaTranslator implements Translator {

	private Loader loader;
	
	public MonMetaTranslator(Loader l){
		loader=l;
	}
	
	/**
	 * surcharge de la méthode onLoad
	 * 
	 */
	@Override
	public void onLoad(ClassPool pool, String classname) throws NotFoundException,CannotCompileException {
		CtClass cc = pool.get(classname);
		boolean aTrMethod = false;
		for(CtMethod m : cc.getDeclaredMethods()){
			if(m.hasAnnotation(fr.upmc.aladyn.transactionables.annotations.Transactionable.class)){
				aTrMethod = true;
				break;
			}
		}
		if(cc.hasAnnotation(fr.upmc.aladyn.transactionables.annotations.Transactionable.class)|| aTrMethod){
			CtClass curr = cc;
			ArrayList<CtClass> lct = new ArrayList<>();
			while(curr.getName()!="java.lang.Object"){
				lct.add(curr);
				curr = curr.getSuperclass();
			}
			Object[] tabCt =  lct.toArray();
			for(int i=tabCt.length-1;i>-1;i--){
				loader.makeReflective(((CtClass)tabCt[i]).getName(), "metaObj.MetaTransac", "javassist.tools.reflect.ClassMetaobject");
			}
			}
		
		
	}

	@Override
	public void start(ClassPool pool) throws NotFoundException,CannotCompileException {}


}
