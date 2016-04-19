package ag1;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageClass{
	
	Image brick, brickWallLeft, brickWallRight, 
		  fallingblock, cloud, playerLeft1,
		  playerLeftAttack, playerRight1, playerRightAttack, 
		  repairerLeft1, repairerLeft2, repairerRight1, 
		  repairerRight2, heldBrick;
	
	public ImageClass(){
		try {
			System.out.println("Did I read?");
			brick = ImageIO.read(HGame1.class.getResource("Graphics/bricks.png"));
			brickWallLeft = ImageIO.read(HGame1.class.getResource("Graphics/LeftWall.png"));
			brickWallRight = ImageIO.read(HGame1.class.getResource("Graphics/RightWall.png"));
			fallingblock = ImageIO.read(HGame1.class.getResource("Graphics/fallingblock.gif")); 
			cloud = ImageIO.read(HGame1.class.getResource("Graphics/cloud.png"));
			playerLeft1 = ImageIO.read(HGame1.class.getResource("Graphics/Left_Knight_Walking1.png")); //help from pixabay.com
			playerLeftAttack = ImageIO.read(HGame1.class.getResource("Graphics/attackleft.png"));
			playerRight1 = ImageIO.read(HGame1.class.getResource("Graphics/Right_Knight_Walking1.png"));
			playerRightAttack = ImageIO.read(HGame1.class.getResource("Graphics/attackright.png"));
			repairerLeft1 = ImageIO.read(HGame1.class.getResource("Graphics/Mage_Walking1_L.png")); //help from pixabay.com
			repairerLeft2 = ImageIO.read(HGame1.class.getResource("Graphics/Mage_Walking2.png"));
			repairerRight1 = ImageIO.read(HGame1.class.getResource("Graphics/Mage_Walking3.png"));
			repairerRight2 = ImageIO.read(HGame1.class.getResource("Graphics/Mage_Walking4.png"));
			heldBrick = ImageIO.read(HGame1.class.getResource("Graphics/bricks.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
