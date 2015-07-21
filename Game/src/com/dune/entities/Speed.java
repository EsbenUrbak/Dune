package com.dune.entities;


import com.baseframework.util.Miscellaneous;
import com.dune.planet.PlanetMap;


public class Speed {

	public static int speed(int x, int y, int xNext, int yNext) {
		double speed, speedTerrain, speedSlope=100;
		double Alevel1, Blevel1, xX = 0, xXA1, xXA2, xXB1, xXB2, SlopeASteepness, SlopeBSteepness;
		double Alevel2, Blevel2, yY = 0, yYA1, yYA2, yYB1, yYB2;
		
				
		xX=(int) (x/(double)PlanetMap.WIDTHSLOPE);
		yY=(int) (y/(double)PlanetMap.WIDTHSLOPE);
		
		speedTerrain=PlanetMap.getTer(xX,yY);
		speed = speedTerrain;
		
		//finding the diagonal points 
		xXA1=(int) (xX-1);
		yYA1=(int) (yY-1);
		
		xXA2=(int) (xX+1);
		yYA2=(int) (yY+1);
		
		xXB1=(int) (xX+1);
		yYB1=(int) (yY-1);
		
		xXB2=(int) (xX-1);
		yYB2=(int) (yY+1);
		
		
		//finding whether on a slope
		Alevel1=PlanetMap.getElev(xXA1,yYA1);
		Alevel2=PlanetMap.getElev(xXA2,yYA2);
		SlopeASteepness = (Alevel1-Alevel2);

		Blevel1=PlanetMap.getElev(xXB1,yYB1);
		Blevel2=PlanetMap.getElev(xXB2,yYB2);
		SlopeBSteepness = (Blevel1-Blevel2);
		
		
		//if on a slope we need to alter the speed accordingly
		if(SlopeASteepness!=0||SlopeBSteepness!=0){
			int movingRight, movingUp;
			double x1, y1, x2, y2, h1_1, h1_2, h2_2, h2_1, pX, pY;
			double height, heightNext, changeInHeight;
			double angle, dist;
			
			//finding the angle of the movement
			dist=Math.sqrt((x-xNext)*(x-xNext)+(y-yNext)*(y-yNext));
			angle = Math.acos(Math.abs((double)(x-xNext))/dist);
			
			//Using a bilinear intepolation to find the height difference. Please see Miscellaneous (and wikipedia for the formula)
			//These are the corner points of my know rectangle
			x1 = (xXB2+1)*PlanetMap.WIDTHSLOPE;
			x2 = (xXA2+1)*PlanetMap.WIDTHSLOPE;
			y1 = (yYB2+1)*PlanetMap.WIDTHSLOPE;
			y2 = (yYA1+1)*PlanetMap.WIDTHSLOPE;
			h1_1 = Blevel2;
			h2_1 = Alevel2;
			h1_2 = Alevel1;
			h2_2 = Blevel1;
			
			height = Miscellaneous.BilinearInterpolator(x1, y1, x2, y2, h1_1, h1_2, h2_2, h2_1, x, y);	
			
			//finding whether we are moving to the moving to the right or left
			movingRight=(x-xNext)<0?1:-1;
			//finding whether we are moving to the moving to the right or left
			movingUp=(y-yNext)<0?1:-1;
		
			pX= x+movingRight*Math.cos(angle);
			pY= y+movingUp*Math.sin(angle);
			
			heightNext = Miscellaneous.BilinearInterpolator(x1, y1, x2, y2, h1_1, h1_2, h2_2, h2_1, pX, pY);	
			
			changeInHeight=heightNext-height;
			
			if(changeInHeight>0){
				//squad is moving uphill
				speed = speedTerrain - speedSlope;
			}else{
				if(changeInHeight<0){
				//squad is moving downhill
				speed = speedTerrain + speedSlope;
				}
				
			}
			
		}
		
				
		return (int) speed;
	}
	
	
	
	
}
