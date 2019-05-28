import java.awt.color.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WinScreen {
	
	private Image img;

	JFrame mainmenu;
	Runner main;
	Container con;
	JPanel textPanel, startButtonPanel, redJemPanel, blueJemPanel;
	JLabel textLabel, redJemLabel, blueJemLabel;
	Font titleFont = new Font("Times New Roman", Font.PLAIN, 60);
	Font buttonFont = new Font("Times New Roman", Font.PLAIN, 70);
	Font jemFont = new Font("Times New Roman", Font.PLAIN, 50);
	JButton startButton;
	
	public boolean vis = true;
	public int level;
	private final static int W = 1200;
	private final static int H = 800;
	
	public static void main(String[] args) {
		new WinScreen(null);
	}
	
	public WinScreen(Runner runner) {
		img = getImage("ws.png");
		
		
		mainmenu = new JFrame();
		main = runner;
		mainmenu.setSize(W, H);
		mainmenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainmenu.getContentPane().setBackground(Color.black);
		mainmenu.setLayout(null);
		mainmenu.setVisible(false);
		con = mainmenu.getContentPane();
		
		textPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(img, 0, 0, null);
			}
		};
		textPanel.setBounds(0, 0, 1200, 800);
		textPanel.setBackground(Color.black);
		
		startButtonPanel = new JPanel();
		startButtonPanel.setBounds(300, 460, 600, 100);
		startButtonPanel.setBackground(Color.black);
		
		startButton = new JButton();
		startButton.setSize(200,200);
		startButton.setBackground(Color.pink);
		startButton.setForeground(Color.gray);
		startButton.setFont(buttonFont);
		
		
		redJemPanel = new JPanel();
		redJemPanel.setBounds(600, 330, 125, 75);
		redJemPanel.setBackground(Color.green);
		
		redJemLabel = new JLabel();
		redJemLabel.setForeground(Color.WHITE);
		redJemLabel.setFont(jemFont);
		
		
		blueJemPanel = new JPanel();
		blueJemPanel.setBounds(600, 390, 125, 75);
		blueJemPanel.setBackground(Color.green);
		
		blueJemLabel = new JLabel();
		blueJemLabel.setForeground(Color.WHITE);
		blueJemLabel.setFont(jemFont);
		
		
		startButtonPanel.add(startButton);
		redJemPanel.add(redJemLabel);
		blueJemPanel.add(blueJemLabel);		
		
		startButton.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	    	
	    	vis = false;
	    	mainmenu.setVisible(false);
	    	if (main.state == 3) {
	    		main.state ++;
	    	}
	    	else {main.state = 0;}
	    	main.start();
	    	
	      }
	    });
		
		con.add(redJemPanel);
		con.add(blueJemPanel);
		con.add(textPanel);
		con.add(startButtonPanel);
		
		mainmenu.setVisible(true);
	}

	protected  BufferedImage getImage(String s) {
		BufferedImage img = null;
		try {

			img = ImageIO.read(new File("src/" +s));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public JFrame getJFrame() {
		return this.mainmenu;
	}

	public boolean getVisible() {
		if (this.vis) {
			return true;
		}
		return false;
	}

	public int checkDone() {
		if(this.vis) {
			return 0;
		}
		return 1;
	}

	public void repaint() {
	//	textLabel.repaint();
		startButtonPanel.repaint();
		redJemLabel.repaint();
		startButton.repaint();
		mainmenu.repaint();
		
	}

	public void displayJems(ArrayList<Jewel> jems) {
		int sumred = 0;
		int sumblue = 0;
		for (Jewel j: jems) {
			if(j.getisRed()) {
				sumred++;
			}
			else {
				sumblue++;
			}
		}
		
		redJemLabel.setText("x  " + sumred );
		blueJemLabel.setText("x  " + sumblue);
		
		
	}
	
	public void setText(int level) {
		if (level == 1) {
			startButton.setText("Level 2 Start");
		}
		if (level == 2) {
			startButton.setText("Play Again?");
		}
	}

}
