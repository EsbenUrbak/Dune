package com.baseframework.UI;

import java.awt.Image;


public class UIDragItem extends UIDragImage {
	
	private int priority=9;
	private float targetX, targetY;
	private UIBar refBar;
	private UIBarSlot barSlot = null;

	public UIDragItem(int topX, int topY, Image dragImage, UIBar refBar) {
		super(topX, topY, dragImage);
		targetX = (float) topX;
		targetY = (float) topY;
		this.refBar = refBar;
	}
	
	@Override
	public boolean onReleased(int x, int y){
		UIBarSlot placeHolder;
		
		// release the image in the mother class
		// if the item isn't being dragged, exits
		if(!super.onReleased(x, y)) return false;
		
		//then checks if the item is dropped in a bar slot
		placeHolder = refBar.inBarItem(x, y);
		if (placeHolder != null){
			this.barSlot = placeHolder;
			barSlot.setItem(this);
			this.hide();
			refBar.update();
			return true;
		}
		
		// then, only if the item was in the bar, checks if it was dropped in the collapse zone
		if (refBar.inBarCollapseZone(rPos) && barSlot != null) {
			// if so, send it back to its bar slot, and hide it
			barSlot.setItem(this);
			this.hide();
			return true;
		}
		
		// then checks if the item was dropped into the bar
		placeHolder = refBar.inCatchZone(this.rPos);	
		if( placeHolder != null){
			this.barSlot = placeHolder;
			barSlot.setItem(this);
			this.hide();
			refBar.update();
			return true;
		}
		
		//if dropped outside of the bar catch and collapse zone, reset bar slot to null
		//refBar.inDropZone()
		//if in bar, send to where the squad is
		if(barSlot != null) barSlot = null;		
		
		//otherwise don't move
		refBar.update();
		return true;
	}
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	

}
