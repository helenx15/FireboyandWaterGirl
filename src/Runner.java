import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JComponent;

public class Runner {

	private JPanel panelL1, panelL2;
	private Timer mastertimer;
	private int ticks;
	private Color bgCol = new Color(216, 216, 216);
	private Image bgImg;
	
	public final static int WT = 25; //stands for wall thickness
	
	private BMusic bmusic;
	private MainMenuScreen mainmenu;
	private GameOverScreen gameover;
	private WinScreen winscreen;
	private JFrame mainframe = new JFrame("Fireboy and Watergirl");
	
	public int state = 0;
	
	private Character wg;
	private Character fb;
	
	
	//all level 2 stuff
	private Wall w1;//  = new Wall(0,0,1200,WT);
	private Wall w2;//  = new Wall(0,775,1200,WT);
	private Wall w3;//  = new Wall(0,0,WT,800);
	private Wall w4;//  = new Wall(1175,0,WT,800);
	private Wall w5;//  = new Wall(0,125,800,WT);
	private Wall w6;//  = new Wall(775,125,WT,100);
	private Wall w7;//  = new Wall(800,200,75,WT);
	private Wall w8;//  = new Wall(225,325,275,WT);
	private Wall w9;//  = new Wall(25,300,50,75);
	private Wall w10;// = new Wall(25,375,125,75);
	private Wall w11;// = new Wall(25,450,200,75);
	private Wall w12;// = new Wall(25,525,375,WT);
	private Wall w13;// = new Wall(25,650,200,WT);
	private Wall w14;// = new Wall(1125,550,50,75);
	private Wall w15;// = new Wall(1075,625,100,75);
	private Wall w16;// = new Wall(1025,700,150,75);
	private Wall w17;// = new Wall(600,325,575,WT);
	private Wall w18;// = new Wall(500,525,200,WT);
	private Wall w19;// = new Wall(800,525,225,WT);
	
	private BlueWater bluewater;// = new BlueWater(700, 525);
	private RedWater redwater;// = new RedWater(400, 525);
	private GreenWater greenwater;// = new GreenWater(500,325);

	private Door reddoor;// = new Door(100, 50, true);
	private Door bluedoor;// = new Door(200, 50, false);

	
	private Block block; 
	

	private ArrayList<Wall> walls = new ArrayList<Wall>();
	private ArrayList<Water> waters = new ArrayList<Water>();
	private ArrayList<Jewel> jems = new ArrayList<Jewel>();
	private ArrayList<Character> chars = new ArrayList<Character>();
	private ArrayList<Jewel> removedjems = new ArrayList<Jewel>();

	private Jewel jf1;// = new Jewel(400,700, true);
	private Jewel jf2;// = new Jewel(750,450, true);
	private Jewel jf3;// = new Jewel(450,500, true);
	private Jewel jf4;// = new Jewel(525,250, true);
	private Jewel jf5;// = new Jewel(500, 50, true);

	private Jewel jw1;// = new Jewel(525,700, false);
	private Jewel jw2;// = new Jewel(725,500, false);
	private Jewel jw3;// = new Jewel(425,450, false);
	private Jewel jw4;// = new Jewel(550,250, false);
	private Jewel jw5;// = new Jewel(725,50, false);

	public static final int WIDTH = 1200,HEIGHT = 800;
	private static final int REFRESH_RATE = 1;

