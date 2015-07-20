package com.dune.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.dune.planet.PlanetMap;
import com.dune.planet.Tile;

public class AStar {
	static Map<String, Node> nodeMap=new HashMap<String, Node>();
	static Map<String, Node> nodeMapOpen = new HashMap<String, Node>();
	
    //Comparator for queue
    public static Comparator<Node> fComparator = new Comparator<Node>(){
        @Override
        public int compare(Node n1, Node n2) {
        	return Double.compare(n1.getFNode(), n2.getFNode());
        }
    };
	
	
	 static int stepSize = 15;
	 
	//input has to be in absolute coordinates!
	public static Map<String, ArrayList<Integer>> AStarFunction(int xStart, int yStart, int xEnd, int yEnd) {
		 int Delta;
		 nodeMap.clear();
		 nodeMapOpen.clear();
		 
		 Node startPoint;
		 Node currentNode;
		 Node intermidiateNode;
		 String nextNodeID;

		ArrayList<Integer> xPath = new ArrayList<Integer>();
		ArrayList<Integer> yPath = new ArrayList<Integer>();

		Queue<Node> fPriorityQueue = new PriorityQueue<>(1, fComparator);

		
		//Adding the starting to openlist
		startPoint = new Node(xStart, yStart,PlanetMap.mapArray.get((xStart+yStart*PlanetMap.width)/Tile.getSizeX()),"x=" + 0 + "_y=" + 0,"x=" + 0 + "_y=" + 0,0,0,0);
		fPriorityQueue.add(startPoint);
		nodeMapOpen.put("x=" + 0 + "_y=" + 0,startPoint);
		

		Delta=stepSize+1;
		
		currentNode=startPoint;
		
		while(Delta>stepSize){
			
			nodeMap.put(currentNode.getNodeID(),currentNode);
			nodeMapOpen.remove(currentNode);
			
			//adding adjacent nodes to the openlist if they are not already on it and checks which is the correct parent for these nodes
			fPriorityQueue=adjacentNodes(currentNode, fPriorityQueue, xStart, yStart, xEnd, yEnd);
			currentNode=fPriorityQueue.poll();
	
			Delta = (int) Math.sqrt((Math.pow(xEnd-currentNode.getxNode(),2)+Math.pow(yEnd-currentNode.getyNode(),2)));
	
			
		}
		
		nodeMap.put(currentNode.getNodeID(),currentNode);
		
		//now going back through the closed list to find the path and make the path.
		//First we add end point
		xPath.add(xEnd);
		yPath.add(yEnd);
		
		intermidiateNode= currentNode;
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
	

	
	public static Queue<Node> adjacentNodes(Node currentNodeFunc,Queue<Node> openListFunc, int xStart, int yStart, int xEnd,int yEnd) {

		Node point;
		String nodeID, terrain,terrain_dia1,terrain_dia2, nodeParentID=currentNodeFunc.getNodeID();

		double gFunc, hFunc, fFunc = 0,sqrt,hItermidiate, gParent = currentNodeFunc.getGNode();
		
		int xNodef, yNodef,terrainNumber,nodeSpeed;
		int xParent = currentNodeFunc.getxNode();
		int yParent = currentNodeFunc.getyNode();
		int xNodef_dia1, yNodef_dia1,nodeSpeed_dia1 = 0, j1,i1;
		int xNodef_dia2, yNodef_dia2,nodeSpeed_dia2 = 0, j2,i2;
		
		// finding all adjacent to the starting square
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				// finding neighbours coordinates
				xNodef = xParent + stepSize * (i-1);
				yNodef = yParent + stepSize * (j-1);
				nodeID = "x=" + (xNodef - xStart)/stepSize + "_y=" + (yNodef - yStart)/stepSize;
				// finding terrain type for this node point
				nodeSpeed=Speed.Speed(xParent, yParent, xNodef, yNodef);
				
				//Check to make sure the squad doesnt move accross diagonals where it will get stuck
				if(Math.abs(i-1)==Math.abs(j-1)){
					i1=ifunction1((i-1),(j-1));
					j1=jfunction1((i-1),(j-1));
					xNodef_dia1 = xParent + stepSize * i1;
					yNodef_dia1 = yParent + stepSize * j1;
					
					nodeSpeed_dia1=Speed.Speed(xNodef_dia1, yNodef_dia1, xNodef_dia1, yNodef_dia1);
					
					i2=ifunction2((i-1),(j-1));
					j2=jfunction2((i-1),(j-1));
					xNodef_dia2 = xParent + stepSize * i2;
					yNodef_dia2 = yParent + stepSize * j2;
					
					nodeSpeed_dia2=Speed.Speed(xNodef_dia2, yNodef_dia2, xNodef_dia2, yNodef_dia2);
					
				}
				
				//check whether on the closed list already or is a zero speed tile:

				if(nodeSpeed==0||nodeSpeed_dia1==0||nodeSpeed_dia2==0||nodeMap.containsKey(nodeID)){
					//do nothing
				}else{
					
						if (nodeMapOpen.containsKey(nodeID)) {
							// if already on the list we need to check whether the path to this node is quicker from the current node then earlier recorded

							// Calculating the "cost of moving". I use the time to move as the cost.
							sqrt =Math.sqrt(((double)Math.abs(((double)i) - 1.0) + Math.abs(((double)j)- 1.0)));
							gFunc = gParent+ sqrt * stepSize/ ((double)nodeSpeed);
							
							// check which path has the lowest g:
							if (gFunc < nodeMapOpen.get(nodeID).getGNode()) {
								// if the g score is lower with the current node then we remove the old node and replace it with updated parameters.
								nodeMapOpen.remove(nodeID);
								hItermidiate = Math.sqrt(Math.pow(xNodef - xEnd,2)+ Math.pow(yNodef - yEnd,2));
								hFunc = hItermidiate/((double)nodeSpeed);
								// finally f:
								fFunc = gFunc + hFunc;
								// adding the node point to the openlist.
								point = new Node(xNodef, yNodef, "NA", nodeParentID, nodeID, gFunc, hFunc,fFunc);
								openListFunc.add(point);
								nodeMapOpen.put(nodeID,point);	
							}

						}

						else {
							// if the neighbour is not on the open list we add it with parent node the current node finding terrain type for this node point

							// calculate g, h and F scores for the tile Calculating the "cost of moving". I use the time to move as the cost.
							sqrt =Math.sqrt(((double)Math.abs(((double)i) - 1.0) + Math.abs(((double)j)- 1.0)));
							gFunc = gParent+ sqrt * stepSize/ ((double)nodeSpeed);
							hItermidiate = Math.sqrt(Math.pow(xNodef - xEnd,2)+ Math.pow(yNodef - yEnd,2));
							hFunc = hItermidiate/((double)nodeSpeed);
							// finally f:
							fFunc = gFunc + hFunc;

							// adding the node point to the openlist.
							point = new Node(xNodef, yNodef, "NA", nodeParentID,nodeID, gFunc, hFunc, fFunc);
							openListFunc.add(point);
							nodeMapOpen.put(nodeID,point);						
						}
								
					}

			}}
			
		return openListFunc;

	}
	

	
	public static int ifunction1(int i, int j){
		int neighbourI = 0;
		if(i==-1&&j==-1){
			neighbourI=0;
		}
		if(i==-1&&j==1){
			neighbourI=-1;
		}
		if(i==1&&j==1){
			neighbourI=0;
		}
		if(i==1&&j==-1){
			neighbourI=1;
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
		if(i==1&&j==1){
			neighbourI=1;
		}
		if(i==1&&j==-1){
			neighbourI=0;
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
		if(i==1&&j==1){
			neighbourJ=1;
		}
		if(i==1&&j==-1){
			neighbourJ=0;
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
		if(i==1&&j==1){
			neighbourJ=0;
		}
		if(i==1&&j==-1){
			neighbourJ=1;
		}
		
		return neighbourJ;
		
	}
	
	
}
