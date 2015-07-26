package com.baseframework.UI;

import java.awt.Graphics;
import java.util.concurrent.CopyOnWriteArrayList;

public interface UIObject {
	
	public void render(Graphics g);
	
	public void update();
	
	public boolean updateList(CopyOnWriteArrayList<UIObject> list);
	
	public void show();
	
	public void hide();
	
	public boolean onPressed(int absX, int absY);
	
	public boolean onReleased(int absX, int absY);
	
	public boolean onDragged(int absX, int absY);
	
	public void performAction();
	
	public void cancel();
	
	public int getTypePriority();
}
