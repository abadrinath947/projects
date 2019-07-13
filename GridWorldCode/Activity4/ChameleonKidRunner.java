import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Rock;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;
import java.awt.Color;

/**
 * This class runs a world that contains <code>ChameleonKid</code>; critters. <br />
 */
public class ChameleonKidRunner
{
    public static void main(String[] args)
    {
        BoundedGrid<Actor> mygrid = new BoundedGrid<Actor>(10,10);
        ActorWorld world = new ActorWorld(mygrid);
        world.add(new Location(7, 8), new Rock(Color.CYAN));
        world.add(new Location(3, 6), new Rock(Color.CYAN));
        world.add(new Location(3, 3), new Rock(Color.WHITE));
        world.add(new Location(3, 1), new Rock(Color.GREEN));
        world.add(new Location(0, 3), new Rock(Color.MAGENTA));
        world.add(new Location(0, 9), new Rock(Color.GRAY));
        world.add(new Location(4, 7), new Rock(Color.GRAY));
        world.add(new Location(2, 8), new Rock(Color.ORANGE));
        world.add(new Location(1, 1), new Rock(Color.GREEN));
        world.add(new Location(1, 7), new Rock(Color.YELLOW));
        world.add(new Location(5, 3), new Rock(Color.GRAY));
        world.add(new Location(5, 5), new Rock(Color.PINK));
        world.add(new Location(1, 5), new Rock(Color.RED));
        world.add(new Location(7, 2), new Rock(Color.YELLOW));
        world.add(new Location(0, 6), new ChameleonKid());
        world.add(new Location(2, 7), new ChameleonKid());
        world.show();
    }
}
