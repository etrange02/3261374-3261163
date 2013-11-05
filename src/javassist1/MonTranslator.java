package javassist1;

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
		
		boolean classTr = false;
		if(cc.hasAnnotation(fr.upmc.aladyn.transactionables.annotations.Transactionable.class))
			classTr = true;
		
		CtMethod[] tabM = cc.getMethods();
		for(CtMethod m : tabM){
			if(classTr && m.getName().startsWith("set")){
				System.out.println(classname+" "+m.getName()+" - 1");
				javassist1.Main.changeSet(cc,m);
				System.out.println(classname+" "+m.getName()+" - 1,2");
			}
			else {
				System.out.println(classname+" "+m.getName()+" - 2");
				Object[] tabA = null;
				try {
					tabA = m.getAnnotations();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				for(int i=0;i<tabA.length;i++){
					if(tabA[i] instanceof Transactionable){
						System.out.println(classname+" "+m.getName()+" - 2,2");
						javassist1.Main.changeMethodeTr(cc, m);
						break;
					}
				}
				System.out.println(classname+" "+m.getName()+" - 2,3");
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
