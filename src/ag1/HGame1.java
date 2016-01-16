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
	float y = 10;
	float velocity = 0;
	float gratity = 0.5f;
	boolean canJump = false;
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
		g.setColor(Color.green);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		velocity += gratity;
		y += velocity;
		
		g.setColor(Color.BLACK);
		g.fillOval(0, (int) y, 100, 100);
		
		if(y + 100 >= HEIGHT){
			velocity = 0;
			
			y = HEIGHT - 100;
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
