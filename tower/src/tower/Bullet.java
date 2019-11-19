package tower;

import java.awt.*;

@SuppressWarnings("serial")
public class Bullet extends Rectangle{
	public boolean inGame = false;
	double xDirection ;
    double yDirection ;
	public Bullet(int x, int y, int xt, int yt) {
		this.x = x;
		this.y = y;
		double a = xt-x, b= yt - y;
		double c = Math.sqrt(a*a + b*b);
		xDirection =  2* a/c;
	    yDirection =  2* b/c;
	    inGame = true;
	}
	public void drawBullet(Graphics g) {
        if(inGame)
        {
        	g.setColor(Color.red);
            g.drawOval(x, y, 10, 10);
            g.fillOval(x, y, 10, 10);
        }
    }
	
	public void physic()
	{
	    x += (int)xDirection;
	    y += (int)yDirection;
	    
	}
}
