package ag1;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class movingblock extends block{

	private int speed = 2;
	Image cloud;
	
	public movingblock(float x, float y, char type, int p, Image c, Image b, Image bwl, Image bwr) {
		super(x, y, 64, 32, type, p, b, bwl, bwr);
		cloud = c;	
	}

	public void tick(LinkedList<GameObject> objects, Player player) {
		
		for(GameObject temp: objects){
			if(temp.getId().equals(GOID.Block))
				if(((block)temp).getType() == 'w')
					if(temp.getBounds().intersects(getBounds()))
						speed *= -1;
		}
		x += speed;
	}
	
	public void render(Graphics2D g){
		g.drawImage(cloud,(int)x, (int)y, width, height,null);
	}
	
}
