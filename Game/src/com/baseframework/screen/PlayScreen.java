package com.baseframework.screen;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import com.baseframework.game.main.*;
import com.dune.entities.Path;
import com.dune.entities.Squad;
import com.dune.planet.*;



public class PlayScreen extends GameScreen{
	// Creating variables and objects
	
	// Creating a unit to move around on the planet
	public Squad squad;
	
	// Creating a the surface object of the planet
	public static ViewFrame viewframe;
	public static Map map;

	// Graphics objects
	Graphics2D g2;
	
	// numerical variables
	public static int screenSizeX = 800;
	public static int screenSizeY = 480;
	public static final int INITIALSCREENX = 0;
	public static final int INITIALSCREENY = 0;
	
	//Mouse elements
	public static ArrayList<Integer> pathXPoints = new ArrayList<Integer>();
	public static ArrayList<Integer> pathYPoints = new ArrayList<Integer>();
	public static ArrayList<Integer> activeList = new ArrayList<Integer>();
	boolean squadSelected;
	int active = 1;
	Path pathPoint;

	@Override
	public void init() {
		MainHolder.setResizeable(false);
		
		MainHolder.thegame.setDimensions(screenSizeX, screenSizeY);
		
		viewframe = new ViewFrame((float) INITIALSCREENX, (float) INITIALSCREENY, screenSizeX, screenSizeY);
		squad = new Squad();
		
		try {
			map = new Map(Resources.URLmap1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		viewframe.setBoundX(map.getWidth(true));
		viewframe.setBoundY(map.getHeight(true));
		squad.setBounds(map.getWidth(false), map.getHeight(false));
	}

	@Override
	public void update(float delta) {
		// Updating the screen, squad, and zone of the map to be displayed
		viewframe.update(delta);
		squad.update(delta);
		
		map.update((int) viewframe.getFrameX(), (int) viewframe.getFrameY());
		
		Resources.selectAnim.update(delta);
		squad.getCurrentAnim().update(delta);
		
		//updateTiles();
		
		/* update squad properties
		if (squadSelected) {
			squadCurrent = Resources.squadClickedImagine;
		} else {
			squadCurrent = Resources.squadImagine;
		}*/
	}

	@Override
	public void render(Graphics g) {
		renderTiles(g);
		renderPaths(g);	
		renderSquad(g);
	}
	
	private void renderTiles(Graphics g) {
		// paint ONLY the tiles in the catch zone, and adjusts for relative coordinates
		for (int i = 0; i < map.getScopeTileArray().size(); i++) {
			Tile t = (Tile) map.getScopeTileArray().get(i);
			g.drawImage(t.getTileImage(), t.getTileX() - (int) viewframe.getFrameX(), t.getTileY() - (int) viewframe.getFrameY(), null);
		}	
	}

	private void renderPaths(Graphics g) {
		g2 = (Graphics2D) g;
	    g2.setStroke(Resources.strokeSize);	

		if(pathXPoints.size()>0){
			for ( int i = 0; i < pathXPoints.size(); ++i ) {
				if(i==0){
					g2.drawLine((int) (squad.getTopX()+ (float)squad.getxImagine()/2f - viewframe.getFrameX()), 
								(int) (squad.getTopY()+ (float)squad.getyImagine()/2f - viewframe.getFrameY()), 
								pathXPoints.get(0)- (int) viewframe.getFrameX(), 
								pathYPoints.get(0)- (int) viewframe.getFrameY());
				
					g.drawOval(pathXPoints.get(0)-1 - (int) viewframe.getFrameX(),pathYPoints.get(0)-1- (int) viewframe.getFrameY(),3,3);
					g.drawOval(pathXPoints.get(0)-7- (int) viewframe.getFrameX(),pathYPoints.get(0)-7- (int) viewframe.getFrameY(),15,15);
				}else{
					g2.drawLine(pathXPoints.get(i-1)- (int) viewframe.getFrameX(), pathYPoints.get(i-1)- (int) viewframe.getFrameY(),
								pathXPoints.get(i)- (int) viewframe.getFrameX(), pathYPoints.get(i)- (int) viewframe.getFrameY());	
					g.drawOval(pathXPoints.get(i)-1- (int) viewframe.getFrameX(),pathYPoints.get(i)-1- (int) viewframe.getFrameY(),3,3);
					g.drawOval(pathXPoints.get(i)-5- (int) viewframe.getFrameX(),pathYPoints.get(i)-5- (int) viewframe.getFrameY(),11,11);
				}	
			}
		}
	}
	
	private void renderSquad(Graphics g) {
		
		if(squadSelected){
			Resources.selectAnim.render(g, 
					(int) (squad.getTopX() - viewframe.getFrameX() + (squad.xImagine - Resources.selectAnim.getCurrentWidth())/2), 
					(int) (squad.getTopY() - viewframe.getFrameY()) + squad.yImagine - Resources.selectAnim.getCurrentHeight()/2 - 5);			
		}
		
		squad.getCurrentAnim().render(g, (int) (squad.getTopX() - viewframe.getFrameX()), (int) (squad.getTopY()-viewframe.getFrameY()));			

	}
	
	
	@Override
	public void onClick(MouseEvent e) {
		int xPos, yPos;
		boolean addNewPath = false;
		
		// Getting click coordinates
		xPos = e.getX();
		yPos = e.getY();

		// adding path to the list but only if outside of squad and if the squad
		// is selected

		// Logic to check whether it was within the rectangle (in relative coordinate)
		if (squad.rect.contains(xPos+ (int) viewframe.getFrameX(), yPos+ (int) viewframe.getFrameY())) {
			squadSelected = !squadSelected;
		}
		
		if(squadSelected){
			if (!squad.rect.contains(xPos+ (int) viewframe.getFrameX(), yPos+ (int) viewframe.getFrameY())){
				
				if(pathXPoints.size() ==0){
					addNewPath = true;
				} else if (pathXPoints.get(pathXPoints.size()-1) != xPos+ (int) viewframe.getFrameX() || 
						pathYPoints.get(pathYPoints.size()-1) != yPos + (int) viewframe.getFrameY()) {
					addNewPath = true;
				}
				
				if(addNewPath){
					pathXPoints.add(xPos+(int) viewframe.getFrameX());
					pathYPoints.add(yPos+(int) viewframe.getFrameY());
					activeList.add(active);
				}
			}	
		}


		
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			viewframe.scrollUp();
			break;

		case KeyEvent.VK_DOWN:
			viewframe.scrollDown();
			break;

		case KeyEvent.VK_LEFT:
			viewframe.scrollLeft();
			break;

		case KeyEvent.VK_RIGHT:
			viewframe.scrollRight();
			break;
			
		case KeyEvent.VK_Z:
			//ZOOMin
			break;

		case KeyEvent.VK_X:
			//ZOOMout
			break;
		}
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			if(viewframe.getSpeedY() < 0f) viewframe.setSpeedY(0f);
			break;

		case KeyEvent.VK_DOWN:
			if(viewframe.getSpeedY() >0f) viewframe.setSpeedY(0f);
			break;

		case KeyEvent.VK_LEFT:
			if(viewframe.getSpeedX() <0f) viewframe.setSpeedX(0f);
			break;

		case KeyEvent.VK_RIGHT:
			if(viewframe.getSpeedX() > 0f) viewframe.setSpeedX(0f);
			break;
		}
	}

