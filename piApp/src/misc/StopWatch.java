package misc;

import java.util.concurrent.TimeUnit;

public class StopWatch 
{
	long startTime;
	long stopTime;
	
	public StopWatch()
	{
		Reset();
	}
	
	public void Start()
	{
		startTime = System.nanoTime();
	}
	
	public void Stop()
	{
		stopTime = System.nanoTime();
	}
	
	public void Reset()
	{
		startTime = 0;
		stopTime = 0;
	}
	
	public long TimeInNano()
	{
		return stopTime - startTime;
	}
	
	public long TimeInMillis()
	{
		return TimeUnit.NANOSECONDS.toMillis(stopTime - startTime);
	}
	
	public long TimeInSecs()
	{
		return TimeUnit.NANOSECONDS.toSeconds(stopTime - startTime);
	}
}
