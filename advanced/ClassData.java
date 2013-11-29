package com.chaosdev.textmodloader.advanced;

import java.util.HashMap;
import java.util.Map;

import com.chaosdev.textmodloader.advanced.members.Member;

public class ClassData
{
	public static Map<String, ClassData> classes = new HashMap();
	
	public String name;
	public String thePackage;
	public String fullClassName;
	
	/**
	 * Imports. The key is the short class name, the value is the ClassData instance.
	 */
	public Map<String, ClassData>	imports	= new HashMap<String, ClassData>();
	
	/**
	 * Members. The key is the short member name, the value is the Member instance.
	 */
	public Map<String, Member>	members = new HashMap<String, Member>();
	
	public static ClassData getClass(String className)
	{
		return classes.get(className);
	}
	
	public String getFullClassName(String className)
	{
		if (className.equals(name) || className.equals(fullClassName))
			return fullClassName;
		
		ClassData classData = imports.get(className);
		return classData != null ? classData.fullClassName : className;
	}
	
	public Member getMember(String className, String memberName)
	{
		Member member = members.get(memberName);
		if (className.equals(fullClassName) || member != null)
			return member;
		else
			return getClass(className).getMember(className, memberName);
	}
}
