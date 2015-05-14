package gameEngine;

import java.awt.Graphics;

import planet.Surface;

public class Squad {


	private int centerX = 100;
	private int centerY = 100;

	private int speedX = 0;
	private int speedY = 0;
	
	private Surface surface = StartingClass.getPlanetSurface();

	public void update() {

		speedX=surface.getSpeedX();
		speedY=surface.getSpeedY();
		centerX+=speedX;
		centerY+=speedY;
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


}