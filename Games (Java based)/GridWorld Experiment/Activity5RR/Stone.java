import info.gridworld.actor.Rock;
import java.awt.Color;
public class Stone extends Rock
{
	private int lifetime;
	private final int threshold = 3;
	
	public Stone()
	{
		setColor(null);
		lifetime = (int)(Math.random() * 200) + 1;
	}
	
	public Stone(int life)
	{
		setColor(null);
		lifetime = life;
	}

	public void act()
	{
		if(lifetime == 0)
		{
			Boulder bould = new Boulder();
			bould.putSelfInGrid(getGrid(), getLocation());
		}
		if(lifetime < threshold)
			setColor(Color.GREEN);
		lifetime--;
	}
}
