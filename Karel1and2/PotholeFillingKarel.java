/*
 * File: PotholeFillingKarel.java
 * -----------------------------
 * The PotholeFillingKarel class extends the basic Karel class
 * so that Karel fills a pothole with a beeper.
 */

import stanford.karel.*;

//public class PotholeFillingKarel extends Karel {
public class PotholeFillingKarel extends NewImprovedKarel {

	public void run() {
		move();
		fillPothole();
		move();
		
	}
	//decomposed turnRight()
	/**public void turnRight(){
		//replace 3 turnLeft() commands with turnAround(); turnLeft();
		turnAround();
		turnLeft();
	}
	public void turnAround(){
		turnLeft();
		turnLeft();
	}
	*/
	public void fillPothole(){
		turnRight();
		move();
		if (noBeepersPresent()){
			if (beepersInBag()){
				putBeeper();
			}
		}
		turnAround();
		move();
		turnRight();
	}
    
}
