package com.baseframework.UI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;


public class UIButton implements UIObject{
	private static final int typePriority = 17;
	
	protected Rectangle buttonRect;
	protected Boolean buttonDown = false, visible = true;
	protected Image buttonImageDown, buttonImageUp;
	
	public UIButton(int topX, int topY, int sizeX, int sizeY, Image buttonImageDown, Image buttonImageUp){
		buttonRect = new Rectangle(topX, topY, sizeX, sizeY);
		this.buttonImageDown = buttonImageDown;
		this.buttonImageUp = buttonImageUp;
	}
	
	public void render(Graphics g){
		if (visible){
			Image currentButtonImage = buttonDown ? buttonImageDown : buttonImageUp;
			g.drawImage(currentButtonImage, buttonRect.x, buttonRect.y, buttonRect.width, buttonRect.height, null);
		}
	}
	
	@Override
	public void update() {
	}
	
	@Override
	public boolean updateList(CopyOnWriteArrayList<UIObject> list){
		return false;
	}
	
	@Override
	public void show() {
		visible = true;
	}

	@Override
	public void hide() {
		visible = false;
	}
	
	@Override
	public boolean onPressed(int x, int y){
		buttonDown = buttonRect.contains(x,y) ? true : false;
		return buttonDown && visible;
	}
	
	@Override
	public boolean onReleased(int x, int y) {
		boolean isActive = buttonDown;
		buttonDown = false;
		
		return isActive && buttonRect.contains(x, y) && visible;
	}
	
	@Override
	public boolean onDragged(int absX, int absY) {
		return false;
	}
	
	@Override
	public void performAction() {
		// a generic button does not perform any action
	}
	
	@Override
	public void cancel(){
		buttonDown = false;
	}
	
	//@Override
	public int getTypePriority() {
		return typePriority;
	}
	
}
