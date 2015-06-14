package com.dune.planet;

public class Wrapper {
    public String name;
    public int rotation;
    
    public Wrapper(String name, int rotation) {
       this.name = name;
       this.rotation = rotation;
    }

    public String getName() {
    	return this.name;
    	}
    
    public int getRotation() { 
    	return this.rotation; 
    	}


}

