package tower;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


@SuppressWarnings("serial")
public class Block extends Rectangle {
	private static boolean isFirst = true;
	public static int towerSqareSize = 112; 
	public Rectangle towerSqare;
	public int groundID;
	public int airID;
	public int loseTime = 80, loseFrame = 0;
	public int countTime = 0;
	public static int lessMoney;
	
	public int shotMob = -1;
	public boolean shooting = false;
	
	public Block(int x, int y, int width, int height, int groundID, int airID) {
		if(isFirst) {
			isFirst = false;
		}
		setBounds(x, y, width, height);
		towerSqare = new Rectangle(x - (towerSqareSize/2), y - (towerSqareSize/2), width + towerSqareSize, height + towerSqareSize);
		this.groundID = groundID;
		this.airID = airID;
	}
	
	public void draw(Graphics g) {
		g.drawImage(Screen.tileset_ground[groundID], x, y, width, height, null);
		
		if(airID != Value.airAir) {
			g.drawImage(Screen.tileset_air[airID], x, y, width, height, null);
		}
	}
	
	public void physic() {
		if (shotMob != -1 && towerSqare.intersects(Screen.mobs.get(shotMob))) {
			shooting = true;
			countTime += 1;
			if(countTime == 100)
			{
				Bullet temp = new Bullet(x + Screen.room.blockSize/2, y + Screen.room.blockSize/2, Screen.mobs.get(shotMob).x +Screen.room.blockSize/2 , Screen.mobs.get(shotMob).y + Screen.room.blockSize/2);
				Screen.bullets.add(temp);
				countTime = 0;
			}
		} else {
			shooting = false;
		}
		
		if(!shooting) {
			
			if (airID == Value.airTowerLaser) {
				for (int i = 0; i < Screen.mobs.size(); i++) {
					if (Screen.mobs.get(i).inGame) {
						if (towerSqare.intersects(Screen.mobs.get(i))) {
							shotMob = i;
						}
					}
				}
			}
		}	
		
		if(shooting) {
			if(loseFrame >= loseTime) {
				
				Screen.mobs.get(shotMob).loseHealth(1);
				
				loseFrame = 0;
			} else {
				loseFrame += 1;
			}
			
			if(Screen.mobs.get(shotMob).isDead()) {
				shooting = false;
				shotMob = -1;
				Screen.hasWon();
			}	
		}
	}

	public void getMoney(int mobID) {
		if(Store.towerCounter >= lessMoney) {
			Screen.coinage += (Value.deathReward[mobID] / (Store.towerCounter / lessMoney));	
		} else {
			Screen.coinage += (Value.deathReward[mobID]);
		}
	}
	
	public void fight(Graphics g) {
		if(shooting)
		{
			
		}
	}
}
