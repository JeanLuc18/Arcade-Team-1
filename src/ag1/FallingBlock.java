package ag1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

import arcadia.Game;



public class FallingBlock extends Enemy{
	private int fallInt;
	private boolean Fall = false;
	
	Image fallingblock;

	public ArrayList<Integer> fallYs = new ArrayList<>();
	public FallingBlock(float x, float y, int width, int height) {
		super(x, y, width, height);
	}
	
	
	
	public void tick(LinkedList<GameObject> objects, Player player) {

		if(!Fall){
			this.setVelY(0);//if the falling block hasn't been triggered it stays put
		}
		//TRIGGERED!!!!!!!!!
		else{
			
			this.x += velX;
			this.y += velY;
			this.velY += gravity;
			this.setVelY(velY);
		
		}
		
		
	}
	
	public void render(Graphics2D g){
		g.setColor(Color.RED);
		g.fillRect((int)x, (int)y, width, height);//Colors in block for debugging
		g.drawImage(fallingblock,(int)x, (int)y, width, height, null);
	}
	public void collided(Player p){
		//checks to see if the falling block hasn't been triggered 
		if(!Fall){
			//sets the falling blocks texture
			try {
				fallingblock = ImageIO.read(HGame1.class.getResource("Graphics/fallingblock.gif")); //help from pixabay.com
				this.setX(p.getX());//sets the block to fall right above the player
				this.setY(p.getY()-300);//sets the block above the player
				
				this.setVelY(1);//begins its downward journey 
				Fall = true;//sets that it has been triggered 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		else{
			
		p.lives -= 1;//decrements players life
		this.setX(-80000);//sets the block wayyyyyy off screen
		this.setY(0);
		this.setVelY(0);//stops it from moving 
		
		//this.fallInt = (fallInt+1)%2;
		System.out.println("got hit by the falling block");
		//resets players position
		p.x = p.startingX;
		p.y = p.startingY;
		
		}

	}
}