package planet;

public class ViewFrame {

	private int frameX, frameY, speedX, speedY, sizeX, sizeY, boundX, boundY;

	public ViewFrame(int x, int y, int sx, int sy) {
		frameX = x;
		frameY = y;
		sizeX = sx;
		sizeY = sy;
		speedX = 0;
		speedY = 0;
		boundX = 100;
		boundY = 100;
	}

	public void update() {
		//makes sure the view frame does not get out of the map 
		if (frameX + speedX <= 0 && speedX < 0) {
			frameX = 0;
			speedX = 0;
		} else if (frameX + speedX >= boundX && speedX > 0){
			speedX = 0;
			frameX = boundX;
		}  else {
			frameX += speedX;
		}
		
		if (frameY + speedY <= 0 && speedY < 0) {
			speedY = 0;
			frameY = 0;
		} else if (frameY + speedY >= boundY && speedY > 0) {
			speedY = 0;
			frameY = boundY;
		} else {
			frameY += speedY;
		}

	}

	public int getFrameX() {
		return frameX;
	}

	public void setFrameX(int frameX) {
		this.frameX = frameX;
	}

	public int getFrameY() {
		return frameY;
	}

	public void setFrameY(int frameY) {
		this.frameY = frameY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
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

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

}
