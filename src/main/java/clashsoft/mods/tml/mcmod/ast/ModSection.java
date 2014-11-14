package clashsoft.mods.tml.mcmod.ast;

import clashsoft.mods.tml.mcmod.MCMod;

public class ModSection extends Section
{
	public final MCMod mod;
	
	private ConstantSection constantSection;
	
	public ModSection(MCMod mod)
	{
		super(null);
		this.mod = mod;
	}
	
	public void setConstantSection(ConstantSection constantSection)
	{
		this.constantSection = constantSection;
		this.addSubSection(constantSection);
	}
	
	public ConstantSection getConstantSection()
	{
		return this.constantSection;
	}
}
