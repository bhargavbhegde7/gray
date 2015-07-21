package com.photo.grey;

import org.opencv.core.*;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Contrast {
	public static void main(String[] args) {
		try {
	         //System.loadLibrary( Core.NATIVE_LIBRARY_NAME);
	         Mat source = Highgui.imread("D:/JavaTries/GreyScale/rendered_lena_rgb_1.png", 
	         Highgui.CV_LOAD_IMAGE_GRAYSCALE);
	         Mat destination = new Mat(source.rows(),source.cols(),source.type());
	         
	         Imgproc.equalizeHist(source, destination);
	         Highgui.imwrite("contrast.jpg", destination);
	         
	      }catch (Exception e) {
	         System.out.println("error: " + e.getMessage());
	      }
	}
}
