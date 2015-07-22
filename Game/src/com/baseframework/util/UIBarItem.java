package com.baseframework.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import com.baseframework.game.main.Resources;

public class UIBarItem {
	
	public static int width=45, height=45;
	private int priority=0;
	private boolean visible;

	private Rectangle frameRect;
	public Rectangle catchRect;
	
	private Image image=null;
	
	private UIBar refBar;
	private UIDragItem item=null;

	
	public UIBarItem(UIBar refBar){
		this.refBar = refBar;		
		visible = true;
		frameRect = new Rectangle(refBar.frameRect.x, refBar.frameRect.y,0,0);
		catchRect =  new Rectangle(refBar.catchRect.x, refBar.catchRect.x,0,0);
	}
	
	public void setInBar(int relX, int relY){
		frameRect.setBounds(relX + refBar.frameRect.x, relY + refBar.frameRect.y, width, height);
		catchRect.setBounds(relX + refBar.frameRect.x, relY + refBar.frameRect.y, width, height);
	}
	
	public void setItem(UIDragItem item){
		this.item = item;
		this.image = item.dragImage;
	}

	public void render(Graphics g){
		if(!visible) return;
		
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
	
	public int getPriority(){
		return priority;
	}
	
	public void setPriority(int priority){
		this.priority = priority;
	}
	
}

