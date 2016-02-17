package piApp;

import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) 
	{
		//final String DEFAULT_PATH = RUN_PATH + "\\config.txt";
		//final String DEFAULT_PATH = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()) + "\\config.txt";
		final String DEFAULT_PATH = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getAbsolutePath() + "\\config.txt";

		
		String configPath = "";
		
		if(args.length > 0)
		{
			String path = args[0].trim();
			configPath = path;
		}
		else
		{
			System.out.println("No argument given for config path, using default path. ");
			configPath = DEFAULT_PATH;
		}
		
		if(!Config.GetInstance().SetPath(configPath))
		{
			System.err.println("Invalid path given, using default...");
			configPath = DEFAULT_PATH;
			if(!Config.GetInstance().SetPath(configPath))
			{
				System.err.println("Could not find config file, exiting...");
				System.exit(-1);
			}
		}
		
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
