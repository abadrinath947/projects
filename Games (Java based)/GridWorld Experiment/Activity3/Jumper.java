/**
 * Jumper class - similar to Bug but jumps two spaces instead of one. Drops
 * a blossom everytime it moves and turns every 1-5 steps (random). 
 * 
 * @author Anirudhan Badrinath
 * @since Mar 12 2018
 */
import info.gridworld.actor.*;
import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.gui.*;
import java.awt.Color;

public class Jumper extends Bug
{
	// steps left till turn
    private int turnTime;
    // var to store how many acts without moving
    private int safeGuard;
    /**
     * Constructs a blue jumper.
     */
    public Jumper()
    {
		// set turn time and color
        turnTime = 5;
        safeGuard = 0;
        setColor(Color.BLUE);
    }

    /**
     * Moves if it can move, turns otherwise.
     */
    public void act()
    {
		// set grid object
        Grid<Actor> gr = getGrid();
        // do nothing if there is a null grid
        if (gr == null)
            return;
        // get the location and store it
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection()).getAdjacentLocation(getDirection());
        // decrement time till turning
        turnTime--;
        // turn every 5 steps
        if (turnTime == 0){
            turnTime = 5;
            turn();
            safeGuard++;
        }
        // if turned 16 times without moving, die
        if (safeGuard >= 15) 
        	removeSelfFromGrid();
		// if the jumper can move, move, otherwise, turn
        if (canMove()) {
        	try {
        		moveTo(next);
        		Blossom b = new Blossom((int)(Math.random() * 5) + 1);
            	b.putSelfInGrid(gr, loc);
            	safeGuard = 0;
        	} catch (IllegalArgumentException ex) {
        		turn();
        		safeGuard++;
        	}
        }
        else {
            turn();
            safeGuard++;
        }

    }
    /**
     * Tests whether this bug can move forward into a location that is empty or
     * contains a blossom.
     * @return true if this bug can move.
     */
    public boolean canMove()
    {
		// create grid object
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return false;
        Location loc = getLocation();
        // check if next location is 
        Location next = loc.getAdjacentLocation(getDirection()).getAdjacentLocation(getDirection());
        if (!gr.isValid(next))
            return false;
        Actor neighbor = gr.get(next);
        return (neighbor == null) || (neighbor instanceof Blossom);
        // ok to move into empty location or onto blossom
        // not ok to move onto any other actor
    }
}
