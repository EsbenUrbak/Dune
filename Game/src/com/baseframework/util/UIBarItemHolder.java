package com.baseframework.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.baseframework.game.main.Resources;

public class UIBarItemHolder {
	
	public static int width=45, height=45;
	private boolean visible, empty = true;
	private Rectangle frameRect;
	public Rectangle catchRect;
	
	public UIBarItemHolder(int topX, int topY){
		frameRect = new Rectangle(topX, topY, width, height);
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

