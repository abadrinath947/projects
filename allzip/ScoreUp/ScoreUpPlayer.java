/**
 *  This class is one ScoreUp player.
 *	A ScoreUp player has a name and keeps track of all his/her scores
 *	in the rounds.
 *
 *  @author Mr Greenstein
 *  @since	September 12, 2017
 */

public class ScoreUpPlayer {
	
	private String name;		// name of the player

	private int [] roundScore;	// score of each round of the game
								// round 1 is index 0, round 2 is index 1, ..., 
								
	/**
	 *	Constructor to initialize this player
	 *	@param numRounds		the number of rounds in a game
	 */
	public ScoreUpPlayer(int numRounds) {
		roundScore = new int[numRounds];
		for (int i = 0; i < roundScore.length; i++)
			roundScore[i] = 0;
	}

	/** Accessors and modifiers */
	public void setName(String name) { this.name = name; }
	
	public String getName() { return name; }
	
	public int getRoundScore(int num) { return roundScore[num]; }
	
	/**
	 *	Records the score for the round at index round-1.
	 *	@param round		the round number 1, 2, 3, ...
	 *	@param score		the score for that round
	 */
	public void scoreUpRound(int round, int score) {
		roundScore[round-1] = score;
	}
		
	/**
	 *	@return		the total score of all the rounds
	 */
	public int getTotalScore() {
		int total = 0;
		for (int i = 0; i < roundScore.length; i++) total += roundScore[i];
		return total;
	}
}