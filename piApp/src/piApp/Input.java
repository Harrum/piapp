package piApp;

public enum Input 
{
	UP, DOWN, LEFT, RIGHT, ENTER, BACK, ESCAPE, UNKOWN;
	
	public static Input getInputFromRemoteCode(String key)
	{
		Input input = Input.UNKOWN;
		if(key.equals("up"))
			input = Input.UP;
		else if(key.equals("down"))
			input = Input.DOWN;
		else if(key.equals("left"))
			input = Input.LEFT;
		else if(key.equals("right"))
			input = Input.RIGHT;
		else if(key.equals("select"))
			input = Input.ENTER;
		else if(key.equals("exit"))
			input = Input.ESCAPE;
		else if(key.equals("return"))
			input = Input.BACK;
		else
			input = Input.UNKOWN;
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
