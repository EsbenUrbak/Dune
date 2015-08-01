package com.baseframework.UI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Comparator;

import com.baseframework.game.main.Resources;
import com.baseframework.screen.PlayScreen;

public class UIBar extends UIObject {
	
	public static final int typePriority = 16;
	
	private static final int MINTILEX =2, MAXTILEX=10, MINTILEY =2, MAXTILEY=5;
	private static final int MAXITEMS =5;
	public static final int EDGESIZEX=20, EDGESIZEY=10, MINGAPX=5, MINGAPY=5;
	
	//unused now because of compatibility issue with Java 7
    public static Comparator<UIBarSlot> priorityOrder = new Comparator<UIBarSlot>(){
		@Override
		public int compare(UIBarSlot item1, UIBarSlot item2) {
			return Integer.compare(item1.getPriority(), item2.getPriority());
		}
    };
	
	protected boolean selected=false, visible=true;
	protected Rectangle frameRect;
	protected Rectangle catchRect, lvl1catchRect; // if not in lvl1catchRect, then assumes it will be on lvl2
	
	private Image barTileN, barTileS, barTileW, barTileE, barTileNW, barTileSW, barTileNE, barTileSE, barTileIn;
	private int tileCountX, tileCountY, slotCount1=0, slotCount2=0, tileHeight, tileWidth;
	private ArrayList<UIBarSlot> lvl1slots, lvl2slots;
	private ArrayList<UIBarSlot> addedSlots, removedSlots;
	
	protected UIAdvButton refButton = null;
	private UIBarDrag collapseItem = null;
	
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
		
