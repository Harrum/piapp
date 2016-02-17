package piApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Config 
{
	private static Config instance;
	
	private String configPath;
	
	private Config()
	{
		configPath = "";
	}
	
	public static Config GetInstance()
	{
		if(instance == null)
			instance = new Config();
		return instance;
	}
	
	public boolean SetPath(String path)
	{
		File f = new File(path);
		if(f.exists())
		{
			configPath = path;
			System.out.println("Config path set: " + configPath);
			return true;
		}
		else
		{
			System.err.println("Invalid path given: " + path);
			return false;
		}
	}
	
	public List<File> GetPaths()
	{
		List<File> files = new ArrayList<File>();
		File f = new File(configPath);
		if(f.exists())
		{
			try 
			{
				BufferedReader br = new BufferedReader(new FileReader(configPath));
				String rootPath = br.readLine();
				while(rootPath != "" && rootPath != null)
				{
					File folder = new File(rootPath);
					files.add(folder);
					rootPath = br.readLine();
				}
				br.close();
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			System.err.println("Could not find config file: " + configPath);
			Window.getInstance().ExitPanel();
		}
		return files;
	}
}
