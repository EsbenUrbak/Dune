package com.baseframework.util;

public class Miscellaneous {
	

	
	public static double BilinearInterpolator(double x1, double y1, double x2, double y2, double h1_1,double h1_2,double h2_2,double h2_1, double pX, double pY){
		double h;
		
		h=  (1/((x2-x1)*(y2-y1))) * (  h1_1 * (x2-pX)*(y2-pY) + h2_1*(pX-x1)*(y2-pY) + h1_2*(x2-pX)*(pY-y1) + h2_2*(pX-x1)*(pY-y1)) ;
		
		return h;
	}

	public static int neighbourX(int x, int y, int i, int j){
		
		if(y%2==0){
		
		if(i==-1&&j==-1){
			x=x;
		}
		if(i==-1&&j==0){
			x=x-1;
		}
		if(i==-1&&j==1){
			x=x-1;
		}
		if(i==0&&j==-1){
			x=x;
		}
		if(i==0&&j==0){
			x=x;
		}
		if(i==0&&j==1){
			x=x-1;
		}
		if(i==1&&j==-1){
			x=x+1;
		}
		if(i==1&&j==0){
			x=x;
		}
		if(i==1&&j==1){
			x=x;
		}}else{
			if(i==-1&&j==-1){
				x=x;
			}
			if(i==-1&&j==0){
				x=x;
			}
			if(i==-1&&j==1){
				x=x-1;
			}
			if(i==0&&j==-1){
				x=x+1;
			}
			if(i==0&&j==0){
				x=x;
			}
			if(i==0&&j==1){
				x=x;
			}
			if(i==1&&j==-1){
				x=x+1;
			}
			if(i==1&&j==0){
				x=x+1;
			}
			if(i==1&&j==1){
				x=x;
			}
			
		}
		//safety
		if(x<0){
			x=0;
		}
		
		return x;
	}
	public static int neighbourY(int x, int i, int j){
		
		if(i==-1&&j==-1){
			x=x-2;
		}
		if(i==-1&&j==0){
			x=x-1;
		}
		if(i==-1&&j==1){
			x=x;
		}
		if(i==0&&j==-1){
			x=x-1;
		}
		if(i==0&&j==0){
			x=x;
		}
		if(i==0&&j==1){
			x=x+1;
		}
		if(i==1&&j==-1){
			x=x;
		}
		if(i==1&&j==0){
			x=x+1;
		}
		if(i==1&&j==1){
			x=x+2;
		}
		if(x<0){
			x=0;
		}
		return x;
	}
	
	public static int carToIsoIndexX(int xNumber, int yNumber, int tileWidth){
		int x;
		if(yNumber%2==0){
		x = xNumber*tileWidth;
		}else{
		x = xNumber*tileWidth+tileWidth/2;
		}
				
		return x;
	}
	
	public static int carToIsoIndexY(int xNumber, int yNumber, int tileHeight){
		int y;
		
		y=yNumber*tileHeight/2;
		
		return y;
	}
	
	public static int carToIsoX(int xNumber, int yNumber, int tileWidth){
		int x;
		if(yNumber%2==0){
		x = xNumber*tileWidth;
		}else{
		x = xNumber*tileWidth+tileWidth/2;
		}
				
		return x;
	}
	
	public static int carToIsoY(int xNumber, int yNumber, int tileHeight){
		int y;
		
		y=yNumber*tileHeight/2;
		
		return y;
	}
	

	
	
	
	
	
}
