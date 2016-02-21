package ag1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import arcadia.Game;


/**
 * 
 * @author Steve Amatangelo
 *
 * This is a subclass of the enemy class. This object creates enemies that will
 * walk back and forth to repair any space blocks with breakable ones
 *
 */

public class Repairer  extends Enemy{
	boolean repair;
	boolean faceing = true;
	boolean pushLeft, pushRight;
	boolean inAir = false;
	Image left1, left2, right1, right2, brick;
	
	public Repairer(float x, float y, int width, int height) {
		super(x, y, width, height);
		try {
			left1 = ImageIO.read(HGame1.class.getResource("Graphics/Mage_Walking1_L.png")); //help from pixabay.com
			left2 = ImageIO.read(HGame1.class.getResource("Graphics/Mage_Walking2.png"));
			right1 = ImageIO.read(HGame1.class.getResource("Graphics/Mage_Walking3.png"));
			right2 = ImageIO.read(HGame1.class.getResource("Graphics/Mage_Walking4.png"));
			brick = ImageIO.read(HGame1.class.getResource("Graphics/bricks.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public void tick(LinkedList<GameObject> objects, Player player){
		
		if(inAir){
			velY += gravity;
		}
		x += speed;
		y += velY;
		//change direction an edges of screen 
		
		
		for(GameObject object : objects){
			if(object != null){
				if(object.getId() == GOID.Block){
					blockCollision((block)object);
				}
			}
		}
		
	}
	
	private void blockCollision(block block){
		Rectangle feet = new Rectangle((int)x, (int)y, width, height + 2);
		Rectangle blockBounds = block.getBounds();
		
		if(block.getType() == 'w'){
			if(feet.intersects(blockBounds)){
				if(speed > 0)
					faceing = false;
				else
					faceing = true;
			speed *= -1;
			//System.out.println(repair + " " + pushLeft + " " + pushRight);
			//Sets block in front of enemy showing that it is ready to repair a space
			if(repair){
				if(speed > 0){
					pushRight = true;
					if(pushLeft){
						pushLeft = false;
						pushRight = false;
					}
				}
				else{
					pushLeft = true;
					if(pushRight){
						pushRight = false;
						pushLeft = false;
					}
				}
				repair = false;
			}
			else{
				repair = false;
				pushLeft = false;
				pushRight = false;
			}
			}
		}
			
		if(block.getType() == 'b'){
			if(feet.intersects(blockBounds)){
				y = block.getY() - this.height;
				velY = 0;
				inAir = false;
			}
			else
				inAir = true;
		}
		
		//space block next
		if(block.getType() == 's'){
			if(feet.intersects(blockBounds)){
				if(speed > 0)
					faceing = false;
				else
					faceing = true;
				
				speed *= -1;
				x += 2 * speed;
				if(pushLeft || pushRight){
					block.setType('b');
					pushLeft = false;
					pushRight = false;
					repair =  false;
				}
				if(!repair){
					repair = true;
				}
				
			}
		}
	}
	public void render(Graphics2D g){
		g.setColor(Color.RED);
		if(faceing == false)
			g.drawImage(right1,(int)x, (int)y, width, height,null);
		else
			g.drawImage(left1,(int)x, (int)y, width, height,null);
		
		
		
		if(pushLeft){
			int blockX = (int)x;
			int blockY = ((int)y + height) -25;
			int blockWidth = 25;
			int blockHeight = 25;
			
			g.setColor(Color.GRAY);
			g.drawImage(brick,blockX, blockY, blockWidth, blockHeight, null);
		}
		if(pushRight){
			int blockX = ((int)x + width);
			int blockY = ((int)y + height) - 25;
			int blockWidth = 25;
			int blockHeight = 25;
			
			g.setColor(Color.GRAY);
			g.drawImage(brick,blockX, blockY, blockWidth, blockHeight, null);
		}
	}
	
	public void collided(Player p){
		p.lives -= 1;
		p.x = p.startingX;
		p.y = p.startingY;
	}
}

