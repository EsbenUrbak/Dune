package com.baseframework.UI;

import java.awt.Image;
import java.util.ArrayList;


public class UIDragItem extends UIDragImage {
	
	private int priority=9;
	private float targetX, targetY;
	
	private boolean toRemove=false;
	
	private UIBar refBar;
	private UIBarSlot barSlot = null;

	public UIDragItem(int topX, int topY, Image dragImage, UIBar refBar) {
		super(topX, topY, dragImage);
		targetX = (float) topX;
		targetY = (float) topY;
		this.refBar = refBar;
	}
	
	@Override
	public boolean updateList(ArrayList<UIObject> list){
		if(!toRemove) return false;
		
		if(list.contains(this)){
			list.remove(this);
			toRemove = false;
			return true;
		}
		return false;
		
	}
	
	@Override
	public boolean onReleased(int x, int y){
		UIBarSlot placeHolder;
		
		// release the image in the mother class
		// if the item isn't being dragged, exits
		if(!super.onReleased(x, y)) return false;
		
		//then checks if the item is dropped in a bar slot
		placeHolder = refBar.inBarItem(x, y, this);
		if (placeHolder != null){
			assignSlot(placeHolder);
			toRemove = true;
			return true;
		}
		
		// then, only if the item was in the bar, checks if it was dropped in the collapse zone
		if (refBar.inBarCollapseZone(rPos) && barSlot != null) {
			// if so, hide the bar, send the item back to its bar slot, and hide it
			refBar.hide();
			assignSlot(barSlot);
			toRemove = true;
			return true;
		}
		
		// then checks if the item was dropped into the bar
		placeHolder = refBar.inCatchZone(this);	
		if( placeHolder != null){
			assignSlot(placeHolder);
			toRemove = true;
			return true;
		}
		
		//if dropped outside of the bar catch and collapse zone, reset bar slot to null
		//refBar.inDropZone()
		//if in bar, send to where the squad is
		if(barSlot != null) {
			assignSlot(null);
			return true;
		}	
		
		//otherwise don't move
		return true;
	}
	
	private void assignSlot(UIBarSlot newSlot){
		// if both current slot and new slot are null, exits
		if(barSlot == null && newSlot == null) return;
		
		// then if current slot = new slot, reset the slot but do NOT reorganize the bar
		if(barSlot == newSlot){
			barSlot.setItem(this);
			return;
		}
		
		// then if current slot is null, reset only the new slot
		if(barSlot == null){
			this.barSlot = newSlot;
			barSlot.setItem(this);
			refBar.reOrganize(barSlot);
			refBar.update();
			return;			
		}
		
		// finally if slots are different and current is not null, reset both slots position
		else{
			refBar.reOrganize(barSlot);
			this.barSlot = newSlot;
			if(newSlot != null ) {
				barSlot.setItem(this);
				refBar.reOrganize(barSlot);
			}
			refBar.update();	
			return;
		}
	}
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	

}
