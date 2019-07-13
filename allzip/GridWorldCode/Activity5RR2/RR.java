
import java.util.ArrayList;
 
import info.gridworld.actor.Critter;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;
import info.gridworld.actor.Actor;
public class RR extends Critter
{
    public RR()
    {
        setDirection(0);
        setColor(null);
    }
    
     public void processActors(ArrayList<Actor> actors)
     {
     }
       
     public ArrayList<Location> getMoveLocations()
     {
         ArrayList<Location> locs = new ArrayList<Location>();
         int[] dirs = {0, 45, 90, 135, 180, 225, 270, 315};
         for(int dir: dirs)
         {
             Location loc1 = getLocation().getAdjacentLocation(dir);
             Location loc2 = loc1.getAdjacentLocation(dir);
             Location loc3 = loc2.getAdjacentLocation(dir);
             if(getGrid().isValid(loc1) && (getGrid().get(loc1) instanceof Boulder || getGrid().get(loc1) instanceof Coyote || getGrid().get(loc1) == null))
                 locs.add(loc1);
             if(getGrid().isValid(loc2) && getGrid().get(loc1) == null && (getGrid().get(loc2) instanceof Boulder || getGrid().get(loc2) instanceof Coyote || getGrid().get(loc2) == null))
                 locs.add(loc2);
             if(getGrid().isValid(loc3) && getGrid().get(loc2) == null && getGrid().get(loc1) == null  && (getGrid().get(loc3) instanceof Boulder || getGrid().get(loc3) instanceof Coyote || getGrid().get(loc3) == null))
                 locs.add(loc3);
         }
         return locs;
     }
      
     public void makeMove(Location loc)
     {
         if(loc == null)
             return;
         Actor a = getGrid().get(loc);
         if(a instanceof Boulder)
         {
             Kaboom boom = new Kaboom();
             boom.putSelfInGrid(getGrid(), a.getLocation());
             removeSelfFromGrid();
             return;
         }
         if(a instanceof Coyote)
         {
             SickCoyote c = new SickCoyote();
             ArrayList<Location> locs = getGrid().getEmptyAdjacentLocations(a.getLocation());
             if (locs.size() != 0)
             {
                 int r = (int) (Math.random() * locs.size());
                 c.putSelfInGrid(getGrid(), locs.get(r));
             }
         }
         moveTo(loc);
     }
}