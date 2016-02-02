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

	ArrayList<block> blocks1 = new ArrayList<block>();
	/**
	 * level class constructor which opens level text document and scanner to read said document
	 * @throws FileNotFoundException
	 */
	public levels(String levelN) throws FileNotFoundException{
		level = new File(levelN+".txt");
		reader = new Scanner(level);
		//		reader.useDelimiter("X");//tells scanner to parse text input by every "X" EX: TheXDelimiter would be broken up
		//								 //"The" and "Delimiter" with each call to reader.next();
	}

	/**
	 * genLevel reads a single level segment or enemy list from the level txt and then gives back the array of blocks/enemies for that level with the two wall
	 * objects in the last two indexes left first right second
	 * @param reader level.txt scanner
	 * @return block[] which is the level segment block array
	 */
	private LinkedList<GameObject> genLevel(Scanner reader){
		int usableH = Game.HEIGHT;
		int usableW = Game.WIDTH;
		int blocksPerRow = 0;
		int blocksPerlvl = 0;
		int lvl = 0;// current level * wallHeight
		int blocksAdded = 0;
		int idiotAdjust = 0;
		int wallY = Game.HEIGHT;
		int blockY = Game.HEIGHT-32;
		boolean gotNext = false;
		LinkedList<GameObject> level = new LinkedList<>();

		//Scanner segReader = new Scanner(reader.next());//reads first level segment ie anything before first "X" in level file
		String token = " ";
		//reads the level segment assuming the level segment isn't empty
		System.out.println("did i get here ");// + reader.hasNext());
		while(reader.hasNext()){
			token = reader.next();
			if(token.equals("W")){
				//Wall type Block

				wallWidth = reader.nextInt();
				wallHeight = reader.nextInt();
				usableW = usableW - (wallWidth*2);
				usableH = wallHeight;
				wallY = wallY - wallHeight;
				blocksPerRow = usableW / 32;
				blocksPerlvl = (wallHeight/32)*blocksPerRow;
				if(wallWidth % 32 > 0 || wallHeight % 32 > 0){  //checks to make sure the wall allows even block distribution 
					System.err.println("Invalid Wall Deminsions: Must be a divisiable by 32");
					System.exit(1);
				}
				
				
				level.add(new block(0, wallY, wallWidth, wallHeight, 'w'));//adds left wall
				level.add(new block(Game.WIDTH- wallWidth, wallY, wallWidth, wallHeight,'w'));//adds right wall
			}
			//level = new block[((usableW/32)*(usableH/32))+2];//creates single level segment array with space for rest of blocks + 2 for the walls
			else if(token.equals("E")){
				LinkedList<GameObject> enemies = new LinkedList<>();
				while(reader.hasNext()){
					reader.next();
				}
				//return enemies;
			}
			else{
				block temp = null;
				
				while(blocksAdded != blocksPerlvl){
					if(blocksAdded >= token.length()){
						if(reader.hasNext()){
							token = reader.next();
							idiotAdjust += blocksAdded;
							blocksAdded = 0;
							
						}
						else{
							if(blocksAdded % blocksPerRow == 0){
								//System.out.println("Im here block - 32");
								blockY -= 32;
							}
							//System.out.println("no more");
							//temp = new block(calcXCord(blocksAdded+idiotAdjust, blocksPerRow), blockY, 's');
							blocksAdded +=1;
							//level.add(temp);
						}
					}
					else{
						
						char curr = token.charAt(blocksAdded);
						if(((blocksAdded+idiotAdjust) % blocksPerRow) == 0 && blocksAdded+idiotAdjust != 0){
							System.out.println("Im here block - 32");
							blockY -= 32;
						}
						if(curr == 'b'){
							//gets the x coord by getting the block before its x coord and adding that blocks width to its x coord 
							temp = new block(calcXCord(blocksAdded+idiotAdjust, blocksPerRow), blockY, 'b');//creates new breakable block 
						}
						else if(curr == 's'){
							temp = new block(calcXCord(blocksAdded+idiotAdjust, blocksPerRow), blockY, 's');//creates new space block
						}
						else if(curr == 'w'){
							temp = new block(calcXCord(blocksAdded+idiotAdjust, blocksPerRow), blockY, 'w');//creates new unbreakable block
						}
						blocksAdded += 1;
						level.add(temp);
					}
				}




				//token = reader.next();//reads the string of blocks

				//			for(int i = 0; i < token.length(); i++){
				//				
				//				char curr = token.charAt(i);
				//				if(i == blocksPerlvl){
				//					temp = new block(lvl + wallHeight, Game.WIDTH-wallWidth,wallWidth,wallHeight,'W');//add right wall
				//					lvl += lvl + wallHeight;
				//				}else if(i == 0){
				//					temp = new block(0, lvl + wallHeight, wallWidth, wallHeight,'W');//adds left wall
				//				}else if(curr == 'b'){
				//					//gets the x coord by getting the block before its x coord and adding that blocks width to its x coord 
				//					temp = new block(calcXCord(i, blocksPerRow), calcYCord(i, blocksPerRow, lvl), 'b');//creates new breakable block 
				//				}
				//				else if(curr == 's'){
				//					temp = new block(level.get(i-1).getX() + level.get(i-1).getWidth(), calcYCord(i, blocksPerRow, lvl), 's');//creates new space block
				//				}
				//				else if(curr == 'w'){
				//					temp = new block(level.get(i-1).getX() + level.get(i-1).getWidth(), calcYCord(i, blocksPerRow, lvl), 'w');//creates new unbreakable block
				//				}

				//				if(i == level.length-3){
				//					System.out.println("No more room!!!");//if the level segment cant fit any more blocks
				//					break;
				//				}
				//System.out.println(temp.getType());


				//}
			}
			//level[level.length-2] = new block(x, y, width, height, type);//left wall
			//level[level.length-1] = new block(x, y, width, height, type);//right wall



		}
		System.out.println(level.size()+ "is the number of blocks in level");
		return level;
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

	public LinkedList<GameObject> mikeTestLevel(){
		//create new list to be run by HGame1
		LinkedList<GameObject> objects = new LinkedList<GameObject>();

		objects = this.genLevel(reader);

		//add floor
		for(int i = 0; i < Game.WIDTH/32; i++){
			objects.add(new block(32*i, Game.HEIGHT+32, 32, 32, 'b'));
		}

		//add walls
		//		objects.add(new block(0, Game.HEIGHT-232, 128, 200, 'w'));
		//		objects.add(new block(Game.WIDTH-128, Game.HEIGHT-232, 128, 200, 'w'));
		//
		//		//add second level with hole in middle
		//		for(int i = 128; i < Game.WIDTH-128; i+=32){
		//			if(i < Game.WIDTH/2 - 64 || i > Game.WIDTH/2 + 32){
		//				objects.add(new block(i, Game.HEIGHT-200-32, 32, 32, 's'));
		//			}
		//		}

		//TEST PRINTING
		System.out.println(objects);
		block b = (block)objects.get(0);
		System.out.println(b.getId());
		System.out.println(b.getX());
		System.out.println(b.getY());
		System.out.println(b.getWidth());
		System.out.println(b.getHeight());
		System.out.println(b.getType());

		b = (block)objects.get(1);
		System.out.println(b.getId());
		System.out.println(b.getX());
		System.out.println(b.getY());
		System.out.println(b.getWidth());
		System.out.println(b.getHeight());
		System.out.println(b.getType());

		//return the list that will be continuously ticked and rendered
		return objects;
	}
}

