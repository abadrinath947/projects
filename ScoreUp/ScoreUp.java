/**
 *  This program contains the rules of the game ScoreUp and runs the game.
 * 	
 * 	ScoreUp is a two player game consisting of five rounds and two turns
 * 	per round (one for each player). Both players commence and have 9 tiles
 * 	numbered from 1 to 9. Each player rolls 2 die and chooses, based on the
 * 	tiles he or she has, which tiles to use to represent the sum of the 2 
 * 	die. Any combination of possible sums available on the tiles that adds
 * 	up to the sum on the 2 die is fair game. The game continues until the 
 * 	dice sum yields a number whose sum cannot be formed with the tiles
 * 	remanining. The score is based on the sum of the value of each tile
 * 	used to form sums during the game.
 * 
 * 	Each player does this twice in a round and there are 5 rounds in total
 * 	before a final scoreboard with the final scores are shown. The highest
 * 	total score is the winner!
 *	
 *
 *	The Prompt, ScoreUpPlayer, DiceGroup, and Dice classes are required.
 *
 *  @author Anirudhan Badrinath
 *  @since 	9/26/2017
 */

public class ScoreUp {
	
	private ScoreUpPlayer player1, player2;	// the two players
	private DiceGroup groupOfDice;		// the group of dice
	private boolean player1Turn;		// true = player1's turn, false = player2's turn
	private int round;					// current round in the game (1, 2, 3, ...)
	private TileBoard board;			// the board of tiles
	private int neededNumber;			// sum of the numbers on dice 
	
	// Constants
	public final int NUM_DICE = 2;		// total number of dice
	public final int NUM_ROUNDS = 5;	// total number of rounds
	public final int NUM_TILES = 9; 	// total number of tiles in a round
	
