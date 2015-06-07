package com.baseframework.game.main;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import com.baseframework.animation.Animation;
import com.baseframework.animation.Frame;

public class Resources {
	public static BufferedImage background, tileOcean, tileDirt, squadRight, squadLeft, squadSelect;
	private static BufferedImage squadMoveRg1, squadMoveRg2, squadMoveRg3, squadMoveRg4, squadMoveRg5;
	private static BufferedImage squadSelect1, squadSelect2, squadSelect3, squadSelect4;
	public static Font titleFont1, subTitleFont1;
	public static Color titleColor1, subTitleColor1;
	public static URL URLmap1;
	public static BasicStroke strokeSize;
	
	public static Animation squadMoveRightAnim, squadMoveLeftAnim, selectAnim;
	
	public static void load(){
		URLmap1 = Resources.class.getResource("/resources/map1.txt");
		
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
		squadSelect1 = resize(squadSelect, (int)(squadSelect.getWidth()*1.03), (int)(squadSelect.getHeight()*1.01));
		squadSelect2 = resize(squadSelect, (int)(squadSelect.getWidth()*1.06), (int)(squadSelect.getHeight()*1.02));
		squadSelect3 = resize(squadSelect, (int)(squadSelect.getWidth()*0.97), (int)(squadSelect.getHeight()*0.99));
		squadSelect4 = resize(squadSelect, (int)(squadSelect.getWidth()*0.94), (int)(squadSelect.getHeight()*0.98));		

		Frame f0_0 = new Frame(squadSelect, .1f);
		Frame f0_1 = new Frame(squadSelect1, .1f);
		Frame f0_2 = new Frame(squadSelect2, .1f);
		Frame f0_3 = new Frame(squadSelect3, .1f);
		Frame f0_4 = new Frame(squadSelect4, .1f);
		selectAnim = new Animation(f0_0, f0_1, f0_2, f0_1, f0_0, f0_3, f0_4, f0_3);
		
		// animate the squad
		squadMoveRg1 = loadImage("squad_move1.png");
		squadMoveRg2 = loadImage("squad_move2.png");
		squadMoveRg3 = loadImage("squad_move3.png");
		squadMoveRg4 = loadImage("squad_move4.png");
		squadMoveRg5 = loadImage("squad_move5.png");
		squadRight = squadMoveRg1;
		squadLeft = horizontalflip(squadMoveRg1);
		
		Frame f1_1 = new Frame(squadMoveRg1, .1f);
		Frame f1_2 = new Frame(squadMoveRg2, .1f);
		Frame f1_3 = new Frame(squadMoveRg3, .1f);
		Frame f1_4 = new Frame(squadMoveRg4, .1f);
		Frame f1_5 = new Frame(squadMoveRg5, .1f);
		squadMoveRightAnim = new Animation(f1_1, f1_2, f1_3, f1_4, f1_5);

		Frame f2_1 = new Frame(horizontalflip(squadMoveRg1), .1f);
		Frame f2_2 = new Frame(horizontalflip(squadMoveRg2), .1f);
		Frame f2_3 = new Frame(horizontalflip(squadMoveRg3), .1f);
		Frame f2_4 = new Frame(horizontalflip(squadMoveRg4), .1f);
		Frame f2_5 = new Frame(horizontalflip(squadMoveRg5), .1f);
		squadMoveLeftAnim = new Animation(f2_1, f2_2, f2_3, f2_4, f2_5);		

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
	
	public static BufferedImage horizontalflip(BufferedImage img) {  
        int w = img.getWidth();  
        int h = img.getHeight();  
        BufferedImage dimg = new BufferedImage(w, h, img.getType());  
        Graphics2D g = dimg.createGraphics();  
        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);  
        g.dispose();  
        return dimg;  
    }
	
    private static BufferedImage verticalflip(BufferedImage img) {  
        int w = img.getWidth();  
        int h = img.getHeight();  
        BufferedImage dimg = dimg = new BufferedImage(w, h, img.getColorModel().getTransparency());  
        Graphics2D g = dimg.createGraphics();  
        g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);  
        g.dispose();  
        return dimg;  
    }
    
    private static BufferedImage resize(BufferedImage img, int newW, int newH) {  
        int w = img.getWidth();  
        int h = img.getHeight();  
        BufferedImage dimg = dimg = new BufferedImage(newW, newH, img.getType());  
        Graphics2D g = dimg.createGraphics();  
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);  
        g.dispose();  
        return dimg;  
    }
    
    public static BufferedImage rotate(BufferedImage img, int angle) {  
        int w = img.getWidth();  
        int h = img.getHeight();  
        BufferedImage dimg = dimg = new BufferedImage(w, h, img.getType());  
        Graphics2D g = dimg.createGraphics();  
        g.rotate(Math.toRadians(angle), w/2, h/2);  
        g.drawImage(img, null, 0, 0);  
        return dimg;  
    }  
}  
    
    
