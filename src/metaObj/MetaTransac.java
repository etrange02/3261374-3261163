package metaObj;

import javassist.tools.reflect.Metaobject;

public class MetaTransac extends Metaobject {

	public MetaTransac(Object self, Object[] args){
		super(self,args);
		System.out.println("construct meta : "+self.getClass().getName());
	}
	
	@Override
	public Object trapMethodcall(int arg0, Object[] arg1) throws Throwable {
		if(getMethodName(arg0).startsWith("set")&&getObject().getClass().isAnnotationPresent(fr.upmc.aladyn.transactionables.annotations.Transactionable.class)){
			fr.upmc.aladyn.transactionables.TransacPool.Get().saveObject(Thread.currentThread().getId(),getObject());
			System.out.println("Meta : set sauvé : "+getObject().getClass().getName()+";"+getMethodName(arg0));
		}
			
		if(getObject().getClass().getDeclaredMethod(getMethodName(arg0), getParameterTypes(arg0)).isAnnotationPresent(fr.upmc.aladyn.transactionables.annotations.Transactionable.class)){
			System.out.println("Meta : debut method transac : "+getObject().getClass().getName()+";"+getMethodName(arg0));
			try{
				return super.trapMethodcall(arg0, arg1);
			}catch(Throwable t){
				fr.upmc.aladyn.transactionables.TransacPool.Get().restore(Thread.currentThread().getId());
				System.out.println("Meta : catch method transac : "+getObject().getClass().getName()+";"+getMethodName(arg0));
				throw t;
			}finally{
				fr.upmc.aladyn.transactionables.TransacPool.Get().endMethod(Thread.currentThread().getId());
				System.out.println("Meta : end method transac : "+getObject().getClass().getName()+";"+getMethodName(arg0));
			}
		}
		
		System.out.println("Meta : normal method : "+getObject().getClass().getName()+";"+getMethodName(arg0));
		return super.trapMethodcall(arg0, arg1);
	}
	
}
