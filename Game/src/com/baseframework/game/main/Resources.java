package com.baseframework.game.main;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

public class Resources {
	public static BufferedImage background, tileOcean, tileDirt, squadImagine, squadClickedImagine;
	public static Font titleFont1, subTitleFont1;
	public static Color titleColor1, subTitleColor1;
	public static URL URLmap1;
	
	public static void load(){
		background = loadImage("background.png");
		
		titleFont1 = new Font("Arial", Font.BOLD, 50);
		subTitleFont1 = new Font("Arial", Font.ITALIC, 30);
		titleColor1 = Color.DARK_GRAY;
		subTitleColor1 = Color.LIGHT_GRAY;
		
		tileOcean = loadImage("tiledirt.png");
		tileDirt = loadImage("tileocean.png");
		background = loadImage("background.png");
		squadImagine = loadImage("squad.png");
		squadClickedImagine= loadImage("squadClicked.png");
		
		URLmap1 = Resources.class.getResource("/resources/map1.txt");
	}
	
	private static AudioClip loadSound(String filename){
		URL fileURL = Resources.class.getResource("/resources/" + filename);
		return Applet.newAudioClip(fileURL);
	}
	
	private static BufferedImage loadImage(String filename){
		BufferedImage img = null;
		
		try {
			URL fileURL = Resources.class.getResource("/resources/" + filename);
			img = ImageIO.read(fileURL);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return img;
	}

}
