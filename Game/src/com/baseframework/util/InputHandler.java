package com.baseframework.util;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.baseframework.screen.GameScreen;

public class InputHandler implements KeyListener, MouseListener, ComponentListener{

	private GameScreen currentScreen;
	
	@Override
	public void componentHidden(ComponentEvent arg0) {
	
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {

	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		currentScreen.onComponentResize(arg0);
	}

	@Override
	public void componentShown(ComponentEvent arg0) {

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		currentScreen.onClick(arg0);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		currentScreen.onKeyPress(arg0);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		currentScreen.onKeyRelease(arg0);
	
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	
	}

	public void setCurrentScreen(GameScreen currentScreen) {
		this.currentScreen = currentScreen;
	}
	
}
