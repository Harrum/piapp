package piApp;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class MainMenu extends PiPanel
{
	private final int COLLUMNS = 2;
	private ButtonGroup buttonGroup;
	private int selectedButtonIndex = 0;
	
	public MainMenu()
	{
		super();
		buttonGroup = new ButtonGroup();
		createMainMenu();
	}
	
	private void createMainMenu()
	{		
		GridLayout buttonLayout = new GridLayout(0, COLLUMNS);
		super.setLayout(buttonLayout);
		
		createButton("Buienradar");
		createButton("Films");
		createButton("Foto's");
		createButton("Leeg");
		
	}
	
	private void createButton(String buttonText)
	{
		Font f = new Font("Arial", Font.PLAIN, 30);
		JToggleButton b = new JToggleButton(buttonText);
		b.setFont(f);
		buttonGroup.add(b);
		super.add(b);
	}
	
	private void EnterButtonAction()
	{
		Enumeration<AbstractButton> buttons = buttonGroup.getElements();
		
		while(buttons.hasMoreElements())
		{
			AbstractButton b = buttons.nextElement();
			if(b.isSelected())
			{
				System.out.println("Button pressed: " + b.getText());
				if(b.getText() == "Buienradar")
				{
					Window.getInstance().ChangePanel(new BuienRadarPanel());
				}
			}
		}
	}
	
	private void SelectButton(int increment)
	{
		int index = 0;
		if(selectedButtonIndex + increment > buttonGroup.getButtonCount() - 1)
			index = -1 + ((selectedButtonIndex + increment) - (buttonGroup.getButtonCount() - 1));
		else if(selectedButtonIndex + increment < 0)
			index = buttonGroup.getButtonCount() + (selectedButtonIndex + increment);
		else
			index = selectedButtonIndex + increment;
		
		Enumeration<AbstractButton> buttons = buttonGroup.getElements();
		int i = 0;
		
		while(buttons.hasMoreElements())
		{
			AbstractButton b = buttons.nextElement();
			if(i == index)
			{
				b.setSelected(true);
				selectedButtonIndex = index;
				break;
			}
			else
				i++;
			
		}
	}

	@Override
	public void InputRecieved(Input input) 
	{
		switch(input)
		{
			case RIGHT:
				SelectButton(1);
				break;
			case LEFT:
				SelectButton(-1);
				break;
			case UP:
				SelectButton(-COLLUMNS);
				break;
			case DOWN:
				SelectButton(COLLUMNS);
				break;
			case ENTER:
				EnterButtonAction();
				break;
			case ESCAPE:
				break;
			case UNKOWN:
				break;
			default:
				break;
		}
	}
}
