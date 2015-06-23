package com.baseframework.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;


public class UIButton {
	private Rectangle buttonRect;
	private Boolean buttonDown = false;
	private Image buttonImageDown, buttonImageUp;
	
	public UIButton(int topX, int topY, int sizeX, int sizeY, Image buttonImageDown, Image buttonImageUp){
		buttonRect = new Rectangle(topX, topY, sizeX, sizeY);
		this.buttonImageDown = buttonImageDown;
		this.buttonImageUp = buttonImageUp;
	}
	
	public void render(Graphics g){
		Image currentButtonImage = buttonDown ? buttonImageDown : buttonImageUp;
		g.drawImage(currentButtonImage, buttonRect.x, buttonRect.y, buttonRect.width, buttonRect.height, null);
	}
	
	public void onPressed(int x, int y){
		if(buttonRect.contains(x,y)){
			buttonDown = true;
		} else {
			buttonDown = false;
		}
		//System.out.println("Button is pressed!");
	}
	
	public void cancel(){
		buttonDown = false;
	}
	
	public boolean isPressed(int x, int y){
		return buttonDown && buttonRect.contains(x, y);
	}
	
}
