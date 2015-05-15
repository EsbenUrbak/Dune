package planet;

import gameEngine.StartingClass;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoadMap {

	private ArrayList<Tile> tilearray = new ArrayList<Tile>();
	private int width, height;
	private final int DEFAULTPIXELSIZE = 40;
	
	public void loadMap(String filename) throws IOException {
		ArrayList lines = new ArrayList();
		width = 0;
		height = 0;

		BufferedReader reader = new BufferedReader(new FileReader(filename));
		while (true) {
			String line = reader.readLine();
			height ++;
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
				System.out.println(i + "is i ");

				if (i < line.length()) {
					char ch = line.charAt(i);
					Tile t = new Tile(i, j, Character.getNumericValue(ch));
					tilearray.add(t);
				}

			}
		}

	}

	public ArrayList<Tile> getTilearray() {
		return tilearray;
	}

	public void setTilearray(ArrayList<Tile> tilearray) {
		this.tilearray = tilearray;
	}

	public int getWidth(boolean inPixel) {
		if (inPixel){
			if(tilearray.isEmpty()) {
				return DEFAULTPIXELSIZE * width;
			} else {
				return tilearray.get(0).getSizeX() * width;
			}
			
		} else {
			return width;
		}
	}

	public int getHeight(boolean inPixel) {
		if (inPixel){
			if(tilearray.isEmpty()) {
				return DEFAULTPIXELSIZE * height;
			} else {
				return tilearray.get(0).getSizeY() * height;
			}
			
		} else {
			return height;
		}
	}	
	
}
