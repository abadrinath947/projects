import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;

import java.awt.Color;

/**
 * This class runs a world that contains <code>RockHound</code> critters. <br />
 */
public class RockHoundRunner
{
    public static void main(String[] args)
    {
        BoundedGrid<Actor> mygrid = new BoundedGrid<Actor>(10,10);
        ActorWorld world = new ActorWorld(mygrid);
        world.add(new Location(7, 7), new Flower());
        world.add(new Location(8, 3), new Flower());
        world.add(new Location(3, 2), new Flower());
        world.add(new Location(2, 7), new Flower());
        world.add(new Location(5, 8), new Flower());
        world.add(new Location(1, 3), new Flower());
        world.add(new Location(7, 8), new Rock());
        world.add(new Location(8, 5), new Rock());
        world.add(new Location(3, 3), new Rock());
        world.add(new Location(2, 8), new Rock(Color.BLUE));
        world.add(new Location(5, 2), new Rock(Color.ORANGE));
        world.add(new Location(5, 5), new Rock(Color.PINK));
        world.add(new Location(1, 5), new Rock(Color.RED));
        world.add(new Location(7, 2), new Rock(Color.YELLOW));
        world.add(new Location(4, 4), new RockHound());
        world.add(new Location(5, 8), new RockHound());
        world.add(new Location(9, 1), new RockHound());
        world.show();
    }
}
