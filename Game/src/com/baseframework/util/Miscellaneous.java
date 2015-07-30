package com.baseframework.util;

import com.dune.entities.Point;

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
	
	public static Point CoordinateToTile(Point p, double width, double height){
		//need to look at a grid with 
		width=width/2;
		height=height/2;
		
		boolean colEqual, rowEqual,same;
		double distanceFromCol = p.getX()%width;
		double distanceFromRow = p.getY()%height;
		int col, row;
		double distanceA,distanceB,distanceC,distanceD, smallest;
		
		
		//finding which column and row the point is closest too
		if(distanceFromCol<width/2){
			col=(int)Math.floor(p.getX()/width);
			}else{
			col=(int)Math.ceil(p.getX()/width);	
		}
		if(distanceFromRow<height/2){
			row=(int)Math.floor(p.getY()/height);
			}else{
			row=(int)Math.ceil(p.getX()/height);	
		}
		//This is then all done if Col and Row are both equal or unequal. If they are not then this point is not actually a tile but precisely inbetween 4 tiles.
		// so in this case we will have to check whice of the surrounding tiles it is closest to:
		
		colEqual=col%2==0?true:false;
		rowEqual=row%2==0?true:false;
		same=colEqual==rowEqual?true:false;
		
		if(same){
			//do nothing
		}else{
		//check first point A(col-1,row)
		distanceA=Math.pow(p.getX()-(col-1)*width,2)+Math.pow(p.getY()-(row)*height,2);
		distanceB=Math.pow(p.getX()-(col+1)*width,2)+Math.pow(p.getY()-(row)*height,2);
		distanceC=Math.pow(p.getX()-(col)*width,2)+Math.pow(p.getY()-(row+1)*height,2);
		distanceD=Math.pow(p.getX()-(col)*width,2)+Math.pow(p.getY()-(row-1)*height,2);	
		
		//finding smallest number
		smallest=Math.min(distanceA, Math.min(distanceB, Math.min(distanceC,distanceD)));
		if(smallest==distanceA){
			col--;
		}else if (smallest==distanceB){
			col++;
		}else if (smallest ==distanceC){
			row++;
		}else if(smallest ==distanceD){
			row--;
		}
		
		}
		
		
		//now i have to convert the col number into to the actual column in our tile map:
		if(row%2==0){
			col=col/2;
		}else{
			col=(col-1)/2;
		}
		
		
		p.setX(col);
		p.setY(row);
		
		return p;
	}

	
}
