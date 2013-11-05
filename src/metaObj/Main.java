package metaObj;

import javassist.tools.reflect.Loader;

public class Main {

	public static void main(String[] args) throws Throwable {
		Loader l = new Loader();
		String[] arg = new String[1];
		arg[0]="metaObj.Main2";
		l.main(arg);
	}

}
