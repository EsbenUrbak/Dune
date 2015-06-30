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

	
	static int xNode;
	static int yNode;
	static int xDelta;
	static int yDelta;
	static int stepSize = 10;
	int speed=150;
	static int g;
	static int f;
	static int h;
	private static ArrayList<Node> openList = new ArrayList<Node>();	
	private static ArrayList<Node> closedList = new ArrayList<Node>();	
	private static ArrayList<Integer> xPath = new ArrayList<Integer>();
	private static ArrayList<Integer> yPath = new ArrayList<Integer>();
	public static Map<String, Node> nodeMap=new HashMap<String, Node>();
	

	static Node point;
	static Node startPoint;
	static Node currentNode;
	static Node intermidiateNode;
	static String nextNodeID;
	
	//input has to be in absolute coordinates!
	public static Map<String, ArrayList<Integer>> AStar(int xStart, int yStart, int xEnd, int yEnd) {

		
		//Adding the starting to openlist
		startPoint = new Node(xStart, yStart,PlanetMap.mapArray.get((xStart+yStart*PlanetMap.width)/Tile.getSizeX()),"x=" + 0 + "_y=" + 0,"x=" + 0 + "_y=" + 0,0,0,0);
		openList.add(startPoint);
	
		xNode = 0;
		yNode = 0;
		xDelta=1;
		yDelta=1;
		
		while(xDelta>0||yDelta>0){
			
			
			currentNode = lowestFNode(openList);
			closedList.add(currentNode);
			xNode = currentNode.getxNode();
			yNode = currentNode.getxNode();;
			xDelta=(int) (xEnd-(xNode+(int)Squad.xImagine/2f));
			yDelta=(int) (yEnd-(yNode+(int)Squad.yImagine));
			
			//adding adjacent nodes to the openlist if they are not alreayd on it and checks which is the correct parent for with node
			openList=adjacentNodes(currentNode, openList, xStart, yStart, xEnd, yEnd);
			
			
		}
		
		
		//create a map of find the path
		for(int i=closedList.size()-1;i>-1;i--){
			intermidiateNode= closedList.get(i);
			nodeMap.put(intermidiateNode.getNodeIDNode(),intermidiateNode);
		}
		
		//now going back through the closed list to find the path and make the path.
		//First we add end point
		xPath.add(xEnd);
		yPath.add(yEnd);
		
		intermidiateNode= closedList.get(closedList.size()-1);
		while(intermidiateNode!=startPoint){
			xPath.add(intermidiateNode.getxNode());
			yPath.add(intermidiateNode.getyNode());
			nextNodeID = intermidiateNode.getNodeParentIDNode();
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
	
	public static Node getNode(String nodeName)
	{
		return nodeMap.get(nodeName);
	}
	
	
	
	public void point(int x, int y){
		
	}
	
	public static ArrayList<Node> adjacentNodes(Node currentNodeFunc,ArrayList<Node> openListFunc, int xStart, int yStart, int xEnd,int yEnd) {

		int xParent = currentNodeFunc.getxNode();
		System.out.println("xParent="+xParent);
		int yParent = currentNodeFunc.getyNode();
		int gParent = currentNodeFunc.getGNode();
		String nodeParentID=currentNodeFunc.getNodeIDNode();
		String nodeID;
		String terrain;
		int xNodef;
		int yNodef;
		int openListSize = openList.size();
		int closedListSize = closedList.size();

		// finding all adjacent to the starting square
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {

				// finding neighbours coordinates
				xNodef = xParent + stepSize * (i-1);
				yNodef = yParent + stepSize * (j-1);
				nodeID = "x=" + (xNodef - xStart)/stepSize + "_y=" + (yNodef - yStart)/stepSize;

				
				//check whether on the closed list already:
				for (int n = 0; n < closedListSize; n++) {
				
					if (nodeID.equals(closedList.get(n).getNodeIDNode())) {

						
					}else{

						// Check whether neighbour is in the openlist already:
										for (int k = 0; k < openListSize; k++) {

																if (nodeID.equals(openList.get(k).getNodeIDNode())) {
																	// if already on the list we need to check whether the
																	// path to this node is quicker from the current node
																	// then earlier recorded
																	
																	// finding terrain type for this node point
																	terrain = PlanetMap.mapArray.get((xNodef + yNodef* PlanetMap.width)/Tile.getSizeX());
																	// Calculating the "cost of moving". I use the time to
																	// move as the cost.
																	g = gParent+ (int) Math.sqrt((Math.pow(Math.abs(i), 2) + Math.pow(Math.abs(j), 2))* stepSize)/ Resources.getSpeed(terrain);
																	// check which path has the lowest g:
																	if (g < openList.get(k).getGNode()) {
																		// if the g score is lower with the current node
																		// then we remove the old node and replace it with
																		// updated parameters.
																		openList.remove(k);
																		h = Math.abs((xNodef - xEnd))+ Math.abs((yNodef - yEnd));
																		// finally f:
																		f = g + h;
																		// adding the node point to the openlist.
																		point = new Node(xNodef, yNodef, terrain, nodeParentID,nodeID, g,h, f);
																		openListFunc.add(point);
																	}

																} else {
																	// if the neighbour is not on the open list we add it
																	// with parent node the current node
																	// finding terrain type for this node point
									
																	terrain = PlanetMap.mapArray.get((xNodef + yNodef* PlanetMap.width)/Tile.getSizeX());
									
																	// calculate g, h and F scores for the tile
																	// Calculating the "cost of moving". I use the time to
																	// move as the cost.
																	g = gParent+ (int) Math.sqrt((Math.pow(Math.abs(i), 2) + Math.pow(Math.abs(j), 2))* stepSize)/ Resources.getSpeed(terrain);
									
																	h = Math.abs((xNodef - xEnd)) + Math.abs((yNodef - yEnd));
																	// finally f:
																	f = g + h;
									
																	// adding the node point to the openlist.
																	point = new Node(xNodef, yNodef, terrain, nodeParentID, nodeID, g, h, f);
																	openListFunc.add(point);
																}

											}
						
						
					}
				}

			}
		}

		return openListFunc;

	}
	
	//finding the node with the lowest F:
	public static Node lowestFNode(ArrayList<Node> openListFunc){

		int f=0;
		int fsmallest=999999999;
		int iSmallest=0;

		for(int i = 0;i<openListFunc.size();i++){
			f=openListFunc.get(i).getFNode();
			if(f<fsmallest){
				fsmallest=f;
				iSmallest=i;
			}
			
		}

		return openListFunc.get(iSmallest);
		
	}
	
	
	
}
