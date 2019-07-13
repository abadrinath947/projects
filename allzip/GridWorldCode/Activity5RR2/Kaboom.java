import info.gridworld.actor.Actor;

public class Kaboom extends Actor
{
	private int lifetime;
	private final int threshold = 3;

	public Kaboom()
	{
		setColor(null);
		lifetime = threshold;
	}

	public void act()
	{
		if(lifetime == 0)
			removeSelfFromGrid();
		lifetime--;
	}
}