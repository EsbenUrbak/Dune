package com.dune.planet;

public class ViewFrame {

	private float frameX, frameY, speedX, speedY;
	private int sizeX, sizeY, boundX, boundY;

	public static final int SCROLLSPEED = 400;
	
	public ViewFrame(float x, float y, int sx, int sy) {
		frameX = x;
		frameY = y;
		sizeX = sx;
		sizeY = sy;
		speedX = 0f;
		speedY = 0f;
		boundX = 100;
		boundY = 100;
	}

	public void update(float delta) {
		//makes sure the view frame does not get out of the map 

		if (frameX + speedX * delta <= 0f && speedX < 0f) {
			frameX = 0f;
			speedX = 0f;
		} else if (frameX + speedX * delta >= (float) boundX && speedX > 0f){
			speedX = 0f;
			frameX = (float) boundX;
		}  else {
			frameX += speedX * delta;
		}
		
		if (frameY + speedY*delta <= 0f && speedY < 0f) {
			speedY = 0f;
			frameY = 0f;
		} else if (frameY + speedY*delta >= (float) boundY && speedY > 0f) {
			speedY = 0f;
			frameY = (float) boundY;
		} else {
			frameY += speedY * delta;
		}
		


	}

	public float getFrameX() {
		return frameX;
	}

	public void setFrameX(float frameX) {
		this.frameX = frameX;
	}

	public float getFrameY() {
		return frameY;
	}

	public void setFrameY(float frameY) {
		this.frameY = frameY;
	}

	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}
	
	// adjust the maximum x, y position that the screen may get based on its size
	public void setBoundX(int boundX) {
		this.boundX = boundX - sizeX - 1;
	}

	public void setBoundY(int boundY) {
		this.boundY = boundY - sizeY - 1;
	}

	public int getBoundX() {
		return boundX;
	}

	public int getBoundY() {
		return boundY;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeX(int sizeX) {
		this.boundX += this.sizeX - sizeX;
		this.sizeX = sizeX;
	}

	public void setSizeY(int sizeY) {
		this.boundY += this.sizeY - sizeY;
		this.sizeY = sizeY;
	}

	public void scrollUp(){
		this.speedY = -SCROLLSPEED;
	}
	
	public void scrollDown(){
		this.speedY = SCROLLSPEED;
	}
	
	public void scrollLeft(){
		this.speedX = -SCROLLSPEED;
	}
	
	public void scrollRight(){
		this.speedX = SCROLLSPEED;
	}

}
