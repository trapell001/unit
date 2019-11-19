package tower;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Screen extends JPanel implements Runnable {
	public static boolean runGame = false;
	public static ArrayList<Mob> mobs = new ArrayList<Mob>();
	public Thread thread = new Thread(this);
	
	public static Image[] tileset_ground = new Image[100];
	public static Image[] tileset_air = new Image[100];
	public static Image[] tileset_res = new Image[100];
	public static Image[] tileset_mob = new Image[100];
	
	public static boolean saveFileExists;
	public static boolean newLaunch = true;
	public static File saveFile = new File("save" + File.separator + "game");
	public static int startingLevel = 1;
	
	public static int myWidth, myHeight;
	public static int coinage;
	public static int health = 100;
	public static int killed = 0, killsToWin = 0;
	public static int winTime = 3500, winFrame = 0;
	
	public static boolean isFirst = true;
	public static boolean isWin = false;
	public static boolean isGameEnding = false;
	
	public static Point mse = new Point(0, 0);
	
	public static int spawnTime, spawnFrame = 0;
	public static boolean firstSpawn;
	
	public static Room room;
	public static Save save;
	public static Store store;
	
	
	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public Screen(Frame frame) {

		frame.addMouseListener(new KeyHandel());
		frame.addMouseMotionListener(new KeyHandel());
		frame.addKeyListener(new KeyHandel());
		
		thread.start();
	}
	
	public static void hasWon() {
		if(killed == killsToWin) {
			isWin = true;
			killed = 0;
			coinage = 0;
		}
	}
	
	public void define() {
		room = new Room();
		save = new Save();
		store = new Store();
		
		for(int i = 0; i < tileset_ground.length; i++) {
			tileset_ground[i] = new ImageIcon("res" + File.separator + "tileset_ground.png").getImage();
			
			tileset_ground[i] = createImage(new FilteredImageSource(tileset_ground[i].getSource(), new CropImageFilter(0, room.blockSize*i, room.blockSize, room.blockSize)));

		}
		for(int i = 0; i < tileset_air.length; i++) {
			tileset_air[i] = new ImageIcon("res" + File.separator + "tileset_air.png").getImage();
			
				tileset_air[i] = createImage(new FilteredImageSource(tileset_air[i].getSource(), new CropImageFilter(0, room.blockSize*i, room.blockSize, room.blockSize)));
		}
		
		tileset_res[0] = new ImageIcon("res" + File.separator + "cell.png").getImage();
		tileset_res[1] = new ImageIcon("res" + File.separator + "heart.png").getImage();
		tileset_res[2] = new ImageIcon("res" + File.separator + "coin.png").getImage();
		tileset_res[3] = new ImageIcon("res" + File.separator + "level.png").getImage();
		
		tileset_mob[0] = new ImageIcon("res" + File.separator + "mob.png").getImage();
		
		firstSpawn = true;
		spawnFrame = 0;
		
		health = 100;
		
		try {
		
			if(saveFile.exists()) {
				saveFileExists = true;
			} else {
				saveFileExists = false;
				File tmpCreateFolder = new File("save");
				tmpCreateFolder.mkdirs();
				saveFile.createNewFile();
			}
			
			if(newLaunch) {
				newLaunch = false;
				if(saveFileExists) {
					Scanner loadScanner = new Scanner(saveFile);
					while(loadScanner.hasNext()) {
						startingLevel = loadScanner.nextInt();	
					}
					loadScanner.close();
				}	
			}
			
			File loadLevelFile = new File("levels" + File.separator + "mission" + startingLevel + ".btd"); 
			if(loadLevelFile.exists()) {
				save.loadSave(loadLevelFile);	
			} else {
				isGameEnding = true;
				loadLevelFile = new File("levels" + File.separator + "mission" + 1 + ".btd"); 
				save.loadSave(loadLevelFile);
			}
			
		} catch (Exception e) {}
		
		Store.towerCounter = 0;
	
	}
	
	public void paintComponent(Graphics g) {
			if(isFirst) {
				myWidth = getWidth();
				myHeight = getHeight();
				define();
				
				isFirst = false;
			}
			
			g.setColor(new Color(65, 65, 70));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(new Color(0, 0, 0));
			g.drawLine(room.block[0][0].x-1, 0, room.block[0][0].x-1, room.block[room.worldHeight-1][0].y + room.blockSize);
			g.drawLine(room.block[0][room.worldWidth-1].x + room.blockSize, 0, room.block[0][room.worldWidth-1].x + room.blockSize, room.block[room.worldHeight-1][0].y + room.blockSize);
			g.drawLine(room.block[0][0].x, room.block[room.worldHeight-1][0].y + room.blockSize, room.block[0][room.worldWidth-1].x + room.blockSize, room.block[room.worldHeight-1][0].y + room.blockSize);
			
			
			room.draw(g);
			
			for(int i = 0; i < mobs.size(); i++)
			{
				mobs.get(i).draw(g);
			}
			for(int i = 0; i < bullets.size(); i++)
			{
				if(bullets.get(i).inGame)
				{
					bullets.get(i).drawBullet(g);
				}
			}
			
			store.draw(g);	
			
			if(health < 1) {
				String deathMessage = "Game Over";
				int textSize = 70;
				int moreRight = 20;
				int moreDown = 40;
				
				g.setColor(new Color(36, 41, 240));
				g.fillRect(0, 0, myWidth, myHeight);
				g.setColor(new Color(255, 255, 250));
				g.setFont(new Font("Courier New", Font.BOLD, textSize));
				
					g.drawString(deathMessage, (Frame.Size1.width / 3) + moreRight, (Frame.Size1.height / 3) + moreDown);	
			
			}
			
			if(isWin && !isGameEnding) {
				String levelWonMessage = "You've complete level  ...";
				int textSize = 26;
				int right = 18;
				int down = 70;
				
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(new Color(140, 210, 255));
				g.setFont(new Font("Courier New", Font.BOLD, textSize));
				g.drawString(levelWonMessage, right, down);
			}
			
			if(isGameEnding) {
				String gameWonMessage = "You Win, Press Esc to exit";
				int textSize = 26;
				int right = 18;
				int down = 70;
				
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(new Color(140, 210, 255));
				g.setFont(new Font("Courier New", Font.BOLD, textSize));
				g.drawString(gameWonMessage, right, down);
            	if(Screen.saveFile.exists()) {
            		Screen.saveFile.delete();
            	}
			}
	}
	
	public void mobSpawner() {
		if(spawnFrame >= spawnTime)
		{
			Mob temp = new Mob(Value.mobGreeny);
			mobs.add(temp);
			spawnFrame = 0;
		}
		else
		{
			spawnFrame += 1;
		}
	}
	

	public void run() {
		runGame = true;

		while(true) {
			while(runGame) {
				try {
					
				if(!isFirst && health > 0 && !isWin) {
					room.physic();
					mobSpawner();
					for(int i = 0; i < mobs.size(); i++) {
						if(mobs.get(i).inGame) {
							mobs.get(i).physic();
						}
					}
					for(int i = 0; i < bullets.size(); i++)
					{
						if(bullets.get(i).inGame)
						{
							bullets.get(i).physic();
						}
					}
				} else {
					if(isWin && !isGameEnding) {
						if(winFrame >= winTime) {
							isWin = false;
							startingLevel += 1;
							define();
							
							winFrame = 0;
						} else {
							winFrame++;
						}
					} else if(isGameEnding) {
						Thread.sleep(winTime);
						System.exit(0);
					}
				}
				
				repaint();
					
				
				
					Thread.sleep(1);
				} catch(Exception e) {
					runGame = false;
					System.err.println("Error: " + e.getMessage());
					System.exit(100);
				}
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				System.err.println("Error: " + e.getMessage());
				System.exit(0);
			}
		}
	}
}
