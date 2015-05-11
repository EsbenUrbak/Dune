package planet;
import gameEngine.StartingClass;

import java.awt.Image;
import java.awt.Rectangle;


public class Tile {
	//Creating variables: 
	// TileX  = X pixel position  ; TileY = Y pixel position ; speedY = speed in x direction; speedY speed in y direction
	//Type = Type of landscape; SizeX = size of tile; sizeY = size of tile
	private int tileX, tileY, speedX, speedY, type, sizeX=40, sizeY=40;
	public Image tileImage;
	private Rectangle r;
	
	private Background bg = StartingClass.getBg1();
	
	//Loading the tile imagine from data base of landscapes
	public Tile(int x, int y, int typeInt) {
		tileX = x * sizeX;
		tileY = y * sizeY;

		type = typeInt;

		r = new Rectangle();

		if (type == 5) {
			tileImage = StartingClass.tiledirt;
		} else if (type == 8) {
			tileImage = StartingClass.tileocean;
		} else {
			type = 0;
		}

	}
	
	
	public void update() {
		speedX = bg.getSpeedX() * 5;
		tileX += speedX;

		r.setBounds(tileX, tileY, 40, 40);
		
	}
	
	
	public int getTileX() {
		return tileX;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public Image getTileImage() {
		return tileImage;
	}

	public void setTileImage(Image tileImage) {
		this.tileImage = tileImage;
	}

	public Rectangle getR() {
		return r;
	}

	public void setR(Rectangle r) {
		this.r = r;
	}

	
	
}
