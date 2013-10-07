package javassist;

import java.io.IOException;
import java.util.Vector;

import test.transactionables.TrTest1;
import javassist.CannotCompileException;
import javassist.ClassMap;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Loader;
import javassist.NotFoundException;
import javassist.Translator;

public class Test1 {

	/**
	 * @param args
	 */

	public static String directoryName = "C:\\Users\\allan.STARWARS\\workspace\\3261374-3261163\\bin";

//	public static void main(String[] args) {
//		try {
//			ClassPool cp = ClassPool.getDefault();
//
//			CtClass cc = cp.get("test.transactionables.TrTest1");
//			CtClass c2 = cp.get("fr.upmc.aladyn.transactionables.Attribut");
//
//			CtMethod m = cc.getDeclaredMethod("getX");
//			m.addLocalVariable("chose", c2);
//			m.insertBefore("{chose = new fr.upmc.aladyn.transactionables.Attribut(\"chose\",null);System.out.println(\"a\"+chose.getVariable());}");
//
//			CtMethod m2 = cc.getDeclaredMethod("setY");
//			Test1.changeMethode(cc, m2);
//			cc.writeFile(Test1.directoryName);
//		} catch (NotFoundException e) {
//			e.printStackTrace();
//		} catch (CannotCompileException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println("fin change");
//		TrTest1 tt = new TrTest1();
//		System.out.println("fin change2");
//		tt.getX();
//		tt.setY(13);
//		System.out.println("y:"+tt.getY());
//
//	}


	public static void changeMethode(CtClass cc,CtMethod cm){
		try {
			StringBuffer src= new StringBuffer();
			src.append(cm.getReturnType().getName()+" "+cm.getName()+" ( ");
			CtClass[] params = cm.getParameterTypes();

			int i;
			if(params.length>0){
				for(i = 0 ; i< params.length-1; i++){
					src.append(params[i].getName()+" transac_param"+i+",");
				}
				src.append(params[i].getName()+" transac_param"+i);
			}
			int[] tabO = new int[params.length];
			int j=0;
			for(i=0;i<params.length;i++){
				if(!params[i].isPrimitive()){
					tabO[j]=i;
					j++;
				}
			}
			src.append(") { System.out.println(\"debut testing\");");
			src.append( "Object[] tabO = new Object["+(j+1)+"];  tabO[0]=this; " );
			for(i = 0 ; i< j; i++){
				src.append("tabO["+(i+1)+"]= transac_param"+tabO[i]+"; ");
			}
						src.append("fr.upmc.aladyn.transactionables.TransacThreads tt = fr.upmc.aladyn.transactionables.TransacThreads.Get(); tt.save(Thread.currentThread().getId(),tabO); try{ System.out.println(\"try\");");
						src.append("this.transac_"+cm.getName()+"(");
						if(params.length>0){
							for(i = 0 ; i< params.length-1; i++){
								src.append(" transac_param"+i+",");
							}
							src.append(" transac_param"+i);
						}
						src.append(");");
						
						src.append("}catch(Exception e){ tt.restore(Thread.currentThread().getId()); System.out.println(\"catch\");throw e; }finally{ tt.endMethod(Thread.currentThread().getId());} }");
			src.append("}");
			System.out.println("Methode : "+src);
			cm.setName("transac_"+cm.getName());
			CtMethod nm = CtNewMethod.make(src.toString(), cc);
			nm.setModifiers(cm.getModifiers());;

			cc.addMethod(nm);

		} catch (CannotCompileException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

	}

}
