package com.dune.entities;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Map;

import com.baseframework.game.main.Resources;
import com.dune.planet.PlanetMap;


public class Node {

	private int x, y;
	private double F, G, H;
	private String terrain;
	int parentSquare;


	public Node(int x, int y, String terrain) {
		


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



	public double getF() {
		return F;
	}



	public void setF(double f) {
		F = f;
	}



	public double getG() {
		return G;
	}



	public void setG(double g) {
		G = g;
	}



	public double getH() {
		return H;
	}



	public void setH(double h) {
		H = h;
	}
	
	
}
	
	

