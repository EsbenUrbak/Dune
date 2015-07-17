package com.baseframework.screen;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.baseframework.game.main.MainHolder;
import com.baseframework.game.main.Resources;
import com.dune.entities.AStar;
import com.dune.entities.Path;
import com.dune.entities.Squad;
import com.dune.planet.PlanetMap;
import com.dune.planet.Tile;
import com.dune.planet.ViewFrame;



public class PlayScreen extends GameScreen{
	// Creating variables and objects
	
	// Creating a unit to move around on the planet
	public Squad squad;
	
	// Creating a the surface object of the planet
	public static ViewFrame viewframe;
	public static PlanetMap map;

	// Graphics objects
	public Graphics2D g2;
	
	// numerical variables
	public static int screenSizeX = 800;
	public static int screenSizeY = 480;
	
	private final float SQUAD_TOPX = 300f; 
	private final float SQUAD_TOPY = 200f;
	public static final int SCREEN_X = 0;
	public static final int SCREEN_Y = 0;

	@Override
	public void init() {
		MainHolder.setResizeable(false);
		
		MainHolder.thegame.setDimensions(screenSizeX, screenSizeY);
		
		viewframe = new ViewFrame((float) SCREEN_X, (float) SCREEN_Y, screenSizeX, screenSizeY);
		squad = new Squad(SQUAD_TOPX, SQUAD_TOPY, false);	
		map = new PlanetMap(Resources.map1,Resources.elevationMap);
		
		viewframe.setBoundX(map.getWidth(true));
		viewframe.setBoundY(map.getHeight(true));
		squad.setBounds(map.getWidth(false), map.getHeight(false));
	}

