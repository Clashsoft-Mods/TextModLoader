package clashsoft.mods.tml.methods.block;

import clashsoft.cslib.minecraft.block.BlockCustomSlab;
import clashsoft.cslib.minecraft.block.BlockCustomWorkbench;
import clashsoft.cslib.minecraft.item.block.ItemCustomBlock;
import clashsoft.mods.tml.methods.MethodExecutor;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;

public class MethodAddSpecialBlock extends MethodExecutor
{
	@Override
	public Object execute(Object... parameters)
	{
		Block block = null;
		String name = "";
		if (matches(parameters, String.class, Integer.class))
		{
			String type = (String) parameters[0];
			int blockID = (Integer) parameters[1];
			
			if (type.equals("workbench") && matches(parameters, String.class, Integer.class, String[].class, String[].class, String[].class, String[].class, String[].class))
			{
				String[] names = (String[]) parameters[2];
				name = names[0];
				String[] topIcons = (String[]) parameters[3];
				String[] sideIcons = (String[]) parameters[4];
				String[] side2Icons = (String[]) parameters[5];
				String[] bottomIcons = (String[]) parameters[6];
				block = new BlockCustomWorkbench(blockID, names, topIcons, sideIcons, side2Icons, bottomIcons);
				GameRegistry.registerBlock(block, ItemCustomBlock.class, name.toUpperCase().replace(" ", ""));
				((BlockCustomWorkbench) block).addNames();
			}
			if (type.equals("slab") && matches(parameters, String.class, Integer.class, String[].class, String[].class, String[].class, Integer.class, Boolean.class))
			{
				String[] names = (String[]) parameters[2];
				name = names[0];
				String[] topIcons = (String[]) parameters[3];
				String[] sideIcons = (String[]) parameters[4];
				int singleSlabID = (Integer) parameters[5];
				boolean doubleSlab = (Boolean) parameters[6];
				
				block = new BlockCustomSlab(blockID, names, topIcons, sideIcons, singleSlabID, doubleSlab);
				GameRegistry.registerBlock(block, ItemCustomBlock.class, name.toUpperCase().replace(" ", ""));
				((BlockCustomSlab) block).addNames();
			}
		}
		if (block != null)
		{
			System.out.println("  Special block added.");
			return block.blockID;
		}
		return -1;
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
