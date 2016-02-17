package panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import misc.ImgUtils;
import piApp.Input;
import piApp.PiPanel;

public class BuienRadarPanel extends PiPanel implements Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1127789063091959654L;
	private List<BufferedImage> images;
	private int frame;
	private int count;
	private boolean isRunning;
	Thread t;
	
	public BuienRadarPanel()
	{
		super();
		frame = 0;
		count = 0;
		images = new ArrayList<BufferedImage>();
		isRunning = false;
	}

	@Override
	protected void Init() 
	{
		CreatePanel();
		
		isRunning = true;
		t = new Thread(this);
		t.setName("BuienRadarThread");
		t.start();
	}

	@Override
	protected void Exit()
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
		images.clear();
	}
	
	public void CreatePanel()
	{
		ImageReader reader = ImageIO.getImageReadersBySuffix("gif").next();
		ImageInputStream in;
		try 
		{
			URL u = new URL("http://api.buienradar.nl/image/1.0/RadarMapNL?w=1000&h=1000");
		    URLConnection uc = u.openConnection();
		    InputStream raw = uc.getInputStream();
		    InputStream rawin = new BufferedInputStream(raw);
			in = ImageIO.createImageInputStream(rawin);
			//in = ImageIO.createImageInputStream(new URL("http://buienradar.nl/image?fn=buienradarnl-1x1-ani550-verwachting.gif&type=forecastzozw"));
			
			reader.setInput(in);
			count = reader.getNumImages(true);
			
			for (int i = 0; i < count; i++)
			{
			    BufferedImage image = null;
				image = reader.read(i);
				if(image != null)
				{				
					Dimension Size = ImgUtils.getScaledDimension(new Dimension(image.getWidth(), image.getHeight()), new Dimension(this.Width, this.Height));
					images.add(ImgUtils.Scale(image, Size));
				}
			    //images[i] = image;
			    //ImageIO.write(image, "PNG", new File("output" + i + ".png"));
			}
		} 
		catch (MalformedURLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
    protected void paintComponent(Graphics g) 
	{
        super.paintComponent(g); 
        BufferedImage img = images.get(frame);
        int xPos = ((super.getWidth() - img.getWidth()) / 2);
        int yPos = ((super.getHeight() - img.getHeight()) / 2);
        g.drawImage(img, xPos, yPos, null);
        //g.drawImage(img, 0, 0, null); // see javadoc for more info on the parameters            
    }
	
	@Override
	protected void InputRecieved(Input input) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() 
	{
		while(isRunning)
		{
			try 
			{
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			if(frame < images.size() - 1) 
				frame++;
			else 
				frame = 0;
			//invalidate();
			repaint();
		}
	}
}
