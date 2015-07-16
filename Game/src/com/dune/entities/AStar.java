package com.dune.entities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.baseframework.game.main.Resources;
import com.dune.planet.PlanetMap;
import com.dune.planet.Tile;

public class AStar {
	static Map<String, Node> nodeMap=new HashMap<String, Node>();
	
	 static int stepSize = 10;
	 
	//input has to be in absolute coordinates!
	public static Map<String, ArrayList<Integer>> AStar(int xStart, int yStart, int xEnd, int yEnd) {
		 int xNode;
		 int yNode;
		 int xDelta;
		 int yDelta;
		 int Delta;

		 int g;
		 int f;
		 int h;

		 Node startPoint;
		 Node currentNode;
		 Node intermidiateNode;
		 String nextNodeID;
		ArrayList<Node> openList = new ArrayList<Node>();	
		ArrayList<Node> closedList = new ArrayList<Node>();	
		ArrayList<Integer> xPath = new ArrayList<Integer>();
		ArrayList<Integer> yPath = new ArrayList<Integer>();

		
		//Adding the starting to openlist
		startPoint = new Node(xStart, yStart,PlanetMap.mapArray.get((xStart+yStart*PlanetMap.width)/Tile.getSizeX()),"x=" + 0 + "_y=" + 0,"x=" + 0 + "_y=" + 0,0,0,0);
		openList.add(startPoint);
	
		xNode = 0;
		yNode = 0;
		xDelta=stepSize+1;
		yDelta=stepSize+1;
		Delta=stepSize+1;
		
		currentNode=startPoint;
		
		while(Delta>stepSize){
			
			

			closedList.add(currentNode);
			//remove it from the openlist
			openList.remove(currentNode);
			

			
			//adding adjacent nodes to the openlist if they are not already on it and checks which is the correct parent for these nodes
			openList=adjacentNodes(currentNode, openList,closedList, xStart, yStart, xEnd, yEnd);
			currentNode = lowestFNode(openList);
			//System.out.println("Node ID ="+currentNode.getNodeID() + "Parent Node ID ="+currentNode.getParentNodeID() + " G SScore = " +currentNode.getGNode()+ " H Score = " +currentNode.getHNode()+" F Score = " +currentNode.getFNode()+" Terrain = " +currentNode.getTerrainNode());
			xNode = currentNode.getxNode();
			yNode = currentNode.getyNode();;
			
			Delta = (int) Math.sqrt((Math.pow(xEnd-xNode,2)+Math.pow(yEnd-yNode,2)));
			xDelta=(int) Math.abs((xEnd-(xNode+(int)Squad.xImagine/2f)));
			yDelta=(int) Math.abs((yEnd-(yNode+(int)Squad.yImagine)));
			
			
		}
		
		closedList.add(currentNode);
		
		//create a map of find the path
		for(int i=closedList.size()-1;i>-1;i--){
			intermidiateNode= closedList.get(i);
			nodeMap.put(intermidiateNode.getNodeID(),intermidiateNode);
		}
		
		//now going back through the closed list to find the path and make the path.
		//First we add end point
		xPath.add(xEnd);
		yPath.add(yEnd);
		
		intermidiateNode= closedList.get(closedList.size()-1);
		while(intermidiateNode!=startPoint){
			xPath.add(intermidiateNode.getxNode());
			yPath.add(intermidiateNode.getyNode());
			nextNodeID = intermidiateNode.getParentNodeID();
			intermidiateNode =getNode(nextNodeID);
		}
		


		//inverting the nodes in the x and y list
		Collections.reverse(xPath);
		Collections.reverse(yPath);
		
		  Map<String,ArrayList<Integer>> map =new HashMap();
		  map.put("x",xPath);
		  map.put("y",yPath);
		  return map;

		  
		  
		  
		
	}
	
