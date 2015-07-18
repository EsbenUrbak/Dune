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
	private Image barTileN, barTileS, barTileW, barTileE, barTileNW, barTileSW, barTileNE, barTileSE, barTileIn;
	private int tileCountX, tileCountY, yOffset=0;
	
	public UIBar(int topX, int topY, int tileCountX, int tileCountY) {
		barTileN = Resources.barTileN;
		barTileS = Resources.barTileS;
		barTileW = Resources.barTileW;
		barTileE = Resources.barTileE;
		barTileNW = Resources.barTileNW;
		barTileSW = Resources.barTileSW;
		barTileNE = Resources.barTileNE;
		barTileSE = Resources.barTileSE;
		barTileIn = Resources.barTileIn;
		
		this.tileCountX = tileCountX;
		this.tileCountY = tileCountY;
		frameRect = new Rectangle(topX, topY, tileCountX * barTileN.getWidth(null), tileCountY * barTileN.getHeight(null));
		catchRect = new Rectangle (topX, topY, tileCountX * barTileN.getWidth(null),PlayScreen.screenSizeY - topY);
		
		yOffset = barTileN.getHeight(null);
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
		Image displayImage;
		
		// fill the bar
		g.setColor(Resources.subTitleColor1);
		g.fillRoundRect(frameRect.x, frameRect.y, frameRect.width, frameRect.height, 20, 20);
		
		// fancy bar display
		for (int j=0; j<tileCountY; j++){
			for (int i=0; i<tileCountX; i++){
				if(i == 0 && j == 0) {displayImage = barTileNW;}
				else if(i == 0 && j == tileCountY-1) {displayImage = barTileSW;}
				else if(i == tileCountX-1 && j == 0) {displayImage = barTileNE;}
				else if(i == tileCountX-1 && j == tileCountY-1) {displayImage = barTileSE;}
				else if(i != 0 && j == 0) {displayImage = barTileN;}
				else if(i == tileCountX-1 && j != tileCountY-1) {displayImage = barTileE;}
				else if(i != 0 && j == tileCountY-1) {displayImage = barTileS;}				
				else if(i == 0 && j != 0) {displayImage = barTileW;}
				else {displayImage = barTileIn;}
				
				g.drawImage(displayImage, frameRect.x + i * barTileN.getWidth(null), frameRect.y + j * barTileN.getHeight(null), null);
			}
		}
		
/*		if(yOffset > 0){
			g.drawImage(barTileSW, frameRect.x, frameRect.y + yOffset, null);
			g.drawImage(barTileSE, frameRect.x + (tileCountX-1)*barTileN.getWidth(null), frameRect.y + yOffset, null);
			for (int i =1; i<tileCountX-1; i++){
				g.drawImage(barTileS, frameRect.x + i * barTileN.getWidth(null), frameRect.y + yOffset, null);			
			}			
		}
		g.drawImage(barTileNW, frameRect.x, frameRect.y, null);
		g.drawImage(barTileNE, frameRect.x + (tileCountX-1)*barTileN.getWidth(null), frameRect.y, null);
		for (int i =1; i<tileCountX-1; i++){
			g.drawImage(barTileN, frameRect.x + i * barTileN.getWidth(null), frameRect.y, null);			
		}*/

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
