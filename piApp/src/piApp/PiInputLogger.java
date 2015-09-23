package piApp;

import java.util.ArrayList;
import java.util.List;

public class PiInputLogger
{
	public interface PiInputListener
	{
		void inputReceived(Input input);
	}
	
	private List<PiInputListener> listeners = new ArrayList<PiInputListener>();
	
	public void addListener(PiInputListener listener)
	{
		listeners.add(listener);
	}
	
	public void inputPressed(Input input)
	{
		for (PiInputListener pil : listeners)
		{
			pil.inputReceived(input);
		}
	}
}
