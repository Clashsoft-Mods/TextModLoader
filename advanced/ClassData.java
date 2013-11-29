package com.chaosdev.textmodloader.advanced;

import java.util.HashMap;
import java.util.Map;

public class ClassData
{
	public String name;
	public String thePackage;
	public String fullClassName;
	
	public Map<String, String>	imports	= new HashMap<String, String>();
	
	public String getClass(String name)
	{
		if (this.name.equals(name))
			return fullClassName;
		
		return imports.get(name);
	}
}
