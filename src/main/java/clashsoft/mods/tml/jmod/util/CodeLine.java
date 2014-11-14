package clashsoft.mods.tml.jmod.util;

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
		return this.line.indexOf(s);
	}
}
