package com.baseframework.util;

import java.awt.Image;


public class UIDragItem extends UIDragImage {
	
	private float targetX, targetY;
	private float speedX=0f, speedY=0f;
	private boolean moving=false, visible=true;
	private UIBar refBar;
	private UIBarItem barLoc = null;
	
	public UIDragItem(int topX, int topY, Image dragImage, UIBar refBar) {
		super(topX, topY, dragImage);
		targetX = (float) topX;
		targetY = (float) topY;
		this.refBar = refBar;
	}
	
	
	@Override
	public void onReleased(int x, int y){
		UIBarItem placeHolder;
		
		super.onReleased(x,  y);
		
		placeHolder = refBar.inBarItem(x, y);
		if (placeHolder != null){
			this.barLoc = placeHolder;
			barLoc.setItem(this);
			this.visible = false;
			return;
		}
		
		//refBar.inDropZone()
			//if in bar, send to where the squad is
			//otherwise don't move
		
		placeHolder = refBar.inCatchZone(x, y);	
		if( placeHolder != null){
			this.barLoc = placeHolder;
			barLoc.setItem(this);
			this.visible = false;
			return;
		}
	}
	
	public void hide(){
		this.visible = false;
	}
	
	public void show(){
		this.visible = true;
	}
	
	public void update(){
	}
	
	public void sendTo(int targetX, int targetY){
	}
	
	

}
