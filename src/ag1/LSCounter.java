package ag1;

import java.awt.Font;
import java.awt.Graphics2D;
import java.util.LinkedList;

public class LSCounter extends GameObject {

	String Score;
	
	public LSCounter(Player player){
		super(player.getX() + 500, player.getY() - 225, GOID.Counter);
		Score = Long.toString(player.score);
		Score = ("00000000" + Score).substring(Score.length());
		Score = Score + " Lives: " + player.lives;
	}
	
	@Override
	public void tick(LinkedList<GameObject> objects, Player player) {
		x = player.getX() + 200;
		y = player.getY() - 250;
		Score = Long.toString(player.score);
		Score = ("00000000" + Score).substring(Score.length());
		Score = Score + " Lives: " + player.lives;
	}

	@Override
	public void render(Graphics2D g) {
		g.setFont(new Font("Stencil", Font.PLAIN, 36));
		g.drawString(Score, x , y);
		
	}

}
