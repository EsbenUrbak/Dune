package gameEngine;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import planet.*;



public class StartingClass extends Applet implements Runnable, KeyListener {
	// Creating variables and objects
	// Creating a unit to move around on the planet
	public Squad squad;
	// Creating a the surface object of the planet
	public static Surface planetSurface;
	// Creating tile
	private ArrayList<Tile> tilearray = new ArrayList<Tile>();
	//
	public static Image tiledirt, tileocean,background;
	private Image image,squadimagine;
	Graphics second;


	enum GameState {
		Running, Dead
	}

	GameState state = GameState.Running;

	private URL base;

	// Initialisation of game
	public void init() {
		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
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
		
	}

	public void start() {
		planetSurface = new Surface(0, 0);
		squad = new Squad();
		// Creating the planet surface [I have moved the map loader out of the
		// starting class to limit the size]
		LoadMap Maploader = new LoadMap();
		try {
			Maploader.loadMap("data/map1.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tilearray = Maploader.getTilearray();
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		if (state == GameState.Running) {
			while (true) {
				

				// Updating tiles and surface
				planetSurface.update();
				updateTiles();
				
				//update squad properties
				squad.update();
				
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
			g.drawImage(background, planetSurface.getSurfaceX(), planetSurface.getSurfaceY(), this);
			paintTiles(g);
			
			g.drawImage(squadimagine, squad.getCenterX(), squad.getCenterY(), this);
			//squad.getCenterX(), squad.getCenterY(), this);
			
			//Happens if the character dies
		} else if (state == GameState.Dead) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.drawString("Dead", 360, 240);

		}

	}

	private void updateTiles() {

		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			t.update();
		}

	}

	private void paintTiles(Graphics g) {
		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
		}
	}
	
	
	public void stop() {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			planetSurface.setSpeedY(6);
			break;

		case KeyEvent.VK_DOWN:
			planetSurface.setSpeedY(-6);
			break;

		case KeyEvent.VK_LEFT:
			planetSurface.setSpeedX(6);
			break;

		case KeyEvent.VK_RIGHT:
			planetSurface.setSpeedX(-6);
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
			planetSurface.setSpeedY(0);
			break;

		case KeyEvent.VK_DOWN:
			planetSurface.setSpeedY(0);
			break;

		case KeyEvent.VK_LEFT:
			planetSurface.setSpeedX(0);
			break;

		case KeyEvent.VK_RIGHT:
			planetSurface.setSpeedX(0);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static void setPlanetSurface(Surface planetSurface) {
		StartingClass.planetSurface = planetSurface;
	}

	public static Surface getPlanetSurface() {
		return planetSurface;
	}

}
