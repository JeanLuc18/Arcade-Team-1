package ag1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

public class block extends GameObject {
	
	private char type = 'w';// block type can be 's' (space), 'b' (breakable block), or 'w' (unbreakable block)
	private boolean breakable;//boolean of whether the player can break this block
	private boolean passable; //boolean of whether the player can pass through this block
	
	public block(float x, float y, char type){
		super(x, y, 32, 32, GOID.Block);
		setType(type);
	}

	public block(float x, float y, int width, int height, char type){//wall type block
		super(x, y, width, height, GOID.Block);
		setType(type);
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

	/**
	 * returns type of block 's','b', or 'w'
	 * @return block type
	 */
	public char getType(){
		return type;
	}
	
	public void tick(LinkedList<GameObject> objects) {}
	public void render(Graphics2D g) {
		switch(type){
			case 'b':	g.setColor(Color.CYAN);
						break;
			case 's':	g.setColor(Color.LIGHT_GRAY);
						break;
			case 'w':	g.setColor(Color.BLUE);
						break;
		}
		
		g.fillRect((int)x, (int)y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect((int)x, (int)y, width, height);
	}
}
