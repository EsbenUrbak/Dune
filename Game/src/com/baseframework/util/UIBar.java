package com.baseframework.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import com.baseframework.game.main.Resources;
import com.baseframework.screen.PlayScreen;

public class UIBar {
	private static final int MINTILEX =2, MAXTILEX=10, MINTILEY =2, MAXTILEY=5;
	private static final int MAXITEMS =5;
	public static final int EDGESIZEX=20, EDGESIZEY=10, MINGAPX=5, MINGAPY=5;
	
    public static Comparator<UIBarItem> priorityOrder = new Comparator<UIBarItem>(){
		@Override
		public int compare(UIBarItem item1, UIBarItem item2) {
			return Integer.compare(item1.getPriority(), item2.getPriority());
		}
    };
	
	private boolean selected=false, visible=true;
	public Rectangle frameRect;
	public Rectangle catchRect, lvl1catchRect; // if not in lvl1catchRect, then assumes it will be on lvl2
	private Image barTileN, barTileS, barTileW, barTileE, barTileNW, barTileSW, barTileNE, barTileSE, barTileIn;
	private int tileCountX, tileCountY, itemCount1=0, itemCount2=0, tileHeight, tileWidth;
	private CopyOnWriteArrayList<UIBarItem> lvl1items, lvl2items;
	
	public UIBar(int topX, int topY, int tileCountX, int tileCountY) {
		barTileN = Resources.barTileN;
		barTileS = Resources.barTileS;
		barTileW = Resources.barTileW;
		barTileE = Resources.barTileE;
		barTileNW = Resources.barTileNW;
		barTileSW = Resources.barTileSW;
		barTileNE = Resources.barTileNE;
		barTileSE = Resources.barTileSE;
		barTileIn = Resources.barTileIn;
		
		this.tileCountX = tileCountX;
		this.tileCountY = tileCountY;
		tileWidth = barTileN.getWidth(null);
		tileHeight = barTileN.getHeight(null);
		
		frameRect = new Rectangle(topX, topY, tileCountX * tileWidth, tileCountY * tileHeight);
		catchRect = new Rectangle (topX, topY, tileCountX * tileWidth,PlayScreen.screenSizeY - topY);
		lvl1catchRect = new Rectangle(catchRect);
		
		lvl1items = new CopyOnWriteArrayList<UIBarItem>();
		lvl2items = new CopyOnWriteArrayList<UIBarItem>();
	}
	
	public void onPressed(int x, int y){
		if(catchRect.contains(x,y)){
			selected = true;
		} else {
			selected = false;
		}
	}
	
	public boolean isPressed(int x, int y){
		return selected && catchRect.contains(x,y);
	}
	
	public void update(){
		int itemX; 
		int itemY; 
		
		// update locations of items in level 1
		
		if(!lvl1items.isEmpty()){
			itemX = UIBarItem.width * itemCount1 + MINGAPX * Math.max(itemCount1-1, 0);
			itemX = (frameRect.width - itemX)/2;
			itemY = frameRect.height -EDGESIZEY -UIBarItem.height;
			
			// sort by order of priority for better display
			lvl1items.sort(priorityOrder);
			for(int i=0; i<lvl1items.size(); i++){
				lvl1items.get(i).setInBar(itemX, itemY);
				itemX += UIBarItem.width + MINGAPX;
			}
		}
		
		// update locations of items in level 2
		if(!lvl2items.isEmpty()){
			itemX = UIBarItem.width * itemCount2 + MINGAPX * Math.max(itemCount2-1, 0);
			itemX = (frameRect.width - itemX)/2;
			itemY = EDGESIZEY;
			
			// sort by order of priority for better display
			lvl2items.sort(priorityOrder);
			for(int i=0; i<lvl2items.size(); i++){
				lvl2items.get(i).setInBar(itemX, itemY);
				itemX += UIBarItem.width + MINGAPX;
			}
		}
	}
	
