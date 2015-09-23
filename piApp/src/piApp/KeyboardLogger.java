package piApp;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class KeyboardLogger extends InputLogger implements KeyEventDispatcher 
{
	public KeyboardLogger(PiInputLogger piInputLogger) 
	{
		super(piInputLogger);
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(this);  
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) 
	{
		if (e.getID() == KeyEvent.KEY_PRESSED) 
		{
            //System.out.println("Key pressed " + e.getKeyCode());
        } 
		else if (e.getID() == KeyEvent.KEY_RELEASED) 
        {
			System.out.println("Key released " + e.getKeyCode());
			inputPressed(Input.getInputFromKeyCode(e.getKeyCode()));
        } 
        else if (e.getID() == KeyEvent.KEY_TYPED) 
        {
        	//System.out.println("Key typed " + e.getKeyCode());
        }
        return false;
	}
	
}
