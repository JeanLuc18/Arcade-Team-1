package ag1;

import java.awt.Rectangle;

public class block {
	
	private int x = 0;//x position
	private int y = 0;//y position
	private char type;// block type can be 's' (space), 'b' (breakable block), or 'w' (unbreakable block)
	private boolean breakable;//boolean of whether the player can break this block
	private boolean passable; //boolean of whether the player can pass through this block
	private int width; //blocks width
	private int height;//blocks height
	/**
	 * constructor for the normal blocks
	 * @param p1 x position
	 * @param p2 y position
	 * @param t type
	 */
	public block(int p1, int p2, char t){
		x = p1;
		y = p2;
		type = t;
		width = 32;
		height = 32;
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
	 * constructor for type Wall
	 * @param p1 x position
	 * @param p2 y position
	 * @param t type
	 * @param h height
	 * @param w width
	 */
	public block(int p1, int p2, char t, int w, int h){
		x = p1;
		y = p2;
		type = t;
		height = h;
		width = w;
		breakable = false;
		passable = false;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public int getX(){
		return x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getY(){
		return y;
	}
	
	public void setType(char t){
		type = t;
	}
	public void setWidth(int a){
		width = a;
	}
	
	public void setHeight(int a){
		height = a;
	}
	
	/**
	 * returns type of block 's','b', or 'w'
	 * @return block type
	 */
	public char getType(){
		return type;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public Rectangle bounds(){
		return new Rectangle(x,y, getWidth(), getHeight());
	}
	
}
