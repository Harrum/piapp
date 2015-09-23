package piApp;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class BuienRadarPanel extends PiPanel implements Runnable
{
	private List<BufferedImage> images;
	private int frame;
	private int count;
	
	public BuienRadarPanel()
	{
		super();
		frame = 0;
		count = 0;
		images = new ArrayList<BufferedImage>();
		CreatePanel();
		
		Thread t = new Thread(this);
		t.start();
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
			in = ImageIO.createImageInputStream(raw);
			//in = ImageIO.createImageInputStream(new URL("http://buienradar.nl/image?fn=buienradarnl-1x1-ani550-verwachting.gif&type=forecastzozw"));
			
			reader.setInput(in);
			count = reader.getNumImages(true);
			
			for (int i = 0; i < count; i++)
			{
			    BufferedImage image = null;
				image = reader.read(i);
				if(image != null)
					images.add(image);
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
        g.drawImage(images.get(frame), 0, 0, Width, Height, null);
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
		while(true)
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
