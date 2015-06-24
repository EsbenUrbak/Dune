package com.dune.planet;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.baseframework.game.main.Resources;



public class Tile {

	private int tileX, tileY;
	int type;
	String S0,S1,S2,S3,S4,S5,S6,S7,S8,S9;
	private static int sizeX=45, sizeY=45;
	public Image tileImage;
	private Rectangle r;
	BufferedImage CombinedTileImage;
	
	public static Image tileocean, tiledirt;
		
	//Loading the tile imagine from data base of landscapes
	public Tile(int x, int y, String ID) {
		
		
		tileX = x * sizeX;
		tileY = y * sizeY;

		type = Resources.W.getType();

		r = new Rectangle();
		r.setBounds(tileX, tileY, sizeX, sizeY);
		tileImage = PlanetMap.getImage(ID);

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
