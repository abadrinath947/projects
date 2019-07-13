/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment Karel4.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends NewImprovedKarel {

	public void run() {
	while (true){
		if (facingEast()){
	whenFacingEast();
}
		if (facingWest()){
			whenFacingWest();
		}
	
	}

public void whenFacingEast(){
	putBeeper();
	move();
	if (frontIsClear()){
	move();
}
else{
	
		turnLeft();

		move();
		putBeeper();
		turnLeft();
	}}

public void whenFacingWast(){
	putBeeper();
	move();
	if (frontIsClear()){
	move();
}
else{
	
		turnRight();

		move();
		putBeeper();
		turnRight();
	}}
}
