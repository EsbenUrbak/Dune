package com.baseframework.game.main;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.imageio.ImageIO;

import com.baseframework.animation.Animation;
import com.baseframework.animation.Frame;
import com.baseframework.util.ImageHandler;

public class Resources {
	public static BufferedImage background, tileOcean, tileDirt, squadRight, squadLeft, squadSelect;
	private static BufferedImage squadMoveRg1, squadMoveRg2, squadMoveRg3, squadMoveRg4, squadMoveRg5;
	private static BufferedImage squadSelect1, squadSelect2, squadSelect3, squadSelect4;
	private static BufferedImage squadBlink;
	public static Font titleFont1, subTitleFont1;
	public static Color titleColor1, subTitleColor1;
	public static BufferedReader map1;
	public static BasicStroke strokeSize;
	
	public static Animation squadMoveRightAnim, squadMoveLeftAnim, squadStandRightAnim, squadStandLeftAnim, selectAnim;
	
	private static final String DIRECTORY = "/resources/";
	
	public static void load(){
		map1 = loadFile("map1.txt");
		
		background = loadImage("background.png");
		
		titleFont1 = new Font("Arial", Font.BOLD, 50);
		subTitleFont1 = new Font("Arial", Font.ITALIC, 30);
		titleColor1 = Color.DARK_GRAY;
		subTitleColor1 = Color.LIGHT_GRAY;
		strokeSize = new BasicStroke(2f);
		
		tileOcean = loadImage("tiledirt.png");
		tileDirt = loadImage("tileocean.png");
		background = loadImage("background.png");

		// animate the selector
		squadSelect = loadImage("selector.png");		
		squadSelect1 = ImageHandler.resize(squadSelect, (int)(squadSelect.getWidth()*1.03), (int)(squadSelect.getHeight()*1.01));
		squadSelect2 = ImageHandler.resize(squadSelect, (int)(squadSelect.getWidth()*1.06), (int)(squadSelect.getHeight()*1.02));
		squadSelect3 = ImageHandler.resize(squadSelect, (int)(squadSelect.getWidth()*0.97), (int)(squadSelect.getHeight()*0.99));
		squadSelect4 = ImageHandler.resize(squadSelect, (int)(squadSelect.getWidth()*0.94), (int)(squadSelect.getHeight()*0.98));		

		Frame f0_0 = new Frame(squadSelect, .05f);
		Frame f0_1 = new Frame(squadSelect1, .05f);
		Frame f0_2 = new Frame(squadSelect2, .15f);
		Frame f0_3 = new Frame(squadSelect3, .05f);
		Frame f0_4 = new Frame(squadSelect4, .15f);
		selectAnim = new Animation(f0_0, f0_1, f0_2, f0_1, f0_0, f0_3, f0_4, f0_3);
		
		// animate the squad movement
		squadMoveRg1 = loadImage("squad_move1.png");
		squadMoveRg2 = loadImage("squad_move2.png");
		squadMoveRg3 = loadImage("squad_move3.png");
		squadMoveRg4 = loadImage("squad_move4.png");
		squadMoveRg5 = loadImage("squad_move5.png");
		squadRight = squadMoveRg1;
		squadLeft = ImageHandler.horizontalflip(squadMoveRg1);
		
		Frame f1_1 = new Frame(squadMoveRg1, .1f);
		Frame f1_2 = new Frame(squadMoveRg2, .1f);
		Frame f1_3 = new Frame(squadMoveRg3, .1f);
		Frame f1_4 = new Frame(squadMoveRg4, .1f);
		Frame f1_5 = new Frame(squadMoveRg5, .1f);
		squadMoveRightAnim = new Animation(f1_1, f1_2, f1_3, f1_4, f1_5);

		Frame f2_1 = new Frame(ImageHandler.horizontalflip(squadMoveRg1), .1f);
		Frame f2_2 = new Frame(ImageHandler.horizontalflip(squadMoveRg2), .1f);
		Frame f2_3 = new Frame(ImageHandler.horizontalflip(squadMoveRg3), .1f);
		Frame f2_4 = new Frame(ImageHandler.horizontalflip(squadMoveRg4), .1f);
		Frame f2_5 = new Frame(ImageHandler.horizontalflip(squadMoveRg5), .1f);
		squadMoveLeftAnim = new Animation(f2_1, f2_2, f2_3, f2_4, f2_5);
		
		// animate the squad standing position
		squadBlink = loadImage("squad_blink.png");
		Frame f10_0 = new Frame(squadRight, 1f);
		Frame f10_1 = new Frame(squadBlink, .1f);
		squadStandRightAnim = new Animation(f10_0, f10_1, f10_0);

		Frame f11_0 = new Frame(squadLeft, 1f);
		Frame f11_1 = new Frame(ImageHandler.horizontalflip(squadBlink), .1f);
		squadStandLeftAnim = new Animation(f11_0, f11_1, f11_0);		
		
	}
	
	private static AudioClip loadSound(String filename){
		URL fileURL = Resources.class.getResource(DIRECTORY + filename);
		return Applet.newAudioClip(fileURL);
	}
	
	private static BufferedImage loadImage(String filename){
		BufferedImage img = null;
		
		try {
			URL fileURL = Resources.class.getResource(DIRECTORY + filename);
			img = ImageIO.read(fileURL);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return img;
	}
	
	private static BufferedReader loadFile(String filename){
		BufferedReader reader = null;
		try {
			URL fileURL = Resources.class.getResource(DIRECTORY + filename);
			reader = new BufferedReader(new InputStreamReader(fileURL.openStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reader;
	}	
	
  
}  
    
    
