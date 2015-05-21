package planet;
import gameEngine.StartingClass;

import java.applet.Applet;
import java.awt.Image;
import java.awt.Rectangle;
import java.net.URL;


public class Tile {
	//Creating variables: 
	// TileX  = X pixel position  ; TileY = Y pixel position ; speedY = speed in x direction; speedY speed in y direction
	//Type = Type of landscape; SizeX = size of tile; sizeY = size of tile
	private int tileX, tileY, speedX, speedY, type;
	private static int sizeX=40, sizeY=40;
	public Image tileImage;
	private Rectangle r;
	private URL base;
	
	public static Image tileocean, tiledirt;
	
	//private ViewFrame viewframe = StartingClass.getViewFrame();
	
	
	//Loading the tile imagine from data base of landscapes
	public Tile(int x, int y, int typeInt) {
		
		
		tileX = x * sizeX;
		tileY = y * sizeY;

		type = typeInt;

		r = new Rectangle();
		r.setBounds(tileX, tileY, sizeX, sizeY);

		if (type == 1) {
			tileImage = StartingClass.tiledirt;
		} else if (type == 2) {
			tileImage = StartingClass.tileocean;
		} else {
			type = 0;
		}

	}
	
	
	public void update() {
		// absolute coordinates - no need to update the tile movement anymore
		
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

	public static int getSizeX() {
		return sizeX;
	}

	public static int getSizeY() {
		return sizeY;
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
