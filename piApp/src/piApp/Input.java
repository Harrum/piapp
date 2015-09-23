package piApp;

public enum Input 
{
	UP, DOWN, LEFT, RIGHT, ENTER, ESCAPE, UNKOWN;
	
	public static Input getInputFromRemoteCode(int remoteCode)
	{
		Input input = Input.UNKOWN;
		switch(remoteCode)
		{
		default: 	
			input = Input.UNKOWN;
			break;
		}
		return input;
	}
	
	public static Input getInputFromKeyCode(int keyCode)
	{
		Input input = Input.UNKOWN;
		switch (keyCode)
		{
		case 38:
			input = Input.UP;
			break;
		case 39:
			input = Input.RIGHT;
			break;
		case 40:
			input = Input.DOWN;
			break;
		case 37:
			input = Input.LEFT;
			break;
		case 10:
			input = Input.ENTER;
			break;
		case 27:
			input = Input.ESCAPE;
			break;
		default:
			input = Input.UNKOWN;
			break;
		}
		return input;
	}
}
