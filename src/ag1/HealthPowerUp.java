package ag1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.awt.*;

public class HealthPowerUp extends PowerUp {
	
	int relativeY = 0;
	boolean up = true;
	boolean render = true;
	Image powerUpIMG = null;
	
	public HealthPowerUp(float x, float y, int width, int height, Image powerUpIMG) {
		super(x, y, width, height);
		this.powerUpIMG = powerUpIMG;
	}
	
	public void collided(Player p){
		p.lives += 1;
		render = false;
		powerUpIMG = null;
	}
	public void tick(LinkedList<GameObject> objects, Player player) {
		relativeY = oscillate();
		this.setY(relativeY + this.getY());
	}
	public void render(Graphics2D g){
		g.setColor(Color.RED);
		//g.fillRect((int)x, (int)y, width, height);//Colors in block for debugging
		g.drawImage(powerUpIMG,(int)x, (int)y, width, height, null);
	}
	private int oscillate(){
		
		if(up){
			relativeY++;
			if(relativeY > 10)
				up = false;
		}
		else{
			relativeY--;
			if(relativeY == 0){
				up = true;
			}
		}
		
		return relativeY;
		
	}
}
