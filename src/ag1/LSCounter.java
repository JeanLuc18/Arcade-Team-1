package ag1;

import java.awt.Graphics2D;

public class LSCounter extends GameObject {

	String Score;
	
	public LSCounter(String s){
		super(1024/2 + 25, 196);
		Score = s;
	}
	
	@Override
	public void tick() {
		x += velX;
		y += velY;
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

}
