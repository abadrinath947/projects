import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *  Creates a group of dice (default 5), rolls them, and displays them.
 *
 *  @author Mr Greenstein
 *  @since	September 12, 2017
 */

public class DiceGroup {
	private Die [] die;			// the group of Dice
	private int numDice;		// the number of dice in the group
	
	// Constants (static required if used in constructor)
	private static final int DEFAULT_NUM_DICE = 5;	// default number of dice in group
	
	/* The seven different line images of a die */
	private final String [] LINE = {	" _______ ",
										"|       |",
										"| O   O |",
										"|   O   |",
										"|     O |",
										"| O     |",
										"|_______|" 	};
	
	/**	Constructor for the default DiceGroup */
	public DiceGroup() {
		this(DEFAULT_NUM_DICE);		// default number of Dice
	}
	
	/**	Constructor for DiceGroup specifying the number of dice
	 *	@param num		the number of dice in the group
	 */
	public DiceGroup(int num) {
		if (num > 0 && num < 8) numDice = num;
		else numDice = 5;
		initDice();
		// roll the dice
		rollDice();
	}
	
	/**
	 *	Initialize the DiceGroup
	 */
	public void initDice() {
		// Allocate dice pointers
		die = new Die [numDice];
		// Initialize dice
		for (int i = 0; i < numDice; i++)
			die[i] = new Die();
	}
	
	/**
	 *  Roll all dice.
	 */
	public void rollDice() {
		for (int i = 0; i < die.length; i ++)
			die[i].roll();
	}
	
	/**
	 *  Roll only the dice not in the string "rawHold".
	 *  e.g. If rawHold="24", only roll dice 1, 3, and 5
	 *
	 *  @param rawHold The dice to hold
	 */
	public void rollDice(String rawHold) {
		for (int i = 0; i < die.length; i++) {
			String c = (i + 1) + "";
			if (rawHold.indexOf(c) == -1) die[i].roll();
		}
	}
	
	/**
	 *  @param i The index of the die
	 *  @return  The Dice of the index
	 */
	public Die getDie(int i) { return die[i]; }
	
	/**
	 *	Get the total of all the dice values
	 *	@return			the total of all the dice
	 */
	public int getTotal() {
		int sum = 0;
		for (int i = 0; i < die.length; i++)
			sum += die[i].getValue();
		return sum;
	}
	
	/**
	 *  Prints out the images of the dice
	 */
	public void printDice() {
		printDiceHeaders();
		
		/* Print each line */
		for (int i = 0; i < 6; i++) {
			System.out.print(" ");
			for (int j = 0; j < die.length; j++) {
				printDiceLine(die[j].getValue() + 6 * i);
				System.out.print("     ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 *	Print the tops of the dice figures
	 */
	private void printDiceHeaders() {
		System.out.println();
		for (int i = 1; i < numDice + 1; i++) {
			System.out.printf("   # %d        ", i);
		}
		System.out.println();
	}
	
	/**
	 *	Prints a line of the dice image
	 *  @param value 	The index into the ASCII dice image
	 */
	private void printDiceLine(int value) {
		System.out.print(LINE[getDiceLine(value)]);
	}
	
	/**
	 *  @param value The value of the die print line
	 *  @return  The index into the ASCII dice image
	 */
	private int getDiceLine(int value) {
		if (value < 7) return 0;
		if (value < 14) return 1;
		switch (value) {
			case 20: case 22: case 25:
				return 1;
			case 16: case 17: case 18: case 24: case 28: case 29: case 30:
				return 2;
			case 19: case 21: case 23:
				return 3;
			case 14: case 15:
				return 4;
			case 26: case 27:
				return 5;
			default:	// value > 30
				return 6;
		}
	}
	
	/**	Testing program for DiceGroup	*/
	public static void main(String[] args) {
		DiceGroup dg2 = new DiceGroup(2);
		dg2.rollDice();
		dg2.printDice();
	}
}
