package com.baseframework.util;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class UIVarButton extends UIButton{
	ArrayList <UIDragImage> dragItems;
	int mode = 0;
	int varTopX, varTopY, varWidth, varHeight;
	
	public UIVarButton(int topX, int topY, int sizeX, int sizeY, Image buttonImageDown, Image buttonImageUp) {
		super(topX, topY, sizeX, sizeY, buttonImageDown, buttonImageUp);
	}
	
	public void render(Graphics g){
		Image currentButtonImage = buttonDown ? buttonImageDown : buttonImageUp; 
		g.drawImage(currentButtonImage, buttonRect.x, buttonRect.y, buttonRect.width, buttonRect.height, null);
	}	
	
	
	
	
}
