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
	boolean pushLeft, pushRight;
	Image left1;
	Image left2;
	Image right1;
	Image right2;
	
	public Repairer(float x, float y, int width, int height) {
		super(x, y, width, height);
		try {
			left1 = ImageIO.read(HGame1.class.getResource("Graphics/Mage_Walking1.gif")); //help from pixabay.com
			left2 = ImageIO.read(HGame1.class.getResource("Graphics/Mage_Walking2.gif"));
			right1 = ImageIO.read(HGame1.class.getResource("Graphics/Mage_Walking3.gif"));
			right2 = ImageIO.read(HGame1.class.getResource("Graphics/Mage_Walking4.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public void tick(LinkedList<GameObject> objects, Player player){
		x+=speed;
		
		//change direction an edges of screen 
		if(x >= (Game.WIDTH - width) || x <= 0){
			speed *= -1;
			System.out.println(repair + " " + pushLeft + " " + pushRight);
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
		
		//space block next
		if(block.getType() == 's'){
			if(feet.intersects(blockBounds)){
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
		g.fillRect((int)x, (int)y, width, height);
		g.
		
		if(pushLeft){
			int blockX = (int)x;
			int blockY = ((int)y + height) -25;
			int blockWidth = 25;
			int blockHeight = 25;
			
			g.setColor(Color.GRAY);
			g.fillRect(blockX, blockY, blockWidth, blockHeight);
		}
		if(pushRight){
			int blockX = ((int)x + width);
			int blockY = ((int)y + height) - 25;
			int blockWidth = 25;
			int blockHeight = 25;
			
			g.setColor(Color.GRAY);
			g.fillRect(blockX, blockY, blockWidth, blockHeight);
		}
	}
}
