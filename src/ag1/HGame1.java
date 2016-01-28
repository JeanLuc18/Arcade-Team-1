package ag1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import arcadia.Arcadia;
import arcadia.Button;
import arcadia.Game;
import arcadia.Input;
import arcadia.Sound;
import shooter.Shooter;

public class HGame1 extends Game{

	int block = 0;
	boolean starting = true;
	int startPlace = 0;
	int sLevel = 1;
	levels l;
	int numOLevels = 0;
	boolean levelsLoaded = false;
	Image banner;
	Image Title;
	Enemy testEnemy = null;   //delete later
	Player player;
	ArrayList<String> levelNames;
	boolean hasLevels = false;
	Camera camera;


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
		if(!levelsLoaded)
			loadLevels();
		
		if(starting == true)
			startup(g, p1);
		
		else if(hasLevels){
			g.setColor(Color.LIGHT_GRAY);
			g.setColor(new Color(51, 102, 255));
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
			l.level1(g, HEIGHT, WIDTH);
			player.render(g);
			
			//----TICK----
			player.tick();

			//Player/Block Collision
			for(int i = 0; i < l.blocks1.size(); i += 1){
				player.blockCollision((block)l.blocks1.get(i));
			}
			
			camera.tick(player);

			
			//Player Input/Buttons
			player.input(p1);
			

			//----RENDER----

			g.setColor(new Color(51, 102, 255));
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
			g.translate(camera.getX(), camera.getY());
			
			l.level1(g, HEIGHT, WIDTH);
			
			g.setColor(Color.BLUE);
			g.fillRect(100, 100, 100, 100);

			//Spawn debug enemy
			if(testEnemy == null){
				testEnemy = new Enemy(200, 100, 50, 50);
			}
			testEnemy.update(g, this);
			
			player.render(g);

			g.translate(-camera.getX(), -camera.getY());

		}else{
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(Color.RED);
			g.setFont(new Font("Stencil", Font.PLAIN, 50));
			g.drawString("NO LEVELS FOUND!!!!", 0 + WIDTH/4, HEIGHT/2);
			g.setFont(new Font("Stencil", Font.PLAIN, 30));
			g.drawString("Please close and add levels, Thank you.", 0 + WIDTH/6, HEIGHT/2 + 30);
		}
	}

	private void loadLevels() {
		File f = new File("src/ag1/levelsFolder");
		levelNames = new ArrayList<String>(Arrays.asList(f.list()));
		// System.out.print(levelNames); delete later;
		levelsLoaded = true;
		if(levelNames.size() > 0)
			hasLevels = true;
	}

	public void startup(Graphics2D g, Input p1){
		player = new Player();
		camera = new Camera(0,0);

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
		if(p1.pressed(Button.U)){
			if(startPlace == 0)
				startPlace = 2;
			else
				startPlace -= 1;
			waiting();
		}
		if(p1.pressed(Button.D)){
			if(startPlace == 2)
				startPlace = 0;
			else
				startPlace += 1;
			
			waiting();
		}
		if(p1.pressed(Button.B)){
			if(startPlace == 0)
				starting = false;
			waiting();
		}
		if(p1.pressed(Button.L)){
			if(startPlace == 1)
				if(sLevel == 1)
					sLevel = 20;
				else 
					sLevel -= 1;
			
			waiting();
		}
		if(p1.pressed(Button.R)){
			if(startPlace == 1)
				if(sLevel == 20)
					sLevel = 1;
				else 
					sLevel += 1;
			waiting();
		}
	}

	public void waiting(){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		Arcadia.display(new Arcadia(new Game[] {new HGame1(), new Shooter()}));
	}
}

