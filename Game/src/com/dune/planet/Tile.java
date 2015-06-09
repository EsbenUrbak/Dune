package com.dune.planet;

import java.awt.Image;
import java.awt.Rectangle;

import com.baseframework.game.main.Resources;



public class Tile {

	private int tileX, tileY, type;
	private static int sizeX=40, sizeY=40;
	public Image tileImage;
	private Rectangle r;
	
	public static Image tileocean, tiledirt;
		
	//Loading the tile imagine from data base of landscapes
	public Tile(int x, int y, int typeInt) {
		
		
		tileX = x * sizeX;
		tileY = y * sizeY;

		type = typeInt;

		r = new Rectangle();
		r.setBounds(tileX, tileY, sizeX, sizeY);

		if (type == 1) {
			tileImage = Resources.tileDirt;
		} else if (type == 2) {
			tileImage = Resources.tileOcean;
		} else {
			type = 0;
		}

	}
	
	
	public void update(float delta) {
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