	public static Node getNode(String NodeID)
	{
		return nodeMap.get(NodeID);
	}
	

	
	public static ArrayList<Node> adjacentNodes(Node currentNodeFunc,ArrayList<Node> openListFunc,ArrayList<Node> closedListFunc, int xStart, int yStart, int xEnd,int yEnd) {

		 Node point;

		String nodeID;
		String terrain,terrain_dia1,terrain_dia2;
		int xNodef;
		int yNodef;
		int terrainNumber;

		double gFunc, hFunc, fFunc = 0;
		int nodeSpeed;

		
		boolean isOnOpenList;
		int openListIndex = 0;
		boolean isOnClosedList;
		double sqrt,hItermidiate;

		
		int xParent = currentNodeFunc.getxNode();
		int yParent = currentNodeFunc.getyNode();
		double gParent = currentNodeFunc.getGNode();
		String nodeParentID=currentNodeFunc.getNodeID();
		int openListSize = openListFunc.size();
		int closedListSize= closedListFunc.size();

		int xNodef_dia1, yNodef_dia1,terrainNumber_dia1,nodeSpeed_dia1 = 0, j1,i1;
		int xNodef_dia2, yNodef_dia2,terrainNumber_dia2,nodeSpeed_dia2 = 0, j2,i2;
		
		// finding all adjacent to the starting square
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				// finding neighbours coordinates
				xNodef = xParent + stepSize * (i-1);
				yNodef = yParent + stepSize * (j-1);
				nodeID = "x=" + (xNodef - xStart)/stepSize + "_y=" + (yNodef - yStart)/stepSize;
				// finding terrain type for this node point
				terrainNumber =(xNodef/Tile.getSizeX()) + ((yNodef/Tile.getSizeX())* PlanetMap.width);
				terrain = PlanetMap.mapArray.get(terrainNumber*1);
				//nodeSpeed=Resources.getSpeed(terrain);
				nodeSpeed=Speed.Speed(xParent, yParent, xNodef, yNodef);
				
				//Check to make sure the squad doesnt move accross diagonals where it will get stuck
				if(Math.abs(i-1)==Math.abs(j-1)){
					i1=ifunction1((i-1),(j-1));
					j1=jfunction1((i-1),(j-1));
					xNodef_dia1 = xParent + stepSize * i1;
					yNodef_dia1 = yParent + stepSize * j1;
					
					//terrainNumber_dia1 =(xNodef_dia1/Tile.getSizeX()) + ((yNodef_dia1/Tile.getSizeX())* PlanetMap.width);
					//terrain_dia1 = PlanetMap.mapArray.get(terrainNumber_dia1);
					//nodeSpeed_dia1=Resources.getSpeed(terrain_dia1);
					nodeSpeed_dia1=Speed.Speed(xParent, yParent, xNodef_dia1, yNodef_dia1);
					
					i2=ifunction2((i-1),(j-1));
					j2=jfunction2((i-1),(j-1));
					xNodef_dia2 = xParent + stepSize * i2;
					yNodef_dia2 = yParent + stepSize * j2;
					
					//terrainNumber_dia2 =(xNodef_dia1/Tile.getSizeX()) + ((yNodef_dia1/Tile.getSizeX())* PlanetMap.width);
					//terrain_dia2 = PlanetMap.mapArray.get(terrainNumber_dia2);
					//nodeSpeed_dia2=Resources.getSpeed(terrain_dia2);
					nodeSpeed_dia2=Speed.Speed(xParent, yParent, xNodef_dia1, yNodef_dia1);
					
				}
				
				//check whether on the closed list already or is a zero speed tile:
				isOnClosedList = false;
				if(nodeSpeed==0||nodeSpeed_dia1==0||nodeSpeed_dia2==0){

					isOnClosedList=true;
				}else{
				for (int k = 0; k < closedListSize; k++) {

					if (nodeID.equals(closedListFunc.get(k).getNodeID())) {
						isOnClosedList = true;
						break;
					}
				}
				

					if (isOnClosedList) {
					//do nothing
						
					}else{

						// Check whether neighbour is in the openlist already:
						isOnOpenList = false;
						for (int k1 = 0; k1 < openListSize; k1++) {

							if (nodeID.equals(openListFunc.get(k1).getNodeID())) {
								isOnOpenList = true;
								openListIndex=k1;
								break;
							}
											
										}
						if (isOnOpenList) {
							// if already on the list we need to check whether
							// the
							// path to this node is quicker from the current
							// node
							// then earlier recorded

							// Calculating the "cost of moving". I use the time
							// to
							// move as the cost.
							sqrt =Math.sqrt(((double)Math.abs(((double)i) - 1.0) + Math.abs(((double)j)- 1.0)));
							gFunc = gParent+ sqrt * stepSize/ ((double)nodeSpeed);
							
							// check which path has the lowest g:
							if (gFunc < openListFunc.get(openListIndex).getGNode()) {
								// if the g score is lower with the current node
								// then we remove the old node and replace it
								// with
								// updated parameters.
								openListFunc.remove(openListIndex);
								hItermidiate = Math.sqrt(Math.pow(xNodef - xEnd,2)+ Math.pow(yNodef - yEnd,2));
								hFunc = hItermidiate/((double)nodeSpeed);
								// finally f:
								fFunc = gFunc + hFunc;
								// adding the node point to the openlist.
								point = new Node(xNodef, yNodef, terrain, nodeParentID, nodeID, gFunc, hFunc,fFunc);
								openListFunc.add(point);
							}

						}

						else {
							// if the neighbour is not on the open list we add
							// it
							// with parent node the current node
							// finding terrain type for this node point

							// calculate g, h and F scores for the tile
							// Calculating the "cost of moving". I use the time
							// to
							// move as the cost.
							sqrt =Math.sqrt(((double)Math.abs(((double)i) - 1.0) + Math.abs(((double)j)- 1.0)));
							gFunc = gParent+ sqrt * stepSize/ ((double)nodeSpeed);
							hItermidiate = Math.sqrt(Math.pow(xNodef - xEnd,2)+ Math.pow(yNodef - yEnd,2));
							hFunc = hItermidiate/((double)nodeSpeed);
							// finally f:
							fFunc = gFunc + hFunc;

							// adding the node point to the openlist.
							point = new Node(xNodef, yNodef, terrain, nodeParentID,nodeID, gFunc, hFunc, fFunc);
							openListFunc.add(point);
							//System.out.println("NodeID = "+nodeID +" G = "+gFunc+" H = "+hFunc+" F = "+fFunc);							

						}
											
						

					
				}

			}
			}}
		
		return openListFunc;

	}
	
	//finding the node with the lowest F:
	public static Node lowestFNode(ArrayList<Node> openListFunc){

		double f=0;
		double fsmallest=999999999;
		int iSmallest=0;

		for(int i = 0;i<openListFunc.size()-1;i++){
			f=openListFunc.get(i).getFNode();
			if(f<fsmallest){
				fsmallest=f;
				iSmallest=i;
			}
			
		}

		return openListFunc.get(iSmallest);
		
	}
	
	
	
	public static int ifunction1(int i, int j){
		int neighbourI = 0;
		if(i==-1&&j==-1){
			neighbourI=0;
		}
		if(i==-1&&j==1){
			neighbourI=-1;
		}
		if(i==1&&j==-1){
			neighbourI=0;
		}
		if(i==1&&j==1){
			neighbourI=0;
		}
		
		return neighbourI;
		
	}
	public static int ifunction2(int i, int j){
		int neighbourI = 0;
		if(i==-1&&j==-1){
			neighbourI=-1;
		}
		if(i==-1&&j==1){
			neighbourI=0;
		}
		if(i==1&&j==-1){
			neighbourI=1;
		}
		if(i==1&&j==1){
			neighbourI=1;
		}
		
		return neighbourI;
		
	}
	public static int jfunction1(int i, int j){
		int neighbourJ = 0;
		if(i==-1&&j==-1){
			neighbourJ=-1;
		}
		if(i==-1&&j==1){
			neighbourJ=0;
		}
		if(i==1&&j==-1){
			neighbourJ=-1;
		}
		if(i==1&&j==1){
			neighbourJ=1;
		}
		
		return neighbourJ;
		
	}
	public static int jfunction2(int i, int j){
		int neighbourJ = 0;
		if(i==-1&&j==-1){
			neighbourJ=0;
		}
		if(i==-1&&j==1){
			neighbourJ=1;
		}
		if(i==1&&j==-1){
			neighbourJ=0;
		}
		if(i==1&&j==1){
			neighbourJ=0;
		}
		
		return neighbourJ;
		
	}
	
	
}
