package com.baseframework.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import com.baseframework.game.main.Resources;
import com.baseframework.screen.PlayScreen;

public class UIBar {
	private boolean selected=false;
	private Rectangle frameRect;
	public Rectangle catchRect;
	private Image barTileUp, barTileDown, barTileNW, barTileSW, barTileNE, barTileSE;
	private int tileCountX, tileCountY, yOffset=0;
	
	public UIBar(int topX, int topY, int tileCountX, int tileCountY) {
		barTileUp = Resources.barTileUp;
		barTileDown = Resources.barTileDown;
		barTileNW = Resources.barTileNW;
		barTileSW = Resources.barTileSW;
		barTileNE = Resources.barTileNE;
		barTileSE = Resources.barTileSE;
		
		this.tileCountX = tileCountX;
		this.tileCountY = tileCountY;
		frameRect = new Rectangle(topX, topY, tileCountX * barTileUp.getWidth(null), tileCountY * barTileUp.getHeight(null));
		catchRect = new Rectangle (topX, topY, tileCountX * barTileUp.getWidth(null),PlayScreen.screenSizeY - topY);
		
		if(tileCountY>0) yOffset = barTileUp.getHeight(null);
	}
	
	public void onPressed(int x, int y){
		if(catchRect.contains(x,y)){
			selected = true;
		} else {
			selected = false;
		}
	}
	
	public boolean isPressed(int x, int y){
		return selected && catchRect.contains(x,y);
	}
	
	public void render (Graphics g){
		Graphics2D g2;
		
		// fill the bar
		g.setColor(Resources.subTitleColor1);
		g.fillRoundRect(frameRect.x, frameRect.y, frameRect.width, frameRect.height, 20, 20);
		
		// fancy bar display
		if(yOffset > 0){
			g.drawImage(barTileSW, frameRect.x, frameRect.y + yOffset, null);
			g.drawImage(barTileSE, frameRect.x + (tileCountX-1)*barTileUp.getWidth(null), frameRect.y + yOffset, null);
			for (int i =1; i<tileCountX-1; i++){
				g.drawImage(barTileDown, frameRect.x + i * barTileUp.getWidth(null), frameRect.y + yOffset, null);			
			}			
		}
		g.drawImage(barTileNW, frameRect.x, frameRect.y, null);
		g.drawImage(barTileNE, frameRect.x + (tileCountX-1)*barTileUp.getWidth(null), frameRect.y, null);
		for (int i =1; i<tileCountX-1; i++){
			g.drawImage(barTileUp, frameRect.x + i * barTileUp.getWidth(null), frameRect.y, null);			
		}

		// bar frame
		g2 = (Graphics2D) g;
		g2.setStroke(Resources.strokeSize);		
		if(selected){ 
			g2.setColor(Resources.selectColor);
		} else {
			g2.setColor(Resources.titleColor1);
		}
		g2.drawRoundRect(frameRect.x, frameRect.y, frameRect.width, frameRect.height, 20, 20);
		
	}
	
}
