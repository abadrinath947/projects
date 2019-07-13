/**
 *	A row/column pair on a two-dimensional grid.
 * 
 * 	@author Anirudhan Badrinath
 * 	@since Nov 14, 2017
 */
 
 public class Coordinate {
	private int row, col;
	 
	public Coordinate(int row, int col){
		this.row = row;
		this.col = col;
	}
	 
	/** Accessor methods */
	 
	public int getRow() { return row; }
	public int getCol() { return col; }
	
	/** 
	 * Create a string of the Coordinate
	 * @return 		a string of the coordinate
	 */
	
	@Override
	public String toString() { return "(" + row + "," + col + ")"; }
	
	/** 
	 * Test whetther two Coordinates are equal
	 * @param other 		another Coordinate object
	 * @return 				true if this coordinate equals other
	 * 						false otherwise
	 */
	
	@Override
	public boolean equals(Object other) {
		if (other != null && other instanceof Coordinate &&
			row == ((Coordinate)other).row && col == ((Coordinate)other).col)
			return true;
		else
			return false;
			
	}
		
}
