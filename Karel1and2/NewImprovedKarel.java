/*
 * File: NewImprovedKarel.java
 * -----------------------------
 * The NewImprovedKarel class extends the basic Karel class
 * so that Karel has two new features: turnRight() and turnAround().
 * This demonstrates ENCAPSULATION.
 */

import stanford.karel.*;

public class NewImprovedKarel extends Karel {
	
	/*	Demonstration of ENCAPSULATION
	 *	Provide an extra service but hide the information about it.
	 *
	 *	The following methods are encapsulated into NewImprovedKarel class.
	 */
	public void turnRight() {
		turnAround();
		turnLeft();
	}
	
	public void turnAround() {
		turnLeft();
		turnLeft();
	}
    
}