package panels.fotopanel;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.imageio.stream.FileImageOutputStream;
import javax.swing.JPanel;

import misc.ImageFileFilter;
import misc.ToggleButtonMenu.ToggleButtonMenuListener;
import piApp.Input;
import piApp.PiPanel;
import piApp.Window;

public class FotoPanel extends PiPanel implements ToggleButtonMenuListener
{
	private String path;
	private FotoLijst ThumbLijst;
	private FotoDisplay Display;
	private FotoNavMenu Menu;
	
	public FotoPanel()
	{
		super();	
		this.path = "";
	}
	
	public FotoPanel(String path)
	{
		super();
		this.path = path;
	}
	
	@Override
	protected void Init() 
	{
		CreatePanel();
	}

	@Override
	protected void Exit() 
	{
		// TODO Auto-generated method stub
		
	}
	
	public void CreatePanel()
	{
		JPanel container = new JPanel(new BorderLayout());
		ThumbLijst = new FotoLijst((int)(Width * 0.10), (int)(Height * 0.80));
		if(path == "")
			ThumbLijst.CreatePanelRoot();
		else
			ThumbLijst.CreatePanel(path);		
		Menu = new FotoNavMenu((int)(Width * 0.10), (int)(Height * 0.10));
		Menu.CreatePanel();
		Menu.SetListener(this);
		container.add(ThumbLijst, BorderLayout.PAGE_START);
		container.add(Menu, BorderLayout.PAGE_END);
		
		Display = new FotoDisplay((int)(Width * 0.85), Height);
		super.setLayout(new BorderLayout());
		super.add(container, BorderLayout.WEST);
		//super.add(ThumbLijst, BorderLayout.WEST);
		super.add(Display, BorderLayout.EAST);
		//super.add(Menu, BorderLayout.LINE_START);
		container.invalidate();
		container.setVisible(true);
		super.invalidate();
		super.setVisible(true);
		ThumbLijst.invalidate();
		System.out.println("Created FotoPanel with the path: " + this.path);
	}
	
	private void OpenFolder()
	{
		if(ThumbLijst.IsDirectory())
		{
			PiPanel newPanel = new FotoPanel(ThumbLijst.GetSelectedFile().getAbsolutePath());
			Window.getInstance().EnterPanel(newPanel);
		}
		else
			System.err.println("Can't open this path as it's not a directory. \n" + ThumbLijst.GetSelectedFile().getAbsolutePath());
	}
	
	@Override
	protected void InputRecieved(Input input) 
	{
		if(input == Input.DOWN)
		{
			if(Menu.Down() == false)
				ThumbLijst.ScrollList(false);
		}
		else if(input == Input.UP)
		{
			if(Menu.Up() == false)
				ThumbLijst.ScrollList(true);
		}			
		else if(input == Input.ENTER)
			if(ThumbLijst.IsSelected())
			{
				if(ThumbLijst.IsDirectory())
					OpenFolder();
				else
					Display.LoadFoto(ThumbLijst.GetSelectedFile());
			}
			else
				Menu.Enter();
		else if(input == Input.LEFT)
		{
			if(Menu.Left() == false)
				ThumbLijst.SetSelected(true);
			else
				ThumbLijst.SetSelected(false);
		}
		else if(input == Input.RIGHT)
		{
			if(Menu.Right() == false)
				ThumbLijst.SetSelected(true);
			else
				ThumbLijst.SetSelected(false);
		}
	}

	
	@Override
    protected void paintComponent(Graphics g) 
	{
        super.paintComponent(g);  
    }

	@Override
	public void ToggleMenuButtonPressed(String bText) 
	{
		System.out.println("Toggle menu button clicked: " + bText);
		if(bText == "<")
		{
			ThumbLijst.ScrollList(true);
			if(!ThumbLijst.IsDirectory())
				Display.LoadFoto(ThumbLijst.GetSelectedFile());
		}
		else if(bText == ">")
		{	
			ThumbLijst.ScrollList(false);
			if(!ThumbLijst.IsDirectory())
				Display.LoadFoto(ThumbLijst.GetSelectedFile());
		}
		else if(bText == "|<")
		{	
			ThumbLijst.ScrollToBegin();
			if(!ThumbLijst.IsDirectory())
				Display.LoadFoto(ThumbLijst.GetSelectedFile());
		}
		else if(bText == ">|")
		{	
			ThumbLijst.ScrollToEnd();
			if(!ThumbLijst.IsDirectory())
				Display.LoadFoto(ThumbLijst.GetSelectedFile());
		}
		else if(bText == "Slideshow")
		{
			Window.getInstance().EnterPanel(new SlideShowPanel(ThumbLijst.GetFotos(), false));
		}
		else if(bText == "Slideshuffle")
		{
			Window.getInstance().EnterPanel(new SlideShowPanel(ThumbLijst.GetFotos(), true));
		}
	}
}
