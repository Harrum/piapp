package misc;

import java.io.File;
import java.io.FileFilter;

public class ImageFileFilter implements FileFilter
{
	private final String[] okFileExtensions = new String[] {"bmp", "jpg", "png", "gif"};
	 
	public boolean accept(File file)
	{
		for (String extension : okFileExtensions)
	    {
			if(file.isDirectory())
			{
				return true;
			}
			else if (file.getName().toLowerCase().endsWith(extension))
			{
				return true;
			}
	    }
		return false;
	}
}
