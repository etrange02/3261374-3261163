package javassist;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.Translator;

public class MonTranslator implements Translator {

	@Override
	public void onLoad(ClassPool pool, String classname) throws NotFoundException,CannotCompileException {
		System.out.println("pool.get("+classname+")");
		CtClass cc = pool.get(classname);
		
		CtMethod[] tabM = cc.getDeclaredMethods();
		for(CtMethod m : tabM){
			System.out.println("methode: "+m.getName());
			Object[] tabA = null;
			try {
				tabA = m.getAnnotations();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			for(int i=0;i<tabA.length;i++){
				if(tabA[i].toString().equals("@fr.upmc.aladyn.transactionables.annotations.Transactionable")){
					Test1.changeMethode(cc, m);
					break;
				}
			}
		}
		
		try {
			cc.writeFile(Test1.directoryName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(ClassPool pool) throws NotFoundException,CannotCompileException {}

}
