import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Actor;

public class RRRunner
{
    public static void main(String[] args)
    {
        BoundedGrid<Actor> mygrid = new BoundedGrid<Actor>(10,10);
        ActorWorld world = new ActorWorld(mygrid);
        
		world.add(new Location(0, 0), new RR());
		world.add(new Location(2, 0), new Coyote());
		world.add(new Location(2, 1), new Coyote());
		world.add(new Location(2, 2), new Coyote());
		world.add(new Location(0, 2), new Coyote());
		world.add(new Location(1, 2), new Coyote());
		
		world.add(new Location(8, 8), new RR());
		
		world.add(new Location(4, 4), new Coyote());
		world.add(new Location(8, 2), new Coyote());

        world.show();
    }
}
