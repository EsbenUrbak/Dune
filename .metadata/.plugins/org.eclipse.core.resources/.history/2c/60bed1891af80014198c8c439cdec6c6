package gameEngine;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import planet.Surface;
import Units.Soldier;



public class StartingClass extends Applet implements Runnable, KeyListener {
	//Creating variables and objects
	// Creating a unit to move around on the planet
	private static Soldier Paul;
	//Creating a the surface object of the planet
	private static Surface planetSurface;
	
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

		// Image Setups

		tiledirt = getImage(base, "data/tiledirt.png");
		tilegrassTop = getImage(base, "data/tilegrasstop.png");
		background = getImage(base, "data/background.png");
	}

	public void start() {
		planetSurface = new Surface(0, 0);


		// Initialize Tiles
		try {
			loadMap("data/map1.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Thread thread = new Thread(this);
		thread.start();
	}
	
	
	@Override
	public void run() {
		
	}
	
	
	
	
}
