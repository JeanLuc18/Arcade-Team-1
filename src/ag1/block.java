package ag1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class block extends GameObject {
	
	private char type = 'w';// block type can be 's' (space), 'b' (breakable block), or 'w' (unbreakable block)
	private boolean breakable;//boolean of whether the player can break this block
	private boolean passable; //boolean of whether the player can pass through this block
	final int bd = 8;
	int platform = 0;
	Image brick, brickWallLeft, brickWallRight;
	Player player;
	
	public block(float x, float y, char type, int p){
		super(x, y, 32, 32, GOID.Block);
		setType(type);
		platform = p;
		try {
			brick = ImageIO.read(HGame1.class.getResource("Graphics/bricks.png"));
			brickWallLeft = ImageIO.read(HGame1.class.getResource("Graphics/LeftWall.png"));
			brickWallRight = ImageIO.read(HGame1.class.getResource("Graphics/RightWall.png"));//help from pixabay.com
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public block(float x, float y, int width, int height, char type, int p){//wall type block
		super(x, y, width, height, GOID.Block);
		setType(type);
		platform = p;
		try {
			brick = ImageIO.read(HGame1.class.getResource("Graphics/bricks.png")); 
			brickWallLeft = ImageIO.read(HGame1.class.getResource("Graphics/LeftWall.png"));
			brickWallRight = ImageIO.read(HGame1.class.getResource("Graphics/RightWall.png"));//help from pixabay.com

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public int getPlatform(){
		return platform;
	}
	
	public void setType(char t){
		type = t;
		
		if(type == 'b'){//if the block is a breakable block
			breakable = true;
			passable = false;
		}
		else if(type == 's'){//if the block is a space
			breakable = false;
			passable = true;
		}
		else if(type == 'w'){//if the block is an unbreakable block
			breakable = false;
			passable = false;
		}
	}

	
	public boolean isBreakable(){
		return breakable;
	}
	/**
	 * returns type of block 's','b', or 'w'
	 * @return block type
	 */
	public char getType(){
		return type;
	}
	
	public Rectangle bottomBound(){
		return new Rectangle((int)x, (int)y+height-bd, (int)width, bd);
	}
	
	
	
	public void tick(LinkedList<GameObject> objects, Player player) {
		if(this.breakable)
			if(player.topBound().intersects(bottomBound())){
				System.out.println("BOUNDS");
				for(int i = 0; i < objects.size(); i += 1){
					System.out.println("HIT");
					if(this == objects.get(i)){
						objects.remove(i);
					System.out.println("here");
					}
				}
			}
		this.player = player;
	}
	
	public void render(Graphics2D g) {
		
		switch(type){
			case 'b':	g.drawImage(brick,(int)x, (int)y, width, height, null);						
						break;
			case 's':	
						break;
			case 'w':	if(x > 512)
							g.drawImage(brickWallRight,(int)x, (int)y, width, height, null);
						else
							g.drawImage(brickWallLeft,(int)x, (int)y, width, height, null);
						break;
		}
		
		
		
	}
}
