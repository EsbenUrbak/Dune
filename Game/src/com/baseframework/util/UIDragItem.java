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
		super.onReleased(x,  y);
		
		//refBar.inDropZone()
			//if in bar, send to where the squad is
			//otherwise don't move
		
		//refBar.inBarItem()
			//return barItem
			//update the barItem image
			//hide the dragItem
		
		//refBar.inCatchZone()
			//check whether can add
			//if yes, add a barItem and set it in there
			//if no, send back
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
