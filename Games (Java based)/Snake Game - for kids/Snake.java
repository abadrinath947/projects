import java.util.ArrayList;

/**
 *	An ArrayList of Coordinate objects representing
 *	a snake on a two-dimensional grid.
 *
 *	@author	Mr Greenstein
 *	@since	November 4, 2017
 */
public class Snake extends ArrayList<Coordinate> {
		
	/*
	 *	Constructor for making a Snake that is 5 grids high facing north.
	 *	Places the snake head at row and col and the tail below.
	 *	Precondition: The board must have at least row + 4 rows.
	 */
	public Snake(int row, int col) {
		// add the head
		add(new Coordinate(row, col));
		// add the tail
		for (int a = 1; a < 5; a++)
			add(new Coordinate(row + a, col));
	}
	
	public Snake() {
		this(3, 3);
	}
	
	
}