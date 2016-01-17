package ag1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import arcadia.Arcadia;
import arcadia.Button;
import arcadia.Game;
import arcadia.Input;
import arcadia.Sound;

public class HGame1 extends Game{
	float y = WIDTH/2 - 100;
	float velocity = 0;
	float gratity = 0.5f;
	boolean canJump = false;
	levels l = new levels();
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
		g.setColor(Color.blue);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		velocity += gratity;
		y += velocity;
		
		l.level1(g, HEIGHT, WIDTH);
		
		g.setColor(Color.BLACK);
		g.fillRect(HEIGHT - 100, (int) y, 64, 96);
		
		
		
		if(y + 128 >= HEIGHT){
			velocity = 0;
			
			y = HEIGHT - 128;
			canJump = true;
		}
		
		if(canJump && p1.pressed(Button.A)){
			velocity = -5;
			canJump = false;
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
