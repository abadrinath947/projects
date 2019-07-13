/**
 *	A class to print out the SnakeGame board to the screen.
 *	It has utilities to print the board given a Snake object and
 *	a target Coordinate object.
 *	The height and width of the board must be provided to the constructor.
 *	The structure of the board will look like this:
 *
 *	+ - - - - - - - - - - - +
 *	|                       |
 *	|       @ * *           |
 *	|           *           |
 *	| +         *           |
 *	|           *           |
 *	|                       |
 *	+ - - - - - - - - - - - +
 *
 *	@author	Anirudhan Badrinath
 *	@since	27-11-17
 */
import java.util.ArrayList;
public class SnakeBoard extends ArrayList {
	
	private int widthOfBoard;		// The width of the board
	private int heightOfBoard;		// The height of the board
	
	private char[][] board;			// The 2D array to hold the board
	
	// Constructor
	public SnakeBoard(int height, int width) {
		heightOfBoard = height;
		widthOfBoard = width;
		// create the 2D array to hold the board, plus 2 for the borders
		board = new char[heightOfBoard + 2][widthOfBoard + 2];
		// fill in the border character
	}
	
	/**
	 *	Print the board to the screen. The board will contain the Snake's location
	 *	and the target's location. The snake's head will be an ampersand (@) and
	 *	its tail will be asterisks (*). The target will be a plus sign (+).
	 *	@param snake		a Snake object
	 *	@param target		a Coordinate object of the target
	 */
	public void printBoard(Snake snake, Coordinate target) {
		// remove existing old stuff
		clearArray();
		// add border
		fillBorder();
		// set target 
		board[target.getCol()][target.getRow()] = '+';
		placeSnake(snake);
	    	   
	    printArray(board);
	}
	
	
	// Helper methods go here
	/**
	 * Return width of the board.
	 * @return		width of the board
	 */
	public int getWidth() { return widthOfBoard; }
	
	/**
	 * Return height of the board.
	 * @return		height of the board
	 */
	public int getHeight() { return heightOfBoard; }
	
	/** 
	 * Fill the board array with the border characters
	 */
	 public void fillBorder(){
		// Fill in the corners with '+'
		board[0][0] = '+';
		board[0][widthOfBoard + 1] = '+';
		board[heightOfBoard + 1][0] = '+';
		board[heightOfBoard + 1][widthOfBoard + 1] = '+';
		// Fill in top and bottom rows with '-'
		for (int a = 1; a <= widthOfBoard; a++) {
			board[0][a] = '-';
			board[heightOfBoard + 1][a] = '-';
		}
		// Fill in the left and right sides with '|'
		for (int a = 1; a <= heightOfBoard; a++){
				board[a][0] = '|';
				board[a][widthOfBoard + 1] = '|';
		}
	 }
	 
	 /** 
	  * Print a 2D array of characters to the screen.
	  * @param arr		2D array of characters
	  */
	  public void printArray(char[][] arr){
		// print each row and column with a space
		for (int row = 0; row < arr.length; row++){
			for (int col = 0; col < arr[row].length; col++)
				System.out.print(arr[row][col] + " ");
			System.out.println();
		}
	  }
	  
	  /**
	   * To put spaces into into a two dimensional array of characters.
	   */
		public void clearArray(){
			// set all elements to space char (32)
		   for (int row = 0; row < board.length; row++)
				for (int col = 0; col < board[row].length; col++)
						board[row][col] = ' ';
		
		}
		
		/**
		 * Place snake inside the board depending on its location. Use '@' to represent
		 * the head and '*' for its body.
		 */
		public void placeSnake(Snake snake) {
			// set the coordinate of the snake's head from the first index of the ArrayList
			board[((Coordinate) snake.get(0)).getCol()][((Coordinate) snake.get(0)).getRow()] = '@';
			for (int i = 1; i < snake.size(); i++) 
			  // set body coordinates using other elements of ArrayList
		      board[((Coordinate) snake.get(i)).getCol()][((Coordinate) snake.get(i)).getRow()] = '*';
		}
		
	/********************************************************/
	/********************* For Testing **********************/
	/********************************************************/
	/**
	 * Tester main method
	 */
	public static void main(String[] args) {
		// Create the board
		int height = 10, width = 15;
		SnakeBoard sb = new SnakeBoard(height, width);
		// Place the snake
		Snake snake = new Snake(3, 3);
		// Place the target
		Coordinate target = new Coordinate(1, 7);
		// Print the board
		sb.printBoard(snake, target);
	}
	
}
