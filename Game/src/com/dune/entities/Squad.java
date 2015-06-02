package com.dune.entities;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.baseframework.screen.PlayScreen;

public class Squad {

	private float centerX = 300f;
	private float centerY = 200f;
	private float distX = 0f;
	private float distY = 0f;
	private float diagonalDist=0f;
	boolean lastStep;

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
	
	//private ViewFrame surface = StartingClass.getViewFrame();

	public void update(float delta) {
		
		pathXPoints=PlayScreen.getPathXPoints();
		pathYPoints=PlayScreen.getPathYPoints();
		activeList=PlayScreen.getActiveList();
		
		if(pathXPoints.size()>0){
			//finding the absolute distance between the new point and the center of squad
			distX = (float) pathXPoints.get(0)-(centerX+ (float) xImagine/2);
			distY = (float) pathYPoints.get(0)-(centerY+ (float)yImagine/2);
			//finding the diagonal distance between the two points
			diagonalDist=(float) Math.sqrt(Math.pow(distX,2)+Math.pow(distY,2));
			

						//Now find the speed in each direction -> Notice i have added a speedtile which can represent the max speed on a specific tile
						speedX=(distX * (float) speedTile/diagonalDist);
						speedY=(distY * (float) speedTile/diagonalDist);
						//Have to use a rounding function as otherwise when the squad gets very close to the point the speed blows up to infinite.

						
						//Check whether it is the last step, if so sets the squad to that precise point and sets the speeds to 0
						if(Math.abs(pathXPoints.get(0)-(centerX+xImagine/2))<Math.abs(speedX) * delta||Math.abs(pathYPoints.get(0)-(centerY+yImagine/2))<Math.abs(speedY)*delta){
							centerX= (float) pathXPoints.get(0) - (float)xImagine/2f;
							centerY= (float) pathYPoints.get(0) - (float)yImagine/2f;
							speedX=0f;
							speedY=0f;
						// otherwise updates the squad position using the speeds and safeguards against infinite values using round)	
						} else {
							speedX=(float) Math.round(speedX);
							speedY=(float) Math.round(speedY);
							centerX += speedX * delta;
							centerY += speedY * delta;
						}
						
						//if last step on the path we can remove that point in the list.
						if(centerX == (float) pathXPoints.get(0) - (float)xImagine/2f && centerY == (float) pathYPoints.get(0) - (float)yImagine/2f){
							pathXPoints.remove(0);
							pathYPoints.remove(0);
						}
												
		}else{
			speedX=0f;	
			speedY=0f;
		}

		rect.setRect((int)centerX, (int)centerY,xImagine, yImagine);
		
	}

	public void stop() {
		speedX = 0;
	}

	public float getCenterX() {
		return centerX;
	}

	public float getCenterY() {
		return centerY;
	}


	public float getSpeedX() {
		return speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setCenterX(float centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(float centerY) {
		this.centerY = centerY;
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