package panels.fotopanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import piApp.Config;
import piApp.Window;
import misc.ImageFileFilter;
import misc.ImgUtils;
import misc.StopWatch;

public class FotoLijst extends JPanel
{	
	private final int FRAME_OFFSET = 5;
	private final int THUMB_OFFSET = 3;
	
	private final Stroke NORMAL_STROKE = new BasicStroke(1);
	private final Stroke THICK_STROKE = new BasicStroke(10);
	
	private final int MAX_STRING_WIDTH = 25;
	
	private int Width;
	private int Height;
	
	private int FrameSize;
	private Dimension ThumbSize;
	private int ThumbCount;
	private int ThumbIndex;
	private int SelectionIndex;
	private boolean IsSelected;
	
	private List<File> fotos;
	private ThumbNail[] thumbNails;
	
	public FotoLijst(int width, int height)
	{
		Width = width;
		Height = height;
		
		FrameSize = width - FRAME_OFFSET * 2;		
		ThumbSize = new Dimension(FrameSize - (THUMB_OFFSET * 2), FrameSize - (THUMB_OFFSET * 2));
		IsSelected = true;
		ThumbCount = height / width;
		ThumbIndex = 0;
		SelectionIndex = 0;
		super.setPreferredSize(new Dimension(Width, Height));
		super.setBounds(0, 0, Width, Height);
		fotos = new ArrayList<File>();
		thumbNails = new ThumbNail[ThumbCount];
		System.out.printf("Created fotolijst width %s, height %s \n", Width, Height);
	}
	
	public void CreatePanelRoot()
	{
		fotos.clear();
		
		fotos.addAll(Config.GetInstance().GetPaths());
		
		if(fotos.size() < ThumbCount)
		{
			ThumbCount = fotos.size();
			thumbNails = new ThumbNail[ThumbCount];
		}
		CreateThumbs();
	}
	
	public void CreatePanel(String path)
	{
		ReadDirectory(path);		
		if(fotos.size() < ThumbCount)
		{
			ThumbCount = fotos.size();
			thumbNails = new ThumbNail[ThumbCount];
		}
		CreateThumbs();
	}
	
	public void SetSelected(boolean value)
	{
		if(IsSelected != value)
		{
			IsSelected = value;
			Redraw();
		}
	}
	
	public boolean IsDirectory()
	{
		return fotos.get(ThumbIndex).isDirectory();
	}
	
	public boolean IsSelected()
	{
		return this.IsSelected;
	}
	
	public void ScrollToBegin()
	{
		ThumbIndex = 0;
		CreateThumbs();
	}
	
	public void ScrollToEnd()
	{
		ThumbIndex = fotos.size() - 1;
		CreateThumbs();
	}
	
	public void ScrollList(boolean up)
	{
		if(up)
		{
			if(ThumbIndex > 0)
			{
				ThumbIndex--;
				CreateThumbs();
			}
		}
		else
		{
			if(ThumbIndex <= fotos.size() - 2)
			{
				ThumbIndex++;
				CreateThumbs();
			}
		}
	}
	
	public List<File> GetFotos()
	{
		List<File> fotosOnly = new ArrayList<File>();
		for(File f : fotos)
		{
			if(!f.isDirectory())
				fotosOnly.add(f);
		}
		return fotosOnly;
	}
	
	public File GetSelectedFile()
	{
		return fotos.get(ThumbIndex);
	}
	
	private void ReadDirectory(String path)
	{
		fotos.clear();
		List<File> directories = new ArrayList<File>();
		File folder = new File(path);
	    for (final File fileEntry : folder.listFiles(new ImageFileFilter())) 
	    {
	    	if(fileEntry.isFile())
	    	{
	    		fotos.add(fileEntry);
	    	}
	    	else if(fileEntry.isDirectory())
	    	{
	    		directories.add(fileEntry);
	    	}
	    }
	    fotos.addAll(0, directories);
	}
	
