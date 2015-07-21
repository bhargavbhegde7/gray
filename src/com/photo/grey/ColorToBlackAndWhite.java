package com.photo.grey;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class ColorToBlackAndWhite {
	
	private static int colorToRGB(int alpha, int red, int green, int blue) {

		int newPixel = 0;
		newPixel += alpha;
		newPixel = newPixel << 8;
		newPixel += red; newPixel = newPixel << 8;
		newPixel += green; newPixel = newPixel << 8;
		newPixel += blue;

		return newPixel;

		}
	
	// The average grayscale method
	private static BufferedImage avg(BufferedImage original) {
	 
	    int alpha, red, green, blue;
	    int newPixel;
	 
	    BufferedImage avg_gray = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
	    int[] avgLUT = new int[766];
	    for(int i=0; i<avgLUT.length; i++) avgLUT[i] = (int) (i / 3);
	 
	    for(int i=0; i<original.getWidth(); i++) {
	        for(int j=0; j<original.getHeight(); j++) {
	 
	            // Get pixels by R, G, B
	            alpha = new Color(original.getRGB(i, j)).getAlpha();
	            red = new Color(original.getRGB(i, j)).getRed();
	            green = new Color(original.getRGB(i, j)).getGreen();
	            blue = new Color(original.getRGB(i, j)).getBlue();
	 
	            newPixel = red + green + blue;
	            newPixel = avgLUT[newPixel];
	            // Return back to original format
	            newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);
	 
	            // Write pixels into image
	            avg_gray.setRGB(i, j, newPixel);
	 
	        }
	    }
	 
	    return avg_gray;
	 
	}
	
	// The luminance method
	private static BufferedImage luminosity(BufferedImage original) {
	 
	    int alpha, red, green, blue;
	    int newPixel;
	 
	    BufferedImage lum = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
	 
	    for(int i=0; i<original.getWidth(); i++) {
	        for(int j=0; j<original.getHeight(); j++) {
	 
	            // Get pixels by R, G, B
	            alpha = new Color(original.getRGB(i, j)).getAlpha();
	            red = new Color(original.getRGB(i, j)).getRed();
	            green = new Color(original.getRGB(i, j)).getGreen();
	            blue = new Color(original.getRGB(i, j)).getBlue();
	 
	            red = (int) (0.21 * red + 0.71 * green + 0.07 * blue);
	            // Return back to original format
	            newPixel = colorToRGB(alpha, red, red, red);
	 
	            // Write pixels into image
	            lum.setRGB(i, j, newPixel);
	 
	        }
	    }
	 
	    return lum;
	 
	}
	
	// The desaturation method
	private static BufferedImage desaturation(BufferedImage original) {
	 
	    int alpha, red, green, blue;
	    int newPixel;
	 
	    int[] pixel = new int[3];
	 
	    BufferedImage des = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
	    int[] desLUT = new int[511];
	    for(int i=0; i<desLUT.length; i++) desLUT[i] = (int) (i / 2);
	 
	    for(int i=0; i<original.getWidth(); i++) {
	        for(int j=0; j<original.getHeight(); j++) {
	 
	            // Get pixels by R, G, B
	            alpha = new Color(original.getRGB(i, j)).getAlpha();
	            red = new Color(original.getRGB(i, j)).getRed();
	            green = new Color(original.getRGB(i, j)).getGreen();
	            blue = new Color(original.getRGB(i, j)).getBlue();
	 
	            pixel[0] = red;
	            pixel[1] = green;
	            pixel[2] = blue;
	 
	            int newval = (int) (findMax(pixel) + findMin(pixel));
	            newval = desLUT[newval];
	 
	            // Return back to original format
	            newPixel = colorToRGB(alpha, newval, newval, newval);
	 
	            // Write pixels into image
	            des.setRGB(i, j, newPixel);
	 
	        }
	    }
	 
	    return des;
	 
	}    
	 
	private static int findMin(int[] pixel) {
	 
	    int min = pixel[0];
	 
	    for(int i=0; i<pixel.length; i++) {
	        if(pixel[i] < min)
	                min = pixel[i];
	    }
	 
	    return min;
	 
	}
	 
	private static int findMax(int[] pixel) {
	 
	    int max = pixel[0];
	 
	    for(int i=0; i<pixel.length; i++) {
	        if(pixel[i] > max)
	                max = pixel[i];
	    }
	 
	    return max;
	 
	}
	
	// The maximum decomposition method
	private static BufferedImage decompMax(BufferedImage original) {
	 
	    int alpha, red, green, blue;
	    int newPixel;
	 
	    int[] pixel = new int[3];
	 
	    BufferedImage decomp = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
	 
	    for(int i=0; i<original.getWidth(); i++) {
	        for(int j=0; j<original.getHeight(); j++) {
	 
	            // Get pixels by R, G, B
	            alpha = new Color(original.getRGB(i, j)).getAlpha();
	            red = new Color(original.getRGB(i, j)).getRed();
	            green = new Color(original.getRGB(i, j)).getGreen();
	            blue = new Color(original.getRGB(i, j)).getBlue();
	 
	            pixel[0] = red;
	            pixel[1] = green;
	            pixel[2] = blue;
	 
	            int newval = findMax(pixel);
	 
	            // Return back to original format
	            newPixel = colorToRGB(alpha, newval, newval, newval);
	 
	            // Write pixels into image
	            decomp.setRGB(i, j, newPixel);
	 
	        }
	 
	    }
	 
	    return decomp;
	 
	}
	
	// The minimal decomposition method
	private static BufferedImage decompMin(BufferedImage original) {
	 
	    int alpha, red, green, blue;
	    int newPixel;
	 
	    int[] pixel = new int[3];
	 
	    BufferedImage decomp = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
	 
	    for(int i=0; i<original.getWidth(); i++) {
	        for(int j=0; j<original.getHeight(); j++) {
	 
	            // Get pixels by R, G, B
	            alpha = new Color(original.getRGB(i, j)).getAlpha();
	            red = new Color(original.getRGB(i, j)).getRed();
	            green = new Color(original.getRGB(i, j)).getGreen();
	            blue = new Color(original.getRGB(i, j)).getBlue();
	 
	            pixel[0] = red;
	            pixel[1] = green;
	            pixel[2] = blue;
	 
	            int newval = findMin(pixel);
	 
	            // Return back to original format
	            newPixel = colorToRGB(alpha, newval, newval, newval);
	 
	            // Write pixels into image
	            decomp.setRGB(i, j, newPixel);
	 
	        }
	    }
	    
	   
	 
	    return decomp;
	 
	}
	
	// The "pick the color" method (0 for R, 1 for G and 2 for B)
	private static BufferedImage pickRGB(BufferedImage original, int color) {
	 
	    int alpha, red, green, blue;
	    int newPixel;
	 
	    int[] pixel = new int[3];
	 
	    BufferedImage rgb = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
	 
	    for(int i=0; i<original.getWidth(); i++) {
	        for(int j=0; j<original.getHeight(); j++) {
	 
	            // Get pixels by R, G, B
	            alpha = new Color(original.getRGB(i, j)).getAlpha();
	            red = new Color(original.getRGB(i, j)).getRed();
	            green = new Color(original.getRGB(i, j)).getGreen();
	            blue = new Color(original.getRGB(i, j)).getBlue();
	 
	            pixel[0] = red;
	            pixel[1] = green;
	            pixel[2] = blue;
	 
	            int newval = pixel[color];
	 
	            // Return back to original format
	            newPixel = colorToRGB(alpha, newval, newval, newval);
	 
	            // Write pixels into image
	            rgb.setRGB(i, j, newPixel);
	 
	        }
	 
	    }
	 
	    
	    
	    return rgb;        
	 
	}
	
	public static void main(String[] args) throws IOException 
	  {
		// Read an image. 
		BufferedImage input = ImageIO.read(new File("C:/Users/bhargava/Downloads/test2a.jpg"));
		// Store the resulting image using the PNG format.
		  
		//input = decompMin(input);
		  
		//brightness and contrast (1) scale factor(brighten) (2) offset()darken
	    RescaleOp rescaleOp = new RescaleOp(1f, 1.5f, null);
	    rescaleOp.filter(input, input);
		  
		ImageIO.write(decompMin(input),"PNG",new File("test.png"));
	  }
}