package com.baseframework.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.baseframework.UI.*;
import com.baseframework.game.main.MainHolder;
import com.baseframework.game.main.Resources;
import com.baseframework.util.Miscellaneous;
import com.dune.entities.AStar;
import com.dune.entities.Path;
import com.dune.entities.Squad;
import com.dune.planet.PlanetMap;
import com.dune.planet.Tile;
import com.dune.planet.ViewFrame;

public class PlayScreen extends GameScreen{
	// Creating variables and objects
	
	// units
	public Squad squad;
	Tile t;
	
	// Surface object of the planet
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
	public static final int SCREEN_Y = 0, ELEVATIONHEIGHT=15;
	
	// boolean variables
	boolean spaceKeyPressed = false;
	
	// Buttons, bars and user interface items
	private UIAdvButton buttonMode;
	private UIBar mainBar;
	private ArrayList<UIObject> uiItems;
	
	//start of nested button classes ---------------------------------------------------------------------------------------

	private class UIButtonCollapse extends UIButton{
		public UIButtonCollapse(int topX, int topY, Image buttonImageDown, Image buttonImageUp) {
			super(topX, topY, buttonImageDown, buttonImageUp);
		}
		
		@Override
		public void performAction(){
			mainBar.pushLvl(1);	
		}
	}
	
	private class UIButtonExtend extends UIButton{
		public UIButtonExtend(int topX, int topY, Image buttonImageDown, Image buttonImageUp) {
			super(topX, topY, buttonImageDown, buttonImageUp);
		}
		
		@Override
		public void performAction(){
			mainBar.pushLvl(2);	
		}
	}
	//end of button classes ---------------------------------------------------------------------------------------

	@Override
	public void init() {
		MainHolder.setResizeable(false);
		MainHolder.thegame.setDimensions(screenSizeX, screenSizeY);
		
		map = new PlanetMap(Resources.map1,Resources.elevationMap);
		viewframe = new ViewFrame((float) SCREEN_X, (float) SCREEN_Y, screenSizeX, screenSizeY);
		viewframe.setBoundX(map.getWidth(true));
		viewframe.setBoundY(map.getHeight(true));

		initUI();
		
		squad = new Squad(SQUAD_TOPX, SQUAD_TOPY, false);	
		squad.setBounds(map.getWidth(false), map.getHeight(false));
	}
	
	private void initUI(){
		int btnModeX = 15, btnModeY = screenSizeY - Resources.btnModeUp.getHeight() -10;
		int dftBarTileCountX = 2;
		int dftBarTileCountY = 2;
		int dftBarTopX = btnModeX + Resources.btnModeUp.getWidth() + 20;
		int dftBarTopY = screenSizeY - dftBarTileCountY * Resources.barTileN.getHeight() - 10;
		
		UIDragImage.setScope(screenSizeX, screenSizeY);
		
		// define the specific UI items and add them to the list
		uiItems = new ArrayList<UIObject>();
		
		buttonMode = new UIAdvButton(btnModeX, btnModeY, Resources.btnModeDown, Resources.btnModeUp, Resources.barShow);
		uiItems.add(buttonMode);
		
		mainBar = new UIBar(dftBarTopX, dftBarTopY, dftBarTileCountX, dftBarTileCountY);
		uiItems.add(mainBar);
		
		buttonMode.setBar(mainBar);
		mainBar.setButton(buttonMode);
		
		// create the generic UI items in the list
		//uiItems.add(new UIButtonCollapse(btnModeX-10, btnModeY - 50, Resources.btnCollapseDown, Resources.btnCollapseUp));
		
		//uiItems.add(new UIButtonExtend(btnModeX + 40, btnModeY - 50, Resources.btnExtendDown, Resources.btnExtendUp));
		
		uiItems.add(new UIDragItem(10, 10, Resources.teammate1, mainBar));
		uiItems.add(new UIDragItem(60, 10, Resources.teammate2, mainBar));
		uiItems.add(new UIDragItem(110, 10, Resources.teammate3, mainBar));
		uiItems.add(new UIDragItem(160, 10, Resources.teammate4, mainBar));
		uiItems.add(new UIDragItem(210, 10, Resources.teammate5, mainBar));		

		//sort by order of type priority for proper display
		uiItems.sort(typePriorityOrder);
	}

	@Override
	public void update(float delta) {
		boolean reSort = false;
		
		viewframe.update(delta);
		squad.update(delta);
		map.update((int) viewframe.getFrameX(), (int) viewframe.getFrameY());
		//updateTiles();
		
		for (int i = 0; i < uiItems.size(); i++) {	
			reSort = reSort || uiItems.get(i).updateList(uiItems);
		}
		if(reSort) uiItems.sort(typePriorityOrder);
	}

