package com.dune.entities;

import java.util.ArrayList;
import java.util.Map;

import com.baseframework.game.main.Resources;
import com.dune.planet.PlanetMap;
import com.dune.planet.Tile;

public class Speed {
	private static int speed, speedTerrain, speedSlope=50;
	static String speedID;
	private static double AlevelX, BlevelX, slopeX, slopeXFactor, xX;
	private static double AlevelY, BlevelY, slopeY, slopeYFactor, Resolution,yY;
	private static double angle, dist, speedTerrainX, speedTerrainY, speedX, speedY; 
	private static boolean movingRight, movingUp;
	
	
	public static int Speed(int x, int y, int xNext, int yNext) {
		

		Resolution=Tile.getSizeX()/PlanetMap.WIDTHSLOPE;
		
		//finding the angle of the movement
		dist=Math.sqrt((x-xNext)*(x-xNext)+(y-yNext)*(y-yNext));
		angle = Math.acos(Math.abs(x-xNext)/dist);
		xX=((int) ((x)/PlanetMap.WIDTHSLOPE));
		yY=((int) ((y)/PlanetMap.WIDTHSLOPE));
		speedID = PlanetMap.terrainMap.get(yY).get(xX);
		speedTerrain=Resources.getSpeed(speedID);
		speedTerrainX=(speedTerrain*Math.cos(angle));
		speedTerrainY=(speedTerrain*Math.sin(angle));
		
		
		//X direction
		//finding whether on a slope
		AlevelX=Integer.parseInt(PlanetMap.elevationMap.get(yY).get(xX));
		BlevelX=Integer.parseInt(PlanetMap.elevationMap.get(yY).get(xX+PlanetMap.WIDTHSLOPE));
		slopeX = (BlevelX-AlevelX)/PlanetMap.WIDTHSLOPE;
		
		//slope factor
		slopeXFactor=slopeX<0?-1:1;
		
		//finding whether we are moving to the moving to the right or left
		movingRight=(x-xNext)<0?true:false;
		
		if(movingRight){
			speedX = speedTerrainX -speedSlope*slopeXFactor*Math.abs(slopeX);
		}else{
			speedX = speedTerrainX +speedSlope*slopeXFactor*Math.abs(slopeX);	
		}
		
		//Y direction
		//finding whether on a slope
		AlevelY=Integer.parseInt(PlanetMap.elevationMap.get(yY).get(xX));
		BlevelY=Integer.parseInt(PlanetMap.elevationMap.get(yY+1).get(xX));
		slopeY = (BlevelY-AlevelY)/PlanetMap.WIDTHSLOPE;
		
		//slope factor
		slopeYFactor=slopeY<0?-1:1;
		
		//finding whether we are moving to the moving to the right or left
		movingUp=(y-yNext)<0?true:false;
		
		if(movingUp){
			speedY = speedTerrainY -speedSlope*slopeYFactor*Math.abs(slopeY);
		}else{
			speedY = speedTerrainY +speedSlope*slopeYFactor*Math.abs(slopeY);	
		}
		
		
		speed=(int) Math.sqrt(speedY*speedY+speedX*speedX);
				
		return speed;
	}
	
}
