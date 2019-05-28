import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Character {
	private BufferedImage img;
	private int x,y;
	private boolean isWater;
	private boolean isJumping, isRight, isLeft, hitTop;
	private Rectangle r;
	private final int W = 45;
	private final int H = 75;
	public final static int MOVEGAP = 2; //each horizontal movement of the characters is 1 pixels 
	private final static int RESETVELOCITY = -3; //starting fall velocity
	private static final int FALLRATE = 2; //rate at which the y changes
	private final static int MODIFIER = 5; //adjusted pixels cut out of the char rectangle
	private int velocity = -3; //starting fall velocity
	private int tickst; //time since the jump has begun

	public Character(int xPos, int yPos, boolean b) {
		x = xPos;
		y = yPos;
		isWater = b;
		isJumping = false;
		if(b) img = getImage("watergirl.png");
		else img = getImage("fireboy.png");
		r = new Rectangle(x + MODIFIER + 5 , y,W - MODIFIER, H);

	}

	protected  BufferedImage getImage(String s) {
		BufferedImage img = null;
		try {

			img = ImageIO.read(new File("src/" +s));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}

	public Rectangle getRect() {
		return this.r;
	}

	public void jump() {
		isJumping = true;

		tickst++;

		if (tickst% 15 == 0) {
			this.accelerate();
		}

		this.y += this.velocity;
		this.updateRectangle();

	}
	public void draw(Graphics g) {
		g.drawImage(img,x,y, W, H, null);
	}

	public void moveLeft() {
		this.x = this.x - Character.MOVEGAP;
		this.updateRectangle();
	}

	public void moveRight() {
		this.x = this.x + Character.MOVEGAP;
		this.updateRectangle();

	}

	private void updateRectangle() {
		this.r.x = this.getX();
		this.r.y = this.getY();
	}

	private int getY() {
		return this.y;
	}

	private int getX() {
		return this.x;
	}

	private void accelerate() {
		this.velocity++;	
	}

	public void fall() {
		if(!isJumping) this.y+= FALLRATE;
		this.updateRectangle();
	}

	public void setJumping(boolean b) {
		isJumping = b;
		if(!b) {
			this.velocity = Character.RESETVELOCITY;
			this.tickst = 0;
		}
	}
	public boolean getJumping() {
		return isJumping;
	}

	public void setRight(boolean b) {
		isRight = b;
	}
	public boolean getRight() {
		return isRight;
	}
	public void setLeft(boolean b) {
		isLeft = b;
	}
	public boolean getLeft() {
		return isLeft;
	}

	public void move() {
		if (isRight) moveRight();
		if (isLeft) moveLeft();

	}
	public void moveBack() {
		if (isRight) moveLeft();
		if (isLeft) moveRight();
		if (hitTop) fallBack();
	}

	private void fallBack() {
		this.isJumping = false;
		this.y+=1;
		hitTop = false;
		this.updateRectangle();
	}
 
	public boolean hitWall(ArrayList<Wall> walls) {
		for(Wall w:walls) {
			if (this.r.intersects(w.getRect())) {
				return true;
			}
		}
		return false;
	}
	public boolean hitSide(ArrayList<Wall> walls) {
		for(Wall w:walls) {
			if (this.r.intersects(w.getRect())) {
				Rectangle inter = r.intersection(w.getRect());
				if ((this.x==inter.x||this.x+W>inter.x) && this.y+H < inter.y) return false;
				if (inter.width < inter.height) return true;
			}
			
		}
		return false;
	}
	
	public void setHitTop() {hitTop = false;}
	
	public boolean hitTop(ArrayList<Wall> walls) {
		for(Wall w:walls) {
			if (this.r.intersects(w.getRect())) {
				Rectangle inter = r.intersection(w.getRect());
				if (inter.width > inter.height && inter.y < this.y + inter.height) {
					hitTop = true;
					return true;
				}
			}
		}
		return false;
	}

	public boolean hitBlock (Rectangle rect) {
		if (this.r.intersects(rect)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public boolean hitBlockTop(Block block) {
		if (this.r.intersects(block.getRect())) {
			return true;
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

	public boolean hitBadWater(ArrayList<Water> waters) {
		for(Water w : waters) {
			if (this.r.intersects(w.getRect())) {
				if (w.getColor() == 2) {
					return true;
				}
				if ((this.isWater && w.getColor() == 1) || (!(this.isWater) && w.getColor() == 3)) {
					return true;
				}
			}
		}
		return false;
	}

	public int removeHitJewel(ArrayList<Jewel> jewels) {
		for (int i = 0; i < jewels.size(); i ++ ) {
			if (this.r.intersects(jewels.get(i).getRect())) {

				if (jewels.get(i).getisRed() && !this.isWater) {
					return i;
				}
				else if (!(jewels.get(i).getisRed()) && this.isWater) {
					return i;
				}
			}
		}
		return -1;
	}

	public void adjustBlockHeight(Block block) {
		int bottomchar = this.y + this.H;
		int topblock = block.getY();
		if (bottomchar > topblock) {
			this.y = block.getY();
		}
		
	}

	public boolean hitDoor(Door door) {
		if (!this.r.intersects(door.getRect())) {
			return false;
		}
		if(door.getIsRed() && !this.isWater) {
			return true;
		}
		if(!door.getIsRed() && this.isWater) {
			return true;
		}
		return false;
	}
}
