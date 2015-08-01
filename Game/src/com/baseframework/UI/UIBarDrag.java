package com.baseframework.UI;

import java.awt.Image;
import java.util.ArrayList;

public class UIBarDrag extends UIDragImage {

	private static final int subTypePriority = 12;	
	
	protected boolean toAdd=false, toRemove=false;
	protected UIBar refBar;
	
	public UIBarDrag(int topX, int topY, Image dragImage, UIBar refBar) {
		super(topX, topY, dragImage);		
		this.refBar = refBar;
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
			toRemove = false;
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
		// if the button is released, send the image back to the default location	
		super.onReleased(x, y);
		this.hide();
		toRemove = true;
		
		// if the button is released into the refButton catch zone, collapse the bar
		if(refBar.refButton != null){
			if(refBar.refButton.hideBarRect.contains(x,y)) {
				refBar.hide();
				return false;
			}
		}
		return false;
	}
	
	@Override
	public boolean onDragged(int x, int y){
		super.onDragged(x,y);
		if(!refBar.frameRect.contains(x,y) && !this.visible) {
			this.show();
		}
		return dragged;
	}
	
	@Override
	public int getTypePriority() {
		return subTypePriority;
	}
	
}
