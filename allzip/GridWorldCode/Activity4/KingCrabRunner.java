import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;

/**
 * This class runs the <code>KingCrab</code> critters. <br />
 */
public class KingCrabRunner
{
    public static void main(String[] args)
    {
        BoundedGrid<Actor> grid = new BoundedGrid<Actor>(10,10);
        ActorWorld world = new ActorWorld(grid);
        world.add(new Location(0, 3), new Rock());
        world.add(new Location(0, 4), new Rock());
        world.add(new Location(0, 5), new Flower());
        world.add(new Location(1, 3), new Rock());
        world.add(new Location(1, 5), new Flower());
        world.add(new Location(2, 3), new Rock());
        world.add(new Location(2, 4), new Flower());
        world.add(new Location(2, 5), new Rock());
        world.add(new Location(1, 4), new KingCrab());
        world.show();
    }
}
