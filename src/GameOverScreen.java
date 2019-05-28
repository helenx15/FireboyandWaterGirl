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

public class GameOverScreen {
	
	private Image img;


	JFrame mainmenu;
	Runner main;
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
		new GameOverScreen(null);
	}
	
	public GameOverScreen(Runner runner) {
		img = getImage("es.png");
		
		
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
		textLabel = new JLabel("");
		textLabel.setForeground(Color.WHITE);
		textLabel.setFont(titleFont);
		
		
		startButtonPanel = new JPanel();
		startButtonPanel.setBounds(280, 390, 600, 100);
		startButtonPanel.setBackground(Color.black);
		
		startButton = new JButton("TRY AGAIN?");
		startButton.setSize(200,200);
		startButton.setBackground(Color.pink);
		startButton.setForeground(Color.gray);
		startButton.setFont(buttonFont);
		
		
		textPanel.add(textLabel);
		startButtonPanel.add(startButton);
		
		startButton.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	      
	    	System.out.println("false");
	    	vis = false;
	    	mainmenu.setVisible(false);
	    	main.state = 0;
	    	main.start();
	    	
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
