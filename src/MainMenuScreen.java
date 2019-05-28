import java.awt.color.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenuScreen {
	
	private Image img;

	JFrame mainmenu;
	Container con;
	JPanel textPanel, startButtonPanel;
	JLabel textLabel;
	Font titleFont = new Font("Times New Roman", Font.PLAIN, 60);
	Font buttonFont = new Font("Times New Roman", Font.PLAIN, 70);
	JButton startButton;
	
	public boolean vis = true;
	private final static int W = 1200;
	private final static int H = 800;
	
	public static void main(String[] args) {
		new MainMenuScreen();
	}
	
	public MainMenuScreen() {
		
		img = getImage("mm.png");
		
		
		mainmenu = new JFrame();
		mainmenu.setSize(W, H);
		mainmenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainmenu.getContentPane().setBackground(Color.black);
		mainmenu.setLayout(null);
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
		textLabel = new JLabel("");
		textLabel.setForeground(Color.WHITE);
		textLabel.setFont(titleFont);
		
		
		startButtonPanel = new JPanel();
		startButtonPanel.setBounds(300, 380, 600, 200);
		startButtonPanel.setBackground(Color.pink);
		
		startButton = new JButton("START!");
		startButton.setSize(200, 200);
		startButton.setBackground(Color.pink);
		startButton.setForeground(Color.gray);
		startButton.setFont(buttonFont);
		
		
		textPanel.add(textLabel);
		startButtonPanel.add(startButton);
		
		startButton.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	    	
	    	vis = false;
	    	mainmenu.setVisible(false);
	    	
	      }
	    });
		
		
		
		
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

	public void draw() {
		this.mainmenu.setVisible(true);
		
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
		textLabel.repaint();
		startButtonPanel.repaint();
		textLabel.repaint();
		startButton.repaint();
		mainmenu.repaint();
		
	}

}
