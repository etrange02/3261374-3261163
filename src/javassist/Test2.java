package javassist;

import javassist.ClassPool;
import javassist.Loader;
import javassist.Translator;

public class Test2 {

	public static void main( String [] args) throws Throwable { 
		Translator t = new MonTranslator(); 
		ClassPool pool = ClassPool.getDefault(); 
		Loader cl = new Loader();
		cl.addTranslator(pool,t);
		cl.run("test.Test3",args); 
		}
}
