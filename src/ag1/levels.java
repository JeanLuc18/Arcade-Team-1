package ag1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import arcadia.Game;



public class levels{
	
	private int wallWidth = 128;
	private int wallHeight = 192;
	private int blockType;
	private File level;
	private Scanner reader;
	
	ArrayList blocks1 = new ArrayList();
	/**
	 * level class constructor which opens level text document and scanner to read said document
	 * @throws FileNotFoundException
	 */
	public void level() throws FileNotFoundException{
		level = new File("Level.txt");
		reader = new Scanner(level);
	}
	
	private block[] genLevel(Scanner reader){
		int blocksRead = 0;
		int usableH;
		int usableW = Game.WIDTH;
		String token;
		while(reader.hasNext()){
			token = reader.next();
			//Wall type Block
			if(token.charAt(0) == 'W'){
				wallWidth = reader.nextInt();
				wallHeight = reader.nextInt();
				usableH = wallHeight;
				usableW = usableW - (wallWidth*2); 
				blocksRead = 2;
			}
			//rest of the blocks
			else if(token.charAt(0) == 'r'){
				token = reader.next();
				for(int i = 0; i < token.length(); i++){
					char curr = token.charAt(i);
					//check if the block can fit and add if so
					//actually I should be adding to the array here to be more 
					//that is what I will do here I just need to figure out the number of blocks per level segment (heightWise)
					// and calculate number with wall width considerations. 
				}
			}
		}
		return null;
	}
	
	/**
	 * canFitW checks to see if the block b can fit within the level segment. (in terms of Width)
	 * @param b block being tested 
	 * @param widthLeft the amount of usable width left in level segment(also depends on height position of block)
	 * @return true if the block can fit false otherwise
	 * 
	 */
	private boolean canFitW(block b, int widthLeft){
		return (widthLeft - b.getWidth() > 0);
	}
	
	/**
	 * canFitH checks to see if the block b can fit within the level segment. (in terms of Height)
	 * @param b block being tested
	 * @param heightLeft the amount of height left 
	 * @return true if the block can fit false otherwise
	 */
	private boolean canFitH(block b, int heightLeft){
		return (heightLeft - b.getHieght()>0);
	}
	public void level1(Graphics2D g, int height, int width){
		block newest;
		
		g.setColor(Color.YELLOW);
		g.fillRect(0, height - wallHeight, 128, 192);
		newest = new block(0, height - wallHeight , 2);
		blocks1.add(newest);
		g.fillRect(width - wallWidth, height - wallHeight, 128, wallHeight);
		newest = new block(width - wallWidth, height - wallHeight , 3);
		blocks1.add(newest);
		g.setColor(Color.CYAN);
		g.fillRect(0, height - wallHeight*2, wallWidth, wallHeight);
		newest = new block(0, height - wallHeight*2 , 2);
		blocks1.add(newest);
		g.fillRect(width - wallWidth, height - wallHeight*2, wallWidth, wallHeight);
		newest = new block(width - wallWidth, height - wallHeight*2 , 3);
		blocks1.add(newest);
		
		for(int j = 0; j < 2; j += 1){
		for(int i = 0; i < (width / 32 ); i += 1){
			g.setColor(Color.CYAN);
		g.fillRect(0 + 32 * i, (height - 32) - wallHeight*j  , 32, 32);
		newest = new block(0 + 32 * i, height - 32, 1);
		blocks1.add(newest);
		g.setColor(Color.BLACK);
		g.drawRect(0 + 32 * i, (height - 32) - wallHeight*j , 32, 32);
		}
		}
	}

}
