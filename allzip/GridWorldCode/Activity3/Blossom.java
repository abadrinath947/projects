/** Blossom - defines a Blossom using the definition of a Flower, becomes
 * darker and has a specified lifespan.
 * 
 * @author Anirudhan Badrinath
 * @since Mar 12 2018
 */
import info.gridworld.actor.Flower;
import java.awt.Color;


public class Blossom extends Flower {
	// defines lifetime of a blossom
	private int lifetime;
	/** default constructor (has 10 lives) */
	public Blossom() { this(10); }
	/** constructor with one argument 
	 * @param lifetime		specified lifetime in steps
	 */
	public Blossom(int lifetime) {
		super(Color.GREEN); 
		this.lifetime = lifetime;
	}
	/** Does the act similar to the flower, but has a specified lifetime
	 */
	public void act() {
		if (lifetime > 0) {
			super.act();
			lifetime--;
		}
		else 
			removeSelfFromGrid();
	}

}