	@Override
	public void render(Graphics g) {
		renderTiles(g);
		renderPaths(g);	
		renderSquad(g);
		renderUI(g);
	}
	
	private void renderTiles(Graphics g) {
		int pX, pY,elevation;
		for (int y = 0; y <PlanetMap.tileMap.size() ; y++) {

			for (int x = 0; x < PlanetMap.tileMap.get(y).size(); x++) {
	
				Tile t = PlanetMap.tileMap.get(y).get(x);

				pX = t.getTileX();
				pY= t.getTileY();

			t = PlanetMap.tileMap.get(y).get(x);
			pX = t.getTileX();
			pY= t.getTileY();
			elevation=PlanetMap.getElev(x*3,y*3);  //i tried t.getElevation() but that didnt work for some bizarre reason

			if(PlanetMap.ISOMETRIC){
				//Use these to get Isometric positions
				pX = Miscellaneous.carToIsoIndexX(pX, t.getTileY(), t.getTileImage().getWidth(null));
				pY = Miscellaneous.carToIsoIndexY(pY, t.getTileY(), t.getTileImage().getHeight(null));
			
			}

			

			
			if(elevation>0&&PlanetMap.ISOMETRIC){
				g.drawImage(t.getTileImage(), pX - (int) viewframe.getFrameX(), pY - elevation*ELEVATIONHEIGHT-(int) viewframe.getFrameY(), null);
				Color brown = new Color(128,128,0); 
				g.setColor (brown);
				Polygon p = new Polygon();
				p.addPoint(pX- (int) viewframe.getFrameX(),pY- (int) viewframe.getFrameY()+t.getTileImage().getHeight(null)/2-elevation*ELEVATIONHEIGHT-2);
				p.addPoint(pX- (int) viewframe.getFrameX(),pY- (int) viewframe.getFrameY()+t.getTileImage().getHeight(null)/2+2);
				p.addPoint(pX- (int) viewframe.getFrameX()+t.getTileImage().getWidth(null)/2,pY- (int) viewframe.getFrameY()+t.getTileImage().getHeight(null)+2);
				p.addPoint(pX- (int) viewframe.getFrameX()+t.getTileImage().getWidth(null)/2,pY- (int) viewframe.getFrameY()+t.getTileImage().getHeight(null)-elevation*ELEVATIONHEIGHT-2);
				g.fillPolygon(p);  
				
				Polygon p2 = new Polygon();
				p2.addPoint((pX- (int) viewframe.getFrameX()+t.getTileImage().getWidth(null)),pY- (int) viewframe.getFrameY()+t.getTileImage().getHeight(null)/2-elevation*ELEVATIONHEIGHT-2);
				p2.addPoint(pX- (int) viewframe.getFrameX()+t.getTileImage().getWidth(null),pY- (int) viewframe.getFrameY()+t.getTileImage().getHeight(null)/2+2);
				p2.addPoint(pX- (int) viewframe.getFrameX()+t.getTileImage().getWidth(null)/2,pY- (int) viewframe.getFrameY()+t.getTileImage().getHeight(null)+2);
				p2.addPoint(pX- (int) viewframe.getFrameX()+t.getTileImage().getWidth(null)/2,pY- (int) viewframe.getFrameY()+t.getTileImage().getHeight(null)-elevation*ELEVATIONHEIGHT-2);
				g.fillPolygon(p2);  
			
				//Drawing elevation lines
				g.drawLine(pX - (int) viewframe.getFrameX()+t.getTileImage().getWidth(null)/2, pY - (int) viewframe.getFrameY()-elevation*ELEVATIONHEIGHT, pX - (int) viewframe.getFrameX()+t.getTileImage().getWidth(null), pY - (int) viewframe.getFrameY()+t.getTileImage().getHeight(null)/2-elevation*ELEVATIONHEIGHT);	
				g.drawLine(pX - (int) viewframe.getFrameX(), pY - (int) viewframe.getFrameY()+t.getTileImage().getHeight(null)/2-elevation*ELEVATIONHEIGHT, pX - (int) viewframe.getFrameX()+t.getTileImage().getWidth(null)/2, pY - (int) viewframe.getFrameY()-elevation*ELEVATIONHEIGHT);
				
				/*if(t.iseUpT()){
					g.drawLine(pX - (int) viewframe.getFrameX()+t.getTileImage().getHeight(null)/2, pY - (int) viewframe.getFrameY()-elevation*ELEVATIONHEIGHT, pX - (int) viewframe.getFrameX()+t.getTileImage().getHeight(null), pY - (int) viewframe.getFrameY()+t.getTileImage().getHeight(null)/2-elevation*ELEVATIONHEIGHT);					}
				if(t.iseRightT()){
					g.drawLine(pX - (int) viewframe.getFrameX()+t.getTileImage().getHeight(null), pY - (int) viewframe.getFrameY()+t.getTileImage().getHeight(null)/2-elevation*ELEVATIONHEIGHT, pX - (int) viewframe.getFrameX()+t.getTileImage().getHeight(null)/2, pY - (int) viewframe.getFrameY()+t.getTileImage().getHeight(null)-elevation*ELEVATIONHEIGHT);	
				}
				if(t.iseDownT()){
					g.drawLine(pX - (int) viewframe.getFrameX()+t.getTileImage().getHeight(null)/2, pY - (int) viewframe.getFrameY()+t.getTileImage().getHeight(null)-elevation*ELEVATIONHEIGHT, pX - (int) viewframe.getFrameX(), pY - (int) viewframe.getFrameY()+t.getTileImage().getHeight(null)/2-elevation*ELEVATIONHEIGHT);
				}
				if(t.iseLeftT()){
					g.drawLine(pX - (int) viewframe.getFrameX(), pY - (int) viewframe.getFrameY()+t.getTileImage().getHeight(null)/2-elevation*ELEVATIONHEIGHT, pX - (int) viewframe.getFrameX()+t.getTileImage().getHeight(null)/2, pY - (int) viewframe.getFrameY()-elevation*ELEVATIONHEIGHT);
				}*/
			
			
			}else{
				g.drawImage(t.getTileImage(), pX - (int) viewframe.getFrameX(), pY -(int) viewframe.getFrameY(), null);
			}


			

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
	
	private void renderUI(Graphics g){
		//display in inverse order of priority (so that objects with higher priority appear on top)
		for(int i= uiItems.size() - 1; i>= 0; i--){
			uiItems.get(i).render(g);
		}
	}
	
	
	@Override
	public void onClick(MouseEvent e) {
		// this function is now empty to avoid conflict with the button handlers		
	}
	

	@Override
	public void onMousePressed(MouseEvent e) {
		int xPos, yPos, xTile, yTile;
		boolean addNewPath = false, pressed = false;
		String tileInfo;
		
		// Getting click coordinates
		xPos = e.getX();
		yPos = e.getY();

		
		//check if a UI object was pressed by order of priority
		for (int i = 0; i < uiItems.size(); i++) {
			pressed = uiItems.get(i).onPressed(xPos, yPos);
			if (pressed) break;
		}
		
		// stop performing actions if a UI element was pressed
		if(pressed) return;

		// checks whether the squad rectangle (in relative coordinate)
		if (squad.rect.contains(xPos+ (int) viewframe.getFrameX(), yPos+ (int) viewframe.getFrameY())) {
			squad.setSelected(!squad.isSelected());
		}

		// adding path to the list but only if outside of squad and if the squad is selected
		if(squad.isSelected()){
			if (!squad.rect.contains(xPos+ (int) viewframe.getFrameX(), yPos+ (int) viewframe.getFrameY())){
				
				//Logic to check what the underlying tile is
				xTile=(xPos+ (int) viewframe.getFrameX())/Tile.getSizeX();
				yTile=(yPos+ (int) viewframe.getFrameY())/Tile.getSizeY();
				tileInfo = PlanetMap.mapArray.get(xTile+yTile*PlanetMap.width);
				//System.out.println(tileInfo);					
				
				// exit if on forbidden terrain type
				if(tileInfo.equals("W")) return;
				
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
						map=AStar.AStarFunction((int)squad.getTopX()+ (int)squad.getxImagine()/2,(int)squad.getTopY()+ (int)squad.getyImagine(), xPos+(int) viewframe.getFrameX(),yPos+(int) viewframe.getFrameY());
					}else{
						map=AStar.AStarFunction(squad.paths.get(squad.paths.size()-1).getX(),squad.paths.get(squad.paths.size()-1).getY(), xPos+(int) viewframe.getFrameX(),yPos+(int) viewframe.getFrameY()); 
					}
					  
					for(int i =0;i<map.get("x").size();i++){
						squad.paths.add(new Path(map.get("x").get(i), map.get("y").get(i), true));
					}
				}
			}
		}
	}


	@Override
	public void onMouseReleased(MouseEvent e) {
		
		// release all UI items
		for (int i = 0; i < uiItems.size(); i++){	
			if (uiItems.get(i).onReleased(e.getX(), e.getY())) uiItems.get(i).performAction();
		}
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		boolean dragged=false;
		
		//check if an UI item was dragged by order of priority
		for (int i = 0; i < uiItems.size(); i++) {
			dragged = uiItems.get(i).onDragged(e.getX(), e.getY());
			if (dragged) break;
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

		case KeyEvent.VK_SPACE:
			if(!spaceKeyPressed){
				spaceKeyPressed = true;
				mainBar.pushLvl(2);
			}
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
			
		case KeyEvent.VK_SPACE:
			spaceKeyPressed = false;
			mainBar.pullLvl(2);
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
