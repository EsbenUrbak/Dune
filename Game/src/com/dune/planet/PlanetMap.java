package com.dune.planet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.baseframework.game.main.Resources;
import com.baseframework.screen.PlayScreen;
import com.baseframework.util.ArrayHandler;
import com.baseframework.util.ImageHandler;
import com.baseframework.util.Miscellaneous;

public class PlanetMap {
	public static boolean ISOMETRIC =true;
	private ArrayList<Tile> tilearray = new ArrayList<Tile>();	
	
	public static ArrayList<String> mapArray = new ArrayList<String>();	
	public static ArrayList<String> mapArrayHighRes = new ArrayList<String>();
	public static ArrayList<String> elevationArray = new ArrayList<String>();	
	public static ArrayList<String> elevationArrayHighRes = new ArrayList<String>();	
	
	public static Map<Integer, Map<Integer, String>> terrainMap = new HashMap<Integer, Map<Integer, String>>();	
	public static Map<Integer, Map<Integer, String>> elevationMap = new HashMap<Integer, Map<Integer, String>>();
	public static Map<Integer, Map<Integer, String>> elevationMapLowRes = new HashMap<Integer, Map<Integer, String>>();
	public static Map<Integer, Map<Integer, String>> terMap = new HashMap<Integer, Map<Integer, String>>();
	public static Map<Integer, Map<Integer, Tile>> tileMap = new HashMap<Integer, Map<Integer, Tile>>();
	
	private ArrayList<Tile> scopeTileArray = new ArrayList<Tile>(); 
	
	public static int WIDTHSLOPE=15;
	private static final int DEFAULTTILESIZE = 45;
	public static final int ELEVATIONHEIGHT=15;	
	
	private Rectangle rCatch;
	private static int width = 0, height = 0;
	private int firstIndex=0, lastIndex=0, scopeWidth = 0, scopeHeight = 0;
 
	BufferedImage CombinedTileImage;
	
	public static Map<String, BufferedImage> TileImageMap=new HashMap<String, BufferedImage>();

	private ViewFrame viewframe;
	
	private Polygon p, p2;
	
	public PlanetMap(BufferedReader mapfile, BufferedReader elevationfile) {
		// master array that contains ALL the tiles in the map
		ArrayList mapParser = parsemap(mapfile);
		
		if(ISOMETRIC){
			p = new Polygon();
			p2 = new Polygon();
		}
		
		// creates each tile and puts it in tilearray
		for (int j = 0; j < height; j++) {
			String line = (String) mapParser.get(j);
			
			for (int i = 0; i < width; i++) {
				if (i < line.length()) {
					char ch = line.charAt(i);
					mapArray.add(Character.toString(ch));
				}
			}
		}
		
		//mapArrayHighRes = ArrayHandler.resolutionIncrease(mapArray, Tile.getSizeX()/WIDTHSLOPE);
		terMap = ArrayHandler.tMap(mapArray, width, height);
		terrainMap =ArrayHandler.HighResMap(mapArray, Tile.getSizeX()/WIDTHSLOPE, width, height);
		
		ArrayList elevationParser = parsemap(elevationfile);

		// creates each tile and puts it in tilearray
		for (int j = 0; j < height; j++) {
			String line = (String) elevationParser.get(j);
			
			for (int i = 0; i < width; i++) {
				if (i < line.length()) {
					char ch = line.charAt(i);
					elevationArray.add(Character.toString(ch));
				}
			}
		}
		
		elevationArrayHighRes = ArrayHandler.resolutionIncrease(elevationArray, 1);
		elevationMap =ArrayHandler.HighResMap(elevationArray, Tile.getSizeX()/WIDTHSLOPE, width, height);
		elevationMapLowRes = ArrayHandler.HighResMap(elevationArray, 1, width, height);
		
		tilearray=transitionAlgo( mapArray,elevationArray, height, width);
		
		//tilearray => tileMap
		tileMap = ArrayHandler.tileMap(tilearray, height, width);
		
		// defines the catch area of the map in which tiles may have to be displayed
		rCatch = new Rectangle();	
		
		init(PlayScreen.SCREEN_X, PlayScreen.SCREEN_Y, PlayScreen.screenSizeX, PlayScreen.screenSizeY, false);
	}
	
