/**
 * BlusterCritter looks for actors around it and uses a courage factor 
 * to darken, etc.
 * 
 * @author Anirudhan Badrinath
 * @since today
 */

import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;
import java.util.ArrayList;
import java.awt.Color;
public class BlusterCritter extends Critter
{
	 private int courageFactor;

	 public BlusterCritter(int c)
	 {
		 super();
		 courageFactor = c;
	 }
 /**
 * Gets the actors for processing. The actors must be contained in the
 * same grid as this critter. Implemented to return the actors that
 * occupy neighboring grid locations within two steps of this critter
 * @return a list of actors that are neighbors of this critter
 */
 public ArrayList<Actor> getActors()
 {
	 ArrayList<Actor> actors = new ArrayList<Actor>();

	 Location loc = getLocation();
	 for(int r = loc.getRow() - 2; r <= loc.getRow() + 2; r++ )
		 for(int c = loc.getCol() - 2; c <= loc.getCol() + 2; c++)
		 {
			 Location tempLoc = new Location(r,c);
			 if(getGrid().isValid(tempLoc))
		 {
		 Actor a = getGrid().get(tempLoc);
		 if(a != null && a != this)
			actors.add(a);
		 }
	 }
	 return actors;
 }
 /**
 * Processes the actors. Implemented to count all the actors within
 * 2 locations of this critter. If there are fewer than courageFactor
 * critters in these locations, this BlusterCritter lightens, otherwise
 * it darkens.
 * Precondition: All objects in <code>actors</code> are contained in the
 * same grid as this critter.
 * @param actors the actors to be processed
 */
 public void processActors(ArrayList<Actor> actors)
 {
	 int count = 0;
	 for (Actor a: actors)
		if (a instanceof Critter)
			count++;
	System.out.println(count + " " + courageFactor);
	 if (count >= courageFactor)
		setColor(getColor().darker());
	 else {
		setColor(getColor().brighter());
	 }
 }
 
}
