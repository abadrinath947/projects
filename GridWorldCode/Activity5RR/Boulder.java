import info.gridworld.actor.Actor;
import java.awt.Color;
public class Boulder extends Actor
{
	private int lifetime;
	private final int threshold = 3;
	
	public Boulder()
	{
		setColor(null);
		lifetime = (int)(Math.random() * 200) + 1;
	}
	
	public Boulder(int life)
	{
		setColor(null);
		lifetime = life;
	}

	public void act()
	{
		if(lifetime == 0)
		{
			Kaboom boom = new Kaboom();
			boom.putSelfInGrid(getGrid(), getLocation());
		}
		if(lifetime < threshold)
			setColor(Color.RED);
		lifetime--;
	}
}
