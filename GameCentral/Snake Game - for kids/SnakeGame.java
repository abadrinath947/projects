/**
 *	SnakeGame is a one player game which is based on a SnakeBoard
 *	tile system as well as a Snake object, which is based on the 
 *	Coordinate class and properties of ArrayLists. The game itself
 *	involves moving around a snake to "eat" a target, which then 
 *	disappears. A new target appears and the process repeats until
 *	the snake takes up the whole board. This is very similar to the
 *	classic mobile games, but crashing into the walls do not hurt
 *	the snake. If the snake is stuck, nothing happens. 
 *	
 *	@author	Anirudhan Badrinath
 *	@since	11/27/17
 */

import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SnakeGame {
	
	private Snake snake;		// the snake in the game
	private SnakeBoard board;	// the game board
	private Coordinate target;	// the target for the snake
	private int score;			// the score of the game
	private final int WIDTH = 25; // width of the board
	private final int HEIGHT = 20; // height of the board
	  
	  /**
	   * Constructor which initializes the board with the specified
	   * width and height, initializes the Snake object with a random 
	   * location and the initializes the target.
	   */
	  public SnakeGame() {
		  // create new SnakeBoard of specified height and width
		  board = new SnakeBoard(HEIGHT, WIDTH);
		  // since 5 is the default size of a new Snake object
		  snake = new Snake((int)(Math.random() * (WIDTH - 5)) + 1,
				  (int)(Math.random() * (HEIGHT - 5)) + 1);

		  createNewTarget();
	  }
	  /**
	   * Main method which starts and runs the game. Creates
	   * the game object.
	   * @param args		CLI arguments
	   */
	  public static void main(String[] args)
	  {
		  // create new SnakeGame object
		  SnakeGame newGame = new SnakeGame();
		  // check if user is inputting file
		  if (args.length == 1)
			  newGame.restoreGame(args[0]);

		  newGame.playGame();
	  }
	  /**
	   * Plays the game until it quits due to the board filling up
	   * or user quit. Detects user input and moves the snake with
	   * the keys w, a, s and d. User can see help message or quit 
	   * the game as well.
	   */
	  public void playGame()
	  {
		    boolean quit = false;
		    // play game in a do-while loop
		    do {
			      board.printBoard(snake, target);
			      String str = Prompt.getString("Score: " + score + 
			    		  " (w - North, s - South," + " d - East, a - West, h - Help)");
			      // if user chooses to go north, west, east or south
			      if (str.equalsIgnoreCase("w") || str.equalsIgnoreCase("a")
			    		  || str.equalsIgnoreCase("s") || str.equalsIgnoreCase("d"))
			    	  moveSnake(str.toLowerCase().charAt(0));

			      // if user chooses to save game
			      else if (str.equalsIgnoreCase("f")) {
			    	  saveGame();
			    	  quit = true;
			      }
			      // if user wants help message
			      else if (str.equalsIgnoreCase("h"))
			      {
			    	  	System.out.println();
			    	  	System.out.println("Commands:");
			    	  	System.out.println("\tw - move north");
			  	    	System.out.println("\ts - move south");
			  	    	System.out.println("\td - move east");
				  	    System.out.println("\ta - move west");
				  	    System.out.println("\th - help");
				  	    System.out.println("\tf - save game to file");
				  	    System.out.println("\tq - quit");
				  	    Prompt.getString("Press enter to continue");
			      }
			      // if user wants to quit
			      else if (str.equalsIgnoreCase("q")) {
			    	  if (Prompt.getString("Do you want to quit? (y or n)").toLowerCase().equals("y")) 
			    		  quit = true;
			      }
			// quit if whole board is taken up or if the area is filled
		    } while (!quit && snake.size() <= WIDTH * HEIGHT); 
		    
		    // display final scores
		    System.out.println();
		    System.out.println("Final score = " + score + 
		    		" \n \nThanks for playing the SnakeGame!!!");
	  }
	  /**
	   * Translates snake to a position given a row and column.
	   * Increases score if the target position is met
	   * @param x 			the x coordinate
	   * @param y			the y coordinate
	   */
	  public void doMove(int x, int y) {
			// checks if the location to which to move is valid
		    if (isValidLocation(x, y))
		    {
		      if (isMatchingSpot(x, y)) {
				  // if the target spot is snake head
		    	  score++;
		    	  createNewTarget();
		      }
		      // removes the tail
		      else
		        snake.remove(snake.size() - 1);
		      // add a new head at new coordinate
		      snake.add(0, new Coordinate(x, y));
		    }
	  }
	  /**
	   * Checks if the coordinate to which the user is moving,
	   * which will become the location of the snake's head 
	   * is the same place as the target. 
	   * @param x 			the row
	   * @param y			the column
	   * @return 			whether snake head location is 
	   * 					target location
	   */
	  public boolean isMatchingSpot(int x, int y) {
		  // if the target spot is the spot to which user moves
		  if (target.equals(new Coordinate(x, y)))
			  return true;
		  return false;
	  }
	  /**
	   * Creates a random target location, checks for validity and sets target
	   * to the new coordinate created.
	   */
	  public void createNewTarget()
	  {
		  int x = 0, y = 0; // initialize target coordinate row and col
		  do {
			  x = (int)(Math.random() * WIDTH) + 1; // set random
			  y = (int)(Math.random() * HEIGHT) + 1; // set random
		  } while (!isValidLocation(x, y));
		  // set target coordinate using random, valid row and col
		  target = new Coordinate(x, y);
	  }
	  /**
	   * Saves game into a file by storing individual values for the score,
	   * the location of the target and the coordinates of the snake head and
	   * body.
	   */
	  public void saveGame()
	  {
		System.out.println();
	    String str = Prompt.getString("File name to save game");
	    PrintWriter outFile = OpenFile.openToWrite(str);
	    // print score
	    outFile.println(score);
	    // print target coordinates
	    outFile.println(target.getRow() + " " + target.getCol());
	    // print all of snake coordinates
	    for (int i = 0; i < snake.size(); i++) 
	      outFile.println(((Coordinate)snake.get(i)).getRow() + " " +
	    		  ((Coordinate)snake.get(i)).getCol());
	    // display message to user
	    System.out.println();
	    System.out.println("Game saved to file " + str);
	    outFile.close();
	  }
	  /**
	   * Checks if the location presented is valid and not in the same
	   * location as the target. If the numbers exceed the limits, 
	   * return that it is not valid. Otherwise, return valid.
	   * @param row		the row 
	   * @param col		the column
	   * @return		whether coordinate is valid
	   */
	  public boolean isValidLocation(int row, int col)
	  {
		  // if it's out of bounds or will interfere with border
		  if (row < 1 || row > WIDTH || col < 1 || col > HEIGHT) 
		      return false;
		  // if its on same place as the snake's body or head
		  for (int i = 0; i < snake.size(); i++) 
			  if ((row == ((Coordinate) snake.get(i)).getRow()) 
					  && col == (((Coordinate) snake.get(i)).getCol())) 
				  return false;
	    
		  return true;
	  }
	  /**
	   * Restore the score, location of target, and each coordinate of the snake
	   * from the file inputted by the user in the CLI args. Alert user that game was
	   * restored and close the created Scanner.
	   * @param file		the name of the file
	   */
	  public void restoreGame(String file)
	  {
		  // given that the file is not empty string
		  if (file.length() > 0) {
			  	// clear the newly created Snake in the constructor
			  	snake.clear();
			  	// create a Scanner to read the file
			  	Scanner fileScanner = null;
			  	try { 
			  		fileScanner = new Scanner(new File(file));
			  	}
			  	catch (FileNotFoundException ex) {
			  		System.out.println("File not found.");
			  		System.exit(0);
			  	}
			  	// first number represents score
			    score = fileScanner.nextInt();
			    // second and third represent target coordinates
			    int x = fileScanner.nextInt();
			    int y = fileScanner.nextInt();
			    target = new Coordinate (x, y);
			    // the rest represent coordinates of the snake
			    while (fileScanner.hasNext()) 
			    	snake.add(new Coordinate(fileScanner.nextInt(), fileScanner.nextInt()));
			    // close scanner
			    fileScanner.close();
			  
			    System.out.println();
			    System.out.println("Game restored from file " + file);
	  		}
	  }
	  /** 
	   * Moves the snake one space to the north, south, east or west. Uses the 
	   * doMove method.
	   * @param direction		indicating whether 'w' (north), 'a' (west),
	   * 						's' (south) or 'd' (east)
	   */
	  public void moveSnake(char direction)
	  {
		// use move function to stay in same row and change column
		  switch (direction) {
			  // if pressing w
		  case 'w':
			  doMove(((Coordinate) snake.get(0)).getRow(), ((Coordinate)snake.get(0)).getCol() - 1);
			  break;
			  // if pressing a
		  case 'a':
			  doMove(((Coordinate) snake.get(0)).getRow() - 1, ((Coordinate)snake.get(0)).getCol());
			  break;
			  // if pressing s
		  case 's':
			  doMove(((Coordinate) snake.get(0)).getRow(), ((Coordinate)snake.get(0)).getCol() + 1);
			  break;
			  // if pressing d
		  case 'd':
			  doMove(((Coordinate) snake.get(0)).getRow() + 1, ((Coordinate)snake.get(0)).getCol());
			  break;
		  }
	    
	  }
}
