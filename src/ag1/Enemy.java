package ag1;

import java.awt.Rectangle;

public class Enemy {

	float x, y;             //X and Y coordinates, passed in by constructor
	int width, height;		//width and height, passed in by constructor
	float yVelocity = 0;    //For falling enemies
	float gravity = 0.5f;
	float speed = 1;        //Speed in horizontal direction, constant
	Rectangle collider;
	
	public Enemy(float x, float y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		collider = new Rectangle((int)x, (int)y, width, height);
	}
	
	/*
	 * Function update - update status of enemy object
	 * 					 called every tick
	 * Calling method - HGame1.java tick()
	 */
	public void update(){
		//Move enemy
		x+=speed;
		
	}
	
}
