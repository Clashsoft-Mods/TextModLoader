package clashsoft.mods.tml.jmod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import clashsoft.mods.tml.TextModConstants;
import clashsoft.mods.tml.TextModLoader;
import clashsoft.mods.tml.jmod.util.codeblock.ClassCodeBlock;

public class TextMod extends ClassCodeBlock implements TextModConstants
{
	public String	modName			= "";
	public long		loadTime		= -1L;
	public long		executedTime	= -1L;
	
	public TextMod()
	{
		super();
		loadTime = System.currentTimeMillis();
	}
	
	public long getTotalLoadingTime()
	{
		return executedTime - loadTime;
	}
	
	public static TextMod fromFile(File modClass) throws IOException
	{
		TextMod tm = new TextMod();
		BufferedReader br = new BufferedReader(new FileReader(modClass));
		String line;
		while ((line = br.readLine().trim()) != null)
		{
			if (line.startsWith("@author "))
				tm.author = line.substring(8);
			else if (line.startsWith("@modname "))
				tm.modName = line.substring(9);
			else
				tm.lines.add(line);
		}
		br.close();
		return tm;
	}
	
	public static long load(File modClass)
	{
		try
		{
			TextMod tm = fromFile(modClass);
			System.out.println(" Loading TextMod: " + (tm.modName != null ? tm.modName : modClass) + (tm.author != null ? " by " + tm.author : ""));
			tm.execute();
			tm.executedTime = System.currentTimeMillis();
			System.out.println(" TextMod " + modClass + " loaded. (" + tm.getTotalLoadingTime() + " Milliseconds)");
			TextModLoader.loadedTextMods.add(tm);
			return tm.getTotalLoadingTime();
		}
		catch (Exception ex)
		{
			System.out.println(" Unable to load TextMod: " + ex.getMessage());
			return 0L;
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