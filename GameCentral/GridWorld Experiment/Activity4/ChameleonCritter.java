/**
 * Chameleon Critter
 *
 * @author Anirudhan Badrinath
 * @since today
 */


import info.gridworld.actor.*;
import info.gridworld.gui.*;
import info.gridworld.grid.*;
import info.gridworld.world.*;
import java.awt.Color;

import java.util.ArrayList;

/**
 * Chameleon Critter
 */
public class ChameleonCritter extends Critter
{
	private final double DARKENING_FACTOR = 0.1;
    /**
     * Randomly selects a neighbor and changes this critter's color to be the
     * same as that neighbor's. If there are no neighbors, no action is taken.
     */
     public void processActors(ArrayList<Actor> actors)
     {
          int n = actors.size();
          if (n == 0)
          {
          darken();
          return;
          }

          int r = (int) (Math.random() * n);
          Actor other = actors.get(r);
          setColor(other.getColor());
     }

    /**
     * Turns towards the new location as it moves.
     */
    public void makeMove(Location loc)
    {
          setDirection(getLocation().getDirectionToward(loc));
          super.makeMove(loc);
    }

    private void darken()
    {
          Color c = getColor();
          int red = (int) (c.getRed() * (1 - DARKENING_FACTOR));
          int green = (int) (c.getGreen() * (1 - DARKENING_FACTOR));
          int blue = (int) (c.getBlue() * (1 - DARKENING_FACTOR));
          setColor(new Color(red, green, blue));
    }
}
