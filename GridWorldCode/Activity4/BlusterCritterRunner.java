import java.awt.Color;
import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Rock;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;

/**
 * This class runs a world that contains <code>BlusterCritter</code>. <br />
 */
public class BlusterCritterRunner
{
    public static void main(String[] args)
    {
        BoundedGrid<Actor> mygrid = new BoundedGrid<Actor>(10,10);
        ActorWorld world = new ActorWorld(mygrid);
        world.add(new Location(7, 8), new Rock());
        world.add(new Location(3, 3), new Rock());
        world.add(new Location(1, 3), new Rock());
        world.add(new Location(2, 2), new Rock());
        world.add(new Location(2, 8), new Rock(Color.PINK));
        world.add(new Location(1, 5), new Rock(Color.ORANGE));
        world.add(new Location(7, 2), new Rock(Color.YELLOW));
        world.add(new Location(2, 1), new BlusterCritter(0));
        world.add(new Location(4, 4), new BlusterCritter(2));
        world.add(new Location(5, 8), new BlusterCritter(5));
        world.show();
    }
}
