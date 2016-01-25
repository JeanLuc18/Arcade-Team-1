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

	int block = 0;
	boolean starting = true;
	int startPlace = 0;
	int sLevel = 1;
	levels l;
	Image banner;
	Image Title;
	Enemy testEnemy = null;   //delete later
	Player player;


	public HGame1(){
		try {
			banner = ImageIO.read(HGame1.class.getResource("Banner.gif")); //help from pixabay.com
			Title = ImageIO.read(HGame1.class.getResource("MenuTitle.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		l  = new levels();
	}

	@Override
	public void tick(Graphics2D g, Input p1, Input p2, Sound s) {
		if(starting == true)
			startup(g, p1);
		else{
			g.setColor(Color.blue);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			player.velocity += player.gratity;
			player.y += player.velocity;

			l.level1(g, HEIGHT, WIDTH);

			player.redraw();

			//Player Collision
			player.groundCollision(128);
			for(int i = 0; i < l.blocks1.size(); i += 1){
				player.blockCollision((block)l.blocks1.get(i));
			}

			//Player Input/Buttons
			player.input(p1);
			
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
		player = new Player(g);

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
			g.drawString("MOUNTAIN    "+ sLevel , WIDTH/2 - 125, 316);
		else{
			g.setColor(Color.red);
			g.drawString("MOUNTAIN    " + sLevel, WIDTH/2 - 125, 316);
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

		if(p1.pressed(Button.L))
			if(startPlace == 1)
				if(sLevel == 1)
					sLevel = 20;
				else 
					sLevel -= 1;
		if(p1.pressed(Button.R))
			if(startPlace == 1)
				if(sLevel == 20)
					sLevel = 1;
				else 
					sLevel += 1;

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
