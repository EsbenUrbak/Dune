package gameEngine;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import planet.ViewFrame;

public class Squad {

	private int centerX = 300;
	private int centerY = 200;
	private int pointX = 0;
	private int pointY = 0;
	private double distX = 0;
	private double distY = 0;
	private double diagonalDist=0;
	private double diagonalDistLast=0;
	boolean lastStep;

	private double speedX = 0;
	private double speedY = 0;
	private int speedTile = 4;
	
	public static Rectangle rect = new Rectangle(0, 0, 0, 0);
	//squad imagine size
	public int xImagine =78;
	public int yImagine =80;
	
	public List<Integer> pathXPoints = new ArrayList<Integer>();
	public List<Integer> pathYPoints = new ArrayList<Integer>();
	public List<Integer> activeList = new ArrayList<Integer>();
	
	//private ViewFrame surface = StartingClass.getViewFrame();

	public void update() {
		
		pathXPoints=StartingClass.getPathXPoints();
		pathYPoints=StartingClass.getPathYPoints();
		activeList=StartingClass.getActiveList();
		
		System.out.println(pathXPoints.size());
		if(pathXPoints.size()>0){
			//finding the absolute distance between the new point and the center of squad
			distX = pathXPoints.get(0)-(centerX+xImagine/2);
			distY = pathYPoints.get(0)-(centerY+yImagine/2);
			//finding the diagonal distance between the two points
			diagonalDist=Math.sqrt(Math.pow(distX,2)+Math.pow(distY,2));
			

						//Now find the speed in each direction -> Notice i have added a speedtile which can represent the max speed on a specific tile
						speedX=(distX*speedTile/diagonalDist);
						speedY=(distY*speedTile/diagonalDist);
						//Have to use a rounding function as otherwise when the squad gets very close to the point the speed blows up to infinite.
						speedX=Math.round(speedX);
						speedY=Math.round(speedY);
						
						//Check whether last step if then change speed to make the squad move to that precise point
						if(Math.abs(pathXPoints.get(0)-(centerX+xImagine/2))<Math.abs(speedX)||Math.abs(pathYPoints.get(0)-(centerY+yImagine/2))<Math.abs(speedY)){
							speedX=pathXPoints.get(0)-(centerX+xImagine/2);
							speedY=pathYPoints.get(0)-(centerY+yImagine/2);
							speedX=Math.round(speedX);
							speedY=Math.round(speedY);
						}

						
						//if last step on the path we can remove that point in the list.
						if(diagonalDistLast==diagonalDist){
							pathXPoints.remove(0);
							pathYPoints.remove(0);
						}
						
						diagonalDistLast=diagonalDist;

												
		}else{
			speedX=0;	
			speedY=0;
		}

		
		centerX+=speedX;
		centerY+=speedY;
	
		
		rect.setRect(centerX, centerY,xImagine, yImagine);
		
	}

	public void moveRight() {
		speedX = 6;
	}

	public void moveLeft() {
		speedX = -6;
	}

	public void stop() {
		speedX = 0;
	}





	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}


	public double getSpeedX() {
		return speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}


	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(int speedY) {
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