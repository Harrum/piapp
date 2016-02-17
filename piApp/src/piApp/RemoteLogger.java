package piApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RemoteLogger extends InputLogger implements Runnable
{

	private final String KEY_PRESSED = "key pressed:";
	
	String cmd = "cec-client";
	Process p;
	Thread t;
	
	protected RemoteLogger(PiInputLogger piInputLogger) throws IOException 
	{
		super(piInputLogger);

		p = new ProcessBuilder(cmd).start();
		
		Runtime.getRuntime().addShutdownHook(new Thread() 
		{
		    public void run() 
		    {
		    	String quitValue = "quit";
		    	
		        try 
		        {
					p.getOutputStream().write(quitValue.getBytes());
					Stop();
				} 
		        catch (IOException e) 
		        {
					// TODO Auto-generated catch block
					e.printStackTrace();	
				}
		        System.out.println("shutdownhook stopped");
		    }
		});
	}
	
	public void Start()
	{
		t = new Thread(this);
		t.start();
	}
	
	public void Stop()
	{
		t = null;
	}
	
	private void ProcesLine(String line)
	{
		//System.out.println("Received input: " + line);
		if(line.contains(KEY_PRESSED))
		{
			line.trim();
			String key = line.split(" +")[4];
			System.out.println("Key pressed: " + key);
			Input input = Input.getInputFromRemoteCode(key);
			inputPressed(input);
			//key pressed
		}
	}

	@Override
	public void run() 
	{
		String line;
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		try 
		{
			while ((line = input.readLine()) != null) 
			{
				ProcesLine(line);
				//System.out.println(line);
			}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try 
		{
			input.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		System.out.println("java main program stopped");
	}
	
	public static void main(String[] args) throws IOException 
	{
         String line = "DEBUG:   [          154105]     key released: up (1)";
         line.trim();
         String key = line.split(" +")[4];
         System.out.println("Key pressed: " + key);
         Input input = Input.getInputFromRemoteCode(key);
         System.out.println("Input pressed: " + input.toString());
	}
}
