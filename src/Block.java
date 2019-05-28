import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Block {

	private int x,y;
	private Image img;
	private Rectangle r;
	private boolean isFalling, hitBot;
	public final static int W = 60;
	public final static int H = 60;
	private static final int FALLRATE = 2; //rate at which the y changes
	private final static int RESETVELOCITY = -3; //starting fall velocity
	private int velocity = -3; //starting fall velocity
	public int tickst; //time since the jump has begun


	public Block(int xPos, int yPos) {
		x = xPos;
		y = yPos;
		isFalling = false;
		img = getImage("Block.png");
		r = new Rectangle(xPos,yPos, W, H);

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

	public void move(Character c) {
		Rectangle intersect = c.getRect().intersection(this.r);
		if (intersect.width < intersect.height) {
			if (c.getLeft() && intersect.x<= c.getRect().x) this.x -= Character.MOVEGAP; //character on the right
			if (c.getRight() && c.getRect().x < intersect.x) this.x += Character.MOVEGAP; //character on the left
		}
		this.updateRectangle();
	}
	private void updateRectangle() {
		this.r.x = this.getX();
		this.r.y = this.getY();
	}

	public int getY() {
		// TODO Auto-generated method stub
		return this.y;
	}
	public int getX() {
		// TODO Auto-generated method stub
		return this.x;
	}

	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return this.r;
	}
	public boolean hitWall(ArrayList<Wall> walls) {
		for(Wall w:walls) {
			if (this.r.intersects(w.getRect())) {
				return true;
			}
		}
		return false;
	}
	public boolean hitWater(ArrayList<Water> waters) {
		for(Water w: waters) {
			if (this.r.intersects(w.getRect())) {
				return true;
			}
		}
		return false;
	}
	public Rectangle hitSide(ArrayList<Wall> walls) {
		for(Wall w:walls) {
			if (this.r.intersects(w.getRect())) {
				Rectangle inter = r.intersection(w.getRect());
				if ((this.x==inter.x||this.x+W>inter.x) && this.y+H < inter.y) return null;
				if (inter.width < inter.height) return inter;
			}

		}
		return null;
	}
	public void setHitBot() {hitBot = false;}
	public boolean hitBot(Rectangle rect) {
		if (this.r.intersects(rect)) {
			Rectangle inter = r.intersection(rect);
			if (inter.width > inter.height && inter.y < this.y + inter.height) {
				hitBot = true;
				return true;
			}
		}
		return false;
	}
	public void moveBack(Rectangle rect) {
		Rectangle intersect = rect.intersection(this.r);
		if (intersect.width < intersect.height) {
			if (intersect.x<= this.x) this.x += intersect.width; //wall on the right
			if (this.x < intersect.x) this.x -= intersect.width; //wall on the left
		}
		this.updateRectangle();
	}
	public void fall() {
		this.y+= FALLRATE;
		this.updateRectangle();
	}
	public void setFalling(boolean b) {
		isFalling = b;
		if(!b) {
			this.velocity = Block.RESETVELOCITY;
			this.tickst = 0;
		}
	}
	private void accelerate() {
		this.velocity++;	
	}
}

