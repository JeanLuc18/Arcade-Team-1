package ag1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

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
	long score = 0;
	String scoreS;
	boolean levelsLoaded = false;
	Image banner;
	Image Title, background;
	Enemy testEnemy = null;   //delete later
	//int lives = 3;
	ArrayList<String> levelNames;
	boolean hasLevels = false;

	Player player;
	Camera camera;
	LinkedList<GameObject> objects;
	boolean initialized = false;

	public HGame1() throws FileNotFoundException{
		try {
			banner = ImageIO.read(HGame1.class.getResource("Banner.gif")); //help from pixabay.com
			Title = ImageIO.read(HGame1.class.getResource("MenuTitle.gif"));
			background = ImageIO.read(HGame1.class.getResource("Graphics/background.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Override
	public void tick(Graphics2D g, Input p1, Input p2, Sound s) {
		if(!initialized)
			intialize(g);
		if(starting == true)
			startup(g, p1);
		else if(player.lives <= 0)
			gameover(g, p1);
		else{
		
			
			
		
						
			//-----!!!!TICK!!!!-----
			//tick objects
			for(GameObject tempObject : objects){
				if(tempObject != null)
					tempObject.tick(objects, player);	
			}
			
			player.tick(objects, player);
			camera.tick(player); //camera
			player.input(objects, p1); //keyboard input for player
			
			
			//-----!!!!RENDER!!!!-----
			//background
			g.setColor(new Color(51, 102, 255));
			g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
			
			g.translate(camera.getX(), camera.getY()); //camera
			
			//render objects
			for(GameObject tempObject : objects){
				if(tempObject != null)
					tempObject.render(g);
			}
			player.render(g);
			
			g.translate(-camera.getX(), -camera.getY()); //camera
		}
		}
	

	private void gameover(Graphics2D g, Input p1) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
		g.setColor(Color.RED);
		g.setFont(new Font("Stencil", Font.PLAIN, 50));
		g.drawString("Game Over!!!", 0 + WIDTH/2 - 150, HEIGHT/2 - 50);
		scoreS = Long.toString(player.score);
		scoreS = ("00000000" + scoreS).substring(scoreS.length());
		g.setFont(new Font("Stencil", Font.PLAIN, 30));
		g.drawString("Score: " + scoreS, 0 + WIDTH/2 - 150, HEIGHT/2 - 20);
		g.drawString("Hit A to go to Menu.", 0 + WIDTH/2 - 150, HEIGHT/2 + 10);
		
		if(p1.pressed(Button.A)){
			starting = true;
			player.lives = 3;
		}
		
	}
	
	/**
	 * Initialize game objects, load levels, etc. in here.
	 * 
	 * @param g Graphics2D Object used for drawing.
	 */
	private void intialize(Graphics2D g){
		//initialize player and camera
		player = new Player();
		camera = new Camera(0,0);
		
		//initialize level
		
		
		//initialize level files
		File f = new File("src/ag1/levelsFolder");
		levelNames = new ArrayList<String>(Arrays.asList(f.list()));
		
		//if no level files, create error screen
		if(levelNames.size() <= 0){
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(Color.RED);
			g.setFont(new Font("Stencil", Font.PLAIN, 50));
			g.drawString("NO LEVELS FOUND!!!!", 0 + WIDTH/4, HEIGHT/2);
			g.setFont(new Font("Stencil", Font.PLAIN, 30));
			g.drawString("Please close and add levels, Thank you.", 0 + WIDTH/6, HEIGHT/2 + 30);
		}
		
		try {
			l = new levels("src/ag1/levelsFolder/" + levelNames.get(sLevel));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialized = true;
		
	}
	
	public void setLevel(){
		objects = l.genLevel();
		objects.add(new LSCounter(player));
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
			if(startPlace == 0){
				starting = false;
				setLevel();
			}
			waiting();
		}
		if(p1.pressed(Button.L)){
			if(startPlace == 1)
				if(sLevel == 1)
					sLevel = levelNames.size() - 1;
				else 
					sLevel -= 1;

			waiting();
		}
		if(p1.pressed(Button.R)){
			if(startPlace == 1)
				if(sLevel == levelNames.size() - 1)
					sLevel = 1;
				else 
					sLevel += 1;
			waiting();
		}
	}

	public void waiting(){
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void reset() {}
	public Image banner() {return banner;}
	
	public static void main(String[] args) throws FileNotFoundException{
		Arcadia.display(new Arcadia(new Game[] {new HGame1(), new Shooter()}));
	}
}


