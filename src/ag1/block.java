package ag1;

import java.awt.Rectangle;

public class block {
	
	private int x = 0;
	private int y = 0;
	private int type;
	
	public block(int p1, int p2, int t){
		x = p1;
		y = p2;
		type = t;
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
	
	public void setType(int t){
		type = t;
	}
	
	public int getType(){
		return type;
	}
	
	public int getHieght(){
		if(type == 1)
			return 32;
		else if(type == 2 || type == 3)
			return 192;
		else 
			return 0;
	}
	
	public int getWidth(){
		if(type == 1)
			return 32;
		else if(type == 2 || type == 3)
			return 128;
		else 
			return 0;
	}
	
	public Rectangle bounds(){
		return new Rectangle(x,y, getWidth(), getHieght());
	}
	
}
