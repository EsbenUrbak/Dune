package com.baseframework.UI;

import java.awt.Image;
import java.util.ArrayList;

public class UIButtonDrag extends UIDragImage {
	private static final int subTypePriority = 12;	
	
	private UIAdvButton refButton;
	protected boolean toAdd = false, toRemove = false;
	
	public UIButtonDrag(int topX, int topY, Image dragImage, UIAdvButton refButton) {
		super(topX, topY, dragImage);
		this.refButton = refButton;
	}
	
	@Override
	public boolean updateList(ArrayList<UIObject> list){
		if(!toRemove) return false;
		
		if(toRemove && list.contains(this)){
			list.remove(this);
			toRemove = false;
			return true;
		}
		return false;
	}
	
	@Override
	// need to override this function so that it updates it even when the buttonDrag is not visible
	public boolean onPressed(int x, int y){
		if(rPos.contains(x,y)){
			dragged = true;
			toRemove = false;
			offsetX = x - rPos.x;
			offsetY = y - rPos.y;
		} else { 
			dragged = false;
		}
		return dragged;
	}
	
	@Override
	public boolean onReleased(int x, int y){
		super.onReleased(x, y);
		this.hide();
		this.toRemove = true;
		
		// if the buttonDrag is released into the bar show zone, switches the bar display and exits
		if(refButton.refBar != null){
			if(refButton.showBarRect.contains(x, y)) {
				refButton.refBar.switchDisplay();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean onDragged(int x, int y){
		super.onDragged(x,y);
		if(!refButton.buttonRect.contains(rPos) && !this.visible) this.show();
		return dragged;
	}
	
	@Override
	public int getTypePriority() {
		return subTypePriority;
	}

}
