package com.dune.entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.baseframework.animation.Animation;
import com.baseframework.game.main.Resources;
import com.baseframework.screen.PlayScreen;
import com.dune.planet.Tile;
import com.dune.planet.ViewFrame;

public class Squad {

	private float topX = 45f;
	private float topY = 45f;
	private float speedX = 0f;
	private float speedY = 0f;	
	
	private boolean moving=false;
	private boolean selected=false;
	
	private int minX = 0;
	private int minY = 0;
	private int maxX = 100;
	private int maxY = 100;
	private int xTile, yTile, xTileNext, yTileNext;
	private String tileInfo;
	
	private int SQUADSPEED = 150;
			
	//squad imagine size
	public static int xImagine =Resources.squadRight.getWidth();
	public static int yImagine =Resources.squadRight.getHeight();

	//squad graphical object
	private Animation currentAnim = Resources.squadStandRightAnim;
	private Animation selectAnim = Resources.selectAnim;
	
	//squad properties
	public Rectangle rect; 
	public ArrayList<Path> paths = new ArrayList<Path>();
	
	//game reference
	private ViewFrame viewframe;
	
	public Squad(float topX, float topY, boolean selected){
		this.topX = topX;
		this.topY = topY;
		this.viewframe = PlayScreen.getViewFrame();
		this.selected = selected;
		rect = new Rectangle((int) topX, (int) topY, xImagine, yImagine);	
	}
	
	public void setBounds(int boundX, int boundY){
		maxX = boundX * Tile.getSizeX() - 1 - xImagine;
		maxY = boundY * Tile.getSizeY() - 1 - yImagine;		
	}
	
	public void update(float delta) {
		
		if(paths.size()>0){
			//Logic to check what the underlying tile is
			xTile=(int) ((topX+xImagine/2f+ (int) viewframe.getFrameX()));
			yTile=(int) ((topY+yImagine+ (int) viewframe.getFrameY()));
			xTileNext=(int) (paths.get(0).getX());
			yTileNext=(int) (paths.get(0).getY());

			if(xTile==xTileNext&&yTile==yTileNext){	
				SQUADSPEED=SQUADSPEED;
			}else{
				SQUADSPEED = Speed.speed(xTile, yTile, xTileNext, yTileNext);				
			}

			//System.out.println(" speed ="+ SQUADSPEED);
			
			// local variables to ensure that the squad does not get out of the map
			float pathX, pathY, distX, distY, diagonalDist;
			
			//safety variable to delete a path when the distance is zero
			boolean interrupt=false;
			
			pathX = (float) Math.min(Math.max(paths.get(0).getX(),minX + xImagine/2),maxX + xImagine/2);
			pathY = (float) Math.min(Math.max(paths.get(0).getY(),minY + yImagine),maxY + yImagine/2);
			
			//finding the absolute distance between the new point and the center of squad
			distX = pathX -(topX+ (float) xImagine/2f);
			distY = pathY -(topY+ (float) yImagine);
			
			//finding the diagonal distance between the two points
			diagonalDist=(float) Math.sqrt(Math.pow((double) distX,2d)+Math.pow((double) distY,2d));
			if(diagonalDist < 0.1){
				diagonalDist = 1f;
				interrupt = true;
			}

			//Now find the speed in each direction -> Notice i have added a speedtile which can represent the max speed on a specific tile
			speedX=(distX * (float) SQUADSPEED/diagonalDist);
			speedY=(distY * (float) SQUADSPEED/diagonalDist);			
				
			if(!moving){
				moving = true;				
				if(speedX > 0f) currentAnim = Resources.squadMoveRightAnim;
				else if (speedX < 0f) currentAnim = Resources.squadMoveLeftAnim;
				// case where speed speedX=0 but speedY !=0
				else currentAnim = Resources.squadMoveRightAnim;
			}
			
			//Check whether it is the last step, if so sets the squad to that precise point and sets the speeds to 0
			if(interrupt || Math.abs(pathX-(topX+(float)xImagine/2f))<Math.abs(speedX) * delta || Math.abs(pathY-(topY+(float) yImagine))<Math.abs(speedY)*delta){
				topX= inBoundX(pathX - (float)xImagine/2f);
				topY= inBoundY(pathY - (float)yImagine);	
				
				// remove the path that is now reached
				paths.remove(0);
				moving = false;

				// update the animation, either to standing or walking in the good direction
				if(paths.isEmpty()){
					if(speedX >= 0f){
						currentAnim = Resources.squadStandRightAnim;
					} else {
						currentAnim = Resources.squadStandLeftAnim;									
					}				
				}
				
				// finally sets the speed to 0
				stop();
				
			// otherwise updates the squad position using the speeds and safeguards against infinite values)	
			} else {
				if(Math.abs(speedX)<1f || Math.abs(speedX)>1000000f) speedX = 0f;
				if(Math.abs(speedY)<1f || Math.abs(speedY)>1000000f) speedY = 0f;
				
				topX += speedX * delta;
				topY += speedY * delta;
			}					
		rect.setBounds((int) topX, (int) topY, xImagine, yImagine);	
		
		}else{
			stop();
		}
		
		//update the current animations
		currentAnim.update(delta);
		if(selected) selectAnim.update(delta);
		
	}
	
	
	public void render(Graphics g) {	
		if(selected){
			Resources.selectAnim.render(g, 
					(int) (topX - viewframe.getFrameX() + (xImagine - Resources.selectAnim.getCurrentWidth())/2), 
					(int) (topY - viewframe.getFrameY()) + yImagine - Resources.selectAnim.getCurrentHeight()/2 - 5);			
		}	
		getCurrentAnim().render(g, (int) (topX - viewframe.getFrameX()), (int) (topY-viewframe.getFrameY()));			
	}
	
