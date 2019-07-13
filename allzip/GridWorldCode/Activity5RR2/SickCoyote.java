import info.gridworld.actor.Actor;
public class SickCoyote extends Actor
{
	private int lifetime, threshold;
	
	public SickCoyote()
	{
		threshold = 10;
		lifetime = threshold;
		setColor(null);
	}
	
	public SickCoyote(int life)
	{
		threshold = 10;
		lifetime = life;
		setColor(null);
	}
	
	public void act()
	{
		lifetime--;
		if(lifetime == 0)
		{
			Coyote c = new Coyote();
			c.putSelfInGrid(getGrid(), getLocation());
		}
	}
	
}
