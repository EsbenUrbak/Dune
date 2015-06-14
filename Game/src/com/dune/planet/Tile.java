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
	public Tile(int x, int y, String S0,String S1,String S2,String S3,String S4,String S5,String S6,String S7,String S8) {
		
		
		tileX = x * sizeX;
		tileY = y * sizeY;

		type = Resources.W.getType();

		r = new Rectangle();
		r.setBounds(tileX, tileY, sizeX, sizeY);
		//tileImage=Resources.getImage(S0);
		tileImage = ImageMerge(type, sizeX,sizeY,S0,  S1, S2, S3, S4, S5, S6, S7, S8);
		

	}
	
	public BufferedImage ImageRotation(BufferedImage imageBase, int rotationDegrees){
			double w = imageBase.getWidth();    
	        double h = imageBase.getHeight();    
	        BufferedImage result = new BufferedImage((int)w, (int)h, imageBase.getType());  
	        Graphics2D g2 = result.createGraphics();

	        if(rotationDegrees!=0){
	        	g2.rotate(Math.toRadians(rotationDegrees), w/2, h/2);
	        }
	        
	        g2.drawImage(imageBase,null,0,0);  
	        return result;   
	}
	
	
	public BufferedImage ImageMerge(int type, int sizeX, int sizeY,String S0,String S1,String S2,String S3,String S4,String S5,String S6,String S7,String S8){
		
		CombinedTileImage=new BufferedImage(sizeX, sizeY, type);
		
		int sizeXX=sizeX/3;
		int sizeYY=sizeY/3;
		

		//CombinedTileImage =ImageRotation(Resources.getImage(Resources.getWrapper(S1).getName()),Resources.getWrapper(S1).getRotation());
		System.out.println("name E3= "+Resources.getWrapper(S7).getName()+" rotation = "+Resources.getWrapper(S7).getRotation());
		//Load first picture
		CombinedTileImage.createGraphics().drawImage(ImageRotation(Resources.getImage(Resources.getWrapper(S8).getName()),Resources.getWrapper(S8).getRotation()),sizeXX* 0, sizeYY * 0, null);
		CombinedTileImage.createGraphics().drawImage(ImageRotation(Resources.getImage(Resources.getWrapper(S1).getName()),Resources.getWrapper(S1).getRotation()),sizeXX * 1, sizeYY * 0, null);
		CombinedTileImage.createGraphics().drawImage(ImageRotation(Resources.getImage(Resources.getWrapper(S2).getName()),Resources.getWrapper(S2).getRotation()),sizeXX * 2, sizeYY * 1, null);
		CombinedTileImage.createGraphics().drawImage(ImageRotation(Resources.getImage(Resources.getWrapper(S3).getName()),Resources.getWrapper(S3).getRotation()),sizeXX * 1, sizeYY * 2, null);
		CombinedTileImage.createGraphics().drawImage(ImageRotation(Resources.getImage(Resources.getWrapper(S4).getName()),Resources.getWrapper(S4).getRotation()),sizeXX * 0, sizeYY * 1, null);
		CombinedTileImage.createGraphics().drawImage(ImageRotation(Resources.getImage(Resources.getWrapper(S5).getName()),Resources.getWrapper(S5).getRotation()),sizeXX * 2, sizeYY * 0, null);
		CombinedTileImage.createGraphics().drawImage(ImageRotation(Resources.getImage(Resources.getWrapper(S6).getName()),Resources.getWrapper(S6).getRotation()),sizeXX * 2, sizeYY * 2, null);
		CombinedTileImage.createGraphics().drawImage(ImageRotation(Resources.getImage(Resources.getWrapper(S7).getName()),Resources.getWrapper(S7).getRotation()),sizeXX * 0, sizeYY * 2, null);
		CombinedTileImage.createGraphics().drawImage(ImageRotation(Resources.getImage(Resources.getWrapper(S0).getName()),Resources.getWrapper(S0).getRotation()),sizeXX * 1, sizeYY * 1, null);

		//System.out.println("Picture created");
		
		return CombinedTileImage;
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
