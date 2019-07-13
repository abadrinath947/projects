/**
 *	A coordinate on a grid. Integer XY values.
 *
 *	@author Mr Greenstein
 *	@since	March 30, 2018
 */
public class Coordinate implements Comparable<Coordinate>
{
	private int x, y;
		
	public Coordinate(int myX, int myY)
	{
		x = myX;
		y = myY;
	}
	
	/* Accessor methods */
	public int getX() { return x; }
	public int getY() { return y; }
	
	public boolean equals(Object other)
	{
		return compareTo((Coordinate)other) == 0;
	}
	
	/**
	 *	Coordinate is greater when:
	 *	1. x is greater or
	 *	2. x is equal and y is greater
	 *	3. otherwise Coordinates are equal
	 *	@return		negative if less than, 0 if equal, positive if greater than
	 */
	public int compareTo(Coordinate other) {
		if (! (other instanceof Coordinate))
			throw new IllegalArgumentException("compareTo not Coordinate object");
		if (x > ((Coordinate)other).x || x < ((Coordinate)other).x)
			return x - ((Coordinate)other).x;
		if (y > ((Coordinate)other).y || y < ((Coordinate)other).y)
			return y - ((Coordinate)other).y;
		return 0;
	}
	
	public String toString()
	{	return "[ " + x + ", " + y + "]";  }
	
}
