package com.baseframework.screen;

import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.baseframework.game.main.MainHolder;

public abstract class GameScreen {
	
	public abstract void init();
	
	public abstract void update(float delta);
	
	public abstract void render(Graphics g);
	
	public abstract void onClick(MouseEvent e);
	
	public abstract void onKeyPress(KeyEvent e);
	
	public abstract void onKeyRelease(KeyEvent e);
	
	public abstract void onComponentResize( ComponentEvent e);
	
	public void setCurrentScreen(GameScreen newScreen){
		MainHolder.thegame.setCurrentScreen(newScreen);
	}
	
}
