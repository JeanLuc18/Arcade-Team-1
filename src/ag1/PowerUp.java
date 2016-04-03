package ag1;

import java.awt.Graphics2D;
import java.util.LinkedList;

public class PowerUp extends GameObject{
	
	boolean up = false;
	int move = 0;
	
	public PowerUp(float x, float y, int width, int height){
		super(x, y, width, height, GOID.Enemy);
	}
	
	public void collided(Player p){}
	public void tick(LinkedList<GameObject> objects, Player player) {}
	public void render(Graphics2D g) {}
	
}
