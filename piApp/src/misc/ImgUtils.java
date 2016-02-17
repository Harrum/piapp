package misc;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImgUtils 
{
	/*
	public static BufferedImage Scale(BufferedImage source, double ratio) 
	{
		int w = (int) (source.getWidth() * ratio);
		int h = (int) (source.getHeight() * ratio);
		BufferedImage bi = GetCompatibleImage(w, h);
		Graphics2D g2d = bi.createGraphics();
		double xScale = (double) w / source.getWidth();
		double yScale = (double) h / source.getHeight();
		AffineTransform at = AffineTransform.getScaleInstance(xScale,yScale);
		g2d.drawRenderedImage(source, at);
		g2d.dispose();
		return bi;
	}*/
	
	public static BufferedImage Scale(BufferedImage source, Dimension size) 
	{
		int w = size.width;
		int h = size.height;
		BufferedImage bi = GetCompatibleImage(w, h);
		Graphics2D g2d = bi.createGraphics();
		double xScale = (double) w / source.getWidth();
		double yScale = (double) h / source.getHeight();
		AffineTransform at = AffineTransform.getScaleInstance(xScale,yScale);
		g2d.drawRenderedImage(source, at);
		g2d.dispose();
		return bi;
	}

	private static BufferedImage GetCompatibleImage(int w, int h) 
	{
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		BufferedImage image = gc.createCompatibleImage(w, h);
		return image;
	}
	
	public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) 
	{
	    int original_width = imgSize.width;
	    int original_height = imgSize.height;
	    int bound_width = boundary.width;
	    int bound_height = boundary.height;
	    int new_width = original_width;
	    int new_height = original_height;

	    if(original_width > original_height)
	    {
	    	new_width = bound_width;
	    	new_height = (new_width * original_height) / original_width;
	    	
	    	// then check if we need to scale even with the new height
		    if (new_height > bound_height) 
		    {
		        //scale height to fit instead
		        new_height = bound_height;
		        //scale width to maintain aspect ratio
		        new_width = (new_height * original_width) / original_height;
		    }

	    }
	    else if(original_width < original_height)
	    {
	    	new_height = bound_height;
	    	new_width = (new_height * original_width) / original_height;
	    	
	    	if(new_width > bound_width)
	    	{
	    		new_width = bound_width;
	    		new_height = (new_width * original_height) / original_width;
	    	}
	    }
	    return new Dimension(new_width, new_height);
	}
	/*
	public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) 
	{
	    int original_width = imgSize.width;
	    int original_height = imgSize.height;
	    int bound_width = boundary.width;
	    int bound_height = boundary.height;
	    int new_width = original_width;
	    int new_height = original_height;

	    // first check if we need to scale width
	    if (original_width > bound_width) {
	        //scale width to fit
	        new_width = bound_width;
	        //scale height to maintain aspect ratio
	        new_height = (new_width * original_height) / original_width;
	    }

	    // then check if we need to scale even with the new height
	    if (new_height > bound_height) {
	        //scale height to fit instead
	        new_height = bound_height;
	        //scale width to maintain aspect ratio
	        new_width = (new_height * original_width) / original_height;
	    }

	    return new Dimension(new_width, new_height);
	}*/
}
	