	/**
	 * Initializes the players, the tileboard on which the game will be played
	 * and the two dice that will be used.
	 */
	public ScoreUp(){
		player1Turn = true; 
		player1 = new ScoreUpPlayer(NUM_ROUNDS); // Create new player1 object
		player2 = new ScoreUpPlayer(NUM_ROUNDS); // Create new player2 object
		board = new TileBoard(NUM_TILES); // Create new tileboard for game
		groupOfDice = new DiceGroup(NUM_DICE); // Create new dice for game
	}
	/**
	 * Game object is declared and initialized as well 
	 * as run.
	 * @param args 		The CLI arguments
	 */
	public static void main(String[] args){
		ScoreUp game = new ScoreUp();
		game.run();
	}
	/**
	 * Runs the game by printing the introductory message, receiving names of
	 * both players, finding the first player using the die, then playing NUM_ROUNDS
	 * rounds of the game. Finally, the winner is determined using the total score 
	 * and printed out.
	 */
	public void run(){
		printIntroduction();
		getNames();
		findFirst();
		loopRound(NUM_ROUNDS);
		determineWinner();
	}
	/**
	 * Congratulate whoever is the higher scorer or remark if it was a tie. Print out 
	 * the final score board as well.
	 */
	public void determineWinner() {
		int player1FinalScore = player1.getTotalScore();
		int player2FinalScore = player2.getTotalScore();
		printRoundScore();	
		// Displays final congratulations message and winner
		System.out.print("\nCONGRATULATIONS >>> ");
		if (player1FinalScore > player2FinalScore)
			System.out.println(player1.getName() + " for being the HIGH SCORER.\n");
		else if (player2FinalScore > player1FinalScore)
			System.out.println(player2.getName() + " for being the HIGH SCORER.\n");
		else 
			System.out.println("It was a TIE.\n");
	}
	/**
	 * Receives the names of both the players using the Prompt class and converts them 
	 * to uppercase. Displays a welcome message.
	 */
	public void getNames(){
		do {
			player1.setName(Prompt.getString("Player 1, please enter your first name").toUpperCase());
		} while (player1.getName().length() == 0);
		
		System.out.println();
		do {
			player2.setName(Prompt.getString("Player 2, please enter your first name").toUpperCase());
		} while (player2.getName().length() == 0);
		
		System.out.println("\nWelcome " + player1.getName() + " and " + player2.getName() + "\n");
	}
	/**
	 * Determines who should play first based on die roll (from DiceGroup class) and
	 * congratulates them. In the case of a tie, the process is repeated.
	 */
	public void findFirst(){
		int player1Total = 0, player2Total = 0;
		
		Prompt.getString("Let's see who will go first. " + player1.getName() + 
				", please hit enter to roll the dice");
		// Roll dice and assign the total value to player 1's total
		groupOfDice.rollDice();
		player1Total = groupOfDice.getTotal();
		groupOfDice.printDice();
		// Roll dice and assign the total value to player 2's total
		Prompt.getString(player2.getName() + ", it's your turn. Please hit" +
		" enter to roll the dice");
		groupOfDice.rollDice();
		player2Total = groupOfDice.getTotal();
		groupOfDice.printDice();
		// If they are the same, then "do over" and call the method again
		if (player1Total == player2Total){
			System.out.println("\n>>>>>>><<<<<<"); 
			System.out.println(">> DO OVER << Both of you got the same value!"); 
			System.out.println(">>>>>>><<<<<<\n");
			findFirst();
		}
		// Otherwise, print congratulations message and proceed with the game
		else {
			System.out.println(player1.getName() + ", you rolled a sum of " + 
			player1Total + ", and, " + player2.getName() + ", you rolled a sum of " + 
			player2Total + ".");
			player1Turn = (player1Total > player2Total);
		
			System.out.println(		"\n*******************");
			if (player1Turn)
				System.out.println(	"* Congratulations * " + player1.getName() + 
				", you rolled a higher number so you get to go first.");
			else
				System.out.println(	"* Congratulations * " + player2.getName() + 
				", you rolled a higher number so you get to go first.");
			System.out.println(		"*******************\n");
		}
	}
	/**
	 * Plays one round of the game (two turns, one for each player). After each
	 * turn, the player switches.
	 */
	public void playRound(){
		// Print beginning of round message
		System.out.println(">>>>>>>>>>>>>><<<<<<<<<<<<<<"); 
		System.out.println(">>> Round " + round + " of 5 rounds <<<<"); 
		System.out.println(">>>>>>>>>>>>>><<<<<<<<<<<<<<\n"); 
		for (int i = 0; i < 2; i++) {
			doPlayerTurn();				// Play 1 turn		
			player1Turn = !player1Turn; // Makes it the other person's turn
		}	
	}
	/**
	 * Plays one turn of the game until there are no valid choices left to 
	 * use. Continuously prompt if there are any errors (e.g. number not on tile,
	 * incorrect sum, etc.). Display message when turn is finished.
	 */
	public void doPlayerTurn() {
		
		board.resetTiles();
		if (player1Turn) 
			Prompt.getString(player1.getName() + ", it's your turn to play." +
			" Please hit enter to roll the dice");
		else if (!player1Turn) 
			Prompt.getString(player2.getName() + ", it's your turn to play." +
			" Please hit enter to roll the dice");
		// Play one turn and assign the returned score to a variable turnScore
		int turnScore = playOneTurn();
		// There will be no choices left once a score is returned
		System.out.println("\nUh-oh, looks like there are no valid" + 
		" choices left ... \n");
		// Enter scores
		if (player1Turn)
			player1.scoreUpRound(round, turnScore);
		else 
			player2.scoreUpRound(round, turnScore);
		// Print scores
		printRoundScore();
		System.out.println();
		// Print turn ending message
		if (player1Turn) 
			Prompt.getString(player1.getName() + ", your turn has ended." +
			" Please hit enter to finish your turn");
		else if (!player1Turn) 
			Prompt.getString(player2.getName() + ", your turn has ended." +
			" Please hit enter to finish your turn");
		System.out.println();
	}
	/**
	 * Determines if another combination of numbers on the tile
	 * can form a specific resultant number given an int array by
	 * using a simple looping algorithm.
	 * @param array 	An integer array of all the tile board numbers left
	 * @return 			Whether another play is possible or not
	 */
	public boolean isPlayPossible(int[] array){
			
		int sum = 0;
		// Initializes two for loops and checks if another
		// play is possible based on possible sums on tileboard
		for (int i = 0; i < array.length; i++){				
				sum += array[i];
				for (int j = 0; j < array.length; j++){
					if (j == i) sum = 0; // make sure element isn't counted twice
					if (sum + array[j] == neededNumber) 
						return true;
					else sum += array[j];	
				}
				sum = 0;
		}
		return false;
	}
	/**
	 * Forms a boolean array using if each individual tile is
	 * scored or not then convert to an easier to use int array
	 * of all the numbers left on the tileboard.
	 * @return 		A converted integer array
	 */
	public int[] transcribeBooleanArray(){
		
		boolean[] boolarray = new boolean[NUM_TILES];
		String temp = "";
		int[] finalArray = new int[NUM_TILES];
		// Creates boolean array from the tileboard
		for (int i = 0; i < NUM_TILES; i++) {
			boolarray[i] = board.isTileScored(i);
		}
		// Creates string concatenation of all the remaining numbers on
		// the tileboard
		for (int i = 1; i <= boolarray.length; i++){
			if (boolarray[i-1] == false) temp += i;
		}	
		// Turns string concat into an int array	
		for (int i = 0; i < temp.length(); i++){
			finalArray[i] = Integer.parseInt("" + temp.charAt(i));
		}
		// Return the finished integer array
		return finalArray;
	}
	/**
	 * Reads user input to determine what their play will be (which
	 * numbers they will choose to use). Converts this user input into 
	 * an easier to use int array with separate digits as separate elements.
	 * @return 		An integer array with the digits of user's input
	 */
	public int[] readInput(){
		int[] inputArray = {}; 		// integer array of user's input
		boolean badInput = false;	// whether it is a valid input	
			do {
				String unformattedNumber = Prompt.getString("Enter the tiles to remove. For example,"
						+ " if you'd like to remove tiles 1, 2, and 5, enter 125 ");
				inputArray = new int[unformattedNumber.length()];
				badInput = false;
				try {
					for (int i = 0; i < unformattedNumber.length(); i++){
						inputArray[i] = Integer.parseInt("" + unformattedNumber.charAt(i));
					}
				}
				catch (NumberFormatException ex){
					badInput = true;
				}
			} while (badInput); 
		// Return finished array
		return inputArray;
	}
	/**
	 * Loops to play NUM_ROUNDS rounds of the game.
	 */
	public void loopRound(int numberOfRounds){
		for (round = 1; round <= numberOfRounds; round++) 
			playRound();
	}
	/**
	 * Plays one turn of the game. Reads user input and removes the tiles
	 * if possible. Stops the turn if no more plays are possible.
	 * @return		Returns the score of the turn
	 */
	public int playOneTurn() {
		int sum = 0, score = 0, count = 0;
		boolean rollDice = true; // whether to roll dice again
		int[] numbersToRemove = {}; // user input array
		do {
			if (rollDice) {
				groupOfDice.rollDice();	
				groupOfDice.printDice();
				neededNumber = groupOfDice.getTotal();
				board.printTiles();
				System.out.println();
				rollDice = false;
			}
			if (isPlayPossible(transcribeBooleanArray())) {
				numbersToRemove = readInput();
				sum = 0;
				for (int i = 0; i < numbersToRemove.length; i++)	
					sum += numbersToRemove[i]; 
				if (isSumEqual(sum, neededNumber) && isInputValid(numbersToRemove)) 
					for (int i = 0; i < numbersToRemove.length; i++) {
						board.clearTile(numbersToRemove[i] - 1);
						score += numbersToRemove[i];
						rollDice = true;
					}
				else rollDice = false;
			}
			else count++;
		} while (count < 1);
		
		return score;
	}
	/**
	 * Checks if the sum provided by the user's inputs is equal to the 
	 * sum of the two dice
	 * @param sum 			sum of user's inputs
	 * @param neededNumber	sum of two dice
	 * @param 				whether they are equal
	 */
	public boolean isSumEqual(int sum, int neededNumber) {
		if (sum == neededNumber)
			return true;
		return false;
	}
	/**
	 * Checks if the numbers inputted by the user are on the tile and if the numbers
	 * inputted are acceptable (1-9, no repeats)
	 * @param numbersToRemove	int array of numbers inputted by user
	 * @param 					whether input can be used
	 */
	public boolean isInputValid(int[] numbersToRemove) {
		if (isScoringPossible(numbersToRemove) && isInRange(numbersToRemove))
			return true;
		return false;
	}
	/**
	 * Checks if the numbers inputted are acceptable (1-9, no repeats)
	 * @param numbersToRemove	int array of numbers inputted by user
	 * @param 					whether input is valid
	 */
	public boolean isInRange(int[] numbersToRemove) {
		for (int i = 0; i < numbersToRemove.length; i++) {
			if (numbersToRemove[i] <= 0 || numbersToRemove[i] > 9)
				return false;
			for (int j = 0; j < numbersToRemove.length; j++) 
				if (j != i && numbersToRemove[i] == numbersToRemove[j])
					return false;				
		}
		return true;
	}
	/**
	 * Determines if the numbers inputted by the user are still on the 
	 * tileboard by loopinig through the boolean array given by the 
	 * TileBoard class
	 * @param numbersToRemove		The numbers inputted by the user
	 * @return						Whether the tile can be removed
	 */
	public boolean isScoringPossible(int[] numbersToRemove) {
		for (int i = 0; i < numbersToRemove.length; i++) {
			if (numbersToRemove[i] > 0 && board.isTileScored(numbersToRemove[i] - 1))
				return false;
		}
		return true;
	}
	
