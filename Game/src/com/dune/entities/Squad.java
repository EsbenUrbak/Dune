package com.dune.entities;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.baseframework.screen.PlayScreen;
import com.dune.planet.Tile;

public class Squad {

	private float topX = 300f;
	private float topY = 2800f;
	private float distX = 0f;
	private float distY = 0f;
	private float diagonalDist=0f;
	boolean lastStep;
	
	private int minX = 0;
	private int minY = 0;
	private int maxX = 100;
	private int maxY = 100;

	private float speedX = 0f;
	private float speedY = 0f;
	private int speedTile = 200;
	
	public static Rectangle rect = new Rectangle(0, 0, 0, 0);
	//squad imagine size
	public int xImagine =78;
	public int yImagine =80;
	
	public List<Integer> pathXPoints = new ArrayList<Integer>();
	public List<Integer> pathYPoints = new ArrayList<Integer>();
	public List<Integer> activeList = new ArrayList<Integer>();

	
	public void setBounds(int boundX, int boundY){
		maxX = boundX * Tile.getSizeX() - 1 - xImagine;
		maxY = boundY * Tile.getSizeY() - 1 - yImagine;		
	}
	
	public void update(float delta) {
		
		pathXPoints=PlayScreen.getPathXPoints();
		pathYPoints=PlayScreen.getPathYPoints();
		activeList=PlayScreen.getActiveList();
		
		if(pathXPoints.size()>0){
			// local variables to ensure that the squad does not get out of the map
			float pathX, pathY;
			
			pathX = (float) Math.min(Math.max(pathXPoints.get(0),minX + xImagine/2),maxX + xImagine/2);
			pathY = (float) Math.min(Math.max(pathYPoints.get(0),minY + yImagine/2),maxY + yImagine/2);
			
			//finding the absolute distance between the new point and the center of squad
			distX = pathX -(topX+ (float) xImagine/2);
			distY = pathY -(topY+ (float) yImagine/2);
			
			//finding the diagonal distance between the two points
			diagonalDist=(float) Math.sqrt(Math.pow(distX,2)+Math.pow(distY,2));
			

						//Now find the speed in each direction -> Notice i have added a speedtile which can represent the max speed on a specific tile
						speedX=(distX * (float) speedTile/diagonalDist);
						speedY=(distY * (float) speedTile/diagonalDist);
						//Have to use a rounding function as otherwise when the squad gets very close to the point the speed blows up to infinite.

						
						//Check whether it is the last step, if so sets the squad to that precise point and sets the speeds to 0
						if(Math.abs(pathX-(topX+(float)xImagine/2f))<Math.abs(speedX) * delta || Math.abs(pathY-(topY+(float) yImagine/2f))<Math.abs(speedY)*delta){
							topX= pathX - (float)xImagine/2f;
							topY= pathY - (float)yImagine/2f;
							speedX=0f;
							speedY=0f;
							pathXPoints.remove(0);
							pathYPoints.remove(0);
							
						// otherwise updates the squad position using the speeds and safeguards against infinite values using round)	
						} else {
							speedX=(float) Math.round(speedX);
							speedY=(float) Math.round(speedY);
							topX += speedX * delta;
							topY += speedY * delta;
						}
												
		}else{
			speedX=0f;	
			speedY=0f;
		}
		
		rect.setRect((int)topX, (int)topY,xImagine, yImagine);
		
	}

	public void stop() {
		speedX = 0;
	}

	public float getTopX() {
		return topX;
	}

	public float getTopY() {
		return topY;
	}


	public float getSpeedX() {
		return speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setTopX(float topX) {
		this.topX = topX;
	}

	public void setTopY(float topY) {
		this.topY = topY;
	}


	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	public int getxImagine() {
		return xImagine;
	}

	public void setxImagine(int xImagine) {
		this.xImagine = xImagine;
	}

	public int getyImagine() {
		return yImagine;
	}

	public void setyImagine(int yImagine) {
		this.yImagine = yImagine;
	}


		

}