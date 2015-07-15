package com.baseframework.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import com.baseframework.game.main.Resources;


public class UIBar {
	private Rectangle frameRect;
	private Rectangle catchRect;
	private Image barTileUp, barTileDown;
	private int tileCount, yOffset=0;
	
	public UIBar(int topX, int topY, int tileCount, int sizeY) {
		barTileUp = Resources.barTileUp;
		barTileDown = Resources.barTileDown;
		this.tileCount = tileCount;
		frameRect = new Rectangle(topX, topY, tileCount * barTileUp.getWidth(null), sizeY);
		
		if(sizeY > barTileUp.getHeight(null)) yOffset = sizeY - barTileUp.getHeight(null);
	}
	
	public void render (Graphics g){
		Graphics2D g2;
		
		// fill the bar
		g.setColor(Resources.subTitleColor1);
		g.fillRoundRect(frameRect.x, frameRect.y, frameRect.width, frameRect.height, 20, 20);
		
		// fancy bar display
		if(yOffset > 0){
			for (int i =0; i<tileCount; i++){
				g.drawImage(barTileDown, frameRect.x + i * barTileUp.getWidth(null), frameRect.y + yOffset, null);			
			}			
		}
		for (int i =0; i<tileCount; i++){
			g.drawImage(barTileUp, frameRect.x + i * barTileUp.getWidth(null), frameRect.y, null);			
		}

		// bar frame
		g2 = (Graphics2D) g;
		g2.setStroke(Resources.strokeSize);		
		g2.setColor(Resources.titleColor1);
		g2.drawRoundRect(frameRect.x, frameRect.y, frameRect.width, frameRect.height, 20, 20);
		
	}
	
}
