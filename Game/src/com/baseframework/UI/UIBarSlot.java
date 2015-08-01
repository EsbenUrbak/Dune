package com.baseframework.UI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.baseframework.game.main.Resources;

public class UIBarSlot extends UIObject{

	public static final int typePriority = 15;
	public static int width=45, height=45;

	private int priority=10;
	private boolean visible, empty=true, toAdd=false;

	private Rectangle frameRect;
	public Rectangle catchRect;
	
	private Image image=null;
	
	private UIBar refBar;
	private UIDragItem item=null;

	
	public UIBarSlot(UIBar refBar){
		this.refBar = refBar;		
		visible = true;
		frameRect = new Rectangle(refBar.frameRect.x, refBar.frameRect.y,0,0);
		catchRect =  new Rectangle(refBar.catchRect.x, refBar.catchRect.x,0,0);
	}
	
	@Override
	public void render(Graphics g){
		if(!visible || !refBar.visible) return;
		
		if(item == null){
			Graphics2D g2;
			g2 = (Graphics2D) g;
			g2.setStroke(Resources.strokeSize);
			g2.setColor(Resources.frameColor);
		
			g2.drawRoundRect(frameRect.x, frameRect.y, width, height, 20, 20);
		} else {
			g.drawImage(image, frameRect.x, frameRect.y, null);
		}
	}
	
	@Override
	public void update() {	
	}
	
	@Override
	public boolean updateList(ArrayList<UIObject> list){

		// add the item to the playScreen list, then remove it from the slot and reset the slot to a lower priority in the bar
		if(toAdd){
			toAdd = false;
			pushItem(item, list);
			this.removeItem();
			return true;
		}
		return false;
	}
	
	@Override
	public void show() {
		this.visible = true;
		catchRect.setSize(width, height);
	}

	@Override
	public void hide() {
		this.visible = false;
		catchRect.setSize(0, 0);
	}
	
	@Override
	public boolean onPressed(int absX, int absY){;
		// if clicked inside a full slot, show, active and add the dragItem to the list	
		if(this.catchRect.contains(absX, absY) && visible && !empty){
			item.setLocation(frameRect.x, frameRect.y);
			item.show();
			item.onPressed(absX, absY);
			toAdd = true;
			return true;
		}
		 
		return false;
	}
	
	@Override
	public boolean onReleased(int absX, int absY) {
		return false;
	}
	
	@Override
	public boolean onDragged(int absX, int absY) {
		return false;
	}
	
	@Override
	public void performAction() {
		// a slot is not a button, no action performed
	}

	@Override
	public void cancel() {
	}
	
	//@Override
	public int getTypePriority() {
		return typePriority;
	}
	
	public void setInBar(int relX, int relY){
		frameRect.setBounds(relX + refBar.frameRect.x, relY + refBar.frameRect.y, width, height);
		catchRect.setBounds(relX + refBar.frameRect.x, relY + refBar.frameRect.y, width, height);
	}
	
	public void setItem(UIDragItem item){
		this.priority = item.getPriority();
		this.item = item;
		this.image = item.dragImage;
		this.empty = false;
	}
	
	public void removeItem(){
		if(!empty){
			this.item = null;
			this.empty = true;
			this.priority = 10;
		}
	}
	
	public UIDragItem getItem(){
		return this.item;
	}
	
	
	public int getPriority(){
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	

	public boolean isEmpty() {
		return empty;
	}


}

