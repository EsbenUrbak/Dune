package com.baseframework.screen;

import java.awt.BasicStroke;
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
	private Image squadCurrent;

	public static final int SCROLLSPEED = 20; 	

	public static int screenSizeX = 800;
	public static int screenSizeY = 480;
	public static final int INITIALSCREENX = 0;
	public static final int INITIALSCREENY = 0;
	
	//Mouse elements
	public static ArrayList<Integer> pathXPoints = new ArrayList<Integer>();
	public static ArrayList<Integer> pathYPoints = new ArrayList<Integer>();
	public static ArrayList<Integer> activeList = new ArrayList<Integer>();
	boolean squadSelected;
	boolean outsideX;
	boolean outsideY;
	int active = 1;
	int xPos; 
	int yPos;
	Path pathPoint;

	@Override
	public void init() {
		MainHolder.setResizeable(false);
		
		MainHolder.thegame.setDimensions(screenSizeX, screenSizeY);
		squadCurrent = Resources.squadImagine;
		
		viewframe = new ViewFrame(INITIALSCREENX, INITIALSCREENY, screenSizeX, screenSizeY);
		squad = new Squad();
		
		try {
			map = new Map(Resources.URLmap1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		viewframe.setBoundX(map.getWidth(true));
		viewframe.setBoundY(map.getHeight(true));		
	}

	@Override
	public void update() {
		// Updating the screen, squad, and zone of the map to be displayed
		viewframe.update();
		squad.update();	
		
		map.update(viewframe.getFrameX(), viewframe.getFrameY());
		
		//updateTiles();
		
		// update squad properties
		if (squadSelected) {
			squadCurrent = Resources.squadClickedImagine;
		} else {
			squadCurrent = Resources.squadImagine;
		}
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
			g.drawImage(t.getTileImage(), t.getTileX() - viewframe.getFrameX(), t.getTileY() - viewframe.getFrameY(), null);
		}	
	}

	private void renderPaths(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
	    g2.setStroke(new BasicStroke(3));	

		if(pathXPoints.size()>0){
			for ( int i = 0; i < pathXPoints.size(); ++i ) {
				if(i==0){
					g2.drawLine( squad.getCenterX()+squad.getxImagine()/2 - viewframe.getFrameX(), squad.getCenterY()+ squad.getyImagine()/2- viewframe.getFrameY(), pathXPoints.get(0)- viewframe.getFrameX(), pathYPoints.get(0)- viewframe.getFrameY());	
					g.drawOval(pathXPoints.get(0)-5/2- viewframe.getFrameX(),pathYPoints.get(0)-5/2- viewframe.getFrameY(),5,5);
					g.drawOval(pathXPoints.get(0)-15/2- viewframe.getFrameX(),pathYPoints.get(0)-15/2- viewframe.getFrameY(),15,15);
				}else{
					g2.drawLine(pathXPoints.get(i-1)- viewframe.getFrameX(), pathYPoints.get(i-1)- viewframe.getFrameY(),pathXPoints.get(i)- viewframe.getFrameX(), pathYPoints.get(i)- viewframe.getFrameY());	
					g.drawOval(pathXPoints.get(i)-5/2- viewframe.getFrameX(),pathYPoints.get(i)-5/2- viewframe.getFrameY(),5,5);
					g.drawOval(pathXPoints.get(i)-15/2- viewframe.getFrameX(),pathYPoints.get(i)-15/2- viewframe.getFrameY(),15,15);
			
				}	
			}
		}
	}
	
	private void renderSquad(Graphics g) {
		g.drawRect((int)squad.rect.getX() - viewframe.getFrameX(), (int)squad.rect.getY() - viewframe.getFrameY(), 
				(int)squad.rect.getWidth(), (int)squad.rect.getHeight());
		g.drawImage(squadCurrent, squad.getCenterX() - viewframe.getFrameX(), squad.getCenterY()-viewframe.getFrameY(), null);
	}
	
	
	@Override
	public void onClick(MouseEvent e) {
		// Getting click coordinates
		xPos = e.getX();
		yPos = e.getY();

		// adding path to the list but only if outside of squad and if the squad
		// is selected
		
		if(squadSelected == true){
		if ( 	   xPos < (squad.getCenterX() - viewframe.getFrameX())
				|| xPos > (squad.getCenterX() - viewframe.getFrameX() + squad.getxImagine())){
			
			pathXPoints.add(xPos+viewframe.getFrameX());
			pathYPoints.add(yPos+viewframe.getFrameY());
			activeList.add(active);
			
		}else if((yPos < (squad.getCenterY() - viewframe.getFrameY())
				|| yPos > (squad.getCenterY() - viewframe.getFrameY() + squad.getyImagine()))){
			pathXPoints.add(xPos+viewframe.getFrameX());
			pathYPoints.add(yPos+viewframe.getFrameY());
			activeList.add(active);
			}	
		}
	

		// Logic to check whether it was within the rectangle (in relative coordinate)
		if (       xPos > (squad.getCenterX() - viewframe.getFrameX())
				&& xPos < (squad.getCenterX() - viewframe.getFrameX() + squad.getxImagine())
				&& yPos > (squad.getCenterY() - viewframe.getFrameY())
				&& yPos < (squad.getCenterY() - viewframe.getFrameY() + squad.getyImagine()) 
				&& squadSelected == false) {
			squadSelected = true;
		} else if (xPos > (squad.getCenterX() - viewframe.getFrameX())
				&& xPos < (squad.getCenterX() - viewframe.getFrameX() + squad.getxImagine())
				&& yPos > (squad.getCenterY() - viewframe.getFrameY())
				&& yPos < (squad.getCenterY() - viewframe.getFrameY() + squad.getyImagine()) && squadSelected == true) {
			squadSelected = false;
		}
		
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			viewframe.setSpeedY(-SCROLLSPEED);
			break;

		case KeyEvent.VK_DOWN:
			viewframe.setSpeedY(SCROLLSPEED);
			break;

		case KeyEvent.VK_LEFT:
			viewframe.setSpeedX(-SCROLLSPEED);
			break;

		case KeyEvent.VK_RIGHT:
			viewframe.setSpeedX(SCROLLSPEED);
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
			viewframe.setSpeedY(0);
			break;

		case KeyEvent.VK_DOWN:
			viewframe.setSpeedY(0);
			break;

		case KeyEvent.VK_LEFT:
			viewframe.setSpeedX(0);
			break;

		case KeyEvent.VK_RIGHT:
			viewframe.setSpeedX(0);
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
		map.reInit(viewframe.getFrameX(), viewframe.getFrameY(), screenSizeX, screenSizeY);
		
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

	public void setPathXPoints(ArrayList<Integer> pathXPoints) {
		this.pathXPoints = pathXPoints;
	}

	public static ArrayList<Integer> getPathYPoints() {
		return pathYPoints;
	}

	public void setPathYPoints(ArrayList<Integer> pathYPoints) {
		this.pathYPoints = pathYPoints;
	}

	public static ArrayList<Integer> getActiveList() {
		return activeList;
	}

	public void setActiveList(ArrayList<Integer> activeList) {
		this.activeList = activeList;
	}


}
