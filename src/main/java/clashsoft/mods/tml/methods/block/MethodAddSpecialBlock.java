package clashsoft.mods.tml.methods.block;

import clashsoft.cslib.minecraft.block.BlockCustomSlab;
import clashsoft.cslib.minecraft.block.BlockCustomWorkbench;
import clashsoft.mods.tml.methods.TMLMethod;

import net.minecraft.block.Block;

public class MethodAddSpecialBlock extends TMLMethod
{
	@Override
	public Object call(Object... args)
	{
		Block block = null;
		String name = "";
		
		if (matches(args, int.class, String.class, String[].class, String[].class, String[].class, String[].class, String[].class))
		{
			name = (String) args[0];
			String[] names = (String[]) args[1];
			String[] topIcons = (String[]) args[2];
			String[] sideIcons = (String[]) args[3];
			String[] side2Icons = (String[]) args[4];
			String[] bottomIcons = (String[]) args[5];
			block = new BlockCustomWorkbench(0, names, topIcons, sideIcons, side2Icons, bottomIcons);
		}
		else if (matches(args, int.class, String.class, String[].class, String[].class, String[].class, String.class, Boolean.class))
		{
			name = (String) args[0];
			String[] names = (String[]) args[1];
			String[] topIcons = (String[]) args[2];
			String[] sideIcons = (String[]) args[3];
			String singleSlabID = (String) args[4];
			boolean doubleSlab = (Boolean) args[5];
			
			block = new BlockCustomSlab(names, topIcons, sideIcons, Block.getBlockFromName(name), doubleSlab);
		}
		if (block != null)
		{
			System.out.println("  Special block added.");
			return true;
		}
		return false;
	}
	
	@Override
	public String getName()
	{
		return "addSpecialBlock";
	}
	
	@Override
	public String getUsage()
	{
		return "addSpecialBlock(\"[blocktype]\", [id]i, [parameters]...)";
	}
}
