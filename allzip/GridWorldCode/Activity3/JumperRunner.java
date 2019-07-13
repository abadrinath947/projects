import info.gridworld.grid.Grid;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Flower;
public class JumperRunner
{
 public static void main(String[] args)
 {
		BoundedGrid<Actor> mygrid = new BoundedGrid<Actor>(20,20); 
        ActorWorld world = new ActorWorld(mygrid); 
        world.add(new Location(0,0),new Jumper()); 
        world.add(new Location(0,1),new Jumper()); 
        world.add(new Location(1,0),new Jumper()); 
        world.add(new Location(1,1),new Rock()); 
        world.add(new Location(1,2),new Bug()); 
        world.add(new Location(0,3),new Bug()); 
        world.add(new Location(2,6),new Jumper()); 
        world.add(new Location(2,7),new Jumper()); 
        world.add(new Location(1,7),new Rock()); 
        world.add(new Location(1,6),new Rock()); 
        world.add(new Location(0,7),new Rock()); 
        world.add(new Location(19,1),new Rock()); 
        world.show(); 
 }
}
