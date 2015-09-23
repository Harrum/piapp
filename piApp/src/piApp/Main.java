package piApp;

import java.io.IOException;

public class Main {

	public static void main(String[] args) 
	{
		//Create the main inputlogger
		PiInputLogger inputLogger = new PiInputLogger();
		
		//Create the specific inputloggers who will trigger the main inputlogger
		//The logger for the keyboard input
		KeyboardLogger keyBoardLogger = new KeyboardLogger(inputLogger);
		
		//the logger for the remote inputs
		try 
		{
			RemoteLogger remoteLogger = new RemoteLogger(inputLogger);
			remoteLogger.Start();
		} 
		catch (IOException e) 
		{
			System.err.println("Unable to create the remote logger: " + e.getMessage());
		}
		
		//Create the window for the application
		Window window = new Window();
		window.createWindow();
		
		//Add the window as listener to the main inputlogger so it can receive the input commands
		inputLogger.addListener(window);
	}

}
