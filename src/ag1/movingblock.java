package ag1;

import java.util.LinkedList;

public class movingblock extends block{

	private int speed = 2;
	
	public movingblock(float x, float y, char type, int p) {
		super(x, y, type, p);
		// TODO Auto-generated constructor stub
	}

	public void tick(LinkedList<GameObject> objects, Player player) {
		
		for(GameObject temp: objects){
			if(temp.getId().equals(GOID.Block))
				if(((block)temp).getType() == 'w')
					if(temp.getBounds().intersects(getBounds()))
						speed *= -1;
		}
		x += speed;
	}
	
}
