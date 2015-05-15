package planet;

public class Surface {

	private int surfaceX, surfaceY, speedX, speedY, boundX, boundY;

	public Surface(int x, int y) {
		surfaceX = x;
		surfaceY = y;
		speedX = 0;
		speedY = 0;
		boundX = 100;
		boundY = 100;
	}

	public void update() {
		//bounding the map 
		if (surfaceX >= 0 && speedX > 0) {
			speedX = 0;
		} else if (surfaceX <= boundX && speedX < 0){
			speedX = 0;
			surfaceX = boundX;
		}  else {
			surfaceX += speedX;
		}
		
		if (surfaceY == 0 && speedY > 0) {
			speedY = 0;
		} else if (surfaceY <= boundY && speedY < 0) {
			speedY = 0;
			surfaceY = boundY;
		} else {
			surfaceY += speedY;
		}

	}

	public int getSurfaceX() {
		return surfaceX;
	}

	public void setSurfaceX(int surfaceX) {
		this.surfaceX = surfaceX;
	}

	public int getSurfaceY() {
		return surfaceY;
	}

	public void setSurfaceY(int surfaceY) {
		this.surfaceY = surfaceY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	
	public void setBoundX(int boundX) {
		this.boundX = boundX;
	}

	public void setBoundY(int boundY) {
		this.boundY = boundY;
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
