package com.baseframework.UI;

import java.awt.Image;
import java.awt.Rectangle;

public class UIDragButton extends UIDragImage {

	private static final int subTypePriority = 13;
	private Image buttonUpImage, buttonDownImage;
	protected Rectangle defaultRect, showBarRect, hideBarRect;	
	private UIBar refBar = null;

	public UIDragButton(int topX, int topY, Image buttonDownImage, Image buttonUpImage) {
		
		super(topX, topY, buttonUpImage);	
		
		defaultRect = new Rectangle(rPos);
		hideBarRect = new Rectangle(UIDragImage.rScope.x, rPos.y, 
				Math.max(0, (int) rPos.getMaxX() - rScope.x), Math.max(0, (int) UIDragImage.rScope.getMaxY() - rPos.y));
		
		this.buttonUpImage = buttonUpImage;
		this.buttonDownImage = buttonDownImage;
	}
	
	public void setBar(UIBar refBar){
		this.refBar = refBar;
		this.showBarRect = new Rectangle(refBar.catchRect.x, refBar.catchRect.y, 
								(int) UIDragImage.rScope.getMaxX() - refBar.catchRect.x, (int) UIDragImage.rScope.getMaxY() - refBar.catchRect.y); 
	}
	
	@Override
	public boolean onPressed(int x, int y){
		boolean pressed = super.onPressed(x, y);
		if (pressed) super.dragImage = buttonDownImage;
		return pressed;
	}
	
	@Override
	public boolean onReleased(int x, int y){
		if(dragged && visible){
			// if the button is released, send the image back to the default location
			super.dragImage = buttonUpImage;
			super.setLocation(defaultRect.x, defaultRect.y);
			super.onReleased(x, y);
			
			// if the button is released into the bar show zone, switch the bar display and exit
			if(refBar != null){
				if(showBarRect.contains(x, y)) {
					refBar.switchDisplay();
					return false;
				}
			}
			
			//if the button is released into the default rect, return true to execute the perform action
			if(defaultRect.contains(x, y)) return true;			
		}	
		return false;
	}
	
	@Override
	public void performAction(){
		// switch back and forth to shuttle mode
		if(refBar != null){
			refBar.switchDisplay();
		}
	}
	
	@Override
	public int getTypePriority() {
		return subTypePriority;
	}

}
