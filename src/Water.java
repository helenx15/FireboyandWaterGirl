import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Water {

	private int x,y, color;
	private Image img;
	private Rectangle r;
	private final static int HEIGHT = 50;
	public final static int W = 100;
	public final static int H = 25;
	
	public Water(int xPos, int yPos, String c, int colour) {
		x = xPos;
		y = yPos;
		img = getImage(c);
		color = colour;
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
	
	public Rectangle getRect() {
		return this.r;
	}
	
	public int getColor () {
		return this.color;
	}
	

}
