package panels.fotopanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import misc.ImgUtils;
import misc.StopWatch;
import piApp.Input;
import piApp.PiPanel;
import piApp.Window;

public class SlideShowPanel extends PiPanel implements Runnable
{
	private static final long serialVersionUID = -2894971312365547733L;
	private final int SLIDE_DELAY = 5000;
	private List<File> Slides = new ArrayList<File>();
	private int Index;
	private BufferedImage Slide;
	private BufferedImage NextSlide;
	private Thread t;
	private boolean isRunning;
	
	public SlideShowPanel(List<File> slides, boolean shuffle)
	{
		super();
		
		this.Slides = slides;
		if(shuffle)
			Collections.shuffle(Slides);
		Index = 0;
		Slide = null;
		NextSlide = null;
		isRunning = false;	
	}
	
	@Override
	protected void Init() 
	{
		StartSlideShow();
	}

	@Override
	protected void Exit() 
	{
		StopSlideShow();
	}
	
	public void StartSlideShow()
	{
		System.out.println("Starting slideshow");
		isRunning = true;
		t = new Thread(this);
		t.setName("SlideShowThread");
		t.start();
	}
	
	private void StopSlideShow()
	{
		isRunning = false;
		
		while(t.isAlive())
		{
			try 
			{
				Thread.sleep(100);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.printf("Thread %s has stopped. \n", t.getName());
		t = null;	
		System.out.println("Slideshow stopped");
	}
	
	private void LoadSlide()
	{
		NextSlide = null;
		File foto = Slides.get(Index);
	    BufferedImage unscaledFoto = null;
		try 
		{
			unscaledFoto = ImageIO.read(foto);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		if(unscaledFoto != null)
		{	
			Dimension size = ImgUtils.getScaledDimension(new Dimension(unscaledFoto.getWidth(), unscaledFoto.getHeight()), new Dimension(this.Width, this.Height));
			NextSlide = ImgUtils.Scale(unscaledFoto, size);
		}
	}
	
	@Override
    protected void paintComponent(Graphics g) 
	{
        super.paintComponent(g);
        if(Slide != null)
        {
	        int xPos = ((super.getWidth() - Slide.getWidth()) / 2);
	        int yPos = ((super.getHeight() - Slide.getHeight()) / 2);
	        g.drawImage(Slide, xPos, yPos, null);
        }
	}
	
	private void Redraw()
	{
		this.repaint();
		this.setVisible(true);
		this.setEnabled(true);
	}
	
	@Override
	protected void InputRecieved(Input input) 
	{

	}

	@Override
	public void run() 
	{
		//The loop needs the redraw at the the first roulation.
		LoadSlide();
		long timer = SLIDE_DELAY * 2;
		StopWatch s = new StopWatch();
		
		while(isRunning)
		{
			s.Start();
			if(NextSlide != null)
			{
				if(timer > SLIDE_DELAY)
				{
					Slide = NextSlide;
					Redraw();
					if(Index < Slides.size() - 1)
						Index++;
					else
						Index = 0;
					
					LoadSlide();
					timer = 0;
				}				
			}
			try 
			{
				Thread.sleep(200);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			s.Stop();
			timer += s.TimeInMillis();
			s.Reset();
		}
	}
}