		lvl1slots = new ArrayList<UIBarSlot>();
		lvl2slots = new ArrayList<UIBarSlot>();
		addedSlots = new ArrayList<UIBarSlot>();
		removedSlots = new ArrayList<UIBarSlot>();
	}
	
	public void setButton(UIAdvButton refButton){
		this.refButton = refButton;
		collapseItem = new UIBarDrag(catchRect.x, catchRect.y, Resources.barCollapse, this);
		collapseItem.hide();
	}
	
	
	@Override
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
		if(!lvl2slots.isEmpty()){
			g2.drawLine(frameRect.x + EDGESIZEX, frameRect.y  + frameRect.height/2, 
					(int) frameRect.getMaxX() - EDGESIZEX, frameRect.y  + frameRect.height/2);
		}
	}
	
	
	@Override
	public void update(){
		int itemX; 
		int itemY; 
		
		// update locations of items in level 1
		if(!lvl1slots.isEmpty()){
			itemX = UIBarSlot.width * slotCount1 + MINGAPX * Math.max(slotCount1-1, 0);
			itemX = (frameRect.width - itemX)/2;
			itemY = frameRect.height -EDGESIZEY -UIBarSlot.height;
			
			// sort by order of priority for better display (unused because of compatibility issue)
			//lvl1slots.sort(priorityOrder);
			
			// display from the left in priority order
			for(int i=0; i< lvl1slots.size(); i++){
				lvl1slots.get(i).setInBar(itemX, itemY);
				itemX += UIBarSlot.width + MINGAPX;
			}
		}
		
		// update locations of items in level 2
		if(!lvl2slots.isEmpty()){
			itemX = UIBarSlot.width * slotCount2 + MINGAPX * Math.max(slotCount2-1, 0);
			itemX = (frameRect.width - itemX)/2;
			itemY = EDGESIZEY;
			
			// sort by order of priority for better display (unused because of compatibility issue)
			//lvl2slots.sort(priorityOrder);
			
			// display from the left in priority order
			for(int i=0; i< lvl2slots.size(); i++){
				lvl2slots.get(i).setInBar(itemX, itemY);
				itemX += UIBarSlot.width + MINGAPX;
			}
		}
	}
	
	@Override
	public boolean updateList(ArrayList<UIObject> list){
		boolean hasUpdated = false;
		UIBarSlot slot;
		
		// checks if new bar slots were added to the bar, and if so, add them
		if(!addedSlots.isEmpty()){
			hasUpdated = true;
			for (int i = addedSlots.size()-1; i >= 0; i--) {
				slot = addedSlots.get(i);
				pushItem(slot, list);
				addedSlots.remove(slot);
			}	
		}
		
		// checks if bar slots were removed from the bar
		if(!removedSlots.isEmpty()){
			for (int i = removedSlots.size()-1; i >= 0; i--)  {
				slot = removedSlots.get(i);
				if(list.contains(slot)) {
					list.remove(slot);
					hasUpdated = true;
				}
				removedSlots.remove(slot);
			}	
		}		
		
		// checks if the bar itself was dragged into its collapse zone
		if(collapseItem != null){
			if(collapseItem.toUpdate){
				if(list.contains(collapseItem)){
					list.remove(collapseItem);
					collapseItem.toUpdate = false;
					hasUpdated = true;
				}
				
				if(!list.contains(collapseItem)){
					pushItem(collapseItem, list);
					collapseItem.toUpdate = false;
					hasUpdated = true;
				}
			}
		}
		return hasUpdated;
	}
	
	@Override
	public void show(){
		visible = true;
		catchRect.setSize(tileCountX * tileWidth, tileCountY * tileHeight);
		if(lvl2slots.isEmpty()){
			lvl1catchRect.setBounds(catchRect);
		} else {
			lvl1catchRect.setRect(lvl1catchRect.x, frameRect.y  + frameRect.height/2, 
					lvl1catchRect.width, catchRect.getMaxY() - frameRect.y - frameRect.height/2 );		
		}
		
		//show all bar slots
		for (int i = 0; i< lvl1slots.size(); i++) {
			lvl1slots.get(i).show();
		}
		
		for (int i = 0; i< lvl2slots.size(); i++) {
			lvl2slots.get(i).show();
		}
	}
	
	@Override
	public void hide(){
		visible = false;
		catchRect.setSize(0,0);
		lvl1catchRect.setBounds(catchRect);
		
		//hide all bar slots
		for (int i = 0; i< lvl1slots.size(); i++) {
			lvl1slots.get(i).hide();
		}
		
		for (int i = 0; i< lvl2slots.size(); i++) {
			lvl2slots.get(i).hide();
		}
	}
	
	@Override
	public boolean onPressed(int x, int y){
		boolean interrupt=false;
		
		if(visible && catchRect.contains(x,y)){
			selected = true;
			
			// first checks if an item was selected (level 1)
			for (int i = 0; i< lvl1slots.size(); i++) {
				interrupt = lvl1slots.get(i).catchRect.contains(x,y);
				if(interrupt) return selected;
			}
			
			// in level 2
			for (int i = 0; i< lvl2slots.size(); i++) {
				interrupt = lvl2slots.get(i).catchRect.contains(x,y);
				if (interrupt) return selected;
			}
			
			// else display the collapse item
			if(collapseItem != null){
				if(collapseItem.visible == false){
					collapseItem.show();
					interrupt = collapseItem.onPressed(x, y);
					if(interrupt) return selected;
				}
			}
		} else {
			selected = false;
		}
		return selected;
	}
	
	@Override
	public boolean onReleased(int absX, int absY){
		if(collapseItem != null) collapseItem.onReleased(absX, absY);
		if(!catchRect.contains(absX, absY)) selected = false;
		return selected && visible;
	}
	

	@Override
	public boolean onDragged(int absX, int absY) {
		return false;
	}

	
	@Override
	public void performAction() {
		// bar is not a button, no action performed
	}
	
	@Override
	public void cancel() {
		selected = false;
	}
	
	//@Override
	public int getTypePriority() {
		return typePriority;
	}
	
	public void switchDisplay(){
		if (visible){
			hide();
		} else {
			show();
		}
	}
	
	public void invoke(){
	}
	
	public void vanish(){
	}
	
	private void extendright(){
		if (tileCountX < MAXTILEX){
			tileCountX++;
			frameRect.setSize(frameRect.width + tileWidth, frameRect.height);
			catchRect.setSize(catchRect.width + tileWidth, catchRect.height);
			lvl1catchRect.setSize(lvl1catchRect.width + tileWidth, lvl1catchRect.height);
		}
	}
	
	private void collapseleft(){
		if (tileCountX > MINTILEX){
			tileCountX--;
			frameRect.setSize(frameRect.width - tileWidth, frameRect.height);
			catchRect.setSize(catchRect.width - tileWidth, catchRect.height);
			lvl1catchRect.setSize(lvl1catchRect.width - tileWidth, lvl1catchRect.height);
		}
	}
	
	private void extendup(){
		if (tileCountY < MAXTILEY){
			tileCountY++;
			frameRect.setRect(frameRect.x, frameRect.y - tileHeight, frameRect.width, frameRect.height + tileHeight);
			catchRect.setRect(catchRect.x, catchRect.y - tileHeight, catchRect.width, catchRect.height + tileHeight);
			lvl1catchRect.setRect(lvl1catchRect.x, frameRect.y  + frameRect.height/2, 
					lvl1catchRect.width, catchRect.getMaxY() - frameRect.y - frameRect.height/2 );
		}
	}
	
	private void collapsedown(){
		if (tileCountY > MINTILEY){
			tileCountY--;
			frameRect.setRect(frameRect.x, frameRect.y + tileHeight, frameRect.getWidth(), frameRect.getHeight() - tileHeight);
			catchRect.setRect(catchRect.x, catchRect.y + tileHeight, catchRect.getWidth(), catchRect.getHeight() - tileHeight);
			lvl1catchRect.setRect(catchRect);
		}
	}

	public boolean addSlot(int level, UIBarSlot newItem){
		if(level == 1 && lvl1slots.size() <= MAXITEMS ){
			insertSlot(newItem,lvl1slots);				
			slotCount1++;
		
		} else if(level == 2 && lvl2slots.size() <= MAXITEMS ){
			insertSlot(newItem,lvl2slots);	
			slotCount2++;
			
			//if just added a second level, check if the bar needs to be extended upward
			if(slotCount2==1 && 2*UIBarSlot.height + 2*EDGESIZEY + MINGAPY > tileCountY * tileHeight){
					this.extendup();
			}	
		} else {
			return false;
		}

		//extends the bar to the right to fit in more items if necessary
		if (Math.max(slotCount1, slotCount2) * UIBarSlot.width + 2 * EDGESIZEX + 
				Math.max(Math.max(slotCount1, slotCount2)-1,0) * MINGAPX > tileCountX * tileWidth){
			this.extendright();
		}
		
        addedSlots.add(newItem);
		return true;
	}
	
	public boolean removeSlot(int level, UIBarSlot item){
		if(level == 1 && lvl1slots.contains(item)){
			lvl1slots.remove(item);
			slotCount1--;
		} else if(level == 2 && lvl2slots.contains(item)){
			lvl2slots.remove(item);
			slotCount2--;
			
			//shrinks the bar downward if just removed the last item from the second level
			if(slotCount2==0 && UIBarSlot.height + 2*EDGESIZEY < (tileCountY-1) * tileHeight) this.collapsedown();
			
		} else {
			return false;
		}

		//shrinks the bar left if all items do not need the space anymore
		if (Math.max(slotCount1, slotCount2) * UIBarSlot.width + 2 * EDGESIZEX + 
				Math.max(Math.max(slotCount1, slotCount2)-1,0) * MINGAPX < (tileCountX-1) * tileWidth){
			this.collapseleft();
		}
        removedSlots.add(item);
		return true;
	}
	

	public UIBarSlot inCatchZone(UIDragItem item){
		boolean added = false;
		UIBarSlot slot = null;
		
		if(lvl1catchRect.intersects(item.rPos)){
			
			// first checks if there is an empty item on level1
			slot = findEmptySlot(1);
			if (slot != null) {
				slot.setItem(item);
				reOrganize(slot);
				return slot;
			}
			
			// then try adding the item to the bar
			slot = new UIBarSlot(this);
			slot.setItem(item);
			added = this.addSlot(1, slot);
			
			// if item can be added, set item to new value
			slot = added ? slot : null;
			return slot;
			
		} else if (catchRect.intersects(item.rPos)){
			
			// first checks if there is an empty item on level1
			slot = findEmptySlot(2);
			if (slot != null) {
				slot.setItem(item);
				reOrganize(slot);
				return slot;			
			}
			
			// then try adding the item to the bar			
			slot = new UIBarSlot(this);
			slot.setItem(item);
			added = this.addSlot(2, slot);
			
			// if item can be added, set item to new value			
			slot = added ? slot : null;
			return slot;
		}
		
		return slot;

	}
	
	public UIBarSlot inBarItem(int absX, int absY, UIDragItem item){
		boolean added = false;
		UIBarSlot slot=null;
		
		// first checks if the coordinates correspond to a level 1 item
		for (int i = 0; i< lvl1slots.size(); i++) {
			slot = lvl1slots.get(i);
			
			if (slot.catchRect.contains(absX, absY)){
				
				// if the container is empty, returns the container to be filled
				if (slot.isEmpty()){
					slot.setItem(item);
					reOrganize(slot);
					return slot;				
				} else {
					
					// else, first checks if there is an empty container
					slot = findEmptySlot(1);
					if (slot != null) {
						slot.setItem(item);
						reOrganize(slot);
						return slot;
					}
					
					// if no empty items, creates a new one
					slot = new UIBarSlot(this);
					slot.setItem(item);
					added = this.addSlot(1, slot);
					slot = added ? slot : null;
					return slot;
				}
			}
		}
		
		// then checks if the coordinates correspond to a level 2 item
		for (int i = 0; i< lvl2slots.size(); i++) {
			
			if (lvl2slots.get(i).catchRect.contains(absX, absY)){
				slot = lvl2slots.get(i);			
				// if the container is empty, returns the container to be filled
				if (slot.isEmpty()){
					slot.setItem(item);
					reOrganize(slot);
					return slot;
				} else {
					
					// else, first checks if there is an empty container
					slot = findEmptySlot(2);
					if (slot != null) {
						slot.setItem(item);
						reOrganize(slot);
						return slot;					
					}
					
					// if no empty items, creates a new one					
					slot = new UIBarSlot(this);
					slot.setItem(item);
					added = this.addSlot(2, slot);
					slot = added ? slot : null;
					return slot;
				}
			}
		}
		
		return null;
	}
	
	public boolean inBarCollapseZone(Rectangle itemRect){
		if(refButton != null){
			if(refButton.hideBarRect.intersects(itemRect)){
				this.hide();
				return true;
			}
		}
		return false;
	}
	
	
	private UIBarSlot findEmptySlot (int level){
		if (level ==1 && !lvl1slots.isEmpty()){
			for (int i = 0; i< lvl1slots.size(); i++) {
				if(lvl1slots.get(i).isEmpty()) return lvl1slots.get(i);
			}
		} else if( level ==2 && !lvl2slots.isEmpty()) {
			for (int i = 0; i< lvl2slots.size(); i++) {
				if(lvl2slots.get(i).isEmpty()) return lvl2slots.get(i);
			}
		} 				
		return null;
	}
	
	private void insertSlot(UIBarSlot slot,  ArrayList<UIBarSlot> refArray){
		if(refArray.isEmpty()) {
			refArray.add(slot);
			return;
		}
		
		int index=0; 
		int priority = slot.getPriority();
		
		while (index<refArray.size()){
			if(refArray.get(index).getPriority()<= priority) {index++;} 
			else {break;}
		}
		
		refArray.add(index, slot);
	}
	
	protected void reOrganize(UIBarSlot slot){
		if(lvl1slots.contains(slot)){
			lvl1slots.remove(slot);
			insertSlot(slot, lvl1slots);
		} else if (lvl2slots.contains(slot)){
			lvl2slots.remove(slot);
			insertSlot(slot, lvl2slots);			
		}
		
	}
	
	//temporary method to manually add an item
	public void pushLvl(int level){
		if (level !=1 && level !=2) return;
		this.addSlot(level, new UIBarSlot(this));
		this.update();
	}
	
	//temporary method to manually remove an item (only when the item is empty)
	public void pullLvl(int level){
		UIBarSlot emptySlot = null;
		
		if (level !=1 && level !=2) return;
		
		if(level == 1 && !lvl1slots.isEmpty()) {
			emptySlot = findEmptySlot(1);
			if (emptySlot != null) this.removeSlot(1, emptySlot);
		}
		if(level == 2 && !lvl2slots.isEmpty()) {
			emptySlot = findEmptySlot(2);
			if (emptySlot != null) this.removeSlot(2, emptySlot);
		}
		this.update();
	}




}
