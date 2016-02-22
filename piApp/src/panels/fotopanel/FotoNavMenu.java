package panels.fotopanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JToggleButton;

import misc.ToggleButtonMenu;
import misc.ToggleButtonMenu.ToggleButtonMenuListener;

public class FotoNavMenu extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4525604971125729260L;
	private ToggleButtonMenu buttonMenu;
	
	public FotoNavMenu(int width, int height)
	{
		super.setPreferredSize(new Dimension(width, height));
		buttonMenu = new ToggleButtonMenu();
		buttonMenu.SetSelectedIndex(-1);
	}
	
	public void SetListener(ToggleButtonMenuListener listener)
	{
		buttonMenu.SetListener(listener);
	}
	
	public void Enter()
	{
		buttonMenu.EnterButtonAction();
	}
	
	public boolean Left()
	{
		if(buttonMenu.GetSelectedIndex() == 0 || buttonMenu.GetSelectedIndex() > 3)
		{
			buttonMenu.SetSelectedIndex(-1);;
			return false;
		}
		else if(buttonMenu.GetSelectedIndex() == -1)
		{
			buttonMenu.SetSelectedIndex(3);;
			return true;
		}
		else
		{
			buttonMenu.SelectButton(-1);
			return true;
		}
	}
	
	public boolean Right()
	{
		if(buttonMenu.GetSelectedIndex() >= 3)
		{
			buttonMenu.SetSelectedIndex(-1);;
			return false;
		}
		else if(buttonMenu.GetSelectedIndex() == -1)
		{
			buttonMenu.SetSelectedIndex(0);
			return true;
		}
		else
		{
			buttonMenu.SelectButton(+1);
			return true;
		}
	}
	
	public boolean Up()
	{
		if(buttonMenu.GetSelectedIndex() >= 0 && buttonMenu.GetSelectedIndex() <= 3)
		{
			//Do nothing but keep it selected
			return true;
		}
		if(buttonMenu.GetSelectedIndex() == -1)
		{
			return false;
		}
		else
		{
			buttonMenu.SelectButton(-1);
			return true;
		}
	}
	
	public boolean Down()
	{
		if(buttonMenu.GetSelectedIndex() >= 0 && buttonMenu.GetSelectedIndex() <= 3)
		{
			buttonMenu.SetSelectedIndex(4);
			return true;
		}
		else if(buttonMenu.GetSelectedIndex() == buttonMenu.GetButtonCount() - 1)
		{
			//Do nothing but keep it selected
			return true;
		}
		if(buttonMenu.GetSelectedIndex() == -1)
		{
			return false;
		}
		else
		{
			buttonMenu.SelectButton(1);
			return true;
		}
	}
	
	public void CreatePanel()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JToggleButton leftstart = buttonMenu.createButton("|<");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		this.add(leftstart, c);
		
		JToggleButton left = buttonMenu.createButton("<");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		this.add(left, c);
		
		JToggleButton right = buttonMenu.createButton(">");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		this.add(right, c);
		
		JToggleButton rightend = buttonMenu.createButton(">|");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 3;
		c.gridy = 0;
		this.add(rightend, c);
		
		JToggleButton slideshow = buttonMenu.createButton("Slideshow");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 1;
		this.add(slideshow, c);
		
		JToggleButton slideshuffle = buttonMenu.createButton("Slideshuffle");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 2;
		this.add(slideshuffle, c);
		
		this.repaint();
        this.setVisible(true);
        this.setEnabled(true);
	}
}
