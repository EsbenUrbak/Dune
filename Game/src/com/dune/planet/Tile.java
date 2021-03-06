package com.dune.planet;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.baseframework.game.main.Resources;



public class Tile {

	private int tileX, tileY;
	int type;
	String S0,S1,S2,S3,S4,S5,S6,S7,S8,S9;
	private static int sizeX=45, sizeY=45, elevationTile=0;
	public Image tileImage;
	private Rectangle r;
	BufferedImage CombinedTileImage;
	ArrayList<Integer> elevationActionTile=new ArrayList<Integer>();
	boolean eUpT, eDownT, eRightT, eLeftT;
		
	//Loading the tile imagine from data base of landscapes
	public Tile(int x, int y, String ID,boolean eUp, boolean eDown,boolean eRight, boolean eLeft, int elevationOfTile) {
		
		if(PlanetMap.ISOMETRIC){
			tileX = x;
			tileY = y;	
		}else{
		tileX = x * sizeX;
		tileY = y * sizeY;
		}
		
		elevationTile=elevationOfTile*1;
		
		
		type = Resources.W.getType();

		r = new Rectangle();
		//rect start point

		r.setBounds(x*sizeX, y*sizeX, sizeX, sizeY);
		tileImage = PlanetMap.getImage(ID);

		eUpT= eUp;
		eDownT = eDown;
		eRightT= eRight;
		eLeftT =eLeft;

		
		
	}
	
	
	
	public static void setSizeX(int sizeX) {
		Tile.sizeX = sizeX;
	}



	public static int getElevationTile() {
		return elevationTile;
	}



	public static void setElevationTile(int elevationTile) {
		Tile.elevationTile = elevationTile;
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



	public boolean iseUpT() {
		return eUpT;
	}



	public void seteUpT(boolean eUpT) {
		this.eUpT = eUpT;
	}



	public boolean iseDownT() {
		return eDownT;
	}



	public void seteDownT(boolean eDownT) {
		this.eDownT = eDownT;
	}



	public boolean iseRightT() {
		return eRightT;
	}



	public void seteRightT(boolean eRightT) {
		this.eRightT = eRightT;
	}



	public boolean iseLeftT() {
		return eLeftT;
	}



	public void seteLeftT(boolean eLeftT) {
		this.eLeftT = eLeftT;
	}




	
	
}
