
/**
 * Coyote - represents a Coyote in GridWorld
 *  
 * @author Anirudhan Badrinath
 * @since 5th april 2018
 */
 
import java.util.ArrayList;
import info.gridworld.grid.Location;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Rock;
public class Coyote extends Critter
{
    private int steps, sleep;
    private boolean placeStone;
    public Coyote()
    {
        setColor(null);
        steps = sleep = 0;
 
        int dir = (int)(Math.random()*8);
        setDirection(dir*45);
    }
 
    public void processActors(ArrayList<Actor> actors)
    {
        boolean act = false;
        if (getGrid().isValid(getLocation().getAdjacentLocation(getDirection())))
            act = getGrid().get(getLocation().getAdjacentLocation(getDirection())) instanceof Actor && 
            		!(getGrid().get(getLocation().getAdjacentLocation(getDirection())) instanceof Boulder);
              
        if (sleep == 0 && (steps > 4 || act || isEdgeOfGrid())) {
            sleep++;
            steps = 0;
        }
          
          else if (sleep == 4 && isEdgeOfGrid()) {
              sleep = 0;
 
              setDirection((int)(Math.random()*8) * 45);
                    
          }
          else if (sleep == 4 && !isEdgeOfGrid()) {
              placeStone = true;
              sleep = 0;
                setDirection((int)(Math.random()*8) * 45);
                    
          }
          else if (sleep != 0) {
              sleep++;
                
          }
    }
      
      public Location selectMoveLocation(ArrayList<Location> locs)
      {     
              if (sleep == 0)
                  return getLocation().getAdjacentLocation(getDirection());
              else
                  return getLocation();
      }
         
      public void makeMove(Location loc)
      {
         if (getGrid().isValid(loc) && getGrid().get(loc) instanceof Boulder) {
             getGrid().get(loc).removeSelfFromGrid();
                
             (new Kaboom()).putSelfInGrid(getGrid(), loc);
             removeSelfFromGrid();
             placeStone = false;
             return;
         }
         else if (getGrid().isValid(loc) && getGrid().get(loc) == null) {
             Location old = getLocation();
             if (!loc.equals(getLocation())) steps++;
             moveTo(loc);
             if (placeStone) {
                 ArrayList<Location> a = getGrid().getEmptyAdjacentLocations(getLocation());
                 if (a.size() != 0)
                 (new Stone()).putSelfInGrid(getGrid(), a.get((int)(Math.random()*a.size())));
                 placeStone = false;
            }
         }
      }
         
      private boolean isEdgeOfGrid() {
          int col = getLocation().getCol(), row = getLocation().getRow(), numCol = getGrid().getNumCols(),
                    numRow = getGrid().getNumRows();
          if (col == 0 || col == numCol - 1 || row == 0 || row == numRow - 1)
              return true;
          return false;
      }
         
        
      
}
