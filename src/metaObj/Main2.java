package metaObj;


public class Main2 {
	public static void main(String[] args) throws Throwable {
		javassist.tools.reflect.Loader cl
		= (javassist.tools.reflect.Loader)Main2.class.getClassLoader();
		cl.makeReflective("test.transactionables.TrTest1", "metaObj.MetaTransac",
				"javassist.tools.reflect.ClassMetaobject");
		cl.run("javassist1.Test", args);
	}
}


