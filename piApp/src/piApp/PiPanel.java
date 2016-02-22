package piApp;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JPanel;

public abstract class PiPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1310488017099957600L;
	protected int Width;
	protected int Height;
	
	public PiPanel()
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		Width = gd.getDisplayMode().getWidth();
		Height = gd.getDisplayMode().getHeight();
		super.setBounds(0, 0, Width, Height);
	}
	
	protected abstract void Init();
	
	protected abstract void Exit();
	
	protected abstract void InputRecieved(Input input);
}
