package com.chaosdev.textmodloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import com.chaosdev.textmodloader.util.TextModConstants;
import com.chaosdev.textmodloader.util.codeblock.ClassCodeBlock;

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
		while ((line = br.readLine()) != null)
		{
			if (line.startsWith("@author "))
				tm.author = line.replaceFirst(Pattern.quote("@author "), "");
			if (line.startsWith("@modname "))
				tm.modName = line.split("=")[1];
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
			System.out.println(" Loading TextMod: " + tm.modName != null ? tm.modName : modClass + (tm.author != null ? " by " + tm.author : ""));
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
}
