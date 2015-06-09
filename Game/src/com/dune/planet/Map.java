package com.dune.planet;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import com.baseframework.screen.PlayScreen;

public class Map {
	
	private ArrayList<Tile> tilearray = new ArrayList<Tile>();	
	private ArrayList<Tile> scopeTileArray = new ArrayList<Tile>(); 
	private int width = 0, height = 0, firstIndex=0, lastIndex=0, scopeWidth = 0, scopeHeight = 0;
	private Rectangle rCatch; 
	private final int DEFAULTTILESIZE = 40;

	public Map(URL mapURL) throws IOException {
		
		// master array that contains ALL the tiles in the map
		ArrayList lines = new ArrayList();
		
		// defines the catch area of the map in which tiles may have to be displayed
		rCatch = new Rectangle();		
		
		// loads the tiles from the txt file
		BufferedReader reader = new BufferedReader(new InputStreamReader(mapURL.openStream()));
		
		while (true) {
			String line = reader.readLine();
			height++;
			if (line == null) {
				reader.close();
				break;
			}

			if (!line.startsWith("!")) {
				lines.add(line);
				width = Math.max(width, line.length());

			}
		}
		height = lines.size();

		for (int j = 0; j < height; j++) {
			String line = (String) lines.get(j);
			for (int i = 0; i < width; i++) {
				// System.out.println(i + "is i ");

				if (i < line.length()) {
					char ch = line.charAt(i);
					Tile t = new Tile(i, j, Character.getNumericValue(ch));
					tilearray.add(t);
				}

			}
		}	
		init(PlayScreen.INITIALSCREENX, PlayScreen.INITIALSCREENY, PlayScreen.screenSizeX, PlayScreen.screenSizeY, false);
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


}