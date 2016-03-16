package ag1;

import arcadia.Game;

public class Camera {
	
	private float x, y;
	
	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(Player player){
		//x = -player.getX() + Game.WIDTH/2;
		float temp = y;
		y = -player.getY() + Game.HEIGHT/3;
		
		if(temp > y)
			y = temp;
		
		//System.out.println(player.getY() + "  "+ y);

	}
	
	public float getX() {return x;}
	public void setX(float x) {this.x = x;}
	public float getY() {return y;}
	public void setY(float y) {this.y = y;}
}
