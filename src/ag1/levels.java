package ag1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class levels {
	
	int wallWidth = 128;
	int wallHeight = 192;
	
	ArrayList blocks1 = new ArrayList();
	
	public void level1(Graphics2D g, int height, int width){
		block newest;
		
		g.setColor(Color.YELLOW);
		g.fillRect(0, height - wallHeight, 128, 192);
		newest = new block(0, height - wallHeight , 2);
		blocks1.add(newest);
		g.fillRect(width - wallWidth, height - wallHeight, 128, wallHeight);
		newest = new block(width - wallWidth, height - wallHeight , 3);
		blocks1.add(newest);
		g.setColor(Color.CYAN);
		g.fillRect(0, height - wallHeight*2, wallWidth, wallHeight);
		newest = new block(0, height - wallHeight*2 , 2);
		blocks1.add(newest);
		g.fillRect(width - wallWidth, height - wallHeight*2, wallWidth, wallHeight);
		newest = new block(width - wallWidth, height - wallHeight*2 , 3);
		blocks1.add(newest);
		
		for(int j = 0; j < 2; j += 1){
		for(int i = 0; i < (width / 32 ); i += 1){
			g.setColor(Color.CYAN);
		g.fillRect(0 + 32 * i, (height - 32) - wallHeight*j  , 32, 32);
		newest = new block(0 + 32 * i, height - 32, 1);
		blocks1.add(newest);
		g.setColor(Color.BLACK);
		g.drawRect(0 + 32 * i, (height - 32) - wallHeight*j , 32, 32);
		}
		}
	}

}
