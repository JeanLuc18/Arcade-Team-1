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
	/**
	 * genLevel reads a single level segment from the level txt and then gives back the array of blocks for that level with the two wall
	 * objects in the [][ ....last two of this bracket](sorry for my nontechnical speak] will probably add a getWalls method for easier access
	 * @param reader level.txt scanner
	 * @return block[][] which is the level segment block array
	 */
	private block[][] genLevel(Scanner reader){
		int blocksRead = 0;
		int usableH;
		int usableW = Game.WIDTH;
		block[][] level;
		String token = " ";
		//while loop reads an entire level segment and generates a level block array;
		while(reader.hasNext() && token != "X"){//Level Text file should have an X at the end of each level segment
			token = reader.next();
			//Wall type Block
			if(token.charAt(0) == 'W'){
				wallWidth = reader.nextInt();
				wallHeight = reader.nextInt();
				if(wallWidth % 32 > 0 || wallHeight % 32 > 0){  //checks to make sure the wall allows even block distribution 
					System.err.println("Invalid Wall Deminsions: Must be a divisiable by 32");
					System.exit(1);
				}
				usableH = wallHeight;
				usableW = usableW - (wallWidth*2);
				
				blocksRead = 2;                              // right [wallHeight/32][usableW/32 + 2] + 2 for the wall blocks
			}
			level = new block[wallHeight/32][(usableW/32)+2];//creates single level segment array y, x format: from bottom left [0][0] to top
			//rest of the blocks
			if(token.charAt(0) == 'b'){
				token = reader.next();//gets the string of blocks to be read
				int blockArrayPosX = 0;
				int blockArrayPosY = 0;
				for(int i = 0; i < token.length(); i++){
					char curr = token.charAt(i);
					
					if(curr == 'b'){//b is a breakable block
						//create block 'b'
					}
					else if(curr == 's'){
						//create bloc 's'
					}
					
					
					//adds block here
					if(blockArrayPosX > level.length && blockArrayPosY > level[0].length-2){
						System.out.println("No more room!!!");
					}
					else if(blockArrayPosX > level.length && !(blockArrayPosY > level[0].length-2)){
						blockArrayPosX = 0;
						blockArrayPosY += 1;
						//add block to array at that pos
						blockArrayPosX += 1;
					}
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
