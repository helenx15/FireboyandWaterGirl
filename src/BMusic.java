import java.io.*;
import sun.audio.*;

public class BMusic { 
	
	private InputStream in;
	private AudioStream audioStream;
	
	public static void main(String[] args) throws Exception {
		new BMusic();
    }
	
	public BMusic() throws Exception{
		String bg = "src/bgmusic.wav";
		in = new FileInputStream(bg);
	    audioStream = new AudioStream(in);
	}
		
	public void startMusic() {
		try {
		    AudioPlayer.player.start(audioStream);
		}
		catch (Exception e)  {
		}	
	}

	public void stopMusic() {
		AudioPlayer.player.stop(audioStream); 
		//AudioPlayer.player.
	}
}
		
