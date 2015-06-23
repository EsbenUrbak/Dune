package com.baseframework.screen;

import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.baseframework.game.main.MainHolder;
import com.baseframework.game.main.Resources;

public class MainMenuScreen extends GameScreen {

	@Override
	public void init() {
		MainHolder.setResizeable(false);
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Resources.background, 0, 0, null);
		g.setColor(Resources.titleColor1);
		g.setFont(Resources.titleFont1);
		g.drawString("Main menu screen", MainHolder.thegame.getGameWidth() / 2 - 220, MainHolder.thegame.getGameHeight() / 2 - 10);
		
		g.setColor(Resources.subTitleColor1);
		g.setFont(Resources.subTitleFont1);
		g.drawString("Press any key to start", MainHolder.thegame.getGameWidth() / 2 - 150, MainHolder.thegame.getGameHeight() / 2 + 50);
	}

	@Override
	public void onClick(MouseEvent e) {
		setCurrentScreen(new PlayScreen());		
	}
	
	@Override
	public void onMousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		setCurrentScreen(new PlayScreen());
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		setCurrentScreen(new PlayScreen());
	}

	@Override
	public void onComponentResize(ComponentEvent e) {

	}

}
