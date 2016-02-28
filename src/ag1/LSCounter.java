package ag1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.LinkedList;

import arcadia.Game;

public class LSCounter extends GameObject {

	String Score;
	Camera camera;
	
	public LSCounter(Player player, Camera camera){
		super(camera.getX()+500, camera.getY()+500, GOID.Counter);
		this.camera = camera;
		Score = Long.toString(player.score);
		Score = ("00000000" + Score).substring(Score.length());
		Score = Score + " Lives: " + player.lives;
	}
	
	@Override
	public void tick(LinkedList<GameObject> objects, Player player) {
		x = camera.getX() + Game.WIDTH - 300;
		y = -camera.getY() + 40;
		Score = Long.toString(player.score);
		Score = ("00000000" + Score).substring(Score.length());
		Score = Score + " Lives: " + player.lives;
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		g.setFont(new Font("Stencil", Font.PLAIN, 36));
		g.drawString(Score, x , y);
		
	}

}
