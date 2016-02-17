package panels.fotopanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

import javax.annotation.Resources;
import javax.imageio.ImageIO;

import piApp.Main;
import misc.ImgUtils;

public class ThumbNail 
{
	private static BufferedImage FOLDER_ICON;
	
	private Dimension Size;
	private String FileName;
	private BufferedImage Thumb;
	
	public ThumbNail(Dimension thumbSize, File foto)
	{
		FileName = foto.getName();
		Thumb = null;
		Size = new Dimension(0,0);
		if(foto.isDirectory())
			CreateFolderThumb(thumbSize);		
		else
			CreateThumbNail(foto, thumbSize);
	}
	
	public int GetWidth()
	{
		return this.Size.width;
	}
	
	public int GetHeight()
	{
		return this.Size.height;
	}
	
	public String GetName()
	{
		return this.FileName;
	}
	
	public void DrawThumbNail(Graphics g, int x, int y)
	{
		g.drawImage(Thumb, x, y, Size.width, Size.height, null);		
	}
	
	private void CreateFolderThumb(Dimension thumbDim)
	{
		if(FOLDER_ICON == null)
		{
			File f = new File(System.getProperty("user.dir") + "//resources//normal_folder.png");
			//File f = new File("//resources//normal_folder.png");
			//InputStream input  = this.getClass().getResourceAsStream("/resources/normal_folder.png");
			//ClassLoader CLDR = this.getClass().getClassLoader();
		    //InputStream inputStream = CLDR.getResourceAsStream("/piApp/resources/normal_folder.png");	  		    
			//InputStream input = Main.class,getClass().getResource("/piApp/resources/images/normal_folder.png");
		    /*
			try {
				System.out.println("resource path is: " + CLDR.getResource("resources/normal_folder.txt").toURI().getPath());
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			
			try 
			{
				FOLDER_ICON = ImageIO.read(Main.class.getResource("/images/normal_folder.png"));
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		if(FOLDER_ICON != null)
		{
			Size = ImgUtils.getScaledDimension(new Dimension(FOLDER_ICON.getWidth(), FOLDER_ICON.getHeight()), thumbDim);
			Thumb = ImgUtils.Scale(FOLDER_ICON, thumbDim);
		}
	}
	
	private void CreateThumbNail(File file, Dimension thumbDim)
	{
		BufferedImage image = null;
		try 
		{
			image = ImageIO.read(file);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		if(image != null)
		{
			Size = ImgUtils.getScaledDimension(new Dimension(image.getWidth(), image.getHeight()), thumbDim);
			Thumb = ImgUtils.Scale(image, thumbDim);
		}
	}
}
