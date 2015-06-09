package com.baseframework.screen;

import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.baseframework.game.main.Resources;

public class LoadScreen extends GameScreen {

	@Override
	public void init() {
		Resources.load();
		System.out.println("Loaded Successfully");
	}

	@Override
	public void update(float delta) {
		setCurrentScreen(new MainMenuScreen());
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onComponentResize(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

}
