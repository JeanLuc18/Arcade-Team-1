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

	private int wallWidth = 128;
	private int wallHeight = 192;
	private int blockType;
	int platform = 0;
	private File level;
	String score;
	private Scanner reader;
	private Scanner segReader;

	ArrayList<block> blocks1 = new ArrayList<block>();
	/**
	 * level class constructor which opens level text document and scanner to read said document
	 * @throws FileNotFoundException
	 */
	public levels(String levelN) throws FileNotFoundException{
		level = new File(levelN);
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
	@SuppressWarnings("unused")
	public LinkedList<GameObject> genLevel(){
		
		LinkedList<GameObject> level = new LinkedList<GameObject>();
		int blockX = 0;
		int blockY = Game.HEIGHT - 32;
		int wallY = Game.HEIGHT;
		int shiftX = 0;
		int blocksPerRow = 0;
		int blocksPerLvl = 0;
		int blocksAdded = 0;
		String token = " ";
		
		while(reader.hasNext()){
			segReader = new Scanner(reader.next());
			while(segReader.hasNext()){
			token = segReader.next();
//			if(token.equals("R")){
//				//level.add(new Repairer(segReader.nextInt(), segReader.nextInt(), 32, 32));
//				
//			
//			}
			if(token.equals("W")){
				blocksAdded = 0;
				platform += 1;
				
				final int wallWidth = segReader.nextInt();
				//System.out.println(Game.WIDTH-wallWidth);
				final int wallHeight = segReader.nextInt();
				wallY = wallY - wallHeight;
				blockY = wallY + wallHeight - 32;
				if(wallWidth % 32 > 0 || wallHeight % 32 > 0){
					System.err.println("Wall deminsions must be divisable by 32"); 
					System.exit(1);
				}
				blocksPerRow = (Game.WIDTH - (2 * wallWidth)) / 32;
				blocksPerLvl = (blocksPerRow * (wallHeight/32));
				
				level.add(new block(0, wallY, wallWidth, wallHeight,'w', platform));//adds left wall
				level.add(new block(Game.WIDTH-wallWidth, wallY, wallWidth, wallHeight, 'w', platform));//adds Right wall
				shiftX = Game.WIDTH - wallWidth;//records where the blocks need to shift to when they hit the right wall
				blockX = 0 + wallWidth;
				
			}

			else{
				
				
				while(blocksAdded < token.length()){
					char curr = token.charAt(blocksAdded);
					if(blockX == shiftX){
						System.out.println(Game.WIDTH - wallWidth);
						System.out.println("SHIFT!!!!!!!!!!!!!!!!!!!!!!" + blockX);
						blockY = blockY - 32;//adjusts Y level if current block starts new row
						blockX = -1*(shiftX - Game.WIDTH);
					}
					if(blocksAdded >= blocksPerLvl){
						break;//the level is filled and no more blocks need to be read into current level segment
					}
					if(curr == 'R'){
						level.add(new Repairer(blockX, blockY-32,32,32));
						blocksAdded += 1;
						blocksPerLvl += 1;
					}
					else if(curr == 'F'){
						level.add(new FallingBlock(8000,8000,64,64));
						FallingBlock temp = (FallingBlock) level.get(level.size()-1);
						temp.setFallInt(blocksAdded%10);
					}
					else if(token.charAt(blocksAdded) == 'b'){
						level.add(new block(blockX, blockY, 'b', platform));
						blockX += 32;
						blocksAdded += 1;
					}
					else if(token.charAt(blocksAdded) == 'w'){
						level.add(new block(blockX, blockY, 'w', platform));
						blockX += 32;
						blocksAdded += 1;
					}
					else if(token.charAt(blocksAdded) == 's'){
						level.add(new block(blockX, blockY, 's', platform));
						blockX += 32;
						blocksAdded += 1;
					}
				}
			
			}
			}
			
		}
		//level.add(new FallingBlock(Game.WIDTH/2, -10, 64,64));	
		System.out.println(level.size()+ " is the number of blocks in level");
		
		return level;
	}
	
	public int getMaxPlatform(){
		return platform;
	}
}

