package ag1;

import java.awt.Color;
import java.awt.Font;
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
	boolean starting = true;
	int startPlace = 0;
	int sLevel = 1;
	levels l;
	Image banner;
	Image Title;
	Image NMenu1;
	Image SMenu1;
	Image NMenu2;
	Image SMenu2;
	Image NMenu3;
	Image SMenu3;
	Enemy testEnemy = null;   //delete later

	
	public HGame1(){
	try {
		//banner = ImageIO.read(HGame1.class.getResource("banner.png"));
		Title = ImageIO.read(HGame1.class.getResource("Title.gif"));
		NMenu1 = ImageIO.read(HGame1.class.getResource("NMenu1.gif"));
		SMenu1 = ImageIO.read(HGame1.class.getResource("SMenu1.gif"));
		NMenu2 = ImageIO.read(HGame1.class.getResource("NMenu2.gif"));
		SMenu2 = ImageIO.read(HGame1.class.getResource("SMenu2.gif"));
		NMenu3 = ImageIO.read(HGame1.class.getResource("NMenu3.gif"));
		SMenu3 = ImageIO.read(HGame1.class.getResource("SMenu3.gif"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	}
	
	@Override
	public void tick(Graphics2D g, Input p1, Input p2, Sound s) {
		if(starting == true)
			startup(g, p1);
		else{
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
		
		if(right && p1.pressed(Button.R)){
			x += 5;
			left = true;
		}
		
		if(left && p1.pressed(Button.L)){
			x -= 5;
			right = true;
		}
		
		g.setColor(Color.BLUE);
		g.fillRect(100, 100, 100, 100);
		
		//Spawn debug enemy
		if(testEnemy == null){
			testEnemy = new Enemy(200, 100, 50, 50);
		}
		testEnemy.update(g, this);
		}
	}

	public void startup(Graphics2D g, Input p1){
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.drawImage(Title, 0, 0, null);
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Stencil", Font.PLAIN, 36));
		if(startPlace == 0)
			g.drawString("1 PLAYER GAME", WIDTH/2 - 125, 216);
		else{
			g.setColor(Color.red);
			g.drawString("1 PLAYER GAME", WIDTH/2 - 125, 216);
			g.setColor(Color.yellow);
		}
		if(startPlace == 1)
			g.drawString("MOUNTAIN 01", WIDTH/2 - 125, 316);
		else{
			g.setColor(Color.red);
			g.drawString("MOUNTAIN" + sLevel, WIDTH/2 - 125, 316);
			g.setColor(Color.yellow);
		}
		if(startPlace == 2)
			g.drawString("DEVELOPERS", WIDTH/2 - 125, 416);
		else{
			g.setColor(Color.red);
			g.drawString("DEVELOPERS", WIDTH/2 - 125, 416);
			g.setColor(Color.yellow);
		}
		
		
		
		//Starting Button Mechanics
		if(p1.pressed(Button.U))
			if(startPlace == 0)
				startPlace = 2;
			else
				startPlace -= 1;
		
		if(p1.pressed(Button.D))
			if(startPlace == 2)
				startPlace = 0;
			else
				startPlace += 1;
		
		if(p1.pressed(Button.A))
			if(startPlace == 0)
			starting = false;
		
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
