/*
 * File: DoubleBeeperKarel.java
 * -----------------------------
 * The DoubleBeeperKarel class extends the basic Karel class
 * so that Karel doubles the number of beepers on a corner.
 */

import stanford.karel.*;

public class DoubleBeeperKarel extends NewImprovedKarel {

	public void run() {
		move();
		doubleBeepers();
		move();
	}
	
	public void doubleBeepers() {
		
		while (beepersPresent()){
			if (facingWest()){
			turnRight();
			turnRight();
		}
			pickBeeper();
				move();
				putBeeper();
			
			move();
			putBeeper();
			turnAround();
			move();
			move();
		}
		
		turnAround();
		move();
		
		while(beepersPresent()){
			pickBeeper();
			move();
			putBeeper();
			turnAround();
			move();
			turnAround();
		}
		
		
	}
	    
}
