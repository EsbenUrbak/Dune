package com.baseframework.util;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public class UIDragItem extends UIDragImage {

	protected ArrayList<Rectangle> dropoffZones;
	protected ArrayList <Integer> dropXs, dropYs;
	private float targetX, targetY;
	private float speedX=0f, speedY=0f;
	private boolean moving=false;
	
	public UIDragItem(int topX, int topY, int scopeMaxX, int scopeMaxY, Image dragImage) {
		super(topX, topY, scopeMaxX, scopeMaxY, dragImage);
		dropoffZones = new ArrayList <Rectangle>();
		targetX = (float) topX;
		targetY = (float) topY;
	}
	
	public void addDropoffZone(Rectangle newzone, int dropX, int dropY){
		dropoffZones.add(newzone);
		dropXs.add(dropX);
		dropYs.add(dropY);
	}
	
	public void resetDropoffZones(){
		dropoffZones.clear();
	}
	
	@Override
	public void onReleased(int x, int y){
		super.onReleased(x,  y);
		if(dropoffZones.isEmpty()){
		} else {
			for(int i=0; i<dropoffZones.size(); i++){
				if(dropoffZones.get(i).contains(x,y)){
					targetX = (float) dropXs.get(i);
					targetY = (float) dropYs.get(i);
					break;
				}
			}
		}
		moving = true;	
	}
	

}
