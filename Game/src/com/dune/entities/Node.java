package com.dune.entities;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Map;

import com.baseframework.game.main.Resources;
import com.dune.planet.PlanetMap;


public class Node {

	private int xNode, yNode;
	private int FNode, GNode, HNode;
	private String terrainNode, nodeIDNode, nodeParentIDNode;
	int parentSquare;


	public Node(int x, int y, String terrain, String nodeParentID,String nodeID, int G, int H,int F) {
		xNode = x * 1;
		yNode = y * 1;
		terrainNode =terrain;
		nodeIDNode =nodeParentID;
		nodeParentIDNode =nodeID;
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


	public int getFNode() {
		return FNode;
	}


	public void setFNode(int fNode) {
		FNode = fNode;
	}


	public int getGNode() {
		return GNode;
	}


	public void setGNode(int gNode) {
		GNode = gNode;
	}


	public int getHNode() {
		return HNode;
	}


	public void setHNode(int hNode) {
		HNode = hNode;
	}


	public String getTerrainNode() {
		return terrainNode;
	}


	public void setTerrainNode(String terrainNode) {
		this.terrainNode = terrainNode;
	}


	public String getNodeIDNode() {
		return nodeIDNode;
	}


	public void setNodeIDNode(String nodeIDNode) {
		this.nodeIDNode = nodeIDNode;
	}


	public String getNodeParentIDNode() {
		return nodeParentIDNode;
	}


	public void setNodeParentIDNode(String nodeParentIDNode) {
		this.nodeParentIDNode = nodeParentIDNode;
	}


	public int getParentSquare() {
		return parentSquare;
	}


	public void setParentSquare(int parentSquare) {
		this.parentSquare = parentSquare;
	}



	
	
	
}
	
	

