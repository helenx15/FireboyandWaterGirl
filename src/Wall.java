import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall {
	
	private Rectangle r;
	private int x;
	private int y;
	
	public Wall(int xcoor, int ycoor, int width, int height) {
		this.r = new Rectangle(xcoor,ycoor,width,height);
		
	}

	public Rectangle getRect() {
		return this.r;
	}
	
	public void draw(Graphics g) {	
		g.fillRect(r.x, r.y, r.width, r.height);
		
	}
}
