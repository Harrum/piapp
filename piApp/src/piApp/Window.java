package piApp;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;

import piApp.PiInputLogger.PiInputListener;

public class Window implements PiInputListener
{
	private JFrame frame;
	private int width;
	private int height;
	
	private static Window instance;
	private PiPanel currentPanel;
	
	public Window()
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		width = gd.getDisplayMode().getWidth();
		height = gd.getDisplayMode().getHeight();
		
		System.out.printf("Created a window with the dimension: %d by %d", width, height);
		
		instance = this;
	}
	
	public static Window getInstance()
	{
		return instance;
	}
	
	public void ChangePanel(PiPanel newPanel)
	{
		frame.remove(currentPanel);
		currentPanel = newPanel;
		frame.add(currentPanel);
		frame.invalidate();
		frame.repaint();
		frame.setVisible(true);
	}
	
	public void ExitPanel()
	{
		ChangePanel(new MainMenu());
	}
	
	public void createWindow()
	{
		//1. Create the frame.
		frame = new JFrame("FrameDemo");

		//2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		//4. Size the frame.
		
		frame.setBounds(0, 0, width, height);

		currentPanel = new MainMenu();

		frame.add(currentPanel);

		//5. Show it.
		frame.setVisible(true);
	}
	
	@Override
	public void inputReceived(Input input) 
	{
		System.out.println("Input received: " + input.toString());
		
		if(input == Input.ESCAPE)
		{
			ExitPanel();
		}
		
		currentPanel.InputRecieved(input);
	}
}
