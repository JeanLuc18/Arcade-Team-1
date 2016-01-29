package ag1;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

/**
 * Superclass for all physical game objects. Includes parameters and methods for
 * getting and setting position, velocity, dimensions, bounds, gravity. Also
 * requires that subclasses include tick (for updating the objects properties)
 * and render (for redrawing the object) methods.
 * 
 * @author Michael Drogowski (mrdrogow@mtu.edu)
 */
public abstract class GameObject {
	
	protected float x, y; //pixel coordinates for objects top-left corner
	protected float velX = 0, velY = 0; //velocity of object (if movement added)
	protected int width, height; //pixel width and height of object 
	protected float gravity = 1f; //gravity applied to object
	protected GOID id; //enum - for determining object type
	
	/**
	 * Empty constructor. For objects that position and size do not matter. Sets
	 * x, y, width, and height to 0.
	 * 
	 * @param id Object's type.
	 */
	public GameObject(GOID id) {
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
		this.id = id;
	}
	
	/**
	 * Constructor with x and y position arguments, but sets width and height to
	 * 0. For objects where width and height do not matter.
	 * 
	 * @param x X-coordinate of objects top-left corner.
	 * @param y Y-coordinate of objects top-left corner.
	 * @param id Object's type.
	 */
	public GameObject(float x, float y, GOID id) {
		this.x = x;
		this.y = y;
		this.width = 0;
		this.height = 0;
		this.id = id;
	}
	
	/**
	 * Constructor with x and y position arguments along with object width and
	 * height.
	 * 
	 * @param x X-coordinate of objects top-left corner.
	 * @param y Y-coordinate of objects top-left corner.
	 * @param width Pixel width of object (hitbox - for collision).
	 * @param height Pixel height of object (hitbox - for collision).
	 * @param id Object's type.
	 */
	public GameObject(float x, float y, int width, int height, GOID id) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
	}
	
	public float getX(){
		return x;
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public float getY(){
		return y;
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
	
	public GOID getId(){
		return id;
	}
	
	public void setID(GOID id){
		this.id = id;
	}
	
	/**
	 * Returns the object's bounds based on its x, y, width, and height. Used
	 * for collision.
	 * 
	 * @return Rectangle of the object's bounds.
	 */
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, width, height);
	}
	
	/**
	 * Put mechanics to update/check every game tick in here (Examples: x, y,
	 * velocities, collision, etc). Will be called every game tick. Leave empty
	 * if object is "static" (does not move/change).
	 */
	public abstract void tick(LinkedList<GameObject> objects);
	
	/**
	 * Put visuals to be redrawn/rendered by Graphics2D in here. Will be called
	 * every game tick. Leave empty if no visuals.
	 * 
	 * @param g The Graphics2D object used for rendering the object.
	 */
	public abstract void render(Graphics2D g);

}
