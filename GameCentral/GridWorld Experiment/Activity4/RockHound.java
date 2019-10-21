/**
 * Rockhound eats all the rocks.
 * 
 * @author Anirudan Badrinath
 * @since today
 */

import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;
import java.util.ArrayList;
public class RockHound extends Critter
{
 /**
 * Processes the actors. Implemented to "eat" (i.e. remove) all rocks
 *
 * Precondition: All objects in actors are contained in the
 * same grid as this critter.
 * @param actors the actors to be processed
 */
 public void processActors(ArrayList<Actor> actors) {
		for (Actor a : actors)
			if (a instanceof Rock)
				a.removeSelfFromGrid();
 }
} 
