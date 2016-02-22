package panels.fotopanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import misc.ImgUtils;

public class FotoDisplay extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -971485948776554670L;

	private final int FOTO_OFFSET = 20;
	
	private int FotoWidth;
	private int FotoHeight;
	
	private BufferedImage currentFoto;
	
	public FotoDisplay(int width, int height)
	{
		super.setPreferredSize(new Dimension(width, height));
		FotoWidth = width - (FOTO_OFFSET * 2);
		FotoHeight = height - (FOTO_OFFSET * 2);
		super.setBounds(0, 0, width, height);
		System.out.printf("Created fotodisplay width %s, height %s \n", super.getWidth(), super.getHeight());
	}		
	
	public void LoadFoto(File file)
	{
		currentFoto = null;
	    BufferedImage unscaledFoto = null;
		try 
		{
			unscaledFoto = ImageIO.read(file);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		if(unscaledFoto != null)
		{	
			Dimension size = ImgUtils.getScaledDimension(new Dimension(unscaledFoto.getWidth(), unscaledFoto.getHeight()), new Dimension(FotoWidth, FotoHeight));
		    currentFoto = ImgUtils.Scale(unscaledFoto, size);
		    Redraw();
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
        if(currentFoto != null)
        {
	        int xPos = ((super.getWidth() - currentFoto.getWidth()) / 2);
	        int yPos = ((super.getHeight() - currentFoto.getHeight()) / 2);
	        g.drawImage(currentFoto, xPos, yPos, null);
        }
    }
}
