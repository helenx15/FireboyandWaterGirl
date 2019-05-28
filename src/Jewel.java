import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Jewel {
	
	private int x,y;
	private boolean isRed;
	private Image img;
	private Rectangle r;
	private static int W = 25;
	private static int H = 25;

	
	public Jewel(int xPos, int yPos, boolean b) {
		x = xPos;
		y = yPos;
		isRed = b;
		if(b) img = getImage("redJ.png");
		else img = getImage("blueJ.png");
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
	
	public boolean getisRed() {
		return this.isRed;
	}
	
	public Rectangle getRect() {
		return this.r;
	}
	
	public void draw(Graphics g) {
		g.drawImage(img, x, y, W, H, null);
	}
	
}
