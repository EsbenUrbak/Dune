package com.dune.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.baseframework.game.main.Resources;
import com.dune.planet.PlanetMap;
import com.dune.planet.Tile;

public class Speed {
	private static double speed, speedTerrain, speedSlope=100;
	static String speedID;
	private static double AlevelX, BlevelX, slopeX, slopeXFactor, xX,xXA,xXB, SlopeXSteepness,SlopeYSteepness;
	private static double AlevelY, BlevelY, slopeY, slopeYFactor, Resolution,yY,yYA,yYB;
	private static double angle, dist, speedTerrainX, speedTerrainY, speedX, speedY; 
	private static boolean movingRight, movingUp;
	
	
	public static int Speed(int x, int y, int xNext, int yNext) {
		
		//finding the angle of the movement
		dist=Math.sqrt((x-xNext)*(x-xNext)+(y-yNext)*(y-yNext));
		angle = Math.acos(Math.abs(x-xNext)/dist);
		xX=(int) (x/(double)PlanetMap.WIDTHSLOPE);
		yY=(int) (y/(double)PlanetMap.WIDTHSLOPE);
		
		xXA=(int) ((x-(double)PlanetMap.WIDTHSLOPE)/(double)PlanetMap.WIDTHSLOPE);
		yYA=(int) ((y-(double)PlanetMap.WIDTHSLOPE)/(double)PlanetMap.WIDTHSLOPE);
		
		xXB=(int) ((x+(double)PlanetMap.WIDTHSLOPE)/(double)PlanetMap.WIDTHSLOPE);
		yYB=(int) ((y+(double)PlanetMap.WIDTHSLOPE)/(double)PlanetMap.WIDTHSLOPE);
		
		
		speedTerrain=PlanetMap.getTer(xX,yY);
		speedTerrainX=(speedTerrain*Math.cos(angle));
		speedTerrainY=(speedTerrain*Math.sin(angle));
		
		
		//X direction
		//finding whether on a slope
		AlevelX=PlanetMap.getElev(xXA,yY);
		BlevelX=PlanetMap.getElev(xXB,yY);
		SlopeXSteepness = (BlevelX-AlevelX);
		slopeX = (BlevelX-AlevelX)/((double)PlanetMap.WIDTHSLOPE);
		
		//slope factor - is the slope positive or negative
		slopeXFactor=slopeX<0?-1:1;
		
		//finding whether we are moving to the moving to the right or left
		movingRight=(x-xNext)<0?true:false;
		
		if(movingRight){
			speedX = speedTerrainX -speedSlope*slopeXFactor*Math.abs(SlopeXSteepness);
		}else{
			speedX = speedTerrainX +speedSlope*slopeXFactor*Math.abs(SlopeXSteepness);	
		}
		
		//Y direction
		//finding whether on a slope
		AlevelY=PlanetMap.getElev(xX,yYA);
		BlevelY=PlanetMap.getElev(xX,yYB);
		SlopeYSteepness = (BlevelY-AlevelY);
		slopeY = (BlevelY-AlevelY)/((double)PlanetMap.WIDTHSLOPE);
		
		//slope factor
		slopeYFactor=slopeY<0?-1:1;
		
		//finding whether we are moving to the moving to the right or left
		movingUp=(y-yNext)<0?true:false;
		
		if(movingUp){
			speedY = speedTerrainY -speedSlope*slopeYFactor*Math.abs(SlopeYSteepness);
		}else{
			speedY = speedTerrainY +speedSlope*slopeYFactor*Math.abs(SlopeYSteepness);	
		}
		
		
		speed=(int) Math.sqrt(speedY*speedY+speedX*speedX);
				
		return (int) speed;
	}
	
}
