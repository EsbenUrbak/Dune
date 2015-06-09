package com.baseframework.game.main;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainHolder extends JFrame {

	private static final String GAME_TITLE = "new Game";
	public static final int DEFAULT_GAMEWIDTH = 800;
	public static final int DEFAULT_GAMEHEIGHT = 480;
	private static int gamewidth, gameheight;
	private static boolean resizeable = false;
	public static Game thegame;
	
	private static JFrame frame;
	
	public static void main(String[] args) {
		frame = new JFrame(GAME_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(resizeable);

		gamewidth = DEFAULT_GAMEWIDTH;
		gameheight = DEFAULT_GAMEHEIGHT;
		
		thegame = new Game(gamewidth, gameheight);
		frame.add(thegame);
		frame.pack();
		frame.setVisible(true);
		//frame.setIconImage(Resource.);  //if want to add an icon
	}

	public static int getGameheight() {
		return gameheight;
	}

	public static int getGamewidth() {
		return gamewidth;
	}

	public static void setGameheight(int gameheight) {
		MainHolder.gameheight = gameheight;
	}

	public static void setGamewidth(int gamewidth) {
		MainHolder.gamewidth = gamewidth;
	}

	public static boolean isResizeable() {
		return resizeable;
	}

	public static void setResizeable(boolean resizeable) {
		MainHolder.resizeable = resizeable;
		frame.setResizable(resizeable);
	}

}
