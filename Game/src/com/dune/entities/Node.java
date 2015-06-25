package com.dune.entities;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Map;

import com.baseframework.game.main.Resources;
import com.dune.planet.PlanetMap;


public class Node {

	private int x, y;
	private int F, G, H;
	private String terrain, nodeID, nodeParentID;
	int parentSquare;


	public Node(int x, int y, String terrain, String nodeParentID,String nodeID, int G, int H,int F) {
		


	}



	public int getX() {
		return x;
	}



	public void setX(int x) {
		this.x = x;
	}



	public int getY() {
		return y;
	}



	public void setY(int y) {
		this.y = y;
	}



	public int getF() {
		return F;
	}



	public void setF(int f) {
		F = f;
	}



	public int getG() {
		return G;
	}



	public void setG(int g) {
		G = g;
	}



	public double getH() {
		return H;
	}



	public void setH(int h) {
		H = h;
	}



	public String getTerrain() {
		return terrain;
	}



	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}



	public int getParentSquare() {
		return parentSquare;
	}



	public void setParentSquare(int parentSquare) {
		this.parentSquare = parentSquare;
	}



	public String getNodeID() {
		return nodeID;
	}



	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}



	public String getNodeParentID() {
		return nodeParentID;
	}



	public void setNodeParentID(String nodeParentID) {
		this.nodeParentID = nodeParentID;
	}
	
	
	
}
	
	

