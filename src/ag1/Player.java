package ag1;

import java.awt.*;

import arcadia.*;
import arcadia.Button;

public class Player extends GameObject {
	
	boolean canJump = false;
	//boolean breakb = false;
	boolean inAir = true;
	Color color = Color.BLACK;
	final int bd = 8; //bound dist
	
	public Player(){
		//super(Game.HEIGHT/2 - 100, Game.WIDTH/2 - 50, 64, 96);
		super(200, 400, 64, 96);
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		if(inAir){
			velY += gravity;
		}
	}
	
	public void render(Graphics2D g){
		g.setColor(color);
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
	
	public void blockCollision(block block){
		Rectangle blockBounds = block.getBounds();
		
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
	
//	public void groundCollision(int groundHeight){
//		if(y + groundHeight >= Game.HEIGHT){
//			setVelY(0);
//			
//			y = Game.HEIGHT - groundHeight;
//			canJump = true;
//			inAir = false;
//		}
//	}
//	
//	public void blockCollision(block block){
//		if(collision(block.getBounds())){
//			if(block.getType() == 1){
//				//block = i;
//				//breakb = true;
//			}
//			else if(block.getType() == 2)
//				left = false;
//			else if(block.getType() == 3)
//				right = false;
//		}
//	}
	
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