	public void renderPaths(Graphics g) {
		if(!selected) return;
		
		Graphics2D g2;
		
		g2 = (Graphics2D) g;
	    g2.setStroke(Resources.strokeSize);	

		if(!paths.isEmpty()){
			for ( int i = 0; i < paths.size(); ++i ) {
				if(i==0){
					g2.drawLine((int) (topX+ (float) xImagine/2f - viewframe.getFrameX()), 
								(int) (topY+ (float) yImagine - viewframe.getFrameY()), 
								paths.get(0).getX()- (int) viewframe.getFrameX(), 
								paths.get(0).getY()- (int) viewframe.getFrameY());
				
					g.drawOval(paths.get(0).getX()-1 - (int) viewframe.getFrameX(), paths.get(0).getY()-1- (int) viewframe.getFrameY(),3,3);
					g.drawOval(paths.get(0).getX()-7- (int) viewframe.getFrameX(), paths.get(0).getY()-7- (int) viewframe.getFrameY(),15,15);
				}else{
					g2.drawLine(paths.get(i-1).getX()- (int) viewframe.getFrameX(), paths.get(i-1).getY()- (int) viewframe.getFrameY(),
							paths.get(i).getX()- (int) viewframe.getFrameX(), paths.get(i).getY()- (int) viewframe.getFrameY());	
					g.drawOval(paths.get(i-1).getX()-1- (int) viewframe.getFrameX(),paths.get(i-1).getY()-1- (int) viewframe.getFrameY(),3,3);
					g.drawOval(paths.get(i).getX()-5- (int) viewframe.getFrameX(),paths.get(i).getY()-5- (int) viewframe.getFrameY(),11,11);
				}	
			}
		}
	}
	
	
	private float inBoundX(float x){
		if(Float.isNaN(x)){
			System.out.println("x is not a number");
			return (float) minX;
		} else {
			return Math.min(Math.max(x, (float) minX), (float) maxX);
		}
	}
	
	private float inBoundY(float y){
		if(Float.isNaN(y)){
			System.out.println("y is not a number");
			return (float) minY;
		} else {
			return Math.min(Math.max(y, (float) minY), (float) maxY);
		}
	}

	public void stop() {
		speedX = 0f;
		speedY = 0f;
	}

	public float getTopX() {
		return topX;
	}

	public float getTopY() {
		return topY;
	}


	public float getSpeedX() {
		return speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setTopX(float topX) {
		this.topX = topX;
	}

	public void setTopY(float topY) {
		this.topY = topY;
	}


	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	public int getxImagine() {
		return xImagine;
	}

	public int getyImagine() {
		return yImagine;
	}


	public Animation getCurrentAnim() {
		return currentAnim;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}


	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}


		

}