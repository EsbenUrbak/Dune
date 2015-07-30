package com.baseframework.UI;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;


public class UIAdvButton extends UIButton {

	protected Rectangle showBarRect, hideBarRect;
	
	protected UIBar refBar = null;
	private UIButtonDrag showItem;

	public UIAdvButton(int topX, int topY, Image buttonDownImage, Image buttonUpImage, Image dragImage) {
		super(topX, topY, buttonDownImage, buttonUpImage);
		showItem = new UIButtonDrag(topX, topY, dragImage, this);
		showItem.hide();
		
		hideBarRect = new Rectangle(UIDragImage.rScope.x, buttonRect.y, 
				Math.max(0, (int) buttonRect.getMaxX() - UIDragImage.rScope.x), Math.max(0, (int) UIDragImage.rScope.getMaxY() - buttonRect.y));
	}
	
	public void setBar(UIBar refBar){
		this.refBar = refBar;
		this.showBarRect = new Rectangle(refBar.catchRect.x, refBar.catchRect.y, 
								(int) UIDragImage.rScope.getMaxX() - refBar.catchRect.x, (int) UIDragImage.rScope.getMaxY() - refBar.catchRect.y); 
	}
	
	@Override
	public boolean updateList(ArrayList<UIObject> list){
		if(showItem.toUpdate){
			showItem.toUpdate = false;
			if(list.contains(showItem)) {
				list.remove(showItem);
			}
			else {
				list.add(showItem);
				return true;}
		}
		return false;
	}
	
	@Override
	public boolean onPressed(int x, int y){
		boolean pressed = super.onPressed(x, y);
		if (pressed && !showItem.dragged){
			showItem.setLocation(x - (int) showItem.rPos.getWidth()/2, y - (int) showItem.rPos.getHeight()/2);
			showItem.onPressed(x,y);
			showItem.toUpdate = true;
		}
		return pressed;
	}
	
	@Override
	public void performAction(){
		// switch back and forth to shuttle mode
		if(refBar != null){
			System.out.println("switch between squad or shuttle view");
			//refBar.switchDisplay();
		}
	}

}
