/**
 *	The tile board used in the game ScoreUp.
 *	This class stores tiles as either "not scored" (false) or "scored" (true).
 *
 *	@author	Mr Greenstein
 *	@since	September 12, 2017
 */
public class TileBoard {
	
	private boolean [] tiles;	// tiles in one game,
								// false = tile "not scored"; true = tile "scored"
								// tile 1 is index 0, ..., tile 9 is index 8
	
	/**
	 *	Constructor - creates tiles and resets them to all true
	 */							
	public TileBoard(int numTiles) {
		tiles = new boolean[numTiles];
		resetTiles();
	}

	/**
	 *	Returns the score of all the tiles
	 *	@return		the total score of all the tiles
	 */
	public int computeRound() {
		int total = 0;
		for (int i = 0; i < tiles.length; i++)
			if (tiles[i]) total += (i + 1);
		return total;
	}
	
	/**
	 *	Clear (or score up) the tile
	 *	@param num		the index of tile to score (make true)
	 */
	public void clearTile(int num) { tiles[num] = true; }
	
	/**
	 *	Returns the truth (true = scored) value of the tile with index num.
	 *	@param num		the index number of the tile
	 *	@return			true if the tile is "scored"; false if the tile is not "scored"
	 */
	public boolean isTileScored(int num) { return tiles[num]; }
	
	/**
	 *	Resets all of the tiles to "not scored" (all false)
	 */
	public void resetTiles() {
		for (int i = 0; i < tiles.length; i++)
			tiles[i] = false;
	}
	
	/**
	 *	Print the tiles in the game
	 *	Top of the tiles    +-------+
	 *	Sides of the tiles  |       |
	 *	Tile numbers        |   1   |
	 *	Sides of the tiles  |       |
	 *	Botton of tiles     +-------+
	 */
	public void printTiles() {
		// Print top of tiles
		System.out.print("+-------");
		for (int a = 1; a < tiles.length; a++)
			System.out.print("--------");
		System.out.println("+");
		
		// Print sides of tiles
		for (int a = 0; a < tiles.length; a++)
			System.out.print("|       ");
		System.out.println("|");
		
		// Print tile numbers
		System.out.printf("|");
		for (int a = 0; a < tiles.length; a++)
			if (! tiles[a]) System.out.printf("  %2d   |", (a + 1));
			else System.out.printf("       |");
		System.out.println();
		
		// Print sides of tiles
		for (int a = 0; a < tiles.length; a++)
			System.out.print("|       ");
		System.out.println("|");
		
		// Print bottom of tiles
		System.out.print("+-------");
		for (int a = 1; a < tiles.length; a++)
			System.out.print("--------");
		System.out.println("+");
	}

}