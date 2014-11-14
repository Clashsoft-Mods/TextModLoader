package clashsoft.mods.tml.mcmod.ast;

import java.util.HashMap;
import java.util.Map;

public class ConstantSection extends Section
{
	private Map<String, Object> constants = new HashMap();
	
	public ConstantSection(Section parent)
	{
		super(parent);
	}
	
	public void addConstant(String name, Object value)
	{
		this.constants.put(name, value);
	}
	
	public Object getConstant(String name)
	{
		return this.constants.get(name);
	}
}
