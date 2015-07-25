package com.baseframework.screen;

import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Comparator;

import com.baseframework.UI.UIObject;
import com.baseframework.game.main.MainHolder;

public abstract class GameScreen {
	
	public abstract void init();
	
	public abstract void update(float delta);
	
	public abstract void render(Graphics g);
	
	public abstract void onClick(MouseEvent e);
	
	public abstract void onMousePressed(MouseEvent e);
	
	public abstract void onMouseReleased(MouseEvent e);
	
	public abstract void onMouseDragged(MouseEvent e);
	
	public abstract void onKeyPress(KeyEvent e);
	
	public abstract void onKeyRelease(KeyEvent e);
	
	public abstract void onComponentResize( ComponentEvent e);
	
	public void setCurrentScreen(GameScreen newScreen){
		MainHolder.thegame.setCurrentScreen(newScreen);
	}
	
    public static Comparator<UIObject> typePriorityOrder = new Comparator<UIObject>(){
		public int compare(UIObject obj1, UIObject obj2) {
			return Integer.compare(obj1.getTypePriority(), obj2.getTypePriority());
		}
    };
	
}
