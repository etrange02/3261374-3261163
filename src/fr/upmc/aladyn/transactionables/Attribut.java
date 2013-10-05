package fr.upmc.aladyn.transactionables;

/**
 * 24/09/2013
 * Associe un nom de varaible à sa valeur. Respecte les types simples.
 * @author David Lecoconnier
 * @author Allan Mottier
 * Useless
 */

public class Attribut {

	private String variable;
	private Object value;
	private boolean isSimple;
	
	public Attribut(String varString, Object value) {
		this.variable = varString;
		this.value = null;
		this.isSimple = false;
	}
	
	public Attribut(String varString, Object value, boolean simple) {
		this.variable = varString;
		this.value = null;
		this.isSimple = simple;
	}
	
	/*public Attribut(String variString, int intValue) {
		this.variable = variString;
		this.value = new Integer(intValue);
		this.isSimple = true;
	}
	
	public Attribut(String variString, String stringValue) {
		this.variable = variString;
		this.value = new String(stringValue);
		this.isSimple = true;
	}
	
	public Attribut(String variString, float floatValue) {
		this.variable = variString;
		this.value = new Float(floatValue);
		this.isSimple = true;
	}

	public Attribut(String variString, boolean boolValue) {
		this.variable = variString;
		this.value = new Boolean(boolValue);
		this.isSimple = true;
	}*/
	
	public String getVariable() {
		return variable;
	}

	public Object getValue() {
		return value;
	}

	public boolean isSimple() {
		return isSimple;
	}
}
