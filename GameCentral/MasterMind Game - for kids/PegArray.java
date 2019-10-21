/**
 *  This class creates and manages one array of pegs from the game MasterMind.
 *  
 *  Initializes an array of Pegs in a PegArray object, which can be accessed
 *  and set using public methods. 
 *  
 *  It calculates how many partial and exact matches there are between a user's
 *  input and the random master array, which can be calculated and accessed.
 *
 *  @author Anirudhan Badrinath
 *  @since 10/10/17
*/

public class PegArray {

	// array of pegs
	private Peg [] pegs;

	// the number of exact and partial matches for this array
	// as matched against the master.
	// Precondition: these values are valid after getExactMatches() and getPartialMatches()
	//				are called
	private int exactMatches, partialMatches;
		
	/**
	 *	Constructor
	 *	@param numPegs	number of pegs in the array
	 */
	public PegArray(int numPegs) {
		// initializes array
		pegs = new Peg[numPegs];
		// new Peg objects in the array
		for (int i = 0; i < numPegs; i++) {
			pegs[i] = new Peg();
		}
	}
	
	/**
	 *	Return the peg object
	 *	@param n	The peg index into the array
	 *	@return		the peg object
	 */
	public Peg getPeg(int n) { return pegs[n]; }
	
	/**
	 *  Finds exact matches between master (key) peg array and this peg array
	 *	Postcondition: field exactMatches contains the matches with the master
	 *  @param master	The master (code) peg array
	 *	@return			The number of exact matches
	 */
	public int getExactMatches(PegArray master) 
	{
		int sum = 0;
		// a match whenever the same index of both have the same value
		for (int i = 0; i < pegs.length; i++) 
			if (master.getPeg(i).getLetter() == pegs[i].getLetter())
				sum++;
		// set private instance variable to local variable to hold value
		exactMatches = sum;
		return sum;
	}
	/**
	 *  Find partial matches between master (key) peg array and this peg array
	 *	Postcondition: field partialMatches contains the matches with the master
	 *  @param master	The master (code) peg array
	 *	@return			The number of partial matches
	 */
	public int getPartialMatches(PegArray master) {
		PegArray copy = new PegArray(pegs.length);
		int sum = 0; 	// number of matches
		boolean inputNeeded = true;
		for (int i = 0; i < pegs.length; i++)
			copy.getPeg(i).setLetter(pegs[i].getLetter());
		// loop through the peg array twice
		for (int i = 0; i < pegs.length; i++) {
			for (int j = 0; j < pegs.length; j++) {
				// if the initial peg in master (in index i) is equal to any peg in guesses
				if (inputNeeded && copy.pegs[j].getLetter() == master.getPeg(i).getLetter()) {
					sum++;
					inputNeeded = false;
					copy.getPeg(j).setLetter('X');
				}	
			}
			inputNeeded = true; // reset when it finishes one value
		}
		// set private instance variable to local variable to hold value
		partialMatches = sum - getExactMatches(master);	
		return partialMatches;
	}
	
	// Accessor methods
	// Precondition: getExactMatches() and getPartialMatches() must be called first
	public int getExact() { return exactMatches; }
	public int getPartial() { return partialMatches; }

}
