package clashsoft.mods.tml;

import java.io.File;

public interface IMod
{
	public String getName();
	
	public boolean load(File file);
	
	public long getLoadTime();
}
