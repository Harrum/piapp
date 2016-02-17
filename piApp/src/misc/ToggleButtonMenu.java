package misc;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;

import panels.BuienRadarPanel;
import panels.fotopanel.FotoPanel;
import piApp.Window;

public class ToggleButtonMenu 
{
	public interface ToggleButtonMenuListener
	{
		void ToggleMenuButtonPressed(String bText);
	}
	
	private ButtonGroup buttonGroup;
	private int selectedButtonIndex = 0;
	private ToggleButtonMenuListener listener;
	
	public ToggleButtonMenu()
	{
		buttonGroup = new ButtonGroup();
	}
	
	public int GetButtonCount()
	{
		return buttonGroup.getButtonCount();
	}
	
	public int GetSelectedIndex()
	{
		return selectedButtonIndex;
	}
	
	public void SetSelectedIndex(int index)
	{
		selectedButtonIndex = index;
		SetButtonSelection();
	}
	
	public void ButtonPressed(String bText)
	{
		listener.ToggleMenuButtonPressed(bText);
	}
	
	public void SetListener(ToggleButtonMenuListener listener)
	{
		this.listener = listener;
	}
	
	public JToggleButton createButton(String buttonText)
	{
		return createButton(buttonText, null);
	}
	
	public JToggleButton createButton(String buttonText, Font f)
	{
		JToggleButton b = new JToggleButton(buttonText);
		if(f != null)
		{
			b.setFont(f);
		}
		buttonGroup.add(b);
		return b;
	}
	
	public void EnterButtonAction()
	{
		Enumeration<AbstractButton> buttons = buttonGroup.getElements();
		
		while(buttons.hasMoreElements())
		{
			AbstractButton b = buttons.nextElement();
			if(b.isSelected())
			{
				System.out.println("Button pressed: " + b.getText());
				ButtonPressed(b.getText());
			}
		}
	}
	
	public void SelectButton(int increment)
	{
		int index = 0;
		if(selectedButtonIndex + increment > buttonGroup.getButtonCount() - 1)
			index = -1 + ((selectedButtonIndex + increment) - (buttonGroup.getButtonCount() - 1));
		else if(selectedButtonIndex + increment < 0)
			index = buttonGroup.getButtonCount() + (selectedButtonIndex + increment);
		else
			index = selectedButtonIndex + increment;
		selectedButtonIndex = index;
		SetButtonSelection();
	}
	
	private void SetButtonSelection()
	{
		if(selectedButtonIndex == -1)
		{
			buttonGroup.clearSelection();
		}
		else
		{
			Enumeration<AbstractButton> buttons = buttonGroup.getElements();
			int i = 0;
			
			while(buttons.hasMoreElements())
			{
				AbstractButton b = buttons.nextElement();
				if(i == selectedButtonIndex)
				{
					b.setSelected(true);
					break;
				}
				else
					i++;
				
			}
		}
	}
}
