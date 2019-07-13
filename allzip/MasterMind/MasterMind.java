/**
 *	Plays the game of MasterMind.
 *
 *	MasterMind is a 1 player game in which a player guesses a random 4 letter
 *	long "master" keyword generated using a random letter generator with letters from
 *	'A' to 'F'. The game utilizes Peg objects in a PegArray object to create this master
 *	PegArray keyword. The player then inputs a 4 letter keyword.
 *
 *	The player can either have exact matches, which correspond to the correct order on the 
 *	"master" keyword or partial matches, which do not correspond to the correct order, but still
 *	have the same letters. Presents congratulations message when game is won (if all the
 *	letters are correctly guessed) or an "uh-oh" message when failure to guess in 10
 *	given tries.	
 *
 *	@author Anirudhan Badrinath
 *	@since 10/10/17
 */

public class MasterMind {

	// Fields
	private boolean reveal;			// true = reveal the master combination
	private PegArray[] guesses;		// the array of guessed peg arrays
	private PegArray master;		// the master (key) peg array
	private int round; 				// the number of guesses - 1
	
	// Constants
	private final int PEGS_IN_CODE = 4;		// Number of pegs in code
	private final int MAX_GUESSES = 10;		// Max number of guesses
	private final int PEG_LETTERS = 6;		// 6 = A through F
	
	/**
	 * Constructor for the MasterMind object. Creates an array of 
	 * PegArrays and stores it in guesses as well as instantiating
	 * the PegArrays inside the guesses array. Instantiates a master
	 * PegArray with the key. 
	 */ 
	public MasterMind() {
		guesses = new PegArray[MAX_GUESSES]; //initializes the guesses array
		master = new PegArray(PEGS_IN_CODE); //intializes master array
		for (int i = 0; i < MAX_GUESSES; i++) 
			// initializes PegArray objects in each element of array
			guesses[i] = new PegArray(PEGS_IN_CODE);
	}
	
	/**
	 * Instantiates a MasterMind object and runs the game
	 * @param args 		CLI arguments
	 */
	public static void main(String[] args) {
		MasterMind game = new MasterMind();
		game.run(); 
	}
	
	/** 
	 * Prints introduction message and creates random master array. It 
	 * plays the game by reading user input and stops game if won. 
	 * Postcondition: finished playing the game
	 */
	public void run() {
		// print intro message
		printIntroduction();
		// print starting message
		Prompt.getString("Hit the Enter key to start the game");
		// create random master PegArray
		setMasterPegArray();
		// print preliminary scoreboard
		printBoard();
		// reads user input and commences game
		readInput();
	}
	
	/**
	 * Sets the master Peg to a random set of four char values from
	 * 'A' to 'F'
	 * Precondition: empty array of length PEGS_IN_CODE
	 * Postcondition: array with random char values from 'A' to 'F'
	 */
	public void setMasterPegArray() {
		// Sets each element of the master PegArray to a random value
		// over 'A'
		for (int index = 0; index < PEGS_IN_CODE; index++) {
			int range = (int)(Math.random() * PEG_LETTERS);
			master.getPeg((char)index).setLetter((char)('A' + range));
		}
	}
	
	/**
	 * Checks how many exact matches there are between the master array
	 * and the guesses inputted by the user.
	 * @return 		Number of exact matches
	 */
	public int checkExact() {
		 return guesses[round].getExactMatches(master);
	}
	
	/**
	 * Checks how many partial matches there are between the master array
	 * and the guesses inputted by the user.
	 * @return 		Number of partial matches
	 */
	public int checkPartial() {
		return guesses[round].getPartialMatches(master);
	}
	
	/**
	 * Reads user input and checks the partial matches and exact matches.
	 * Determines if the game should continue or finish.
	 * Postcondition: game has finished (won or lost)
	 * @return 		1 if player wins, 0 if player loses
	 */
	public int readInput() {
		String input = "";
		int partial = 0, exact = 0;
		for (round = 0; round < MAX_GUESSES; round++){
			System.out.println("\nGuess "+ (round + 1) + "\n");
			// Prompt user continuously if input isn't valid and display error message
			do {
				input = Prompt.getString("Enter the code using (A,B,C,D,E,F). " +
				"For example, ABCD or abcd from left-to-right").toUpperCase();
			} while (isInputValid(input));
			// cycle through each character and put it into guesses array of PegArray's
			for (int letters = 0; letters < PEGS_IN_CODE; letters++) 
				guesses[round].getPeg((char)letters).setLetter(input.charAt(letters));
			// Find partial and exact matches and store them in ints
			partial = checkPartial();
			exact = checkExact();
			// game is over if there 4 exacts or 10 rounds
			if (exact == PEGS_IN_CODE || round + 1 == MAX_GUESSES)
				reveal = true;
			// print scoreboard
			printBoard();
			// if game is finished, congratulations message is printed and successful quit
			if (exact == PEGS_IN_CODE) {
				System.out.println("\nNice work! You found the master code in " + (round + 1) + 
				" guesses.\n"); 
				return 1;
			}
		}
		// game is finished and lost, oops message printed and unsuccessful quit
		System.out.println("\nOops. You were unable to find the" + 
				" solution in 10 guesses.\n");
		return 0;
		
	}
	
