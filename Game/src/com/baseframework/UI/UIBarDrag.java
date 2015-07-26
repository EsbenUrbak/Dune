package com.baseframework.UI;

import java.awt.Image;

public class UIBarDrag extends UIDragImage {

	private static final int subTypePriority = 12;	
	
	protected boolean toUpdate=false;
	protected UIBar refBar;
	
	public UIBarDrag(int topX, int topY, Image dragImage, UIBar refBar) {
		super(topX, topY, dragImage);		
		this.refBar = refBar;
	}

	@Override
	public boolean onPressed(int x, int y){
		this.visible = true;
		super.setLocation(x - rPos.width / 2, y - rPos.height/2);
		toUpdate = super.onPressed(x, y);
		return toUpdate;
	}
	
	@Override
	public boolean onReleased(int x, int y){
		if(dragged && visible){
			// if the button is released, send the image back to the default location
			super.onReleased(x, y);
			toUpdate = true;
			this.hide();
			
			// if the button is released into the refButton catch zone, collapse the bar
			if(refBar.refButton != null){
				if(refBar.refButton.hideBarRect.intersects(rPos)) {
					refBar.hide();
				}
			}	
		}	
		return false;
	}
	
	@Override
	public int getTypePriority() {
		return subTypePriority;
	}
	
}
