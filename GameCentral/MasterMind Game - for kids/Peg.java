/**
 *	Defines the peg used in the MasterMind game.
 *	Used inside the PegArray class.
 *
 *	@author	Mr Greenstein
 *	@since	September 23, 2017
 */
public class Peg {
	private char letter;
	
	/* Default constructor, letter is X */
	public Peg() {
		letter = 'X';
	}
	
	/* Constructor in which you assign letter */
	public Peg(char l) {
		letter = l;
	}
	
	// Accessor and modifier methods
	public char getLetter() { return letter; }
	
	// Setter method
	public void setLetter(char c) { letter = c; }
}
