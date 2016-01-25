
package ag1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Enemy {

	float x, y;             //X and Y coordinates, passed in by constructor
	int width, height;		//width and height, passed in by constructor
	float yVelocity = 0;    //For falling enemies
	float gravity = 0.5f;
	float speed = 2;        //Speed in horizontal direction, constant
	Rectangle collider;
	boolean repair;
	boolean pushLeft, pushRight;

	public Enemy(float x, float y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		collider = new Rectangle((int)x, (int)y, width, height);
	}

	/**
	 * Function update - update status of enemy object
	 * 					 called every tick
	 * @param - graphics g from HGame1.java tick()
	 * @param - HGame1 object
	 * Calling method - HGame1.java tick()
	 */
	@SuppressWarnings("static-access")
	public void update(Graphics2D g, HGame1 game){
		//Move enemy
		x+=speed;
		//change direction an edges of screen CHANGE LATER
		if(x >= (game.WIDTH - width) || x <= 0){
			speed *= -1;
		}

		//push a block
		if(pushRight){
			g.setColor(Color.GRAY);
			g.fillRect((int)(x + width), (int)(y - (height - 25)), 25, 25);
		}
		if(pushLeft){
			g.setColor(Color.GRAY);
			g.fillRect((int)(x - 25), (int)(y - (height - 25)), 25, 25);
		}

		//fall
		yVelocity += gravity;
		y += yVelocity;
		if(y + height >= game.HEIGHT){
			yVelocity = 0;

			y = game.HEIGHT - height;
		}

		Rectangle rect;
		for(int i = 0; i < game.l.blocks1.size(); i += 1){

			rect = ((block)game.l.blocks1.get(i)).bounds();

			if(collider.intersects(rect)){
				if(((block)game.l.blocks1.get(i)).getType() == 'b'){					//floor
					yVelocity = 0;
					gravity = 0;
					y = ((block)game.l.blocks1.get(i)).getY() - height;
				}
				else if(((block)game.l.blocks1.get(i)).getType() == 'W'){				//left wall
					if(repair){
						pushRight = true;
					}

					speed *= -1;
					x+= 2 * speed;
				}
				else if(((block)game.l.blocks1.get(i)).getType() == 'W'){				//right wall
					if(repair){
						pushLeft = true;
					}

					speed *= -1;
					x+= 2 * speed;
				}
				else if(((block)game.l.blocks1.get(i)).getType() == 's'){				//space
					System.out.println("intersect");
					System.out.println(i);
					speed *= -1;
					x+= 2 * speed;
					gravity = 0.5f;
					if(repair){
						((block)game.l.blocks1.get(i)).setType('b');
						((block)game.l.blocks1.get(i)).setHeight(((block)game.l.blocks1.get(i)).getHeight() + 2);
						speed *= -1;
						repair = false;
						pushLeft = false;
						pushRight = false;
					}
					else{
						repair = true;
					}
				}
			}
		}

		//if(collider.intersects(game.player)){
			//System.out.println("player dead"); //change to kill player
		//}

		collider.setLocation((int)x, (int)y);
		g.setColor(Color.RED);
		g.fillRect((int)x, (int)y, width, height);
	}

}
