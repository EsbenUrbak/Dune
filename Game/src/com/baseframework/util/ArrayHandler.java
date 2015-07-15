package com.baseframework.util;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

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
		
		for(int i=0;i<height*expansionFactor;i++){
		Map<Integer, String> TerrainMapX = new HashMap<Integer, String>();
		
		for(int j=0;j<width*expansionFactor;j++){
			TerrainMapX.put(j,original.get(k+a*width));
			
			if(((j+1)%expansionFactor)==0){
				k++;	
		}

		}
		TerrainMap.put(i,TerrainMapX);
		
		if(((i+1)%expansionFactor)==0){
		a++;	
		}
		k=0;
		
		}
						
		return TerrainMap;
	}
			
	
	
	
}