	private void CreateThumbs()
	{
		List<ThumbNail> thumbCache = new ArrayList<ThumbNail>();
		thumbCache.addAll(Arrays.asList(thumbNails));
		ClearThumbs();
		
		int start = ThumbIndex;
		if(ThumbIndex + ThumbCount > fotos.size())
		{
			start = fotos.size() - ThumbCount;
			SelectionIndex = ThumbCount - (fotos.size() - ThumbIndex);
		}
		else
			SelectionIndex = 0;
		
		for(int i = 0; i < ThumbCount; i++)
		{
			File f = fotos.get(start + i);
						
			//First check if the thumb is in the cache.
			for (int t = 0; t < thumbCache.size(); t++)
			{
				if(thumbCache.get(t) != null)
				{
					if(thumbCache.get(t).GetName().equals(f.getName()))
					{
						thumbNails[i] = thumbCache.remove(t);
						break;
					}
				}
			}
			
			//If the thumbnail was not in the cache, load it in.
			if(thumbNails[i] == null)
				thumbNails[i] = new ThumbNail(ThumbSize, f);
		}
        Redraw();
	}
	
	private void ClearThumbs()
	{
		for(int i = 0; i < ThumbCount; i++)
		{
			thumbNails[i] = null;
		}
	}
	
	private void Redraw()
	{
		this.repaint();
        this.setVisible(true);
        this.setEnabled(true);
	}
	
	@Override
    protected void paintComponent(Graphics g) 
	{
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for(int i = 0; i < ThumbCount; i++)
        {
        	if(thumbNails[i] != null)
        	{
        		int xPos = FRAME_OFFSET + THUMB_OFFSET;
        		int yPos = FRAME_OFFSET + (i * FRAME_OFFSET) + (i * FrameSize);
        		g.fillRect(FRAME_OFFSET, yPos, FrameSize, FrameSize);
        		
        		int centerY = (ThumbSize.width - thumbNails[i].GetHeight()) / 2;
        		thumbNails[i].DrawThumbNail(g, xPos, yPos + THUMB_OFFSET + centerY);
        		//g.drawImage(thumbNails[i], FRAME_OFFSET + THUMB_OFFSET, yPos + THUMB_OFFSET + centerY, ThumbSize.width, thumbNails[i].GetHeight(), null);	
        		
        		String fileName = thumbNails[i].GetName();
        		int stringWidth = g.getFontMetrics().stringWidth(fileName);
        		int stringHeight = g.getFontMetrics().getHeight();
        		g.setColor(Color.blue);
        		g.fillRect(FRAME_OFFSET, yPos + FrameSize - stringHeight, FrameSize, stringHeight);
        		
        		g.setColor(Color.white);
        		if(stringWidth > FrameSize)
        		{
        			fileName = fileName.substring(0, MAX_STRING_WIDTH);
        			fileName += "...";
        		}
        		g.drawString(fileName, FRAME_OFFSET + 10, yPos + FrameSize - (stringHeight / 3));
        		g.setColor(Color.black);
        		
        		if(i == SelectionIndex)
        		{
        			Graphics2D g2d = (Graphics2D)g;
        			//Change the brush to the color for the selection
        			g2d.setColor(Color.RED);
	        		g2d.setStroke(THICK_STROKE);
	        		
        			if(IsSelected)
        			{		        		
		        		g2d.drawRect(FRAME_OFFSET, yPos, FrameSize, FrameSize);
		        		
        			}
        			else	//Draw a different selection box to indicate the panel is not selected.
        			{
        				g2d.drawLine(FRAME_OFFSET, yPos, FRAME_OFFSET, yPos + FrameSize);
        			}
        			//Set the brush to the normal color for the rest of the drawing operations.
        			g2d.setColor((Color.BLACK));
	        		g2d.setStroke(NORMAL_STROKE);
        		}
        	}
        	else
        	{
        		g.drawRect(0, i * ThumbSize.height, ThumbSize.width, ThumbSize.height);
            	g.drawLine(0, i * ThumbSize.height, ThumbSize.width, (i * ThumbSize.height) + ThumbSize.height);
        	}    	
        }    
    }
}
