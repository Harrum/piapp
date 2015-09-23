package piApp;

public class InputLogger 
{
	private PiInputLogger piInputLogger;
	
	protected InputLogger(PiInputLogger piInputLogger)
	{
		this.piInputLogger = piInputLogger;
	}
	
	protected void inputPressed(Input input)
	{
		piInputLogger.inputPressed(input);
	}
}
