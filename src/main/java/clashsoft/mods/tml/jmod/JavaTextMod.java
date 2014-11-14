package clashsoft.mods.tml.jmod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import clashsoft.mods.tml.IMod;
import clashsoft.mods.tml.jmod.util.codeblock.ClassCodeBlock;

public class JavaTextMod extends ClassCodeBlock implements IMod
{
	public String	modName;
	public String modID;
	public String version;
	
	public long		start;
	public long		end;
	
	public JavaTextMod()
	{
	}
	
	@Override
	public String getName()
	{
		return this.modName;
	}
	
	@Override
	public String getModID()
	{
		return this.modID;
	}
	
	@Override
	public String getVersion()
	{
		return this.version;
	}
	
	@Override
	public long getLoadTime()
	{
		return this.end - this.start;
	}
	
	@Override
	public boolean load(File file)
	{
		try
		{
			this.start = System.currentTimeMillis();
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine().trim()) != null)
			{
				if (line.startsWith("@author "))
				{
					this.author = line.substring(8);
				}
				else if (line.startsWith("@modname "))
				{
					this.modName = line.substring(9);
				}
				else if (line.startsWith("@modid "))
				{
					this.modID = line.substring(7);
				}
				else if (line.startsWith("@version "))
				{
					this.version = line.substring(9);
				}
				else
				{
					this.lines.add(line);
				}
			}
			br.close();
			
			this.execute();
			this.end = System.currentTimeMillis();
			
			return true;
			
		}
		catch (Exception ex)
		{
			System.out.println(" Unable to load TextMod: " + ex.getMessage());
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("TextMod [");
		if (this.modName != null)
		{
			builder.append("modName=");
			builder.append(this.modName);
			builder.append(", ");
		}
		if (this.author != null)
		{
			builder.append("author=");
			builder.append(this.author);
			builder.append(", ");
		}
		builder.append("]");
		return builder.toString();
	}
}
