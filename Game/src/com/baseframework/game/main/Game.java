package com.baseframework.game.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.baseframework.screen.GameScreen;
import com.baseframework.screen.LoadScreen;
import com.baseframework.util.InputHandler;

@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable {
	private int gameWidth, gameHeight;
	private Image gameImage;
	private Dimension d;
	
	private GameScreen currentScreen;
	private volatile boolean running=false;
	private Thread gameThread;
	private InputHandler inputHandler;
	
	private final int MIN_SLEEPTIME = 2;
	private final int MAX_SLEEPTIME = 17;
	
	public Game(int gameWidth, int gameHeight){
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		d = new Dimension(gameWidth, gameHeight);
		setPreferredSize(d);
		setBackground(Color.BLACK);
		setFocusable(true);
		requestFocus();
	}
	
	public void setCurrentScreen(GameScreen newScreen){
		System.gc();
		newScreen.init();
		currentScreen = newScreen;
		inputHandler.setCurrentScreen(currentScreen);
	}
	
	@Override
	public void addNotify(){
		super.addNotify();
		initInput();
		setCurrentScreen(new LoadScreen());
		initGame();
	}

	private void initGame() {
		running = true;
		gameThread = new Thread(this, "Game Thread");
		gameThread.start();
	}
	
	private void initInput(){
		inputHandler = new InputHandler();
		addKeyListener(inputHandler);
		addMouseListener(inputHandler);
		addComponentListener(inputHandler);
	}

	@Override
	public void run() {
		long updateTime = 0;
		long sleepTime = 0;
		
		while (running){
			long beforeTime = System.nanoTime();
			long deltaMillis = updateTime + sleepTime;			
			
			//deltaMillis = 1;
			
			updateAndRender(deltaMillis);
			
			// convert time elapsed into milliseconds
			updateTime = (System.nanoTime() - beforeTime) / 1000000L;
			sleepTime = Math.max((long) MIN_SLEEPTIME, (long) MAX_SLEEPTIME - updateTime);
			
			try{
				Thread.sleep(sleepTime);
			} catch (Exception e){
				e.printStackTrace();
			}
			
		}
		System.exit(0);
		
	}

	private void updateAndRender(long deltaMillis) {
		currentScreen.update(deltaMillis / 1000f);
		prepareGameImage();
		currentScreen.render(gameImage.getGraphics());
		renderGameImage(getGraphics());
	}
	
	private void prepareGameImage(){
		if (gameImage == null){
			gameImage = createImage(gameWidth, gameHeight);
		}
		Graphics g = gameImage.getGraphics();
		g.clearRect(0, 0, gameWidth, gameHeight);
	}

	public void resetGameImage() {
		this.gameImage = null;
	}
	
	private void renderGameImage(Graphics g){
		if (gameImage != null){
			g.drawImage(gameImage, 0, 0, null);
		}
		g.dispose();
	}
	
	private void exit() {
		running = false;
	}

	public int getGameWidth() {
		return gameWidth;
	}

	public int getGameHeight() {
		return gameHeight;
	}

	public void setDimensions(int newwidth, int newheight) {
		this.gameWidth = newwidth;
		this.gameHeight = newheight;
		//d.setSize(gameWidth, gameHeight);
		//setPreferredSize(d);
	}

	
}
