package piApp;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import misc.ToggleButtonMenu;
import misc.ToggleButtonMenu.ToggleButtonMenuListener;
import panels.BuienRadarPanel;
import panels.fotopanel.FotoPanel;

public class MainMenu extends PiPanel implements ToggleButtonMenuListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4657165611978798220L;
	private final int COLLUMNS = 2;
	private ToggleButtonMenu buttonMenu;
	
	public MainMenu()
	{
		super();
		buttonMenu = new ToggleButtonMenu();
		buttonMenu.SetListener(this);	
	}
	

	@Override
	protected void Init() 
	{
		createMainMenu();
	}

	@Override
	protected void Exit() {
		// TODO Auto-generated method stub
		
	}
	
	private void createMainMenu()
	{		
		GridLayout buttonLayout = new GridLayout(0, COLLUMNS);
		super.setLayout(buttonLayout);
		Font f = new Font("Arial", Font.PLAIN, 30);
		this.add(buttonMenu.createButton("Buienradar", f));
		this.add(buttonMenu.createButton("Films", f));
		this.add(buttonMenu.createButton("Foto's", f));
		this.add(buttonMenu.createButton("Leeg", f));
		
	}
	
	@Override
	public void ToggleMenuButtonPressed(String bText) 
	{
		if(bText == "Buienradar")
		{
			Window.getInstance().EnterPanel(new BuienRadarPanel());
		}
		if(bText == "Foto's")
		{
			Window.getInstance().EnterPanel(new FotoPanel());
		}
	}

	@Override
	public void InputRecieved(Input input) 
	{
		switch(input)
		{
			case RIGHT:
				buttonMenu.SelectButton(1);
				break;
			case LEFT:
				buttonMenu.SelectButton(-1);
				break;
			case UP:
				buttonMenu.SelectButton(-COLLUMNS);
				break;
			case DOWN:
				buttonMenu.SelectButton(COLLUMNS);
				break;
			case ENTER:
				buttonMenu.EnterButtonAction();
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
