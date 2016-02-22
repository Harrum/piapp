package panels.fotopanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import misc.ImgUtils;
import piApp.Main;

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
