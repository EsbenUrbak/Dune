package planet;

public class Surface {

	private int surfaceX, surfaceY, speedX, speedY;

	public Surface(int x, int y) {
		surfaceX = x;
		surfaceY = y;
		speedX = 0;
		speedY = 0;
	}

	public void update() {
		//bounding the map 
		if (surfaceX == 0 && speedX < 0) {
			surfaceX = 0;
		} else {
			surfaceX += speedX;
		}
		
		if (surfaceY == 0 && speedY > 0) {
			surfaceY = 0;
		} else {
			surfaceY += speedY;
		}

		surfaceY += speedY;
		
		if (surfaceX <= -2160) {
			surfaceX += 4320;
		}
		if (surfaceY <= -2160) {
			surfaceY += 4320;
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

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

}
