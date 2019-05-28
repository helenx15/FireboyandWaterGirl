import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BlueWater extends Water {
	
	public BlueWater(int xPos, int yPos) {
		super(xPos, yPos, "BlueWater.png", 3);

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