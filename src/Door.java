import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Door {
	
	private int x,y;
	private boolean isRed;
	private Image img;
	private int W = 75;
	private int H = 75;
	private Rectangle r;
	
	public Door(int xPos, int yPos, boolean b) {
		x = xPos;
		y = yPos;
		isRed = b;
		if(b) img = getImage("RedDoor.png");
		else img = getImage("BlueDoor.png");
		r = new Rectangle(x,y,W,H);
	}
	
	protected  Image getImage(String fn) {
		Image img = null;
		try {
			
			img = ImageIO.read(new File("src/" +fn));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}
	
	public void draw(Graphics g) {
		g.drawImage(img, x, y, W, H, null);
	}
	public boolean getIsRed() {
		return this.isRed;
	}
	
	public Rectangle getRect() {
		return this.r;
	}
	
}