	@Override
	public void onComponentResize(ComponentEvent e) {
		
		// does not work yet!!!
		
		// when PlayScreen gets resized, updates size parameters
		
		screenSizeX = MainHolder.thegame.getWidth();
		screenSizeY = MainHolder.thegame.getHeight();
		//MainHolder.thegame.setDimensions(screenSizeX, screenSizeY);
		
		viewframe.setSizeX(screenSizeX);
		viewframe.setSizeY(screenSizeY);
		
		// then identifies the new frames to be displayed
		map.reInit((int) viewframe.getFrameX(), (int) viewframe.getFrameY(), screenSizeX, screenSizeY);
		
		// finally resets the image object that will get resized in update() method
		MainHolder.thegame.resetGameImage();
	}
	

	public static void setViewFrame(ViewFrame viewFrame) {
		PlayScreen.viewframe = viewFrame;
	}

	public static ViewFrame getViewFrame() {
		return viewframe;
	}

	public static ArrayList<Integer> getPathXPoints() {
		return pathXPoints;
	}

	public static void setPathXPoints(ArrayList<Integer> newPathXPoints) {
		pathXPoints = newPathXPoints;
	}

	public static ArrayList<Integer> getPathYPoints() {
		return pathYPoints;
	}

	public static void setPathYPoints(ArrayList<Integer> newPathYPoints) {
		pathYPoints = newPathYPoints;
	}

	public static ArrayList<Integer> getActiveList() {
		return activeList;
	}

	public static void setActiveList(ArrayList<Integer> newActiveList) {
		activeList = newActiveList;
	}


}