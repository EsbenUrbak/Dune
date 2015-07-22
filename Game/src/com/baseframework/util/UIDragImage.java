package com.baseframework.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class UIDragImage {
	protected boolean visible =true;
	private Image dragImage;
	
	// positions rectangles
	private Rectangle rPos;
	private static Rectangle rScope = new Rectangle(0,0,0,0); 
	
	// enable to drag from the selected point in the image (when selected elsewhere than top left)
	private int offsetX=0, offsetY=0;
	private boolean dragged = false;
	
	public UIDragImage(int topX, int topY, Image dragImage){
		this.dragImage = dragImage;
		rPos = new Rectangle(topX, topY, dragImage.getWidth(null), dragImage.getHeight(null));
	}
	
	public static void setScope(int scopeMaxX, int scopeMaxY){
		rScope.setBounds(0, 0, scopeMaxX, scopeMaxY);
	}
	
	public void onPressed(int x, int y){
		if(rPos.contains(x,y) && visible){
			dragged = true;
			offsetX = x - rPos.x;
			offsetY = y - rPos.y;
		} else { 
			dragged = false;
		}
	}
	
	public void onDragged(int x, int y){
		if(dragged){
			rPos.setLocation(x - offsetX,y - offsetY);
		}
		
		// bound the image to the screen
		if(!rScope.contains(rPos)){
			rPos.x = Math.max(rScope.x, (int) Math.min(rScope.getMaxX() - rPos.width, x - offsetX));
			rPos.y = Math.max(rScope.y, (int) Math.min(rScope.getMaxY() - rPos.height, y - offsetY));
		}
	}
	
	public void onReleased(int x, int y){
		dragged = false;
		offsetX = 0;
		offsetY = 0;
	}
	
	public void render(Graphics g){
		if(visible) g.drawImage(dragImage, rPos.x, rPos.y, null);
	}
	
	public void scaleandrender(Graphics g, int width, int height){
		if(visible) g.drawImage(dragImage, rPos.x, rPos.y, width, height, null);
	}

	public boolean isDragged() {
		return dragged;
	}
}
