package gameEngine;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import planet.*;



public class StartingClass extends Applet implements Runnable, KeyListener, MouseListener, ComponentListener{
	// Creating variables and objects
	// Creating a unit to move around on the planet
	public Squad squad;
	// Creating a the surface object of the planet
	public static ViewFrame viewframe;
	public static Map map;
	public static Image tiledirt, tileocean,background;
	private Image image, squadCurrent, squadimagine, squadClickedImagine;
	Graphics second;

	public static final int SCROLLSPEED = 20; 	// use a divisor of 40 or the screen will shift a little too far
												//there must be a way to fix this, will think about it
	public static int screenSizeX = 800;
	public static int screenSizeY = 480;
	public static final int INITIALSCREENX = 0;
	public static final int INITIALSCREENY = 0;
	
	//Mouse elements
	boolean mouseEntered;
	boolean MouseClicked;
	int xPos; 
	int yPos; 
	
	enum GameState {
		Running, Dead
	}

	GameState state = GameState.Running;

	private URL base;

	// Initialisation of game
	public void init() {
		setSize(screenSizeX, screenSizeY);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		//adding mouselistener
		addMouseListener(this);
		addComponentListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Dune");
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}

		// Image Setups 
		tileocean = getImage(base, "data/tiledirt.png");
		tiledirt = getImage(base, "data/tileocean.png");
		background = getImage(base, "data/background.png");
		squadimagine = getImage(base, "data/squad.png");
		squadClickedImagine= getImage(base, "data/squadClicked.png");
		squadCurrent=squadimagine;
	}

	public void start() {
		viewframe = new ViewFrame(INITIALSCREENX, INITIALSCREENY, screenSizeX, screenSizeY);
		squad = new Squad();

		try {
			map = new Map("data/map1.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		viewframe.setBoundX(map.getWidth(true));
		viewframe.setBoundY(map.getHeight(true));
		
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		if (state == GameState.Running) {
			while (true) {

				// Updating the screen, squad, and zone of the map to be displayed
				viewframe.update();
				squad.update();	

				map.update(viewframe.getFrameX(), viewframe.getFrameY());
				//updateTiles();
				
				//update squad properties
				if(MouseClicked){
					squadCurrent=squadClickedImagine;
				}else{
					squadCurrent=squadimagine;
				}

				// Repainting the screen
				repaint();

				try {
					Thread.sleep(17);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}

	}

	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);
	}

	public void paint(Graphics g) {

		if (state == GameState.Running) {
			//g.drawImage(background, viewframe.getFrameX(), viewframe.getFrameY(), this);
			paintTiles(g);
			
			g.drawRect((int)squad.rect.getX() - viewframe.getFrameX(), (int)squad.rect.getY() - viewframe.getFrameY(), 
							(int)squad.rect.getWidth(), (int)squad.rect.getHeight());
			g.drawImage(squadCurrent, squad.getCenterX() - viewframe.getFrameX(), squad.getCenterY()-viewframe.getFrameY(), this);
			
			
			//Happens if the character dies
		} else if (state == GameState.Dead) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, screenSizeX, screenSizeY);
			g.setColor(Color.WHITE);
			g.drawString("Dead", Math.round((float)(screenSizeX)/2) - Tile.getSizeX(), Math.round((float)(screenSizeY)/2));
		}

	}

	private void updateTiles() {
	/* 	for (int i = 0; i < scopetilearray.size(); i++) {
			Tile t = (Tile) scopetilearray.get(i);
			t.update();
		}
	*/
	}

	private void paintTiles(Graphics g) {
		
		// paint ONLY the tiles in the catch zone, and adjusts for relative coordinates
		for (int i = 0; i < map.getScopeTileArray().size(); i++) {
			Tile t = (Tile) map.getScopeTileArray().get(i);
			g.drawImage(t.getTileImage(), t.getTileX() - viewframe.getFrameX(), t.getTileY() - viewframe.getFrameY(), this);
		}
		
	}
	
	
	public void stop() {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			viewframe.setSpeedY(-SCROLLSPEED);
			break;

		case KeyEvent.VK_DOWN:
			viewframe.setSpeedY(SCROLLSPEED);
			break;

		case KeyEvent.VK_LEFT:
			viewframe.setSpeedX(-SCROLLSPEED);
			break;

		case KeyEvent.VK_RIGHT:
			viewframe.setSpeedX(SCROLLSPEED);
			break;
			
		case KeyEvent.VK_Z:
			//ZOOMin
			break;

		case KeyEvent.VK_X:
			//ZOOMout
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			viewframe.setSpeedY(0);
			break;

		case KeyEvent.VK_DOWN:
			viewframe.setSpeedY(0);
			break;

		case KeyEvent.VK_LEFT:
			viewframe.setSpeedX(0);
			break;

		case KeyEvent.VK_RIGHT:
			viewframe.setSpeedX(0);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static void setViewFrame(ViewFrame viewFrame) {
		StartingClass.viewframe = viewFrame;
	}

	public static ViewFrame getViewFrame() {
		return viewframe;
	}

	@Override
	public void mouseClicked(MouseEvent me) {
	//Getting coordinations of the click
	xPos =me.getX();	
	yPos =me.getY();	
	
	//Logic to check whether it was within the rectangle (in relative coordinate)
	if(xPos > squad.getCenterX() - viewframe.getFrameX() && xPos < squad.getCenterX() - viewframe.getFrameX() + squad.getxImagine()  && 
				yPos >squad.getCenterY() - viewframe.getFrameY() && yPos < squad.getCenterY() - viewframe.getFrameY() + squad.getyImagine()){
		MouseClicked=true;
	}else{
		MouseClicked=false;
	}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// when applet get resized, update size parameters
		screenSizeX = this.getWidth();
		screenSizeY = this.getHeight();
		viewframe.setSizeX(screenSizeX);
		viewframe.setSizeY(screenSizeY);
		
		// then identify the new frames to be displayed
		map.reInit(viewframe.getFrameX(), viewframe.getFrameY(), this.getWidth(), this.getHeight());
		
		// finally reset the image object that will get resized in update() method
		image = null;
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
