import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;

/**
 * This class runs the <code>QuickCrab</code> critters. <br />
 */
public class QuickCrabRunner
{
    public static void main(String[] args)
    {
        BoundedGrid<Actor> grid = new BoundedGrid<Actor>(10,10);
        ActorWorld world = new ActorWorld(grid);
        world.add(new Location(7, 5), new Rock());
        world.add(new Location(5, 4), new Rock());
        world.add(new Location(5, 7), new Rock());
        world.add(new Location(7, 3), new Rock());
        world.add(new Location(7, 8), new Flower());
        world.add(new Location(1, 3), new Flower());
        world.add(new Location(1, 4), new Flower());
        world.add(new Location(1, 5), new Flower());
        world.add(new Location(2, 3), new Flower());
        world.add(new Location(2, 5), new Flower());
        world.add(new Location(3, 3), new Flower());
        world.add(new Location(3, 4), new Flower());
        world.add(new Location(3, 5), new Flower());
        world.add(new Location(6, 6), new Bug());
        world.add(new Location(5, 3), new Bug());
        world.add(new Location(2, 4), new QuickCrab());
        world.add(new Location(7, 4), new QuickCrab());
        world.show();
    }
}
