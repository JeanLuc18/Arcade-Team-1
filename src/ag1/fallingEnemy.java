package ag1;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class fallingEnemy extends Enemy{
	
	boolean canfall = false;
	Image fallingblock;
	
	public fallingEnemy(float x, float y, int width, int height) {
		super(x, y, width, height);
		try {
			fallingblock = ImageIO.read(HGame1.class.getResource("Graphics/fallingblock.gif")); //help from pixabay.com
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Rectangle trigger(){
		return new Rectangle((int)x , (int)y + 500, width, height);
	}
	
	public void fallStart(){
		canfall = true;
	}
	
	public void collided(Player p){
		p.lives -= 1;
		System.out.println("got hit by the falling block");
		p.x = p.startingX;
		p.y = p.startingY;
	}
	public void tick(LinkedList<GameObject> objects, Player player) {
		
		for(GameObject temp: objects){
			if(temp.getId().equals(GOID.Block))
				if(((block)temp).getPlatform() == 1)
					if(this.getBounds().intersects(temp.getBounds())){
						objects.remove(this);
						break;
					}
		}
		
		if(canfall){
			this.x += velX;
			this.y += velY;
			this.velY += gravity;
		}
	}
	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		g.draw(trigger());
		g.drawImage(fallingblock,(int)x, (int)y, width, height, null);
	}
	
}
