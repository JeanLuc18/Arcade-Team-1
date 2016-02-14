package ag1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import arcadia.Game;



public class FallingBlock extends Enemy{
	
	public FallingBlock(float x, float y, int width, int height) {
		super(x, y, width, height);
		this.setVelY(2);
		
	}
	
	public boolean canFall(){
		if(this.getY() > Game.HEIGHT)
			return false;
		else
			return true;
	}
	
	public void tick(LinkedList<GameObject> objects, Player player) {
		
//		if(!canFall()){
//			this.setVelY(0);
//		}
		
		//else if(canFall()){
			this.x += velX;
			this.y += velY;
			//System.out.println(y);
			this.velY += gravity;
			this.setVelY(velY);
		//}
		
	}
	public void render(Graphics2D g){
		g.setColor(Color.RED);
		g.fillRect((int)x, (int)y, width, height);
		
		if(!canFall()){
			//g.setColor(new Color(128,128,128,128));
		}
		
		
	}
	public void collided(Player p){
		System.out.println("YOU SUCK");
	}
}
