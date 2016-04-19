package ag1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.LinkedList;

import arcadia.Game;

public class LSCounter extends GameObject {

	String Score;
	String time;
	Camera camera;
	
	public LSCounter(Player player, Camera camera){
		super(camera.getX()+500, camera.getY()+500, GOID.Counter);
		this.camera = camera;
		long score = player.Lscore + player.Tscore;
		Score = Long.toString(score);
		Score = ("00000000" + Score).substring(Score.length());
		Score = Score + " Lives: " + player.lives;
	}
	
	@Override
	public void tick(LinkedList<GameObject> objects, Player player) {
		x = camera.getX() + Game.WIDTH - 300;
		y = -camera.getY() + 40;
		
		if(Long.toString(player.getCurrentTime()) != null)
		time = Long.toString(player.getCurrentTime());
		
		long score = player.Lscore + player.Tscore;
		Score = Long.toString(score);
		Score = ("00000000" + Score).substring(Score.length());
		Score = Score + " Lives: " + player.lives;
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		g.setFont(new Font("Stencil", Font.PLAIN, 36));
		
		if(time != null){
			g.draw3DRect(50, (int) y, 100, 100, true);
			g.drawString("Time", 52 , y + 32);
			g.drawString(time, 52, y + 64);
		}
		
		g.drawString(Score, x , y);
		
	}

}
