import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RedWater extends Water {
	
	public RedWater(int xPos, int yPos) {
		super(xPos, yPos, "RedWater.png", 1);

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

}