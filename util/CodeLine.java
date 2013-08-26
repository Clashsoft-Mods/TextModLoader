package com.chaosdev.textmodloader.util;

public class CodeLine
{
	public int lineNumber;
	public String line;
	
	public CodeLine(int number, String line)
	{
		this.lineNumber = number;
		this.line = line;
	}
	
	public int find(String s)
	{
		return line.indexOf(s);
	}
}
