package com.baseframework.util;

public class Miscellaneous {
	
	
	public static double BilinearInterpolator(double x1, double y1, double x2, double y2, double h1_1,double h1_2,double h2_2,double h2_1, double pX, double pY){
		double h;
		
		h=  (1/((x2-x1)*(y2-y1))) * (  h1_1 * (x2-pX)*(y2-pY) + h2_1*(pX-x1)*(y2-pY) + h1_2*(x2-pX)*(pY-y1) + h2_2*(pX-x1)*(pY-y1)) ;
		
		return h;
	}

}