	/**
	 *	Prints the introduction screen
	 */
	public void printIntroduction() {
		System.out.println("\n");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("|           ______   ______   ______   ______   ______   __  __   ______             |");
		System.out.println("|          /\\  ___\\ /\\  ___\\ /\\  __ \\ /\\  == \\ /\\  ___\\ /\\ \\/\\ \\ /\\  == \\            |");
		System.out.println("|          \\ \\___  \\\\ \\ \\____\\ \\ \\/\\ \\\\ \\  __< \\ \\  __\\ \\ \\ \\_\\ \\\\ \\  _-/            |");
		System.out.println("|           \\/\\_____\\\\ \\_____\\\\ \\_____\\\\ \\_\\ \\_\\\\ \\_____\\\\ \\_____\\\\ \\_\\              |");
		System.out.println("|            \\/_____/ \\/_____/ \\/_____/ \\/_/ /_/ \\/_____/ \\/_____/ \\/_/              |");
		System.out.println("|                                                                                    |");
		System.out.println("| WELCOME TO MONTA VISTA SCOREUP!                                                    |");
		System.out.println("|                                                                                    |");
		System.out.println("| ScoreUp is a dice game played between two players.  There are "
							+ NUM_ROUNDS + " rounds in a game   |");
		System.out.println("| of ScoreUp, and the players alternate turns.  In each turn, a player starts with   |");
		System.out.println("| the tiles 1, 2, 3, 4, 5, 6, 7, 8, and 9 showing.  The player then rolls a pair of  |");
		System.out.println("| dice.  After rolling the dice, the player adds up the dots on the dice, and then   |");
		System.out.println("| \"Scores Up\" any combination of numbers that equals the total number of dots        |");
		System.out.println("| showing on the dice. For example, if the total number of dots is 8, the player may |");
		System.out.println("| choose any of the following sets of numbers (as long as all of the numbers in the  |");
		System.out.println("| set have not yet been removed):                                                    |");
		System.out.println("|          8 or 7 & 1 or 6 & 2 or 5 & 3 or 5 & 2 & 1 or 4 & 3 & 1.                   |");
		System.out.println("|                                                                                    |");
		System.out.println("| The player then rolls the dice again, aiming to remove more numbers. The player    |");
		System.out.println("| continues throwing the dice and removing numbers until reaching a point at which,  |");
		System.out.println("| given the results produced by the dice, the player cannot remove any more numbers. |");
		System.out.println("| At that point, the player scores the sum of the numbers that have been removed.    |");
		System.out.println("| For example, if the numbers 2, 3, and 5 remain when the player rolls 6 & 3, the    |");
		System.out.println("| player's score is 35 (1 + 4 + 6 + 7 + 8 + 9 = 35). Play then passes to the next    |");
		System.out.println("| player.  After five rounds, the winner is the player with the highest total.       |");
		System.out.println("|                                                                                    |");
		System.out.println("| LET'S PLAY SOME SCOREUP!                                                           |");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("\n");
	}

	/**
	 *	Prints the Round Scoreboard
	 */
	public void printRoundScore() {
		int num = 0;
		System.out.println("\n  NAME           Round 1    Round 2    Round 3    Round 4    Round 5     Total");
		System.out.println("+---------------------------------------------------------------------------------+");
		System.out.printf("| %-12s |", player1.getName());
		for (int i = 0; i < NUM_ROUNDS; i++) {
			num = player1.getRoundScore(i);
			if (num == 0) System.out.printf("          |", num);
			else System.out.printf("   %3d    |", num);
		}
		System.out.printf("   %4d    |\n", player1.getTotalScore());
		System.out.println("+---------------------------------------------------------------------------------+");
		System.out.printf("| %-12s |", player2.getName());
		for (int i = 0; i < NUM_ROUNDS; i++) {
			num = player2.getRoundScore(i);
			if (num == 0) System.out.printf("          |", num);
			else System.out.printf("   %3d    |", num);
		}
		System.out.printf("   %4d    |\n", player2.getTotalScore());
		System.out.println("+---------------------------------------------------------------------------------+");
	}
}
