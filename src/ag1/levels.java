package ag1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import jdk.nashorn.internal.ir.Block;
import arcadia.Game;



public class levels{

	private int wallWidth = 128;
	private int wallHeight = 192;
	private int blockType;
	private File level;
	private Scanner reader;

	ArrayList<block> blocks1 = new ArrayList<block>();
	/**
	 * level class constructor which opens level text document and scanner to read said document
	 * @throws FileNotFoundException
	 */
	public void level(String levelN) throws FileNotFoundException{
		level = new File(levelN+".txt");
		reader = new Scanner(level);
		reader.useDelimiter("X");//tells scanner to parse text input by every "X" EX: TheXDelimiter would be broken up
								 //"The" and "Delimiter" with each call to reader.next();
	}
	
	/**
	 * genLevel reads a single level segment or enemy list from the level txt and then gives back the array of blocks/enemies for that level with the two wall
	 * objects in the last two indexes left first right second
	 * @param reader level.txt scanner
	 * @return block[] which is the level segment block array
	 */
	private block[] genLevel(Scanner reader){
		int usableH = Game.HEIGHT;
		int usableW = Game.WIDTH;
		int blocksPerRow = 0;
		block[] level = null;
		Scanner segReader = new Scanner(reader.next());//reads first level segment ie anything before first "X" in level file
		String token = " ";
		//reads the level segment assuming the level segment isn't empty
		if(segReader.hasNext()){
			token = segReader.next();
			if(token == "W"){
			//Wall type Block
			
				wallWidth = segReader.nextInt();
				wallHeight = segReader.nextInt();
				if(wallWidth % 32 > 0 || wallHeight % 32 > 0){  //checks to make sure the wall allows even block distribution 
					System.err.println("Invalid Wall Deminsions: Must be a divisiable by 32");
					System.exit(1);
				}
				usableH = wallHeight;
				usableW = usableW - (wallWidth*2);
			
			level = new block[((usableW/32)*(usableH/32))+2];//creates single level segment array with space for rest of blocks + 2 for the walls
			blocksPerRow = usableW / 32;
			block temp = null;
			token = segReader.next();//reads the string of blocks
			
			for(int i = 0; i < token.length(); i++){
				
				char curr = token.charAt(i);
				
				if(curr == 'b'){
					temp = new block(calcXCord(i, blocksPerRow), calcYCord(i, blocksPerRow), 'b');//creates new breakable block 
				}
				else if(curr == 's'){
					temp = new block(calcXCord(i, blocksPerRow), calcYCord(i, blocksPerRow), 's');//creates new space block
				}
				else if(curr == 'w'){
					temp = new block(calcXCord(i, blocksPerRow), calcYCord(i, blocksPerRow), 'w');//creates new unbreakable block
				}
				if(i == level.length-3){
					System.out.println("No more room!!!");//if the level segment cant fit any more blocks
					break;
				}
				level[i] = temp;
			}
			//level[level.length-2] = new block(x, y, width, height, type);//left wall
			//level[level.length-1] = new block(x, y, width, height, type);//right wall
			}
		
			else if(token == "E"){
				int enemyCount = segReader.nextInt();//number of enemies in map
				block[] enemies = new block[enemyCount];
				for(int i = 0; i < enemies.length; i+=1){
					//enemies[i] = new Enemy(segReader.nextFloat(), segReader.nextFloat(), segReader.next());//I would like a new Constructor to be Enemy (x , y , type)
				}
				return enemies;
			}
		}
		
		return level;
	}
	/**
	 * CalcXCord takes the current index of the read block and the blocks per row and uses it to calculate the block's x coordinate 
	 * @param i
	 * @param blocksPerRow
	 * @return xCoord
	 */
	private int calcXCord(int i, int blocksPerRow){
		i = i % (blocksPerRow);//converts i to the corresponding block number (interms of x)
		int xCoord = i*32; 
			
		return xCoord;
	}
	/**
	 * CalcYCord takes the current index of the read block and the blocks per row and uses it to calculate the block's y coordinate 
	 * @param i
	 * @param blocksPerRow
	 * @return yCoord
	 */
	private int calcYCord(int i, int blocksPerRow){
		i = i/blocksPerRow; //converts i to the corresponding row number
		int yCoord = i*32 + 32;//*32 because a block is 32 pixels tall
		return yCoord;
	}
	

	public void level1(Graphics2D g, int height, int width){
		block newest;

		g.setColor(Color.YELLOW);
		g.fillRect(0, height - wallHeight, 128, 192);
		newest = new block(0, height - wallHeight , 128, 192, 'W');
		blocks1.add(newest);
		g.fillRect(width - wallWidth, height - wallHeight, 128, wallHeight);
		newest = new block(width - wallWidth, height - wallHeight , 128, wallHeight, 'W');
		blocks1.add(newest);
		g.setColor(Color.CYAN);
		g.fillRect(0, height - wallHeight*2, wallWidth, wallHeight);
		newest = new block(0, height - wallHeight*2 , 'b');
		blocks1.add(newest);
		g.fillRect(width - wallWidth, height - wallHeight*2, wallWidth, wallHeight);
		newest = new block(width - wallWidth, height - wallHeight*2 , 'b');
		blocks1.add(newest);

		for(int j = 0; j < 2; j += 1){
			for(int i = 0; i < (width / 32 ); i += 1){

				if(i == 12 || i == 13 || i == 14){
					newest = new block(0 + 32 * i, height - 34, 's');
					blocks1.add(newest);
					g.setColor(Color.BLACK);
					g.drawRect(0 + 32 * i, (height - 34) - wallHeight*j , 32, 32);
					continue;
				}

				g.setColor(Color.CYAN);
				g.fillRect(0 + 32 * i, (height - 32) - wallHeight*j  , 32, 32);
				newest = new block(0 + 32 * i, (height - 32) - wallHeight*j, 'b');
				blocks1.add(newest);
				g.setColor(Color.BLACK);
				g.drawRect(0 + 32 * i, (height - 32) - wallHeight*j , 32, 32);
			}
		}
	}
}

