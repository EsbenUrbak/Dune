package com.dune.entities;

public class GraphNode {

	private int xNode, yNode;
	private double FNode, GNode, HNode;
	private String terrainNode, nodeIDNode, nodeParentIDNode;
	int parentSquare;


	public GraphNode(int x, int y, String terrain, String nodeID,String nodeParentID, double G, double H,double F) {
		xNode = x * 1;
		yNode = y * 1;
		terrainNode =terrain;
		nodeIDNode =nodeID;
		nodeParentIDNode =nodeParentID;
		FNode = F;
		GNode=G;
		HNode=H;

	}
	
	
	
}
