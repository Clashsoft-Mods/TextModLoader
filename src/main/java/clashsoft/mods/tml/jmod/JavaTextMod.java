package clashsoft.mods.tml.jmod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import clashsoft.mods.tml.IMod;
import clashsoft.mods.tml.TextModConstants;
import clashsoft.mods.tml.jmod.util.codeblock.ClassCodeBlock;

public class JavaTextMod extends ClassCodeBlock implements TextModConstants, IMod
{
	public String	modName		= "";
	public long		startTime	= -1L;
	public long		endTime		= -1L;
	
	public JavaTextMod()
	{
		super();
		this.startTime = System.currentTimeMillis();
	}
	
	@Override
	public String getName()
	{
		return this.modName;
	}
	
	@Override
	public long getLoadTime()
	{
		return this.endTime - this.startTime;
	}
	
	@Override
	public boolean load(File file)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine().trim()) != null)
			{
				if (line.startsWith("@author "))
					this.author = line.substring(8);
				else if (line.startsWith("@modname "))
					this.modName = line.substring(9);
				else
					this.lines.add(line);
			}
			br.close();
			
			System.out.println(" Loading TextMod: " + (this.modName != null ? this.modName : file) + (this.author != null ? " by " + this.author : ""));
			this.execute();
			this.endTime = System.currentTimeMillis();
			System.out.println(" TextMod " + file + " loaded. (" + this.getLoadTime() + " Milliseconds)");
			return true;
			
		}
		catch (Exception ex)
		{
			System.out.println(" Unable to load TextMod: " + ex.getMessage());
			return false;
		}
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
