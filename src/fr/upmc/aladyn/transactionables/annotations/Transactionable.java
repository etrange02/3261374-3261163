package fr.upmc.aladyn.transactionables.annotations;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

/** 
 * The annotation <code>Transactionable </code> marks classes of objects that
 * must be made restorables in a previously checkpointed state as well as the
 * methods that w i l l be considered as transactions when called.
 * 
 * <p><strong>Description </strong ></p>
 *
 * The annotation is retained at run−time in order to be used by a meta−object
 * based or a Javassist class transformation that w i l l provide the transaction
 * semantics
 *
 * <p>Created on : 2013−08−29</p>
 *
 * @author <a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
*/
 
@Retention ( RetentionPolicy .RUNTIME)
@Target({ElementType .TYPE, ElementType .METHOD})
public @interface Transactionable { }