	/**
	 * Checks if the user input is valid (a character from 'A' to 'F' and
	 * a length of 4 characters).
	 * Precondition: input is a String
	 * @param input	User input
	 * @return 		Whether input is valid
	 */
	public boolean isInputValid(String input) {
		if (input.length() == 0)
			return true;
		for (int i = 0; i < input.length(); i++) 
				if (input.length() != PEGS_IN_CODE || (input.charAt(i) < 'A' || input.charAt(i) > 'F')) {
					System.out.println("ERROR: Bad input, try again.");
					return true;
				}
		return false;
	}
	/**
	 *	Print the introduction screen
	 */
	public void printIntroduction() {
		System.out.println("\n");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("| ___  ___             _              ___  ___ _             _                       |");
		System.out.println("| |  \\/  |            | |             |  \\/  |(_)           | |                      |");
		System.out.println("| | .  . |  __ _  ___ | |_  ___  _ __ | .  . | _  _ __    __| |                      |");
		System.out.println("| | |\\/| | / _` |/ __|| __|/ _ \\| '__|| |\\/| || || '_ \\  / _` |                      |");
		System.out.println("| | |  | || (_| |\\__ \\| |_|  __/| |   | |  | || || | | || (_| |                      |");
		System.out.println("| \\_|  |_/ \\__,_||___/ \\__|\\___||_|   \\_|  |_/|_||_| |_| \\__,_|                      |");
		System.out.println("|                                                                                    |");
		System.out.println("| WELCOME TO MONTA VISTA MASTERMIND!                                                 |");
		System.out.println("|                                                                                    |");
		System.out.println("| The game of MasterMind is played on a four-peg gameboard, and six peg letters can  |");
		System.out.println("| be used.  First, the computer will choose a random combination of four pegs, using |");
		System.out.println("| some of the six letters (A, B, C, D, E, and F).  Repeats are allowed, so there are |");
		System.out.println("| 6 * 6 * 6 * 6 = 1296 possible combinations.  This \"master code\" is then hidden     |");
		System.out.println("| from the player, and the player starts making guesses at the master code.  The     |");
		System.out.println("| player has 10 turns to guess the code.  Each time the player makes a guess for     |");
		System.out.println("| the 4-peg code, the number of exact matches and partial matches are then reported  |");
		System.out.println("| back to the user. If the player finds the exact code, the game ends with a win.    |");
		System.out.println("| If the player does not find the master code after 10 guesses, the game ends with   |");
		System.out.println("| a loss.                                                                            |");
		System.out.println("|                                                                                    |");
		System.out.println("| LET'S PLAY SOME MASTERMIND!                                                        |");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("\n");
	}
	
	/**
	 *	Print the peg board to the screen
	 */
	public void printBoard() {
		// Print header
		System.out.print("+--------+");
		for (int a = 0; a < PEGS_IN_CODE; a++) System.out.print("-------+");
		System.out.println("---------------+");
		System.out.print("| MASTER |");
		for (int a = 0; a < PEGS_IN_CODE; a++)
			if (reveal)
				System.out.printf("   %c   |", master.getPeg(a).getLetter());
			else
				System.out.print("  ***  |");
		System.out.println(" Exact Partial |");
		System.out.print("|        +");
		for (int a = 0; a < PEGS_IN_CODE; a++) System.out.print("-------+");
		System.out.println("               |");
		// Print Guesses
		System.out.print("| GUESS  +");
		for (int a = 0; a < PEGS_IN_CODE; a++) System.out.print("-------+");
		System.out.println("---------------|");
		for (int g = 0; g < MAX_GUESSES - 1; g++) {
			printGuess(g);
			System.out.println("|        +-------+-------+-------+-------+---------------|");
		}
		printGuess(MAX_GUESSES - 1);
		// print bottom
		System.out.print("+--------+");
		for (int a = 0; a < PEGS_IN_CODE; a++) System.out.print("-------+");
		System.out.println("---------------+");
	}
	
	/**
	 *	Print one guess line to screen
	 *	@param t	the guess turn
	 */
	public void printGuess(int t) {
		System.out.printf("|   %2d   |", (t + 1));
		// If peg letter in the A to F range
		char c = guesses[t].getPeg(0).getLetter();
		if (c >= 'A' && c <= 'F')
			for (int p = 0; p < PEGS_IN_CODE; p++)
				System.out.print("   " + guesses[t].getPeg(p).getLetter() + "   |");
		// If peg letters are not A to F range
		else
			for (int p = 0; p < PEGS_IN_CODE; p++)
				System.out.print("       |");
		System.out.printf("   %d      %d    |\n",
							guesses[t].getExact(), guesses[t].getPartial());
	}

}
