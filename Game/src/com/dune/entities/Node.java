package com.dune.entities;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Map;

import com.baseframework.game.main.Resources;
import com.dune.planet.PlanetMap;


public class Node {

	private int xNode, yNode;
	private double FNode, GNode, HNode;
	private String terrainNode, NodeID, ParentNodeID;
	int parentSquare;


	public Node(int x, int y, String terrain, String nodeParentID,String nodeID, double G, double H,double F) {
		xNode = x * 1;
		yNode = y * 1;
		terrainNode =terrain;
		NodeID =nodeID;
		ParentNodeID =nodeParentID;
		FNode = F;
		GNode=G;
		HNode=H;

	}


	public int getxNode() {
		return xNode;
	}


	public void setxNode(int xNode) {
		this.xNode = xNode;
	}


	public int getyNode() {
		return yNode;
	}


	public void setyNode(int yNode) {
		this.yNode = yNode;
	}


	public double getFNode() {
		return FNode;
	}


	public void setFNode(double fNode) {
		FNode = fNode;
	}


	public double getGNode() {
		return GNode;
	}


	public void setGNode(double gNode) {
		GNode = gNode;
	}


	public double getHNode() {
		return HNode;
	}


	public void setHNode(double hNode) {
		HNode = hNode;
	}


	public String getTerrainNode() {
		return terrainNode;
	}


	public void setTerrainNode(String terrainNode) {
		this.terrainNode = terrainNode;
	}


	public String getNodeID() {
		return NodeID;
	}


	public void setNodeID(String nodeID) {
		NodeID = nodeID;
	}


	public String getParentNodeID() {
		return ParentNodeID;
	}


	public void setParentNodeID(String parentNodeID) {
		ParentNodeID = parentNodeID;
	}


	public int getParentSquare() {
		return parentSquare;
	}


	public void setParentSquare(int parentSquare) {
		this.parentSquare = parentSquare;
	}



	
	
	
}
	
	

