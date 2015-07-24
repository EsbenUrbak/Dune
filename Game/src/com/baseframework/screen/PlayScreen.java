package com.baseframework.screen;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.baseframework.game.main.MainHolder;
import com.baseframework.game.main.Resources;
import com.baseframework.util.UIBar;
import com.baseframework.util.UIButton;
import com.baseframework.util.UIDragImage;
import com.baseframework.util.UIDragItem;
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
	
	// Surface object of the planet
	public static ViewFrame viewframe;
	public static PlanetMap map;

	// Buttons, bars and user interface items
	private UIButton buttonMode, buttonCollapse, buttonExtend;
	private UIDragItem dragSquadFace;
	private UIBar mainBar;
	private CopyOnWriteArrayList<UIDragItem> dragItems;
	
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
		int btnModeX = 15, btnModeY = screenSizeY - Resources.btnModeUp.getHeight() -10;
		int dftBarTileCountX = 2;
		int dftBarTileCountY = 2;
		int dftBarTopX = btnModeX + Resources.btnModeUp.getWidth() + 20;
		int dftBarTopY = screenSizeY - dftBarTileCountY * Resources.barTileN.getHeight() - 10;
				
		MainHolder.setResizeable(false);
		
		MainHolder.thegame.setDimensions(screenSizeX, screenSizeY);
		UIDragImage.setScope(screenSizeX, screenSizeY);		
		
		viewframe = new ViewFrame((float) SCREEN_X, (float) SCREEN_Y, screenSizeX, screenSizeY);
		squad = new Squad(SQUAD_TOPX, SQUAD_TOPY, false);	
		map = new PlanetMap(Resources.map1,Resources.elevationMap);
		
		viewframe.setBoundX(map.getWidth(true));
		viewframe.setBoundY(map.getHeight(true));
		squad.setBounds(map.getWidth(false), map.getHeight(false));
		
		buttonMode = new UIButton(btnModeX, btnModeY, Resources.btnModeUp.getWidth(), Resources.btnModeDown.getHeight(),
								Resources.btnModeDown, Resources.btnModeUp);
		buttonCollapse = new UIButton(btnModeX-10, btnModeY - 50, Resources.btnCollapseUp.getWidth(), 
								Resources.btnCollapseUp.getHeight(), Resources.btnCollapseDown, Resources.btnCollapseUp);
		buttonExtend = new UIButton(btnModeX + 40, btnModeY - 50, Resources.btnExtendUp.getWidth(), 
				Resources.btnExtendUp.getHeight(), Resources.btnExtendDown, Resources.btnExtendUp);

		mainBar = new UIBar(dftBarTopX, dftBarTopY, dftBarTileCountX, dftBarTileCountY);

		dragItems = new CopyOnWriteArrayList<UIDragItem>();
		dragItems.add(new UIDragItem(10, 10, Resources.teammate1, mainBar));
		dragItems.add(new UIDragItem(60, 10, Resources.teammate2, mainBar));
		dragItems.add(new UIDragItem(110, 10, Resources.teammate3, mainBar));
		dragItems.add(new UIDragItem(160, 10, Resources.teammate4, mainBar));
		dragItems.add(new UIDragItem(210, 10, Resources.teammate5, mainBar));
		
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
		renderUI(g);
	}
	
	private void renderTiles(Graphics g) {
		int pX, pY;
		for (int y = 0; y <PlanetMap.tileMap.size() ; y++) {
		for (int x = 0; x < PlanetMap.tileMap.get(y).size(); x++) {
	
			Tile t = PlanetMap.tileMap.get(y).get(x);
			
			pX = t.getTileX();
			pY= t.getTileY();
			
			//Use these to get Isometric positions
			//pX = Miscellaneous.carToIsoX(t.getTileX(), t.getTileY(), t.getTileImage().getWidth(null));
			//pY = Miscellaneous.carToIsoY(t.getTileX(), t.getTileY(), t.getTileImage().getHeight(null));
		
			g.drawImage(t.getTileImage(), pX - (int) viewframe.getFrameX(), pY - (int) viewframe.getFrameY(), null);
			
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
			
			
		}}
		
		
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
		buttonMode.render(g);
		buttonCollapse.render(g);
		buttonExtend.render(g);
		mainBar.render(g);
		for (Iterator<UIDragItem> iterator = dragItems.iterator(); iterator.hasNext();) {
			UIDragItem dragItem = iterator.next();
			dragItem.render(g);
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

		
		//check if a button was pressed
		buttonMode.onPressed(xPos, yPos);
		buttonCollapse.onPressed(xPos, yPos);
		buttonExtend.onPressed(xPos, yPos);
		
		for (Iterator<UIDragItem> iterator = dragItems.iterator(); iterator.hasNext();) {
			UIDragItem dragItem = iterator.next();
			dragItem.onPressed(xPos, yPos);
			if(dragItem.isDragged()) pressed = true;
		}	
		if(!pressed) mainBar.onPressed(xPos, yPos);
		
		// stop performing actions if a UI element is selected was pressed
		if(buttonMode.isPressed(xPos, yPos) || buttonCollapse.isPressed(xPos, yPos) || buttonExtend.isPressed(xPos,  yPos) ||
				pressed || mainBar.isPressed(xPos, yPos)) return;
		

		// Logic to check whether it was within the squad rectangle (in relative coordinate)
		
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
		
		//check if 'clicked' on a button: pressed AND released within the button area
		if(buttonMode.isPressed(e.getX(), e.getY())){
			//mainBar.switchDisplay();
			mainBar.pullLvl(1);
			mainBar.pullLvl(2);
		}
		if(buttonCollapse.isPressed(e.getX(), e.getY())){
			//mainBar.collapsedown();
			//mainBar.collapseleft();
			mainBar.pushLvl(2);
		}
		if(buttonExtend.isPressed(e.getX(), e.getY())){
			//mainBar.extendup();
			//mainBar.extendright();
			mainBar.pushLvl(1);
		}		
		
		
		// in any case cancel the button activation
		buttonMode.cancel();
		buttonCollapse.cancel();
		buttonExtend.cancel();
		
		for (Iterator<UIDragItem> iterator = dragItems.iterator(); iterator.hasNext();) {
			UIDragItem dragItem = iterator.next();
			dragItem.onReleased(e.getX(), e.getY());
		}	
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		for (Iterator<UIDragItem> iterator = dragItems.iterator(); iterator.hasNext();) {
			UIDragItem dragItem = iterator.next();
			dragItem.onDragged(e.getX(), e.getY());
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
