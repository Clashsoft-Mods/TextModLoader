package clashsoft.mods.tml.mcmod;

import java.io.File;

import clashsoft.mods.tml.IMod;

public class MCMod implements IMod
{
	public String name;
	public String modid;
	public String version;
	
	public MCMod()
	{
		
	}
	
	@Override
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public boolean load(File file)
	{
		return false;
	}
	
	@Override
	public long getLoadTime()
	{
		return 0;
	}
	
}
