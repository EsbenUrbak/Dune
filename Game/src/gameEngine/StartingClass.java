package gameEngine;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;

import planet.LoadMap;
import planet.Surface;
import Units.Soldier;

public class StartingClass extends Applet implements Runnable, KeyListener {
	// Creating variables and objects
	// Creating a unit to move around on the planet
	private static Soldier Paul;
	// Creating a the surface object of the planet
	private static Surface planetSurface;

	// Creating tile

	enum GameState {
		Running, Dead
	}

	GameState state = GameState.Running;

	private URL base;

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

	}

	public void start() {
		planetSurface = new Surface(0, 0);

		// Creating the planet surface [I have moved the map loader out of the
		// starting class to limit the size]
		try {
			LoadMap Maploader = new LoadMap();
			Maploader.loadMap("data/map1.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {

	}

	public void stop() {

	}
	
	public void update(Graphics g) {
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
