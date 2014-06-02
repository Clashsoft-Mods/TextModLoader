package clashsoft.mods.tml;

import java.io.File;

public interface IMod
{
	public String getName();
	
	public String getModID();
	
	public String getVersion();
	
	public boolean load(File file);
	
	public long getLoadTime();
}
