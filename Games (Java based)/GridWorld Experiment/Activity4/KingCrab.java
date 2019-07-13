import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;
import java.util.ArrayList;
/**
 * KingCrab eats certain everything in to its North, Northwest and northeast.
 * 
 * @author Anirudhan Badrinath
 * @version March 13, 2018
 */
public class KingCrab extends CrabCritter
{


		 public void processActors(ArrayList<Actor> actors) { 
			 for (Actor a : actors) {
				  Location aLoc = a.getLocation();
				  Location aMoveTo = aLoc.getAdjacentLocation(getLocation().getDirectionToward(aLoc));
				  if(getGrid().isValid(aMoveTo)) a.moveTo(aMoveTo);
				  else a.removeSelfFromGrid(); 
		}
}
} 
