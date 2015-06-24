package com.dune.entities;

import java.util.ArrayList;

import com.dune.planet.PlanetMap;
import com.dune.planet.Tile;

public class AStar {

	int xStart, yStart, xEnd, yEnd;
	int xNode, yNode;
	int stepSize = 10;
	int speed=150;
	int g, f, h;
	private ArrayList<Node> openList = new ArrayList<Node>();	
	private ArrayList<Node> closedList = new ArrayList<Node>();		
	private String terrain;
	Node point;
	
	//input has to be in absolute coordinates!
	public AStar(int xStart, int yStart, int xEnd, int yEnd) {
		
		//Adding the starting to openlist
		point = new Node(xStart, yStart,PlanetMap.mapArray.get(xStart+yStart*PlanetMap.width));
		openList.add(point);
		
		//finding all adjacent to the starting square
		for(int i =-1;i<1;i++){
			for(int j =-1;j<1;j++){
			//Make sure i do not add the center square again
			if(i==0&&j==0){
			}else{
			xNode=xStart+stepSize*i;
			yNode=yStart+stepSize*j;	
			
			//finding terrain type for this node point
			terrain=PlanetMap.mapArray.get(xNode+yNode*PlanetMap.width);
			
			
			// calculate g, h and F scores for the tile
			g=(int) Math.sqrt((Math.pow(Math.abs(i),2)+Math.pow(Math.abs(j),2))*stepSize)/speed;
			

			//adding the node point unless it is water.
			if(terrain.equals("W")){
			}else{
			point = new Node(xNode, yNode,terrain);
			openList.add(point);
			}
			
			
			}}
		}
		
		
		
	}
	
	
	public void point(int x, int y){
		
	}
	
	
}
