package ag1;

import java.awt.*;
import java.util.LinkedList;

import arcadia.*;
import arcadia.Button;

public class Player extends GameObject {
	int lives = 3;
	boolean canJump = false;
	boolean inAir = true;
	boolean faceing = true; //true - player facing right , false - player facing left 
	boolean attacking = false;
	Color color = Color.BLACK;
	final int bd = 8; //bound dist
	
	public Player(){
		super(Game.WIDTH/2, Game.HEIGHT-32-96, 64, 96, GOID.Player);
		//super(200, 400, 64, 96, GOID.Player);
	}
	
	public void tick(LinkedList<GameObject> objects, Player player) {
		x += velX;
		y += velY;
		
		if(inAir){
			velY += gravity;
		}
		
		for(GameObject tempObject : objects){
			if(tempObject != null){
				if(tempObject.getId() == GOID.Block){
					blockCollision((block)tempObject);
				}
			}
		}
	}
	
	public void render(Graphics2D g){
		g.setColor(color);
		//for the graphics
		if(faceing && attacking)
			g.fillRect((int)x, (int)y, width, height);
		else if(!faceing && attacking)
			g.fillRect((int)x, (int)y, width, height);
		else if(faceing && !attacking)
			g.fillRect((int)x, (int)y, width, height);
		else
			g.fillRect((int)x, (int)y, width, height);
			
		g.setColor(Color.GREEN);
		g.draw(leftBound());
		g.draw(rightBound());
		g.draw(topBound());
		g.draw(bottomBound());
	}
	
	public Rectangle leftBound(){
		return new Rectangle((int)x, (int)y+bd, bd, height-bd*2);
	}
	public Rectangle rightBound(){
		return new Rectangle((int)x+width-bd, (int)y+bd, bd, height-bd*2);
	}
	public Rectangle topBound(){
		return new Rectangle((int)x+bd, (int)y, (int)width-bd*2, bd);
	}
	public Rectangle bottomBound(){
		return new Rectangle((int)x+bd, (int)y+height-bd, (int)width-bd*2, bd);
	}
	
	public void blockCollision(GameObject block){
		Rectangle blockBounds = block.getBounds();
	
		if(block.getId() == GOID.Block){
			block temp = (block)block;
			if(temp.getType() == 's'){
				
			}
			else{
				if(blockBounds.intersects(topBound())){
					y = block.getY() + block.getHeight();
					velY = 0;
				}
				if(blockBounds.intersects(bottomBound())){
					y = block.getY() - this.height;
					velY = 0;
				
					canJump = true;
					inAir = false;
				}
				else{
					inAir = true;
				}
			
				if(blockBounds.intersects(leftBound())){
					x = block.getX() + block.getWidth();
				}
				if(blockBounds.intersects(rightBound())){
					x = block.getX() - width;
				}
			}
		}
			
		
		if(block.getId() == GOID.Enemy){
			//System.out.println("I died");
			Enemy temp = (Enemy)block;
			if(blockBounds.intersects(topBound())){
				//System.out.println(lives);
				temp.collided(this);
			}
			if(blockBounds.intersects(bottomBound())){
				//System.out.println(lives);
				temp.collided(this);
			}
			
		
			if(blockBounds.intersects(leftBound())){
				//System.out.println(lives);
				temp.collided(this);
			}
			if(blockBounds.intersects(rightBound())){
				//System.out.println(lives);
				temp.collided(this);
			}
		}
		
	}
	
	public void input(Input input){
		if(canJump && input.pressed(Button.A)){
			setVelY(-20);
			canJump = false;
		}
		
		if(input.pressed(Button.R) && input.pressed(Button.L)){
			velX = 0;
		}else if(input.pressed(Button.R)){
			velX = 8;
		}else if(input.pressed(Button.L)){
			velX = -8;
		}else{
			velX = 0;
		}
	}


}
