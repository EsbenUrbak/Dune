package com.dune.planet;

import java.awt.Image;
import java.awt.Rectangle;

import com.baseframework.game.main.Resources;



public class Tile {

	private int tileX, tileY;
	String type,S0,S1,S2,S3,S4,S5,S6,S7,S8,S9;
	private static int sizeX=40, sizeY=40;
	public Image tileImage;
	private Rectangle r;
	
	public static Image tileocean, tiledirt;
		
	//Loading the tile imagine from data base of landscapes
	public Tile(int x, int y, String S0,String S1,String S2,String S3,String S4,String S5,String S6,String S7,String S8,String S9) {
		
		
		tileX = x * sizeX;
		tileY = y * sizeY;

		type = S0;

		r = new Rectangle();
		r.setBounds(tileX, tileY, sizeX, sizeY);

		if (type.equals("G")) {
			tileImage = Resources.G;
		} else if (type.equals("W")) {
			tileImage = Resources.W;
		} else {

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


	public String getType() {
		return type;
	}

	public void setType(String type) {
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