	public void render (Graphics g){
		if (!visible) return;
		
		Graphics2D g2;
		Image displayImage;
		
		// fancy bar display
		for (int j=0; j<tileCountY; j++){
			for (int i=0; i<tileCountX; i++){
				if(i == 0 && j == 0) {displayImage = barTileNW;}
				else if(i == 0 && j == tileCountY-1) {displayImage = barTileSW;}
				else if(i == tileCountX-1 && j == 0) {displayImage = barTileNE;}
				else if(i == tileCountX-1 && j == tileCountY-1) {displayImage = barTileSE;}
				else if(i != 0 && j == 0) {displayImage = barTileN;}
				else if(i == tileCountX-1 && j != tileCountY-1) {displayImage = barTileE;}
				else if(i != 0 && j == tileCountY-1) {displayImage = barTileS;}				
				else if(i == 0 && j != 0) {displayImage = barTileW;}
				else {displayImage = barTileIn;}
				
				g.drawImage(displayImage, frameRect.x + i * tileWidth, frameRect.y + j * tileHeight, null);
			}
		}	
		
		// bar frame
		g2 = (Graphics2D) g;
		g2.setStroke(Resources.strokeSize);		
		if(selected){ 
			g2.setColor(Resources.selectColor);
		} else {
			g2.setColor(Resources.frameColor);
		}
		
		g2.drawRoundRect(frameRect.x, frameRect.y, frameRect.width, frameRect.height, 20, 20);
		
		// horizontal separation line (if there are items in 2nd level)
		if(!lvl2items.isEmpty()){
			g2.drawLine(frameRect.x + EDGESIZEX, frameRect.y  + frameRect.height/2, 
					(int) frameRect.getMaxX() - EDGESIZEX, frameRect.y  + frameRect.height/2);
		}
			
		// display items within the bar
		this.renderBarItems(g);
	}
	
	private void renderBarItems(Graphics g){
		for (Iterator<UIBarItem> iterator = lvl1items.iterator(); iterator.hasNext();) {
			UIBarItem lvl1item = iterator.next();
			lvl1item.render(g);
		}
		
		for(Iterator<UIBarItem> iterator = lvl2items.iterator(); iterator.hasNext();){
			UIBarItem lvl2item = iterator.next();
			lvl2item.render(g);
		}		
	}
	
	public void switchDisplay(){
		if (visible){
			hide();
		} else {
			show();
		}
	}
	
	public void show(){
		visible = true;
		catchRect.setSize(tileCountX * tileWidth, tileCountY * tileHeight);		
	}
	
	public void hide(){
		visible = false;
		catchRect.setSize(0,0);
	}
	
	public void invoke(){
	}
	
	public void vanish(){
	}
	
	public void extendright(){
		if (tileCountX < MAXTILEX){
			tileCountX++;
			frameRect.setSize(frameRect.width + tileWidth, frameRect.height);
			catchRect.setSize(catchRect.width + tileWidth, catchRect.height);
			lvl1catchRect.setSize(lvl1catchRect.width + tileWidth, lvl1catchRect.height);
		}
	}
	
	public void collapseleft(){
		if (tileCountX > MINTILEX){
			tileCountX--;
			frameRect.setSize(frameRect.width - tileWidth, frameRect.height);
			catchRect.setSize(catchRect.width - tileWidth, catchRect.height);
			lvl1catchRect.setSize(lvl1catchRect.width - tileWidth, lvl1catchRect.height);
		}
	}
	
	public void extendup(){
		if (tileCountY < MAXTILEY){
			tileCountY++;
			frameRect.setRect(frameRect.x, frameRect.y - tileHeight, frameRect.width, frameRect.height + tileHeight);
			catchRect.setRect(catchRect.x, catchRect.y - tileHeight, catchRect.width, catchRect.height + tileHeight);
			lvl1catchRect.setRect(lvl1catchRect.x, frameRect.y  + frameRect.height/2, 
					lvl1catchRect.width, catchRect.getMaxY() - frameRect.y - frameRect.height/2 );
		}
	}
	
	public void collapsedown(){
		if (tileCountY > MINTILEY){
			tileCountY--;
			frameRect.setRect(frameRect.x, frameRect.y + tileHeight, frameRect.getWidth(), frameRect.getHeight() - tileHeight);
			catchRect.setRect(catchRect.x, catchRect.y + tileHeight, catchRect.getWidth(), catchRect.getHeight() - tileHeight);
			lvl1catchRect.setRect(catchRect);
		}
	}

	public boolean addItem(int level, UIBarItem newItem){
		
		if(level == 1 && lvl1items.size() <= MAXITEMS ){
			lvl1items.add(newItem);
			itemCount1++;
			
		} else if(level == 2 && lvl2items.size() <= MAXITEMS ){
			lvl2items.add(newItem);
			itemCount2++;
			
			//if just added a second level, check if the bar needs to be extended upward
			if(itemCount2==1 && 2*UIBarItem.height + 2*EDGESIZEY + MINGAPY > tileCountY * tileHeight){
					this.extendup();
			}	
		} else {
			return false;
		}

		//extends the bar to the right to fit in more items if necessary
		if (Math.max(itemCount1, itemCount2) * UIBarItem.width + 2 * EDGESIZEX + 
				Math.max(Math.max(itemCount1, itemCount2)-1,0) * MINGAPX > tileCountX * tileWidth){
			this.extendright();
		} 	
		this.update();
		return true;
	}
	
