package javassist;

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

	private static String directoryName;

	public static void main(String[] args) {
		Translator t = new MonTranslator(); 
		ClassPool pool = ClassPool.getDefault(); 
		Loader cl = new Loader();
		try {
			cl.addTranslator(pool,t);
			cl.run("javassist.Test",args); 

		} catch (NotFoundException | CannotCompileException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Modifie une méthode transactionnable pour y inserer le system de sauvegarde/restauration
	 * 
	 * @param cc CtClass dont on veut modifier la methode
	 * @param cm La methode a modifier
	 * @throws CannotCompileException 
	 * @throws NotFoundException 
	 */
	public static void changeMethode(CtClass cc,CtMethod cm) throws CannotCompileException, NotFoundException{
			
			// insert la restauration des objet transactionnable au debut de tout les catch de l'utilisateur
			cm.instrument(new ExprEditor() {
				public void edit(Handler ha) throws CannotCompileException{
					ha.insertBefore("fr.upmc.aladyn.transactionables.TransacThreads.Get().restore(Thread.currentThread().getId()); System.out.println(\"Je suis dans le catch !\");");
				}
			});
			
			// crée une nouvelle méthode du nom de l'ancienne afin d'entourer la dite méthode 
			// d'un try catch et du system de sauvegarde/restauration
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
			src.append(") {");
			src.append( "Object[] tabO = new Object["+(j+1)+"];  tabO[0]=this; " );
			for(i = 0 ; i< j; i++){
				src.append("tabO["+(i+1)+"]= transac_param"+tabO[i]+"; ");
			}
			src.append("fr.upmc.aladyn.transactionables.TransacThreads tt = fr.upmc.aladyn.transactionables.TransacThreads.Get(); tt.save(Thread.currentThread().getId(),tabO); try{ ");
			src.append("return this.transac_"+cm.getName()+"(");
			if(params.length>0){
				for(i = 0 ; i< params.length-1; i++){
					src.append(" transac_param"+i+",");
				}
				src.append(" transac_param"+i);
			}
			src.append(");");
			src.append("  }catch(Exception e){ tt.restore(Thread.currentThread().getId()); throw e; }finally{ tt.endMethod(Thread.currentThread().getId()); } }");
			src.append("}");
			
			// Renomme l'ancienne méthode puis ajoute la remplacante
			cm.setName("transac_"+cm.getName());
			CtMethod nm = CtNewMethod.make(src.toString(), cc);
			nm.setModifiers(cm.getModifiers());;

			cc.addMethod(nm);
	}

	/**
	 * 
	 * @return la chaine représentant le path du repertoire contenant les .class
	 */
	public static String getDirectoryName(){
		if(directoryName==null){
			directoryName = System.getProperty("user.dir") + System.getProperty("file.separator") + "bin";
		}
		return directoryName;
	}
}
