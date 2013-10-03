package javassist;

import java.io.IOException;

public class Main {
	
	
	
	public static void main(String[] args) {
		ClassPool pool = ClassPool.getDefault();
		
		
		try {
			CtClass cc = pool.get("javassist.Print");
			CtMethod cm = cc.getDeclaredMethod("draw");
			cm.insertAfter("{System.out.println(\"test\");}");
			cc.writeFile();
			System.out.println("done");
		} catch (CannotCompileException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Print p = new Print();
		p.draw();
	}

}
