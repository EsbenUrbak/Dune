package com.baseframework.UI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;

public class UIDragImage implements UIObject {
	private static final int typePriority = 10;
	
	protected boolean visible =true;
	protected Image dragImage;
	
	// positions rectangles
	protected Rectangle rPos;
	protected static Rectangle rScope = new Rectangle(0,0,0,0); 
	
	// enable to drag from the selected point in the image (when selected elsewhere than top left)
	protected int offsetX=0, offsetY=0;
	protected boolean dragged = false;
	
	public UIDragImage(int topX, int topY, Image dragImage){
		this.dragImage = dragImage;
		rPos = new Rectangle(topX, topY, dragImage.getWidth(null), dragImage.getHeight(null));
	}
	
	public void render(Graphics g){
		if(visible) g.drawImage(dragImage, rPos.x, rPos.y, null);
	}
	
	public void scaleandrender(Graphics g, int width, int height){
		if(visible) g.drawImage(dragImage, rPos.x, rPos.y, width, height, null);
	}
	
	@Override
	public void update() {
	}
	
	@Override
	public boolean updateList(CopyOnWriteArrayList<UIObject> list){
		return false;
	}

	@Override
	public void show() {
		this.visible = true;
	}

	@Override
	public void hide() {
		this.visible = false;
	}
	
	public static void setScope(int scopeMaxX, int scopeMaxY){
		rScope.setBounds(0, 0, scopeMaxX, scopeMaxY);
	}
	
	public void setLocation(int absX, int absY){
		if(rScope.contains(absX, absY, rPos.width, rPos.height)){
			rPos.setLocation(absX, absY);
		} else {
			absX = Math.max(absX, (int) rScope.getMinX());
			absX = Math.min(absX+rPos.width, (int) rScope.getMaxX());
			
			absY = Math.max(absY, (int) rScope.getMinY());
			absY = Math.min(absY+rPos.height, (int) rScope.getMaxY());
			
			rPos.setLocation(absX, absY);
		}
	}
	
	@Override
	public boolean onPressed(int x, int y){
		if(rPos.contains(x,y) && visible){
			dragged = true;
			offsetX = x - rPos.x;
			offsetY = y - rPos.y;
		} else { 
			dragged = false;
		}
		return dragged;
	}
	
	@Override
	public boolean onReleased(int x, int y){
		boolean wasDragged= dragged;
		
		dragged = false;
		offsetX = 0;
		offsetY = 0;
		
		return wasDragged;
	}
	
	@Override
	public void cancel() {
	}
	
	//@Override
	public int getTypePriority() {
		return typePriority;
	}
	
	@Override
	public boolean onDragged(int x, int y){
		if(dragged){
			rPos.setLocation(x - offsetX,y - offsetY);
		}
		
		// bound the image to the screen
		if(!rScope.contains(rPos)){
			rPos.x = Math.max(rScope.x, (int) Math.min(rScope.getMaxX() - rPos.width, x - offsetX));
			rPos.y = Math.max(rScope.y, (int) Math.min(rScope.getMaxY() - rPos.height, y - offsetY));
		}
		
		return dragged;
	}
	
	@Override
	public void performAction() {
		// an image is not a button, no action performed
	}
	

}
