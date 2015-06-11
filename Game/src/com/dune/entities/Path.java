package com.dune.entities;

public class Path {
	private int x;
	private int y;
	private boolean displayed;

	public Path(int x, int y, boolean displayed) {
		this.x = x;
		this.y = y;
		this.displayed = displayed;
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

	public boolean isDisplayed() {
		return displayed;
	}

	public void setDisplayed(boolean active) {
		this.displayed = active;
	}

}
