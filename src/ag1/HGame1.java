package ag1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;



import arcadia.Arcadia;
import arcadia.Button;
import arcadia.Game;
import arcadia.Input;
import arcadia.Sound;

public class HGame1 extends Game{
	float y = HEIGHT/2 - 100;
	float x = WIDTH/2 - 50;
	float velocity = 0;
	float gratity = 0.5f;
	Rectangle player;
	boolean canJump = false;
	boolean left = true;
	boolean right = true;
	boolean breakb = false;
	int block = 0;
	levels l;
	Image banner;
	
	public HGame1(){
	try {
		banner = ImageIO.read(HGame1.class.getResource("banner.png"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	}
	
	@Override
	public void tick(Graphics2D g, Input p1, Input p2, Sound s) {
		l  = new levels();
		g.setColor(Color.blue);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		velocity += gratity;
		y += velocity;
		
		l.level1(g, HEIGHT, WIDTH);
		
		g.setColor(Color.BLACK);
		g.fillRect((int) x, (int) y, 64, 96);
		player = new Rectangle((int) x, (int)y, 64, 96);
		
		//Collision Mechanics
		if(y + 128 >= HEIGHT){
			velocity = 0;
			
			y = HEIGHT - 128;
			canJump = true;
		}
		
		Rectangle rec2;
		for(int i = 0; i < l.blocks1.size(); i += 1){
		
			rec2 = ((block)l.blocks1.get(i)).bounds();
			
			if(player.intersects(rec2)){
				if(((block)l.blocks1.get(i)).getType() == 1){
					block = i;
					breakb = true;
				}
				else if(((block)l.blocks1.get(i)).getType() == 2)
					left = false;
				else if(((block)l.blocks1.get(i)).getType() == 3)
					right = false;
			}
			
		}
		//test
		//Button Mechanics
		if(canJump && p1.pressed(Button.A)){
			velocity = -9;
			canJump = false;
		}
		
		if(right && p1.pressed(Button.C)){
			x += 5;
			left = true;
		}
		
		if(left && p1.pressed(Button.B)){
			x -= 5;
			right = true;
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image banner() {
		// TODO Auto-generated method stub
		return banner;
	}

	public static void main(String[] args){
		Arcadia.display(new Arcadia(new Game[] {new HGame1()}));
	}
}
