package piApp;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Stack;

import javax.swing.JFrame;

import piApp.PiInputLogger.PiInputListener;

public class Window implements PiInputListener
{
	private JFrame frame;
	private int width;
	private int height;
	
	private static Window instance;
	private Stack<PiPanel> panels;
	
	public Window()
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		width = gd.getDisplayMode().getWidth();
		height = gd.getDisplayMode().getHeight();
		panels = new Stack<PiPanel>();
		System.out.printf("Created a window with the dimension: %d by %d", width, height);
		
		instance = this;
	}
	
	public static Window getInstance()
	{
		return instance;
	}
	
	private void ChangePanel()
	{	
		frame.add(panels.peek());
		frame.invalidate();
		frame.repaint();
		frame.setVisible(true);
	}
	
	public void EnterPanel(PiPanel newPanel)
	{
		if(panels.size() > 0)
			frame.remove(panels.peek());
		panels.push(newPanel);
		newPanel.Init();
		ChangePanel();
	}
	
	public void ExitPanel()
	{
		if(panels.size() > 1 )
		{
			panels.peek().Exit();
			frame.remove(panels.pop());
			ChangePanel();
		}
	}
	
	public void createWindow()
	{
		//1. Create the frame.
		frame = new JFrame("FrameDemo");

		//2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		//4. Size the frame.
		
		frame.setBounds(0, 0, width, height);

		EnterPanel(new MainMenu());

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
		
		panels.peek().InputRecieved(input);
	}
}
