package com.baseframework.UI;

import java.awt.Image;


public class UIDragItem extends UIDragImage {
	
	private int priority=9;
	private float targetX, targetY;
	private UIBar refBar;
	private UIBarSlot barLoc = null;

	public UIDragItem(int topX, int topY, Image dragImage, UIBar refBar) {
		super(topX, topY, dragImage);
		targetX = (float) topX;
		targetY = (float) topY;
		this.refBar = refBar;
	}
	
	@Override
	public boolean onReleased(int x, int y){
		UIBarSlot placeHolder;
		boolean check;
		
		// release the image in the mother class
		// if the item isn't being dragged, exits
		check = super.onReleased(x, y);
		if(!check) return check;
		
		//else start by checking is the item is dropped in a container
		placeHolder = refBar.inBarItem(x, y);
		if (placeHolder != null){
			this.barLoc = placeHolder;
			barLoc.setItem(this);
			this.hide();
			refBar.update();
			return true;
		}
		
		//refBar.inDropZone()
			//if in bar, send to where the squad is
			//otherwise don't move
		
		// then checks if the item was dropped into the bar
		placeHolder = refBar.inCatchZone(this.rPos);	
		if( placeHolder != null){
			this.barLoc = placeHolder;
			barLoc.setItem(this);
			this.hide();
			refBar.update();
			return true;
		}
		
		// otherwise perform default reset action
		// sentTo(initial position)
		
		refBar.update();
		return true;
	}
	
	// do not delete: redundant with super class but stop working otherwise
	public void hide(){
		this.visible = false;
	}
	
	public void sendTo(int targetX, int targetY){
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	

}
