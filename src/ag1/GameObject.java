package ag1;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class GameObject {
	
	protected float x, y;
	protected float velX = 0, velY = 0;
	protected int width, height;
	protected float gravity = 0.9f;
	
	public GameObject() {
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
	}
	
	public GameObject(float x, float y) {
		this.x = x;
		this.y = y;
		this.width = 0;
		this.height = 0;
	}
	
	public GameObject(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public float getX(){
		return x; //bottom left pixel x position
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public float getY(){
		return y; //bottom left pixel y position
	}
	
	public void setY(float y){
		this.y = y;
	}
	
	public float getVelX(){
		return velX;
	}
	
	public void setVelX(float velX){
		this.velX = velX;
	}
	
	public float getVelY(){
		return velY;
	}
	
	public void setVelY(float velY){
		this.velY = velY;
	}
	
	public int getWidth(){
		return width;
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, width, height);
	}
	
	public abstract void tick();
	public abstract void render(Graphics2D g);

}