	@Override
	public void update(float delta) {
		viewframe.update(delta);
		squad.update(delta);
		map.update((int) viewframe.getFrameX(), (int) viewframe.getFrameY());
		//updateTiles();
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
		
			//Drawing elevation lines
			if(t.iseUpT()){
				g.drawLine(t.getTileX() - (int) viewframe.getFrameX(), t.getTileY() - (int) viewframe.getFrameY(), t.getTileX() - (int) viewframe.getFrameX()+t.getSizeX(), t.getTileY() - (int) viewframe.getFrameY());
						
			}
			if(t.iseRightT()){
				g.drawLine(t.getTileX() - (int) viewframe.getFrameX()+t.getSizeX(), t.getTileY() - (int) viewframe.getFrameY(), t.getTileX() - (int) viewframe.getFrameX()+t.getSizeX(), t.getTileY() - (int) viewframe.getFrameY()+t.getSizeY());
			}
			if(t.iseDownT()){
				g.drawLine(t.getTileX() - (int) viewframe.getFrameX()-t.getSizeX(), t.getTileY() - (int) viewframe.getFrameY()+t.getSizeY(), t.getTileX() - (int) viewframe.getFrameX(), t.getTileY() - (int) viewframe.getFrameY()+t.getSizeY());
			}
			if(t.iseLeftT()){
				g.drawLine(t.getTileX() - (int) viewframe.getFrameX(), t.getTileY() - (int) viewframe.getFrameY(), t.getTileX() - (int) viewframe.getFrameX(), t.getTileY() - (int) viewframe.getFrameY()+t.getSizeY());
			}
			
		
		}	
	}

	private void renderPaths(Graphics g) {
		g2 = (Graphics2D) g;
	    g2.setStroke(Resources.strokeSize);	

		if(!squad.paths.isEmpty()){
			for ( int i = 0; i < squad.paths.size(); ++i ) {
				if(i==0){
					g2.drawLine((int) (squad.getTopX()+ (float)squad.getxImagine()/2f - viewframe.getFrameX()), 
								(int) (squad.getTopY()+ (float)squad.getyImagine() - viewframe.getFrameY()), 
								squad.paths.get(0).getX()- (int) viewframe.getFrameX(), 
								squad.paths.get(0).getY()- (int) viewframe.getFrameY());
				
					g.drawOval(squad.paths.get(0).getX()-1 - (int) viewframe.getFrameX(),squad.paths.get(0).getY()-1- (int) viewframe.getFrameY(),3,3);
					g.drawOval(squad.paths.get(0).getX()-7- (int) viewframe.getFrameX(),squad.paths.get(0).getY()-7- (int) viewframe.getFrameY(),15,15);
				}else{
					g2.drawLine(squad.paths.get(i-1).getX()- (int) viewframe.getFrameX(), squad.paths.get(i-1).getY()- (int) viewframe.getFrameY(),
							squad.paths.get(i).getX()- (int) viewframe.getFrameX(), squad.paths.get(i).getY()- (int) viewframe.getFrameY());	
					g.drawOval(squad.paths.get(i-1).getX()-1- (int) viewframe.getFrameX(),squad.paths.get(i-1).getY()-1- (int) viewframe.getFrameY(),3,3);
					g.drawOval(squad.paths.get(i).getX()-5- (int) viewframe.getFrameX(),squad.paths.get(i).getY()-5- (int) viewframe.getFrameY(),11,11);
				}	
			}
		}
	}
	
	private void renderSquad(Graphics g) {	
		if(squad.isSelected()){
			Resources.selectAnim.render(g, 
					(int) (squad.getTopX() - viewframe.getFrameX() + (squad.xImagine - Resources.selectAnim.getCurrentWidth())/2), 
					(int) (squad.getTopY() - viewframe.getFrameY()) + squad.yImagine - Resources.selectAnim.getCurrentHeight()/2 - 5);			
		}	
		squad.getCurrentAnim().render(g, (int) (squad.getTopX() - viewframe.getFrameX()), (int) (squad.getTopY()-viewframe.getFrameY()));			
	}
	
	
	@Override
	public void onClick(MouseEvent e) {
		int xPos, yPos,xTile, yTile;
		boolean addNewPath = false;
		String tileInfo;
		
		// Getting click coordinates
		xPos = e.getX();
		yPos = e.getY();

		//Logic to check what the underlying tile is
		xTile=(xPos+ (int) viewframe.getFrameX())/Tile.getSizeX();
		yTile=(yPos+ (int) viewframe.getFrameY())/Tile.getSizeY();
		tileInfo = PlanetMap.mapArray.get(xTile+yTile*PlanetMap.width);
		System.out.println(tileInfo);
		
		if(tileInfo.equals("W")){

		}else{
		// Logic to check whether it was within the rectangle (in relative coordinate)
		if (squad.rect.contains(xPos+ (int) viewframe.getFrameX(), yPos+ (int) viewframe.getFrameY())) {
			squad.setSelected(!squad.isSelected());
		}

		// adding path to the list but only if outside of squad and if the squad is selected
		if(squad.isSelected()){
			if (!squad.rect.contains(xPos+ (int) viewframe.getFrameX(), yPos+ (int) viewframe.getFrameY())){
				
				if(squad.paths.isEmpty()){
					addNewPath = true;
				} else if (squad.paths.get(squad.paths.size()-1).getX() != xPos+ (int) viewframe.getFrameX() || squad.paths.get(squad.paths.size()-1).getY() != yPos + (int) viewframe.getFrameY()) {
					addNewPath = true;
				}
				
				if(addNewPath){
					//create optimal path with Astar logic:
					
					  Map<String,ArrayList<Integer>> map =new HashMap();
					  
					  //logic to make sure it doesnt always start bulding a path from where the squad currently is:
					  if(squad.paths.isEmpty()){
					  map=AStar.AStar((int)squad.getTopX()+ (int)squad.getxImagine()/2,(int)squad.getTopY()+ (int)squad.getyImagine(), xPos+(int) viewframe.getFrameX(),yPos+(int) viewframe.getFrameY());
					  }else{
						  map=AStar.AStar(squad.paths.get(squad.paths.size()-1).getX(),squad.paths.get(squad.paths.size()-1).getY(), xPos+(int) viewframe.getFrameX(),yPos+(int) viewframe.getFrameY()); 
					  }
					  
					  for(int i =0;i<map.get("x").size();i++){
					squad.paths.add(new Path(map.get("x").get(i), map.get("y").get(i), true));
					  }
					
				}
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

}