	public Runner() {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					start();
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
				}
			}
		});
	}

	public static void main(String[] args) {
		new Runner();
	}

	void start() {
		mainmenu = new MainMenuScreen();
		gameover = new GameOverScreen(this);
		winscreen = new WinScreen(this);
		
		try {
			bmusic = new BMusic();
			bmusic.startMusic();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// this timer controls the actions in the game and then repaints after each update to data
		if(mastertimer == null) {
			mastertimer = new Timer(REFRESH_RATE, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(state == 0) {
						gameover.getJFrame().setVisible(false);
						winscreen.getJFrame().setVisible(false);
						state += mainmenu.checkDone();
						mainmenu.repaint();
					}
					else if(state == 1) {
						gameStartL1();
						mainmenu.getJFrame().setVisible(false);
						panelL1.repaint();
					}
					else if (state ==2) {
						//playing level 1
						updateGame();
						panelL1.repaint();
					}
					else if(state == 3) {
						//you won level 1, on to level 2 screen
						mastertimer.stop();
						bmusic.stopMusic();
						System.out.println("You Win!");
						mainmenu.getJFrame().setVisible(false);
						gameover.getJFrame().setVisible(false);
						winscreen.getJFrame().setVisible(true);
						winscreen.displayJems(removedjems);
						winscreen.setText(1);
						state += winscreen.checkDone();
						winscreen.repaint();
					}
					
					else if (state == 4) {
						//start level 2
						gameStartL2();
						winscreen.getJFrame().setVisible(false);
						mainmenu.getJFrame().setVisible(false);
						updateGame();
						panelL2.repaint();
					}
					
					else if (state == 5) {
						//playing level 2 game
						updateGame();
						panelL2.repaint();
						
					}
					else if (state == 6) {
						//you won level 2, option to restart
						mastertimer.stop();
						bmusic.stopMusic();
						System.out.println("You Win!");
						mainframe.setVisible(false);
						winscreen.getJFrame().setVisible(true);
						winscreen.displayJems(removedjems);
						winscreen.setText(2);
					}
					
					else if (state == 7) {
						//you lost somewhere
						mastertimer.stop();
						bmusic.stopMusic();
						System.out.println("Gameover");
						mainframe.setVisible(false);
						gameover.getJFrame().setVisible(true);
					}
					

				}

			});
		}
		mastertimer.start();

	}
	
	private void gameStartL1() {

		gameInitializeVariablesL1();
		bgImg = getImage("bg.png");
		
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panelL1 = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				drawGame(g);
			}
		};

		panelL1.setBackground(bgCol);

		panelL1.setPreferredSize(new Dimension(WIDTH,HEIGHT));

		mainframe.setLocation(WIDTH/10, HEIGHT/10);

		mapKeyStrokesToActions(panelL1);

		mainframe.add(panelL1);
		mainframe.pack();
		mainframe.setVisible(true);

		panelL1.requestFocusInWindow();

		state++;
	}

	private void gameInitializeVariablesL1() {
		walls = new ArrayList<Wall>();
		waters = new ArrayList<Water>();
		jems = new ArrayList<Jewel>();
		chars = new ArrayList<Character>();
		removedjems = new ArrayList<Jewel>();
		
		w1 = new Wall(0,0,1200,100);
		w2 = new Wall(0,100,100,600);
		w3 = new Wall(0,700,300,100);
		w4 = new Wall(300,725,100,75);
		w5 = new Wall(400,700,800,100);
		w6 = new Wall(1100,100,100,600);
		w7 = new Wall(950,200,150,50);
		w8 = new Wall(700,350,400,50);
		w9 = new Wall(500,350, 100, 50);
		w10 = new Wall(250,350,150,50);
		w11 = new Wall(800,575,300, 125);
		w12 = new Wall(400,375,100,25);
		w13 = new Wall(600,375,100,25);
		
		redwater = new RedWater(600,350);
		bluewater = new BlueWater(400,350);
		greenwater = new GreenWater(300,700);

		jf1 = new Jewel(825,275, true);
		jf2 = new Jewel(275,275, true);
		jf3 = new Jewel(175,500, true);
		jf4 = new Jewel(725,550, true);
		
		jw1 = new Jewel(770,275, false);
		jw2 = new Jewel(325,275, false);
		jw3 = new Jewel(175,550, false);
		jw4 = new Jewel(725,500, false);
		
		reddoor = new Door(1000, 500, true);
		bluedoor = new Door(850, 500, false);

		
		walls.add(w1);
		walls.add(w2);
		walls.add(w3);
		walls.add(w4);
		walls.add(w5);
		walls.add(w6);
		walls.add(w7);
		walls.add(w8);
		walls.add(w9);
		walls.add(w10);
		walls.add(w11);
		walls.add(w12);
		walls.add(w13);

		waters.add(redwater);
		waters.add(greenwater);
		waters.add(bluewater);

		jems.add(jf1);
		jems.add(jf2);
		jems.add(jf3);
		jems.add(jf4);

		jems.add(jw1);
		jems.add(jw2);
		jems.add(jw3);
		jems.add(jw4);

		wg = new Character(1050, 275, true);
		fb = new Character(1050, 125, false);
		block = new Block(600, 650);

		chars.add(wg);
		chars.add(fb);
		
	}

	private void gameStartL2() {

		gameInitializeVariablesL2();
		bgImg = getImage("bg.png");
		
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panelL2 = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				drawGame(g);
			}
		};

		panelL2.setBackground(bgCol);

		panelL2.setPreferredSize(new Dimension(WIDTH,HEIGHT));

		mainframe.setLocation(WIDTH/10, HEIGHT/10);

		mapKeyStrokesToActions(panelL2);

		mainframe.add(panelL2);
		mainframe.pack();
		mainframe.setVisible(true);

		panelL2.requestFocusInWindow();

		state++;
	}

	private void gameInitializeVariablesL2() {

		walls = new ArrayList<Wall>();
		waters = new ArrayList<Water>();
		jems = new ArrayList<Jewel>();
		chars = new ArrayList<Character>();
		removedjems = new ArrayList<Jewel>();
		
		w1  = new Wall(0,0,1200,WT);
		w2  = new Wall(0,775,1200,WT);
		w3  = new Wall(0,0,WT,800);
		w4  = new Wall(1175,0,WT,800);
		w5  = new Wall(0,125,800,WT);
		w6  = new Wall(775,125,WT,100);
		w7  = new Wall(800,200,75,WT);
		w8  = new Wall(225,325,275,WT);
		w9  = new Wall(25,300,50,75);
		w10 = new Wall(25,375,125,75);
		w11 = new Wall(25,450,200,75);
		w12 = new Wall(25,525,375,WT);
		w13 = new Wall(25,650,200,WT);
		w14 = new Wall(1125,550,50,75);
		w15 = new Wall(1075,625,100,75);
		w16 = new Wall(1025,700,150,75);
		w17 = new Wall(600,325,575,WT);
		w18 = new Wall(500,525,200,WT);
		w19 = new Wall(800,525,225,WT);
		
		bluewater = new BlueWater(700, 525);
		redwater = new RedWater(400, 525);
		greenwater = new GreenWater(500,325);

		reddoor = new Door(100, 50, true);
		bluedoor = new Door(200, 50, false); 
		

		jf1 = new Jewel(400,700, true);
		jf2 = new Jewel(750,450, true);
		jf3 = new Jewel(450,500, true);
		jf4 = new Jewel(525,250, true);
		jf5 = new Jewel(500, 50, true);

		jw1 = new Jewel(525,700, false);
		jw2 = new Jewel(725,500, false);
		jw3 = new Jewel(425,450, false);
		jw4 = new Jewel(550,250, false);
		jw5 = new Jewel(725,50, false);

		walls.add(w1);
		walls.add(w2);
		walls.add(w3);
		walls.add(w4);
		walls.add(w5);
		walls.add(w6);
		walls.add(w7);
		walls.add(w8);
		walls.add(w9);
		walls.add(w10);
		walls.add(w11);
		walls.add(w12);	
		walls.add(w13);
		walls.add(w14);
		walls.add(w15);
		walls.add(w16);
		walls.add(w17);
		walls.add(w18);
		walls.add(w19);

		waters.add(redwater);
		waters.add(greenwater);
		waters.add(bluewater);

		jems.add(jf1);
		jems.add(jf2);
		jems.add(jf3);
		jems.add(jf4);
		jems.add(jf5);

		jems.add(jw1);
		jems.add(jw2);
		jems.add(jw3);
		jems.add(jw4);
		jems.add(jw5);

		wg = new Character(75, 575, true);
		fb = new Character(75, 700, false);
		block = new Block(750, 268);

		chars.add(wg);
		chars.add(fb);

	}

	// this method is called every time the timer goes off
	protected void updateGame() {
		ticks++;// keeps track of the number of times the timer has gone off

		int hurts = 1000/REFRESH_RATE;
		if(ticks %hurts == 0) {
			System.out.println(ticks/hurts+" seconds");
		}

		fb.move();
		fb.setHitTop();
		if (fb.hitSide(walls) || fb.hitTop(walls)) {
			fb.moveBack();
		}
		if (!fb.hitWall(walls) && !fb.hitWater(waters) && !fb.hitBlockTop(block)) {
			fb.fall();
		}
		if(fb.getJumping()) fb.jump();
		if(fb.hitWall(walls) || fb.hitWater(waters) ||fb.hitBlockTop(block)) fb.setJumping(false);
		int fj = fb.removeHitJewel(jems);
		if (fj >= 0) {
			removedjems.add(jems.remove(fj));
		}

		wg.move(); 
		wg.setHitTop();
		if (wg.hitSide(walls) || wg.hitTop(walls)) {
			wg.moveBack();
		}
		if (!wg.hitWall(walls) && !wg.hitWater(waters) && !wg.hitBlockTop(block)) {
			wg.fall();
		}
		if(wg.getJumping()) wg.jump();
		if (wg.hitSide(walls)) {
			wg.moveBack();
		}
		if(wg.hitWall(walls) || wg.hitWater(waters) || wg.hitBlockTop(block)) wg.setJumping(false);

		if(fb.hitBadWater(waters) || wg.hitBadWater(waters)) {
			this.gameOver();
		}

		if(fb.hitBlock(block.getRect())) {
			block.move(fb);
		}
		if(wg.hitBlock(block.getRect())) {
			block.move(wg);
		}
		
		if (block.hitSide(walls) != null) {
			block.moveBack(block.hitSide(walls));
		}
		if (!block.hitWall(walls) && !block.hitWater(waters)) {
			block.fall();
		}

		int wj = wg.removeHitJewel(jems);
		if (wj >= 0) {
			removedjems.add(jems.remove(wj));
		}
		
		if(fb.hitDoor(reddoor) && wg.hitDoor(bluedoor)) {
			this.win(this.state);
		}
	}

	
	private void win(int s) {
		System.out.println("YOU WIN: THIS SHOULD GO TO A WIN SCREEN");
		if (s == 2) {
			state = 3;
		}
		if (s == 5) {
			state = 6;
		}
	}

	private void gameOver() {
		System.out.println("GAMEOVER: THIS SHOULD GO TO AN END SCREEN");
		state = 7;
	}
	
	protected  Image getImage(String fn) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("src/" +fn));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}


	private void mapKeyStrokesToActions(JPanel panel) {

		// A map is an Data storage interface which defines
		// an association of a key with a value
		// to "add" to a map you use the "put" method
		// to "get" from a map you use "get(key)" and the 
		// value associated with the key is returned (or null)
		ActionMap map = panel.getActionMap();
		InputMap inMap = panel.getInputMap();

		//sets the key presses to commands below
		inMap.put(KeyStroke.getKeyStroke("pressed UP"), "fbup");
		inMap.put(KeyStroke.getKeyStroke("pressed LEFT"), "fbleft");
		inMap.put(KeyStroke.getKeyStroke("pressed RIGHT"), "fbright");

		inMap.put(KeyStroke.getKeyStroke("released LEFT"), "fbleftR");
		inMap.put(KeyStroke.getKeyStroke("released RIGHT"), "fbrightR");

		inMap.put(KeyStroke.getKeyStroke("pressed W"), "wgup");
		inMap.put(KeyStroke.getKeyStroke("pressed A"), "wgleft");
		inMap.put(KeyStroke.getKeyStroke("pressed D"), "wgright");

		inMap.put(KeyStroke.getKeyStroke("released A"), "wgleftR");
		inMap.put(KeyStroke.getKeyStroke("released D"), "wgrightR");

		map.put("fbup", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fb.setJumping(true);
			}
		});
		map.put("fbleft", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fb.setLeft(true);
			}
		});	
		map.put("fbright", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fb.setRight(true);
			}
		});

		map.put("fbleftR", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fb.setLeft(false);
			}
		});

		map.put("fbrightR", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fb.setRight(false);
			}
		});

		map.put("wgup", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wg.setJumping(true);
			}
		});
		map.put("wgleft", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wg.setLeft(true);
			}
		});

		map.put("wgright", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wg.setRight(true);
			}
		});
		map.put("wgleftR", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wg.setLeft(false);
			}
		});

		map.put("wgrightR", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wg.setRight(false);
			}
		});

	}
	public void hit(String s) {
		panel.repaint();
	}

	protected void drawGame(Graphics g) {

		g.drawImage(bgImg, 0, 0, null);
		
		for(Wall w: walls) {
			w.draw(g);
		}

		for(Water w : waters ) {
			w.draw(g);
		}

		for(Jewel j: jems) {
			j.draw(g);
		}

		reddoor.draw(g);
		bluedoor.draw(g);

		block.draw(g);

		for(Character c: chars) {
			c.draw(g);
		}

	}
}
