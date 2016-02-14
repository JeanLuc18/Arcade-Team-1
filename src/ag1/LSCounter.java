package ag1;

import java.awt.Graphics2D;
import java.util.LinkedList;

public class LSCounter extends GameObject {

	String Score;
	
	public LSCounter(String s){
		super(1024/2 + 25, 196, GOID.Block);
		Score = s;
	}
	
	@Override
	public void tick(LinkedList<GameObject> objects, Player player) {
		x += velX;
		y += velY;
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

}
