package com.baseframework.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.baseframework.game.main.Resources;

public class UIBarItem {
	
	public static int width=45, height=45;
	private boolean visible, empty = true;
	private UIBar refBar;
	private Rectangle frameRect;
	public Rectangle catchRect;
	
	public UIBarItem(UIBar refBar, int relX, int relY){
		this.refBar = refBar;		
		frameRect = new Rectangle(relX + refBar.catchRect.x, relY + refBar.catchRect.y, width, height);
		visible = true;
	}

	public void render(Graphics g){
		if(!visible) return;
		
		Graphics2D g2;
		g2 = (Graphics2D) g;
		g2.setStroke(Resources.strokeSize);
		g2.setColor(Resources.frameColor);
		
		g2.drawRoundRect(frameRect.x, frameRect.y, width, height, 20, 20);
	}
	
}

