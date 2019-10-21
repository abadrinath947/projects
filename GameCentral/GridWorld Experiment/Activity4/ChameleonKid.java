/**
 * ChameleonKid is like the critter but only changes color when it is
 * right in front
 * 
 * @author Anirudhan Badrinath
 * @since today
 */

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import java.util.ArrayList;

public class ChameleonKid extends ChameleonCritter
{
	
     public ArrayList<Actor> getActors()
     {
         ArrayList<Actor> actors = new ArrayList<Actor>();
         int[] dirs = {Location.AHEAD, Location.HALF_CIRCLE};
         for (Location loc : getLocationsInDirections(dirs))
         {
             Actor a = getGrid().get(loc);
             if (a != null)
             actors.add(a);
         }
         return actors;
     }

    public ArrayList<Location> getLocationsInDirections(int[] directions)
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        Grid gr = getGrid();
        Location loc = getLocation();
        for (int d : directions)
        {
            Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);
            if (gr.isValid(neighborLoc))
            locs.add(neighborLoc);
        }
        return locs;
    }
}
