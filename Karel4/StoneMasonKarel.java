/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends NewImprovedKarel {

	public void run() {
		while(notFacingNorth()) {
			while(frontIsClear()) {
				if(beepersPresent())
					pickBeeper();
					move();
				}
				if(facingEast()) {
					turnLeft();
					if(frontIsClear()) {
						move();
						turnLeft();
					}
				}
				else {
					turnRight();
					if(frontIsClear()) {
						move();
						turnRight();
					}
				}
			}
	}

		
	
	
	
}
