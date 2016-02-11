
package ag1;

import java.awt.Graphics2D;
import java.util.LinkedList;

/**
 * 
 * @author Steve Amatangelo
 *
 * Enemy superclass. All enemies are derived from this class.
 */

public class Enemy extends GameObject{
	
	float speed = 2;        //Speed in horizontal direction, constant

	public Enemy(float x, float y, int width, int height){
		super(x, y, width, height, GOID.Enemy);
	}

	/**
	 * Function update - update status of enemy object
	 * 					 called every tick
	 * @param - graphics g from HGame1.java tick()
	 * @param - HGame1 object
	 * Calling method - HGame1.java tick()
	 */
	public void update(Graphics2D g, HGame1 game){
		
	}

	public void tick(LinkedList<GameObject> objects) {}
	public void render(Graphics2D g) {}

}
