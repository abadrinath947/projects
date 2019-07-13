/** Identifier class for SimpleCalc
 * 
 * Stores the name and value of an identifier (variable)
 * to be used in SimpleCalc evaluation
 * 
 * @author Anirudhan Badrinath
 * @since Mar 1, 2018
 */
public class Identifier {
	// store name and value of identifier
	private String name;
	private double value;
	/** constructor to create an identifier */
	public Identifier(String idString, double value) { name = idString; this.value = value; }
	/** returns name of identifier
	 *  @return name	name of identifier
	 */
	public String getName() { return name; }
	/** set the name for the identifier
	 *  @param name 	name of the identifer inputted
	 */
	public void setName(String name) { this.name = name; }
	/**
	 * get value of the identifier
	 * @return value 		value of the identifier
	 */
	public double getValue() { return value; }
	/**
	 * set value of the identifier
	 * @param value 		value of the identifier
	 */
	public void setValue(double value) { this.value = value; }

}
