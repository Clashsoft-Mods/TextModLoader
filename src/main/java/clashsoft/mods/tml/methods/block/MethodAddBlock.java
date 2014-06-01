package clashsoft.mods.tml.methods.block;

import clashsoft.cslib.minecraft.block.CSBlocks;
import clashsoft.cslib.minecraft.block.CustomBlock;
import clashsoft.mods.tml.methods.TMLMethod;

public class MethodAddBlock extends TMLMethod
{
	@Override
	public Object call(Object... args)
	{
		CustomBlock block = null;
		String name = "";
		if (matches(args, String.class, String.class, int.class, String.class))
		{
			name = (String) args[0];
			String icon = (String) args[1];
			int material = (Integer) args[2];
			String creativetab = (String) args[3];
			
			block = (CustomBlock) new CustomBlock(getMaterial(material), name, icon, getCreativeTab(creativetab)).setStepSound(getStepSound(material));
		}
		else if (matches(args, String.class, String[].class, String[].class, int.class, String[].class))
		{
			name = (String) args[0];
			String[] names = (String[]) args[1];
			String[] icons = (String[]) args[2];
			int material = (Integer) args[3];
			String[] creativetab = (String[]) args[4];
			
			block = (CustomBlock) new CustomBlock(getMaterial(material), names, icons, getCreativeTabs(creativetab)).setStepSound(getStepSound(material));
		}
		if (block != null)
		{
			CSBlocks.addBlock(block, name);
			System.out.println("  Block added.");
			return true;
		}
		return false;
	}
	
	@Override
	public String getName()
	{
		return "addBlock";
	}
	
	@Override
	public String getUsage()
	{
		return "addBlock(\"[blockName]\", \"[iconName]\", [material]i, \"[creativetab]\") OR " + "addBlock([name], string{\"blockName1\", ...}, string{\"iconName1\", ...}, [material]i, string{\"creativetab1\", ...})";
	}
}
