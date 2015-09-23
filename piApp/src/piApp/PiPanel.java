package piApp;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JPanel;

public abstract class PiPanel extends JPanel
{
	protected int Width;
	protected int Height;
	
	public PiPanel()
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		Width = gd.getDisplayMode().getWidth();
		Height = gd.getDisplayMode().getHeight();
		super.setBounds(0, 0, Width, Height);
		System.out.printf("Created a window with the dimension: %d by %d", Width, Height);
	}
	
	protected abstract void InputRecieved(Input input);
}
