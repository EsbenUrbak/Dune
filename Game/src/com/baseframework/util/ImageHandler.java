package com.baseframework.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

import com.baseframework.game.main.Resources;

public class ImageHandler {

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
        BufferedImage dimg = new BufferedImage(w, h, img.getColorModel().getTransparency());  
        Graphics2D g = dimg.createGraphics();  
        g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);  
        g.dispose();  
        return dimg;  
    }
    
    public static BufferedImage resize (BufferedImage img, int newW, int newH) {  
        int w = img.getWidth();  
        int h = img.getHeight();  
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());  
        Graphics2D g = dimg.createGraphics();  
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);  
        g.dispose();  
        return dimg;  
    }
    
	public static BufferedImage ImageRotation(BufferedImage imageBase, int rotationDegrees){
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
	
	public static BufferedImage ImageRotationAny(BufferedImage imageBase, int rotationDegrees){
		double w = imageBase.getWidth();    
        double h = imageBase.getHeight();
        double d = Math.sqrt(h*h+w*w);
        BufferedImage result = new BufferedImage((int)d, (int)d, imageBase.getType());  
        Graphics2D g2 = result.createGraphics();

        if(rotationDegrees!=0){
        	g2.rotate(Math.toRadians(rotationDegrees), w/2, h/2);
        }
        
        int diffx = (int)(d-w)-4;
        //int diffy=(int)(d-h);		
        int diffy=-2;		//it does not make sense i should be zero...?
        
        g2.drawImage(imageBase,null,diffx,diffy);  
        return result;   
}
	
    public static BufferedImage makeColorTransparent(BufferedImage im, final Color color) {
    	ImageFilter filter = new RGBImageFilter() {

    		// the color we are looking for... Alpha bits are set to opaque
    		public int markerRGB = color.getRGB() | 0xFF000000;

    		public final int filterRGB(int x, int y, int rgb) {
    			if ((rgb | 0xFF000000) == markerRGB) {
    				// Mark the alpha bits as zero - transparent
    				return 0x00FFFFFF & rgb;
    			} else {
    				// nothing to do
    				return rgb;
    			}
    		}
    	};

    	ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
    	return imageToBufferedImage(Toolkit.getDefaultToolkit().createImage(ip));
    }	
   
    private static BufferedImage imageToBufferedImage(Image image) {

    	BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g2 = bufferedImage.createGraphics();
    	g2.drawImage(image, 0, 0, null);
    	g2.dispose();

    	return bufferedImage;

    }
    
    
	
	public static BufferedImage ImageMerge(int type, int sizeX, int sizeY,String S0,String S1,String S2,String S3,String S4,String S5,String S6,String S7,String S8){
		
		BufferedImage CombinedTileImage = new BufferedImage(sizeX, sizeY, type);
		
		int sizeXX=sizeX/3;
		int sizeYY=sizeY/3;
		
		//Putting the subtiles together to create the bigger tile
		CombinedTileImage.createGraphics().drawImage(ImageHandler.ImageRotation(Resources.getImage(Resources.getWrapper(S8).getName()),Resources.getWrapper(S8).getRotation()),sizeXX* 0, sizeYY * 0, null);
		CombinedTileImage.createGraphics().drawImage(ImageHandler.ImageRotation(Resources.getImage(Resources.getWrapper(S1).getName()),Resources.getWrapper(S1).getRotation()),sizeXX * 1, sizeYY * 0, null);
		CombinedTileImage.createGraphics().drawImage(ImageHandler.ImageRotation(Resources.getImage(Resources.getWrapper(S2).getName()),Resources.getWrapper(S2).getRotation()),sizeXX * 2, sizeYY * 1, null);
		CombinedTileImage.createGraphics().drawImage(ImageHandler.ImageRotation(Resources.getImage(Resources.getWrapper(S3).getName()),Resources.getWrapper(S3).getRotation()),sizeXX * 1, sizeYY * 2, null);
		CombinedTileImage.createGraphics().drawImage(ImageHandler.ImageRotation(Resources.getImage(Resources.getWrapper(S4).getName()),Resources.getWrapper(S4).getRotation()),sizeXX * 0, sizeYY * 1, null);
		CombinedTileImage.createGraphics().drawImage(ImageHandler.ImageRotation(Resources.getImage(Resources.getWrapper(S5).getName()),Resources.getWrapper(S5).getRotation()),sizeXX * 2, sizeYY * 0, null);
		CombinedTileImage.createGraphics().drawImage(ImageHandler.ImageRotation(Resources.getImage(Resources.getWrapper(S6).getName()),Resources.getWrapper(S6).getRotation()),sizeXX * 2, sizeYY * 2, null);
		CombinedTileImage.createGraphics().drawImage(ImageHandler.ImageRotation(Resources.getImage(Resources.getWrapper(S7).getName()),Resources.getWrapper(S7).getRotation()),sizeXX * 0, sizeYY * 2, null);
		CombinedTileImage.createGraphics().drawImage(ImageHandler.ImageRotation(Resources.getImage(Resources.getWrapper(S0).getName()),Resources.getWrapper(S0).getRotation()),sizeXX * 1, sizeYY * 1, null);
	
	
		return CombinedTileImage;
	}
	
}