	public boolean removeItem(int level, UIBarItem item){
		if(level == 1 && lvl1items.contains(item)){
			lvl1items.remove(item);
			itemCount1--;
		} else if(level == 2 && lvl2items.contains(item)){
			lvl2items.remove(item);
			itemCount2--;
			
			//shrinks the bar downward if just removed the last item from the second level
			if(itemCount2==0 && UIBarItem.height + 2*EDGESIZEY < (tileCountY-1) * tileHeight) this.collapsedown();
			
		} else {
			return false;
		}

		//shrinks the bar left if all items do not need the space anymore
		if (Math.max(itemCount1, itemCount2) * UIBarItem.width + 2 * EDGESIZEX + 
				Math.max(Math.max(itemCount1, itemCount2)-1,0) * MINGAPX < (tileCountX-1) * tileWidth){
			this.collapseleft();
		}
		this.update();
		return true;
	}
	

	public UIBarItem inCatchZone(Rectangle itemRect){
		boolean added = false;
		UIBarItem item = null;
		
		if(lvl1catchRect.intersects(itemRect)){
			
			// first checks if there is an empty item on level1
			item = findEmptyItem(1);
			if (item != null) return item;
			
			// then try adding the item to the bar
			UIBarItem newItem = new UIBarItem(this);
			added = this.addItem(1, newItem);
			
			// if item can be added, set item to new value
			item = added ? newItem : null;
			return item;
			
		} else if (catchRect.intersects(itemRect)){
			
			// first checks if there is an empty item on level1
			item = findEmptyItem(2);
			if (item != null) return item;			
			
			// then try adding the item to the bar			
			UIBarItem newItem = new UIBarItem(this);
			added = this.addItem(2, newItem);
			
			// if item can be added, set item to new value			
			item = added ? newItem : null;
			return item;
		}
		
		return item;

	}
	
	public UIBarItem inBarItem(int absX, int absY){
		boolean added = false;
		UIBarItem newItem=null;
		
		// first checks if the coordinates correspond to a level 1 item
		for (Iterator<UIBarItem> iterator = lvl1items.iterator(); iterator.hasNext();) {
			UIBarItem lvl1item = iterator.next();
			
			if (lvl1item.catchRect.contains(absX, absY)){
				
				// if the container is empty, returns the container to be filled
				if (lvl1item.isEmpty()){
					return lvl1item;				
				} else {
					
					// else, first checks if there is an empty container
					newItem = findEmptyItem(1);
					if (newItem != null) return newItem;
					
					// if no empty items, creates a new one
					newItem = new UIBarItem(this);
					added = this.addItem(1, newItem);
					newItem = added ? newItem : null;
					return newItem;
				}
			}
		}
		
		// then checks if the coordinates correspond to a level 2 item
		for (Iterator<UIBarItem> iterator = lvl2items.iterator(); iterator.hasNext();) {
			UIBarItem lvl2item = iterator.next();
			
			if (lvl2item.catchRect.contains(absX, absY)){
			
				// if the container is empty, returns the container to be filled
				if (lvl2item.isEmpty()){
					return lvl2item;
				} else {
					
					// else, first checks if there is an empty container
					newItem = findEmptyItem(2);
					if (newItem != null) return newItem;					
					
					// if no empty items, creates a new one					
					newItem = new UIBarItem(this);
					added = this.addItem(2, newItem);
					newItem = added ? newItem : null;
					return newItem;
				}
			}
		}
		
		return newItem;
	}
	
	
	private UIBarItem findEmptyItem (int level){
		if (level ==1 && !lvl1items.isEmpty()){
			for (Iterator<UIBarItem> iterator = lvl1items.iterator(); iterator.hasNext();) {
				UIBarItem lvl1item = iterator.next();
				if(lvl1item.isEmpty()) return lvl1item;
			}
		} else if( level ==2 && !lvl2items.isEmpty()) {
			for (Iterator<UIBarItem> iterator = lvl2items.iterator(); iterator.hasNext();) {
				UIBarItem lvl2item = iterator.next();
				if(lvl2item.isEmpty()) return lvl2item;
			}
		} 				
		return null;
	}
	
	//temporary method to manually add an item
	public void pushLvl(int level){
		if (level !=1 && level !=2) return;
		this.addItem(level, new UIBarItem(this));
	}
	
	//temporary method to manually remove an item
	public void pullLvl(int level){
		if (level !=1 && level !=2) return;
		if(level == 1 && !lvl1items.isEmpty()) this.removeItem(1, lvl1items.get(lvl1items.size()-1));
		if(level == 2 && !lvl2items.isEmpty()) this.removeItem(2, lvl2items.get(lvl2items.size()-1));
	}
}
