package fr.upmc.aladyn.metaObj;

import javassist.tools.reflect.Metaobject;

/**
 * Implémentation spécifique de la classe Metaobject
 * Gere la mise en place de la sauvegarde et de la restauration pour tout ce qui est Transactionable
 * 
 * @author David Lecoconnier
 * @author Allan Mottier
 * 2013-11-01
 */

public class MetaTransac extends Metaobject {

	private static final long serialVersionUID = 1L;

	public MetaTransac(Object self, Object[] args){
		super(self,args);
	}

	@Override
	public Object trapMethodcall(int arg0, Object[] arg1) throws Throwable {
		if(getMethodName(arg0).startsWith("set") && getObject().getClass().isAnnotationPresent(fr.upmc.aladyn.transactionables.annotations.Transactionable.class)){
			// On sauvegarde l'objet dans le cas d'un appel à une méthode set d'une classe Transactionable
			fr.upmc.aladyn.transactionables.TransacPool.Get().saveObject(Thread.currentThread().getId(),getObject());
		}
		if(getClassMetaobject().getMethod(arg0).isAnnotationPresent(fr.upmc.aladyn.transactionables.annotations.Transactionable.class)){
			try{
				// On cree un contexte
				fr.upmc.aladyn.transactionables.TransacPool.Get().transactionableMethod(Thread.currentThread().getId());
				return super.trapMethodcall(arg0, arg1);
			}catch(Throwable t){
				// on restaure tout le contexte
				fr.upmc.aladyn.transactionables.TransacPool.Get().restore(Thread.currentThread().getId());
				throw t;
			}finally{
				// on supprime le contexte
				fr.upmc.aladyn.transactionables.TransacPool.Get().endMethod(Thread.currentThread().getId());
			}
		}

		return super.trapMethodcall(arg0, arg1);
	}

}
