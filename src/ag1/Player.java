package ag1;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import arcadia.*;
import arcadia.Button;

public class Player extends GameObject {
	int lives = 3;
	long score = 0;
	static int startingX = Game.WIDTH/2;
	static int startingY = Game.HEIGHT-32-96;
	int currentplatform = 1;
	boolean canJump = false;
	boolean inAir = true;
	boolean faceing = true; //true - player facing right , false - player facing left 
	boolean attacking = false;
	Color color = Color.BLACK;
	final int bd = 8; //bound dist
	Image left1, leftattack, right1, rightattack;
	
	
	public Player(){
		super(startingX, startingY, 64, 96, GOID.Player);
		//super(200, 400, 64, 96, GOID.Player);
		try {
			left1 = ImageIO.read(HGame1.class.getResource("Graphics/Left_Knight_Walking1.png")); //help from pixabay.com
			leftattack = ImageIO.read(HGame1.class.getResource("Graphics/attackleft.png"));
			right1 = ImageIO.read(HGame1.class.getResource("Graphics/Right_Knight_Walking1.png"));
			rightattack = ImageIO.read(HGame1.class.getResource("Graphics/attackright.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void tick(LinkedList<GameObject> objects, Player player, levels l, HGame1 m, Graphics2D g) {
		x += velX;
		y += velY;
		
		if(inAir){
			velY += gravity;
		}
		
		for(GameObject tempObject : objects){
			if(tempObject != null){
					blockCollision(tempObject, l, m, g);
			}
		}
	}
	
	public void render(Graphics2D g){
		g.setColor(color);
		//for the graphics
		if(faceing && attacking)
			g.drawImage(rightattack,(int)x, (int)y, width + 32, height, null);
		else if(!faceing && attacking)
			g.drawImage(leftattack,(int)x - 32, (int)y , width + 32, height, null);
		else if(faceing && !attacking)
			g.drawImage(right1,(int)x, (int)y, width, height, null);
		else
			g.drawImage(left1,(int)x, (int)y, width, height, null);
			
		g.setColor(Color.GREEN);
//		g.draw(rightAttackBounds());
//		g.setColor(Color.RED);
//		g.draw(leftAttackBounds());
		attacking = false;
//		g.draw(leftBound());
//		g.draw(rightBound());
//		g.draw(topBound());
//		g.draw(bottomBound());
	}
	//test
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
	
	public Rectangle rightAttackBounds(){
		return new Rectangle(((int)this.x + this.width), (int)this.y + 61, 35, 35);
	}
	
	public Rectangle leftAttackBounds(){
		return new Rectangle(((int)this.x - 50), (int)this.y + 61, 35, 35);
	}
	public void blockCollision(GameObject block, levels l, HGame1 m, Graphics2D g){
		Rectangle blockBounds = block.getBounds();
		
		//System.out.println(block.getId());
		if(block.getId().equals(GOID.Enemy)){
			
			Enemy temp = (Enemy)block;
			if(blockBounds.intersects(topBound())){
				temp.collided(this);
			}
			else if(blockBounds.intersects(bottomBound())){
				temp.collided(this);
			}
			
		
			else if(blockBounds.intersects(leftBound())){
				temp.collided(this);
			}
			else if(blockBounds.intersects(rightBound())){
				temp.collided(this);
			}
			
		}
		
		if(block.getId().equals(GOID.Block)){
			block temp = (block)block;
			if(temp.getType() == 's'){
				
			}
			else{
				if(blockBounds.intersects(topBound())){
					y = block.getY() + block.getHeight();
					velY = 0;
					if(temp.isBreakable()){
						temp.setType('s');
						score += 1;
					}
				}
				if(blockBounds.intersects(bottomBound())){
					y = block.getY() - this.height;
					velY = 0;
					if(temp.getPlatform() > currentplatform){
						score += 100;
						currentplatform = temp.getPlatform();
					}
					if((int)m.levelEnders.get(m.sLevel - 1) - 1 == temp.getPlatform()){
						m.sLevel += 1;
						x = startingX;
						y = startingY;
						m.setLevel();
					}
						
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
		
		
	}
	
	public void input(LinkedList<GameObject> objects, Input input){
		if(canJump && input.pressed(Button.A)){
			setVelY(-20);
			canJump = false;
		}
		
		if(input.pressed(Button.B)){
			if(!input.pressed(Button.R) && !input.pressed(Button.L)){
			attacking = true;
				for(GameObject tempObject : objects){
					if(tempObject != null){
						if(tempObject.getId().equals(GOID.Enemy))
						if(faceing){
						if(tempObject.getBounds().intersects(rightAttackBounds())){
							objects.remove(tempObject);
							score += 100;
							break;
						} 
						}
						else{
							if(tempObject.getBounds().intersects(leftAttackBounds())){
								objects.remove(tempObject);
								score += 100;
								break;
							} 
						}
					}
				}
		}
		}
		
		if(input.pressed(Button.R) && input.pressed(Button.L)){
			velX = 0;
		}else if(input.pressed(Button.R)){
			velX = 8;
			faceing = true;
		}else if(input.pressed(Button.L)){
			velX = -8;
			faceing = false;
		}else{
			velX = 0;
		}
	}

	@Override
	public void tick(LinkedList<GameObject> objects, Player player) {
		// TODO Auto-generated method stub
		
	}


}

