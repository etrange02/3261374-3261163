package metaObj;

import javassist.tools.reflect.Metaobject;

public class MetaTransac extends Metaobject {

	public MetaTransac(Object self, Object[] args){
		super(self,args);
	}
	
	@Override
	public Object trapMethodcall(int arg0, Object[] arg1) throws Throwable {
		if(getMethodName(arg0).startsWith("set") && getObject().getClass().isAnnotationPresent(fr.upmc.aladyn.transactionables.annotations.Transactionable.class)){
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
