package com.baseframework.util;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

import com.dune.planet.Tile;

public class ArrayHandler {

	public static ArrayList<String> resolutionIncrease(ArrayList<String> original, int expansionFactor) {
		
		ArrayList<String> expanded = new ArrayList<String>();
		
		for(int j=0; j<original.size();j++){
			for(int i=0; i<expansionFactor;i++){
				expanded.add(original.get(j));
				}	
			}
				
		return expanded;  
    }
	
	public static Map<Integer, Map<Integer, String>> HighResMap(ArrayList<String> original, int expansionFactor, int width, int height){
		
		Map<Integer, Map<Integer, String>> TerrainMap = new HashMap<Integer, Map<Integer, String>>();

		int k=0;
		int a=0;
		
		for(int y=0;y<height*expansionFactor;y++){
		Map<Integer, String> TerrainMapX = new HashMap<Integer, String>();
		
		for(int x=0;x<width*expansionFactor;x++){
			TerrainMapX.put(x,original.get(k+a*width));
			
			if(((x+1)%expansionFactor)==0){
				k++;	
		}

		}
		TerrainMap.put(y,TerrainMapX);
		
		if(((y+1)%expansionFactor)==0){
		a++;	
		}
		k=0;
		
		}
						
		return TerrainMap;
	}
			
public static Map<Integer, Map<Integer, Tile>> tileMap(ArrayList<Tile> original, int width, int height){
		
		Map<Integer, Map<Integer, Tile>> TerrainMap = new HashMap<Integer, Map<Integer, Tile>>();

		int k=0;
		int a=0;
		
		for(int y=0;y<height;y++){
		Map<Integer, Tile> TerrainMapX = new HashMap<Integer, Tile>();
		
			for(int x=0;x<width;x++){
				TerrainMapX.put(x,original.get(x+y*width));
			}

		TerrainMap.put(y,TerrainMapX);
		
		}
						
		return TerrainMap;
	}	
	
	public static Map<Integer, Map<Integer, String>> tMap(ArrayList<String> original, int width, int height){
		
		Map<Integer, Map<Integer, String>> TerrainMap = new HashMap<Integer, Map<Integer, String>>();

		
		for(int y=0;y<height;y++){
		Map<Integer, String> TerrainMapX = new HashMap<Integer, String>();
		
			for(int x=0;x<width;x++){
				TerrainMapX.put(x,original.get(x+y*width));
			}

		TerrainMap.put(y,TerrainMapX);
		
		}
						
		return TerrainMap;
	}
	
	
	
	
}
