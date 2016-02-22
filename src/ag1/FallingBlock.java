package ag1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

import arcadia.Game;



public class FallingBlock extends Enemy{
	private int fallInt;
	private boolean canFall;
	Image fallingblock;
	public FallingBlock(float x, float y, int width, int height) {
		super(x, y, width, height);
		this.setVelY(2);
		try {
			fallingblock = ImageIO.read(HGame1.class.getResource("Graphics/fallingblock.gif")); //help from pixabay.com
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	public void setFallInt(int n){fallInt = n;}
	public boolean canFall(){
		if(this.getY() <= Game.HEIGHT)
			return (canFall = true);
		else
			return (canFall = false);
	}
	
	public void tick(LinkedList<GameObject> objects, Player player) {
		if((player.score) % 6 == 0){
			canFall = true;
		}
		
		if(!canFall){
			this.setVelY(0);
			this.setX(player.getX());
			this.setY(player.getY() - 600);
		}
		
		else{
			
			this.x += velX;
			this.y += velY;
			this.velY += gravity;
			this.setVelY(velY);
			canFall();
		
		}
		
	}
	public void render(Graphics2D g){
		g.setColor(Color.RED);
		g.drawImage(fallingblock,(int)x, (int)y, width, height, null);
		
		if(!canFall()){
			//g.setColor(new Color(128,128,128,128));
		}
		
		
	}
	public void collided(Player p){
		p.lives -= 1;
		this.setX(p.getX());
		this.setY(p.getY() - 600);
		this.setVelY(0);
		this.canFall = false;
		this.fallInt = (fallInt+1)%2;
		System.out.println("got hit by the falling block");
		p.x = p.startingX;
		p.y = p.startingY;
	}
}
