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
			if(token.equals("W")){
				blocksAdded = 0;
				final int wallWidth = segReader.nextInt();
				System.out.println(Game.WIDTH-wallWidth);
				final int wallHeight = segReader.nextInt();
				wallY = wallY - wallHeight;
				blockY = wallY + wallHeight - 32;
				if(wallWidth % 32 > 0 || wallHeight % 32 > 0){
					System.err.println("Wall deminsions must be divisable by 32"); 
					System.exit(1);
				}
				blocksPerRow = (Game.WIDTH - (2 * wallWidth)) / 32;
				blocksPerLvl = (blocksPerRow * (wallHeight/32));
				
				level.add(new block(0, wallY, wallWidth, wallHeight,'w'));//adds left wall
				level.add(new block(Game.WIDTH-wallWidth, wallY, wallWidth, wallHeight, 'w'));//adds Right wall
				System.out.println("Shift X = "+ (Game.WIDTH - wallWidth));
				shiftX = Game.WIDTH - wallWidth;
				blockX = 0 + wallWidth;
				
//				if(!segReader.hasNext()){
//					System.out.println("Entered If");
//					while(blocksAdded < blocksPerLvl){
//						System.out.println("blocksAdded = "+blocksAdded);
//						if(blockX == Game.WIDTH - wallWidth+32){
//							blockY = blockY - 32;//adjusts Y level if current block starts new row
//							blockX =  wallWidth -32;
//						}
//						
//						level.add(new block(blockX, blockY, 's'));
//						blockX += 32;
//						blocksAdded += 1;
//					}
//				}
			}
			//else if(token.equals("E")){
				//need to talk about this before I try to implement
			//}
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
					if(token.charAt(blocksAdded) == 'b'){
						level.add(new block(blockX, blockY, 'b'));
						blockX += 32;
						blocksAdded += 1;
					}
					else if(token.charAt(blocksAdded) == 'w'){
						level.add(new block(blockX, blockY, 'w'));
						blockX += 32;
						blocksAdded += 1;
					}
					else if(token.charAt(blocksAdded) == 's'){
						level.add(new block(blockX, blockY, 's'));
						blockX += 32;
						blocksAdded += 1;
					}
				}
				//adds filler blocks if level segment isn't full
//				if(blocksAdded >= token.length() || !segReader.hasNext()){
//					System.out.println("Entered If");
//					while(blocksAdded < blocksPerLvl){
//						if(blockX == shiftX){
//							System.out.println(Game.WIDTH - wallWidth);
//							System.out.println("SHIFT!!!!!!!!!!!!!!!!!!!!!!" + blockX);
//							blockY = blockY - 32;//adjusts Y level if current block starts new row
//							blockX = -1*(shiftX - Game.WIDTH);
//						}
//						
//						level.add(new block(blockX, blockY, 's'));
//						blockX += 32;
//						blocksAdded += 1;
//					}
//				}
			}
			}
			
		}
			
		System.out.println(level.size()+ " is the number of blocks in level");
		
		return level;
	}


	private static void fillSpace(LinkedList<GameObject> level, int blocksAdded, int blocksPerLvl, int wallWidth, int blockY, int blockX){
		
	}
	/**
	 * CalcXCord takes the current index of the read block and the blocks per row and uses it to calculate the block's x coordinate 
	 * @param i
	 * @param blocksPerRow
	 * @return xCoord
	 */
	private int calcXCord(int blocksAdded, int blocksPerRow){

		int xBlock = (blocksAdded) % (blocksPerRow);//converts i to the corresponding block number (interms of x)
		int xCoord = (xBlock)*32 + wallWidth;//minus one b/c of the x coordinate being in the left corner 

		return xCoord;
	}
	/**
	 * CalcYCord takes the current index of the read block and the blocks per row and uses it to calculate the block's y coordinate 
	 * @param i
	 * @param blocksPerRow
	 * @return yCoord
	 */
	private int calcYCord(int blocksAdded, int blocksPerRow, int wallY){
		int lvl = blocksAdded/blocksPerRow; //converts i to the corresponding row number
		int yCoord = wallY-wallHeight - lvl*32;// because a block is 32 pixels tall
		return yCoord;
	}


	public void level1(Graphics2D g, int height, int width, long score){
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
		newest = new block(0, height - wallHeight*2 , 128, wallHeight,'W');
		blocks1.add(newest);
		g.fillRect(width - wallWidth, height - wallHeight*2, wallWidth, wallHeight);
		newest = new block(width - wallWidth, height - wallHeight*2 , 128, wallHeight,'W');
		blocks1.add(newest);
		g.setFont(new Font("Stencil", Font.PLAIN, 36));

		this.score = Long.toString(score);

		this.score = ("00000000" + this.score).substring(this.score.length());

		//LSCounter

		g.drawString(this.score, width/2 + 25, 196);

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

//	public LinkedList<GameObject> mikeTestLevel(){
//		//create new list to be run by HGame1
//		LinkedList<GameObject> objects = new LinkedList<GameObject>();
//
//		objects = this.genLevel(reader);
//		
//		//add enemy
//				Enemy testRepairer = new Repairer(200, 463, 50, 50);
//				
//				objects.add(testRepairer);
//
//		//add floor
//		for(int i = 0; i < Game.WIDTH/32; i++){
//			objects.add(new block(32*i, Game.HEIGHT+32, 32, 32, 'b'));
//		}
//
//		//add walls
//		//		objects.add(new block(0, Game.HEIGHT-232, 128, 200, 'w'));
//		//		objects.add(new block(Game.WIDTH-128, Game.HEIGHT-232, 128, 200, 'w'));
//		//
//		//		//add second level with hole in middle
//		//		for(int i = 128; i < Game.WIDTH-128; i+=32){
//		//			if(i < Game.WIDTH/2 - 64 || i > Game.WIDTH/2 + 32){
//		//				objects.add(new block(i, Game.HEIGHT-200-32, 32, 32, 's'));
//		//			}
//		//		}
//
//		//TEST PRINTING
//		System.out.println(objects);
//		block b = (block)objects.get(0);
//		System.out.println(b.getId());
//		System.out.println(b.getX());
//		System.out.println(b.getY());
//		System.out.println(b.getWidth());
//		System.out.println(b.getHeight());
//		System.out.println(b.getType());
//
//		b = (block)objects.get(1);
//		System.out.println(b.getId());
//		System.out.println(b.getX());
//		System.out.println(b.getY());
//		System.out.println(b.getWidth());
//		System.out.println(b.getHeight());
//		System.out.println(b.getType());
//
//		//return the list that will be continuously ticked and rendered
//		return objects;
//	}
}

