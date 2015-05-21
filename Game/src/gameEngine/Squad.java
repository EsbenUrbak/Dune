package gameEngine;

import java.awt.Rectangle;

import planet.ViewFrame;

public class Squad {


	private int centerX = 100;
	private int centerY = 100;

	private int speedX = 0;
	private int speedY = 0;
	
	public static Rectangle rect = new Rectangle(0, 0, 0, 0);
	//squad imagine size
	public int xImagine =77;
	public int yImagine =79;
	
	//private ViewFrame surface = StartingClass.getViewFrame();

	public void update() {

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


	public int getSpeedX() {
		return speedX;
	}

	public int getSpeedY() {
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