	private ArrayList parsemap(BufferedReader mapfile){
		ArrayList mapParser = new ArrayList();
		
		// loads the tiles from the txt file
		while (true) {
			try{
				String line = mapfile.readLine();
			
				height++;
				if (line == null) {
					mapfile.close();
					break;
				}
				if (!line.startsWith("!")) {
					mapParser.add(line);
					width = Math.max(width, line.length());
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		
		}
		height = mapParser.size();
		
		return mapParser;
	}
	
	private void init(int viewX, int viewY, int screenSizeX, int screenSizeY, boolean reSized){

		// sets it to the coordinate of the view frame, plus the screen height and width, 
		// extends it in both directions by the screen scrolling speed to ensure tiles
		// affected in the next screen movement will be displayed
		rCatch.setBounds(viewX - ViewFrame.SCROLLSPEED - 1, viewY - ViewFrame.SCROLLSPEED -1, 
							screenSizeX + ViewFrame.SCROLLSPEED * 2 + 2, 
							screenSizeY + ViewFrame.SCROLLSPEED * 2 + 2);

		
		// initializes the list that contains only the tiles in the catch zone
		
		// find the first tile to be included in the catch zone (going row by row)
		if (!reSized){
			while(!tilearray.get(firstIndex).getR().intersects(rCatch)){
					firstIndex++;
					if (firstIndex > width * height) break;
				}
		}
		
		for (int l = 0; l < height - firstIndex / width; l++){
			lastIndex = firstIndex + l*width;
			
			// adds each tile in the row until it is outside of the catch zone
			while(lastIndex < height * width && lastIndex < (firstIndex / width + l + 1)*width){
				scopeTileArray.add(tilearray.get(lastIndex));
				if(tilearray.get(lastIndex).getR().x > (int) rCatch.getMaxX()) {
					lastIndex++;
					break;
				} else 
					lastIndex++;	
			}

			if (l==0) scopeWidth = lastIndex - firstIndex; 
			
			// then moves to the next row, unless it is already outside of the catch zone
			if (tilearray.get(firstIndex + l*width).getR().y > (int) rCatch.getMaxY()) {
				scopeHeight++;
				break;	
			} else 
				scopeHeight++;
						
		}
				
	}
	
	public void reInit(int viewX, int viewY, int screenSizeX, int screenSizeY){
		scopeWidth = 0;
		scopeHeight = 0;
		lastIndex = firstIndex;
		scopeTileArray.clear();
		init(viewX, viewY, screenSizeX, screenSizeY, true);
	}
	

	public void update(int viewX, int viewY) {
		
		// reset the catch zone based only on the new screen coordinates (width and height remain unchanged)
		rCatch.setLocation(viewX, viewY);
		
		// ---------------------------------------------------------------------------------------------------------------------------
		// each 'if' below checks if the catch zone has moved too much in that direction and add the new row / column that is now in the catch zone
		
		
		// if the screen has moved enough to the left, adds a column to the left (using the right index from the big arraylist: tilearray) 
		if (rCatch.x < scopeTileArray.get(0).getR().x && scopeTileArray.get(0).getR().x > 0){
			
			for (int i = 0; i< scopeHeight; i++){
				scopeTileArray.add(scopeWidth * (scopeHeight -1 - i), tilearray.get(firstIndex - 1 + (scopeHeight - 1-i) * width));
			}
			firstIndex--;
			scopeWidth++;
		} 

		// if the screen has moved enough to the left, adds a column to the left
		if((int) rCatch.getMaxX() > (int) scopeTileArray.get(scopeTileArray.size()-1).getR().getMaxX() && 
					scopeTileArray.get(scopeTileArray.size()-1).getR().getMaxX() < width * Tile.getSizeX() - 1){

			for (int i = 0 ; i<scopeHeight; i++){
				scopeTileArray.add(scopeWidth * (scopeHeight - i), tilearray.get(firstIndex + scopeWidth + (scopeHeight - 1 - i)*width));
			}
			scopeWidth++;
		}

		// if the screen has moved enough to lower y (upward in Java), adds a row at the beginning
		if (rCatch.y < scopeTileArray.get(0).getR().y && scopeTileArray.get(0).getR().y > 0){

			for (int i = 0; i<scopeWidth ; i++){
				scopeTileArray.add(i, tilearray.get(firstIndex - width + i));
			}
			firstIndex -= width;
			scopeHeight++;
		}

		// if the screen has moved enough to higher y (downward in Java), appends a row
		if(rCatch.getMaxY() > (int) scopeTileArray.get(scopeTileArray.size()-1).getR().getMaxY() && 
			scopeTileArray.get(scopeTileArray.size()-1).getR().getMaxY() < height * Tile.getSizeX() -1){

			for (int i = 0; i< scopeWidth; i++){
				scopeTileArray.add(scopeWidth * scopeHeight + i, tilearray.get(firstIndex + scopeHeight * width + i));
			}
			scopeHeight++;
		}
		
		
		
		// ---------------------------------------------------------------------------------------------------------------------------
		// each 'if' below checks if the catch zone has moved too much in a direction and removes the row / column that are now out of the catch zone
		
		// if the screen has moved too far to the right, removes the first column whose tiles are now out of the catch zone
		if(rCatch.x > (int) scopeTileArray.get(0).getR().getMaxX()){

			for (int i = 0; i < scopeHeight; i++){
				scopeTileArray.remove(scopeWidth*(scopeHeight - 1 - i));
			}
			firstIndex++;
			scopeWidth--;
		}

		// if the screen has moved too far to the left, removes the last column whose tiles are now out of the catch zone
		if((int) rCatch.getMaxX() < scopeTileArray.get(scopeTileArray.size()-1).getR().x){

			for (int i = 0; i< scopeHeight; i++){
				scopeTileArray.remove(scopeWidth*(scopeHeight -i) -1 );
			}
			scopeWidth--;
		}		

		// if the screen has moved too far toward higher y (downward in Java), removes the first row whose tiles are now out of the catch zone
		if (rCatch.y > (int) scopeTileArray.get(0).getR().getMaxY()){
			
			// remove line at the bottom
			for (int i = 0; i< scopeWidth; i++){
				scopeTileArray.remove(scopeWidth - 1 -i);
			}
			firstIndex +=width;
			scopeHeight--;
		}

		// if the screen has moved too far toward lower y (upward in Java), removes the last row whose tiles are now out of the catch zone
		if ((int) rCatch.getMaxY() < scopeTileArray.get(scopeTileArray.size()-1).getR().y){
			
			// remove line at the top
			for (int i = 0; i<scopeWidth; i++){
				scopeTileArray.remove( scopeWidth*scopeHeight - 1 -i );
			}
			scopeHeight--;
		}
		
		scopeTileArray.trimToSize();
		
	}
	
	public void render(Graphics g){
		if(ISOMETRIC) {
			renderIsometric(g);
		} else {
			renderBirdView(g);
		}
	}
	
	private void renderIsometric(Graphics g) {
		int pX, pY, elevation;
		
		for (int y = 0; y <tileMap.size(); y++) {
			for (int x = 0; x < tileMap.get(y).size(); x++) {
	
				Tile t = tileMap.get(y).get(x);

				pX = t.getTileX();
				pY= t.getTileY();
				elevation = getElev(x*3,y*3);  //i tried t.getElevation() but that didnt work for some bizarre reason

				//get Isometric positions
				pX = Miscellaneous.carToIsoIndexX(pX, t.getTileY(), t.getTileImage().getWidth(null));
				pY = Miscellaneous.carToIsoIndexY(pY, t.getTileY(), t.getTileImage().getHeight(null));
			
				if(elevation>0){
					
					p.reset();
					p2.reset();
					
					g.drawImage(t.getTileImage(), pX - (int) viewframe.getFrameX(), pY - elevation*ELEVATIONHEIGHT - (int) viewframe.getFrameY(), null);
					
					g.setColor (Resources.elevationColor);
					
					p.addPoint(pX- (int) viewframe.getFrameX(),
						pY- (int) viewframe.getFrameY() + t.getTileImage().getHeight(null)/2 - elevation*ELEVATIONHEIGHT - 2);
					p.addPoint(pX- (int) viewframe.getFrameX(),
						pY- (int) viewframe.getFrameY() + t.getTileImage().getHeight(null)/2 + 2);
					p.addPoint(pX- (int) viewframe.getFrameX() + t.getTileImage().getWidth(null)/2,
						pY- (int) viewframe.getFrameY()+t.getTileImage().getHeight(null)+2);
					p.addPoint(pX- (int) viewframe.getFrameX() + t.getTileImage().getWidth(null)/2,
						pY- (int) viewframe.getFrameY() + t.getTileImage().getHeight(null) - elevation*ELEVATIONHEIGHT - 2);
					g.fillPolygon(p);  
				
					p2.addPoint((pX- (int) viewframe.getFrameX() + t.getTileImage().getWidth(null)),
						pY- (int) viewframe.getFrameY()+t.getTileImage().getHeight(null)/2 - elevation*ELEVATIONHEIGHT - 2);
					p2.addPoint(pX- (int) viewframe.getFrameX() + t.getTileImage().getWidth(null),
						pY- (int) viewframe.getFrameY() + t.getTileImage().getHeight(null)/2 + 2);
					p2.addPoint(pX- (int) viewframe.getFrameX() + t.getTileImage().getWidth(null)/2,
						pY- (int) viewframe.getFrameY() + t.getTileImage().getHeight(null) + 2);
					p2.addPoint(pX- (int) viewframe.getFrameX() + t.getTileImage().getWidth(null)/2,
						pY- (int) viewframe.getFrameY() + t.getTileImage().getHeight(null) - elevation*ELEVATIONHEIGHT - 2);
					g.fillPolygon(p2);  
			
					//Drawing elevation lines
					g.drawLine(pX - (int) viewframe.getFrameX() + t.getTileImage().getWidth(null)/2, 
						pY - (int) viewframe.getFrameY() - elevation*ELEVATIONHEIGHT, 
						pX - (int) viewframe.getFrameX() + t.getTileImage().getWidth(null), 
						pY - (int) viewframe.getFrameY() + t.getTileImage().getHeight(null)/2 - elevation*ELEVATIONHEIGHT);	
					g.drawLine(pX - (int) viewframe.getFrameX(), 
						pY - (int) viewframe.getFrameY() + t.getTileImage().getHeight(null)/2 - elevation*ELEVATIONHEIGHT, 
						pX - (int) viewframe.getFrameX() + t.getTileImage().getWidth(null)/2, 
						pY - (int) viewframe.getFrameY() - elevation*ELEVATIONHEIGHT);
				
				} else {
					g.drawImage(t.getTileImage(), pX - (int) viewframe.getFrameX(), pY -(int) viewframe.getFrameY(), null);
				}
			
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
			}
		}	
	}
	
	private void renderBirdView(Graphics g){
		int pX, pY;
		for (int y = 0; y <tileMap.size() ; y++) {

			for (int x = 0; x < tileMap.get(y).size(); x++) {
				Tile t = tileMap.get(y).get(x);
				pX = t.getTileX();
				pY= t.getTileY();
				g.drawImage(t.getTileImage(), pX - (int) viewframe.getFrameX(), pY -(int) viewframe.getFrameY(), null);
			}
		}
	}
	
	

	public ArrayList<Tile> getTilearray() {
		return tilearray;
	}

	public void setTilearray(ArrayList<Tile> tilearray) {
		this.tilearray = tilearray;
	}
	
	public ArrayList<Tile> getScopeTileArray() {
		return scopeTileArray;
	}


	public void setScopeTileArray(ArrayList<Tile> scopeTileArray) {
		this.scopeTileArray = scopeTileArray;
	}

	//enable to pass height and width as index or pixel
	public int getWidth(boolean inPixel) {
		if (inPixel) {
			if (tilearray.isEmpty()) {
				return DEFAULTTILESIZE * width;
			} else {
				return Tile.getSizeX() * width;
			}

		} else {
			return width;
		}
	}

	public int getHeight(boolean inPixel) {
		if (inPixel) {
			if (tilearray.isEmpty()) {
				return DEFAULTTILESIZE * height;
			} else {
				return Tile.getSizeY() * height;
			}

		} else {
			return height;
		}
	}

public ArrayList<Tile> transitionAlgo(ArrayList<String> tilearray, ArrayList<String> elevationArray, int heightArray, int widthArray){

		
		
		ArrayList<Tile> tilearrayNew=new ArrayList<Tile>();

		String S0,  S1, S2, S3, S4, S5, S6, S7, S8, S9;
		String E0,  E1, E2, E3, E4, E5, E6, E7, E8, E9;
		String ID;
		int xX,yY;
		BufferedImage value;
		
		int elevation =0,elevationUP, elevationDown, elevationRight, elevationLeft = 0;
		boolean eUp = false, eDown=false, eRight=false, eLeft=false;

		
		
		int bugID=0;
		for(int y = 0; y<heightArray;y++){
		for (int x = 0; x < widthArray; x++) {

				elevation =0;
				elevation = Integer.parseInt(elevationMapLowRes.get(y).get(x));
				
				//edge of map problem solving -> "tiles" outside of map => are 0 tiles
				S0=tilearray.get((x+y*widthArray));
				S0=terMap.get(y).get(x);
				
				if(y>0){
					xX=x;
					yY=y-1;
					if(ISOMETRIC){
					xX = Miscellaneous.neighbourX(x,y, 0, -1);
					yY= Miscellaneous.neighbourY(y, 0, -1);
					if(yY>heightArray-1||xX>widthArray-1||xX<0||yY<0){
						xX=x;
						yY=y;
						}
					}
					S1=terMap.get(yY).get(xX);
					elevationUP =Integer.parseInt(elevationMapLowRes.get(yY).get(xX));
				}else{
					S1=S0;
					elevationUP = 0;
				}
				
				if(y>0&&x<widthArray-1){
					xX=x+1;
					yY=y-1;
					if(ISOMETRIC){
					xX = Miscellaneous.neighbourX(x,y, +1, -1);
					yY= Miscellaneous.neighbourY(y, +1, -1);
					if(yY>heightArray||xX>widthArray||xX<0||yY<0){
						xX=x;
						yY=y;
						}
					}
					S5=terMap.get(yY).get(xX);
				}else{
					S5=S0;
				}

				if(x<widthArray-1){
					xX=x+1;
					yY=y;
					if(ISOMETRIC){
					xX = Miscellaneous.neighbourX(x,y, 1, 0);
					yY= Miscellaneous.neighbourY(y, 1, 0);
					if(yY>heightArray-1||xX>widthArray||xX<0||yY<0){
						xX=x;
						yY=y;
						}
					}
					S2=terMap.get(yY).get(xX);
					elevationRight = Integer.parseInt(elevationMapLowRes.get(yY).get(xX));
				}else{
					S2=S0;
					elevationRight = 0;
				}

				if(y<heightArray-1&&x<widthArray-1){
					xX=x+1;
					yY=y+1;
					if(ISOMETRIC){
					xX = Miscellaneous.neighbourX(x,y, 1, 1);
					yY= Miscellaneous.neighbourY(y, 1, 1);
					if(yY>heightArray-2||xX>widthArray-1||xX<0||yY<0){
						xX=x;
						yY=y;
						}
					}
					S6=terMap.get(yY).get(xX);
				}else{
					S6=S0;
				}
				
				if((y<heightArray-1)){
					xX=x;
					yY=y+1;
					if(ISOMETRIC){
					xX = Miscellaneous.neighbourX(x,y, 0, 1);
					yY= Miscellaneous.neighbourY(y, 0, 1);
					if(yY>heightArray||xX>widthArray||xX<0||yY<0){
						xX=x;
						yY=y;
						}
					}
					S3=terMap.get(yY).get(xX);
					elevationDown = Integer.parseInt(elevationMapLowRes.get(yY).get(xX));
				}else{
					S3=S0;
					elevationDown=0;
				}

				if(y<heightArray-1&&x>0){
					xX=x-1;
					yY=y+1;
					if(ISOMETRIC){
					xX = Miscellaneous.neighbourX(x,y, -1, 1);
					yY= Miscellaneous.neighbourY(y, -1, 1);
					if(yY>heightArray||xX>widthArray||xX<0||yY<0){
						xX=x;
						yY=y;
						}
					}
					S7=terMap.get(yY).get(xX);
				}else{
					S7=S0;
				}
				
				if(x>0){
					xX=x-1;
					yY=y;
					if(ISOMETRIC){
					xX = Miscellaneous.neighbourX(x,y, -1, 0);
					yY= Miscellaneous.neighbourY(y, -1, 0);
					if(yY>heightArray-1||xX>widthArray||xX<0||yY<0){
						xX=x;
						yY=y;
						}
					}
					S4=terMap.get(yY).get(xX);
					elevationLeft = Integer.parseInt(elevationMapLowRes.get(yY).get(xX));
				}else{
					S4=S0;
				}
				
				if(y>0&&x>0){
					xX=x-1;
					yY=y-1;
					if(ISOMETRIC){
					xX = Miscellaneous.neighbourX(x,y, -1, -1);
					yY= Miscellaneous.neighbourY(y, -1, -1);
					if(yY>heightArray||xX>widthArray||xX<0||yY<0){
						xX=x;
						yY=y;
						}
					}
					S8=terMap.get(yY).get(xX);

				}else{
					S8=S0;
				}

				S9=S1;


				
				E0=S0;
				if(S0.equals(S1)){
					E1=S0;
				}else{
					E1=S0+"_"+S1+"1";
				}

				if(S0.equals(S5)&&S0.equals(S1)&&S0.equals(S2)){
					E5=S0;
				}else{
					E5=S0+"_"+S1+"1"+"_"+S5+"5"+"_"+S2+"2";
				}

				if(S0.equals(S2)){
					E2=S0;
				}else{
					E2=S0+"_"+S2+"2";
				}
				
				if(S0.equals(S6)&&S0.equals(S2)&&S0.equals(S3)){
					E6=S0;
				}else{
					E6=S0+"_"+S2+"2"+"_"+S6+"6"+"_"+S3+"3";
				}				

				if(S0.equals(S3)){
					E3=S0;
				}else{
					E3=S0+"_"+S3+"3";
				}

				if(S0.equals(S7)&&S0.equals(S3)&&S0.equals(S4)){
					E7=S0;
				}else{
					E7=S0+"_"+S3+"3"+"_"+S7+"7"+"_"+S4+"4";
				}
				
				if(S0.equals(S4)){
					E4=S0;
				}else{
					E4=S0+"_"+S4+"4";
				}

				if(S0.equals(S8)&&S0.equals(S1)&&S0.equals(S4)){
					E8=S0;
				}else{
					E8=S0+"_"+S4+"4"+"_"+S8+"8"+"_"+S1+"1";
				}
				
				//full ID of the picture
				ID = E0+E1+E2+E3+E4+E5+E6+E7+E8;
				

				//logic to check elevation boundary:

				eUp=elevation==elevationUP?false:true;
				eLeft=elevation==elevationLeft?false:true;
				eDown=elevation==elevationDown?false:true;
				eRight=elevation==elevationRight?false:true;
				

				//Create pictures but first checks whether the picture is already in there. If then it will not create it again
				value=TileImageMap.get(ID);
				if(value==null){
					if(ISOMETRIC){
						//System.out.println("x ="+x+" y ="+y+" elevation ="+elevation);
						CombinedTileImage = ImageHandler.ImageMerge(Resources.G.getType(), Tile.getSizeX(),Tile.getSizeY(),E0,  E1, E2, E3, E4, E5, E6, E7, E8);
						CombinedTileImage = ImageHandler.resize(CombinedTileImage, 46, 46);
						CombinedTileImage = ImageHandler.ImageRotationAny(CombinedTileImage, 45);
						CombinedTileImage= ImageHandler.makeColorTransparent(CombinedTileImage, Color.BLACK);
						CombinedTileImage= ImageHandler.resize(CombinedTileImage,CombinedTileImage.getWidth(),CombinedTileImage.getHeight()/2);
						TileImageMap.put(ID,CombinedTileImage);
					}else{
					
					TileImageMap.put(ID, ImageHandler.ImageMerge(Resources.G.getType(), Tile.getSizeX(),Tile.getSizeY(),E0,  E1, E2, E3, E4, E5, E6, E7, E8));
					}
				}				
				
				if(elevation==2){
					System.out.println("elevation = "+elevation);
					}
				
				
				Tile t = new Tile(x, y, ID, eUp, eDown, eRight, eLeft,elevation);
								
				if(t.getElevationTile()==1){
					System.out.println("elevation = "+t.getElevationTile());
					}
				
				tilearrayNew.add(t);
		}	
		}
		
		return tilearrayNew;
			
	}

	public static BufferedImage getImage(String imageName)
	{
	return TileImageMap.get(imageName);
	}

	public static int getTer(double x, double y)
	{
		int speed = 0;
		try{
			speed = Resources.getSpeed(terrainMap.get((int)y).get((int)x));
		}catch (Exception err ) {
			speed = 0;
		}
		
		return speed;
			
	}
	
	public static int getElev(double x, double y)
	{
		int elevation =0;
		try{
			elevation= Integer.parseInt(elevationMap.get((int)y).get((int)x));
		}catch (Exception err ) {
			elevation =0;
		}
		
		return elevation;
	
		}

	public void setViewframe(ViewFrame viewframe) {
		this.viewframe = viewframe;
	}

	public static int getWidth() {
		return width;
	}
	
}
