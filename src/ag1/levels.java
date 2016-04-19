package ag1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import jdk.nashorn.internal.ir.Block;
import arcadia.Game;



public class levels{

	int platform = 0;
	private File level;
	String score;
	private Scanner reader;
	private Scanner segReader;
	ImageClass I = new ImageClass();
	ImageClass images;
	//ArrayList<block> blocks1 = new ArrayList<block>(1000);
	/**
	 * level class constructor which opens level text document and scanner to read said document
	 * @throws FileNotFoundException
	 */
	public levels(String levelN) throws FileNotFoundException{
		level = new File(levelN);
		images = new ImageClass();
		reader = new Scanner(level);
		reader.useDelimiter("X");//tells scanner to parse text input by every "X" EX: TheXDelimiter would be broken up
		//"The" and "Delimiter" with each call to reader.next();
	}

	/**
	 * genLevel reads a level text file and builds the level from the bottom left up 
	 * *******NOTE: As of now each sub level must be on one line otherwise the level will not read correctly********
	 * @param reader level.txt scanner
	 * @return block[] which is the level segment block array
	 */

	public LinkedList<GameObject> genLevel(){

		LinkedList<GameObject> level = new LinkedList<GameObject>();//creates level list

		int blockX = 0;//x coordinate for blocks in level
		int blockY = Game.HEIGHT - 32;//y coordinate for blocks in level
		int wallY = Game.HEIGHT;//wall y coordinate for blocks in level
		int shiftX = 0;//variable used to tell the blocks to shift back to the far left when a row has been completed 
		int blocksPerRow = 0;//
		int blocksPerLvl = 0;// blocks___ are variables used for maintaining level structure ie knowing when a sub level is filled 
		int blocksAdded = 0; // or if the current row is filled
		int wallHeight;
		int wallWidth;
		char curr;
		String token = " ";//Initializes the sub level block segment string that will be read

		//loops through text file as long as there are things left to read
		while(reader.hasNext()){
			System.out.println("New level Segment");
			segReader = new Scanner(reader.next());//creates sublevel reader
			while(segReader.hasNext()){//reads through to the end of the sub level
				token = segReader.next();//sets the token string to be the first element of the sub level 
				//which should be a 'W' to signify wall dimensions	
				//********Wall Element********
				if(token.equals("W")){
					blocksAdded = 0;//initializes the number of blocks added to sublevel
					platform += 1;//adds one to platform number

					wallHeight = segReader.nextInt();
					wallWidth = segReader.nextInt();
					wallY = wallY - wallHeight;//calculates the current wall's y coordinate (top left corner)
					blockY = wallY + wallHeight - 32;//initializes the block's y coordinates

					blocksPerRow = (Game.WIDTH - (2 * wallWidth)) / 32;
					blocksPerLvl = (blocksPerRow * (wallHeight/32));


					level.add(new block(0, wallY, wallWidth, wallHeight,'w', platform, I.brick, I.brickWallLeft, I.brickWallRight));//adds left wall
					level.add(new block(Game.WIDTH-wallWidth, wallY, wallWidth, wallHeight, 'w', platform, I.brick, I.brickWallLeft, I.brickWallRight));//adds Right wall
					shiftX = Game.WIDTH - wallWidth;//records where the blocks need to shift to when they hit the right wall
					blockX = 0 + wallWidth;

				}
				//********non Wall elements********
				else{
					//reads through "token" which contains block information 
					while(blocksAdded < token.length()){
						curr = token.charAt(blocksAdded);//reads current block
						//checks to see if the row is filled 
						if(blockX == shiftX){
							blockY = blockY - 32;//adjusts Y level if current block starts new row
							blockX = -1*(shiftX - Game.WIDTH);//resets blockX to be leftmost acceptable x 
						}
						//checks to see if the sublevel is filled
						if(blocksAdded >= blocksPerLvl){
							break;//the level is filled and no more blocks need to be read into current level segment
						}
						//Repairer enemy 
						if(curr == 'R'){
							level.add(new Repairer(blockX, blockY-32,32,32, I.repairerLeft1, I.repairerRight1, I.brick));
							blocksAdded += 1;
							blocksPerLvl += 1;
						}
						//Bomber enemy
						if(curr == 'B'){
							level.add(new Bomber(blockY-32,32,32));
							blocksAdded += 1;
							blocksPerLvl += 1;
						}
						//Moving block
						else if(curr == 'M'){
							level.add(new movingblock(blockX, blockY-32, 'b' , platform, I.cloud, I.brick, I.brickWallLeft, I.brickWallRight));
							blocksAdded += 1;
							blocksPerLvl += 1;
						}
						//Falling block
						else if(curr == 'F'){
							level.add(new FallingBlock(blockX, blockY-32-500,64,64, I.fallingblock));
							level.get(level.size() - 1).setID(GOID.Enemy);
							blocksAdded += 1;
							blocksPerLvl +=1;
						}
						//breakable block
						else if(token.charAt(blocksAdded) == 'b'){
							level.add(new block(blockX, blockY, 'b', platform, I.brick, I.brickWallLeft, I.brickWallRight));
							blockX += 32;
							blocksAdded += 1;
						}
						//un-breakable block
						else if(token.charAt(blocksAdded) == 'w'){
							level.add(new block(blockX, blockY, 'w', platform, I.brick, I.brickWallLeft, I.brickWallRight));
							blockX += 32;
							blocksAdded += 1;
						}
						//space "block" (not really a block but moving block uses these to map out path)
						else if(token.charAt(blocksAdded) == 's'){
							blockX += 32;
							blocksAdded += 1;
						}
					}
				}
			}
		}
		return level;//returns the filled level
	}
	/**
	 * getMaxPlatform returns the "max platform" which is used to signal end of level
	 * @return
	 */
	public int getMaxPlatform(){
		return platform;
	}
}

