import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Flower;

public class StoneRunner
{
    public static void main(String[] args)
    {
        BoundedGrid<Actor> mygrid = new BoundedGrid<Actor>(20,20);
        ActorWorld world = new ActorWorld(mygrid);
        
        // Upper left grid
        for (int row = 2; row < 7; row++)
        	for (int col = 2; col < 7; col++)
        		world.add(new Location(row,col),new Stone());
        // Lower left grid
        for (int row = 13; row < 18; row++)
        	for (int col = 2; col < 7; col++)
        		world.add(new Location(row,col),new Stone());
        // Upper right grid
        for (int row = 2; row < 7; row++)
        	for (int col = 13; col < 18; col++)
        		world.add(new Location(row,col),new Stone());
        // Lower right grid
        for (int row = 13; row < 18; row++)
        	for (int col = 13; col < 18; col++)
        		world.add(new Location(row,col),new Stone());

        world.show();
    }
}
