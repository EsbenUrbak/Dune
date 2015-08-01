package com.baseframework.UI;

import java.awt.Graphics;
import java.util.ArrayList;


public abstract class UIObject {
	
	public abstract void render(Graphics g);
	
	public abstract void update();
	
	public abstract boolean updateList(ArrayList<UIObject> list);
	
	public abstract void show();
	
	public abstract void hide();
	
	public abstract boolean onPressed(int absX, int absY);
	
	public abstract boolean onReleased(int absX, int absY);
	
	public abstract boolean onDragged(int absX, int absY);
	
	public abstract void performAction();
	
	public abstract void cancel();
	
	public abstract int getTypePriority();
	
	public static void pushItem(UIObject item, ArrayList<UIObject> list){
		if(list.isEmpty()) {
			list.add(item);
			return;
		}
		
		int index=0; 
		int priority = item.getTypePriority();
		
		while (index<list.size()){
			if(list.get(index).getTypePriority()<= priority) {index++;} 
			else {break;}
		}
		
		list.add(index, item);
	}
}
