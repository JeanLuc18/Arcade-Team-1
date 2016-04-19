package ag1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

import ag1.Enemy;
import ag1.GOID;
import ag1.GameObject;
import ag1.Player;
import ag1.block;
import arcadia.Game;


public class Bomber extends Enemy{
	private int bombX, bombY, bombWidth, bombHeight, expX, expY, expWidth, expHeight;
	private boolean dropped = false;
	private boolean droppedOnRun = false;
	private boolean exploding = false;
	private int bombRadius = 100;
	private int explosionCounter = 0;
	private int velY = 2;

	public Bomber(float y, int width, int height) {
		super(Game.WIDTH,y,width,height);
		x = Game.WIDTH + 20;
		this.y -= 20;
	}
	public void tick(LinkedList<GameObject> objects, Player player) {
		x-=speed;
		if(x <= (0)){
			x = Game.WIDTH + 20;
			droppedOnRun = false;
			dropped = false;
		}
		//drop bomb when enemy is above player
		if(x == player.getX()){
			if(!droppedOnRun){
				dropBomb();
				droppedOnRun = true;
			}
		}
		//detonate bomb when colliding with something
		if(dropped){
			Rectangle temp = new Rectangle(bombX, bombY, bombWidth, bombHeight);
			for(GameObject object : objects){
				if(object != null){
					if(temp.intersects(object.getBounds())){
						if(object.getId() == GOID.Player){
							collided(player);
						}
						if(object.getId() == GOID.Block && ((block)object).getType() == 's'){
							continue;
						}
						else{
							detonate(player);
						}
					}
				}
			}
			bombY+=velY;
		}
		//explode for 5 ticks
		if(exploding){
			explosionCounter++;
			Rectangle temp = new Rectangle(expX, expY, expHeight, expWidth);
			if (temp.intersects(player.getBounds())){
				collided(player);
			}
			if(explosionCounter > 5){
				bombWidth = 0;
				bombHeight = 0;
				dropped = false;
				explosionCounter = 0;
				exploding = false;
			}
		}
		if(x <= 0){
			x = Game.WIDTH;
		}
	}
	public void render(Graphics2D g) {
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//NEEDS IMAGES
		//NO SUITABLE IMAGES FOR ENEMIES, BOMBS, AND EXPLOSIONS
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		g.setColor(Color.RED);
		g.fillRect((int)x, (int)y, width, height);
		if(dropped){
			g.setColor(Color.BLACK);
			g.fillRect(bombX, bombY, bombWidth, bombHeight);
		}
		if(exploding){
			g.setColor(Color.YELLOW);
			g.fillRect(expX, expY, expWidth, expHeight);
		}
	}
	public void dropBomb(){
		bombX = (int)x;
		bombY = (int)y + height + 1;
		bombWidth = 5;
		bombHeight = 5;
		dropped = true;
	}
	public void detonate(Player player){
		//ste up explosion parameters
		expX = bombX - bombRadius;
		expY = bombY - (2 * bombRadius);
		expWidth = 2 * bombRadius;
		expHeight = 2 * bombRadius;
		exploding = true;
	}
	public void collided(Player p){
		p.lives -= 1;
		p.x = p.startingX;
		p.y = p.startingY;
	}
}
