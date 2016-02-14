package ag1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import arcadia.Game;



public class FallingBlock extends Enemy{
	
	public FallingBlock(float x, float y, int width, int height) {
		super(x, y, width, height);
		this.setVelY(0);
	}
	
	public boolean canFall(){
		if(this.getY() > Game.HEIGHT)
			return false;
		else
			return true;
	}
	
	public void tick(LinkedList<GameObject> objects) {
		x += velX;
		y += velY;
		velY += gravity/1000;
		
	}
	public void render(Graphics2D g){
		g.setColor(Color.RED);
		g.fillRect((int)x, (int)y, width, height);
		
	}
	public void collided(Player p){
		System.out.println("YOU SUCK");
	}
}
