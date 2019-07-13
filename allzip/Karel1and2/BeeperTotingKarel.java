/*
 * File: BeeperTotingKarel.java
 * -----------------------------
 * The BeeperTotingKarel class extends the basic Karel class
 * so that Karel picks up a beeper from 1st Street and then
 * carries that beeper to the center of a ledge on 2nd Street.
 */

import stanford.karel.*;

public class BeeperTotingKarel extends Karel {

	public void run() {
		// Original moveset
		move();
		pickBeeper();
		move();
		// Turn left once to rotate counterclockwise 90 degrees
		turnLeft();
		// Move forward one block after turning
		move();
		//Turn right
		
		/* turnLeft();
		turnLeft();
		turnLeft();
		*/
		
		turnRight();
		// Move forward two blocks
		move();
		move();
		// Place beeper at (5,2)
		putBeeper();
		// Arrive at final destination (5,3)
		move();
		// Arrived at destination
		
	}
		// Decomposed turnRight
	public void turnRight(){
		turnAround();
		turnLeft();
	}
		// Decomposed turnAround
	public void turnAround(){
		turnLeft();
		turnLeft();
	}
    
}
