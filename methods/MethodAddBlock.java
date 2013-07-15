package com.chaosdev.textmodloader.methods;

import com.chaosdev.textmodloader.TextMod;
import com.chaosdev.textmodloader.util.TextModHelper;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import clashsoft.clashsoftapi.CustomBlock;
import clashsoft.clashsoftapi.ItemCustomBlock;
import clashsoft.clashsoftapi.util.CSArray;
import clashsoft.clashsoftapi.util.CSUtil;

public class MethodAddBlock extends MethodExecuter
{

	@Override
	public Object execute(Object... par1)
	{	
		CustomBlock block = null;
		String name = "";
		if (par1.length >= 5 && par1[0] instanceof Integer &&
				par1[1] instanceof String &&
				par1[2] instanceof String &&
				par1[3] instanceof Integer &&
				par1[4] instanceof String)
		{
			int blockID = (Integer) par1[0];
			name = (String) par1[1];
			String icon = (String) par1[2];
			int material = (Integer) par1[3];
			String creativetab = (String) par1[4];

			block = (CustomBlock) new CustomBlock(blockID, getMaterial(material), name, icon, getCreativeTab(creativetab)).setStepSound(getStepSound(material));
		}
		else if (par1.length >= 5 &&
				par1[0] instanceof Integer &&
				par1[1] instanceof String[] &&
				par1[2] instanceof String[] &&
				par1[3] instanceof Integer &&
				par1[4] instanceof String[])
		{
			int blockID = (Integer) par1[0];
			String[] names = (String[]) par1[1];
			String[] icons = (String[]) par1[2];
			int material = (Integer) par1[3];
			String[] creativetab = (String[]) par1[4];

			block = (CustomBlock) new CustomBlock(blockID, getMaterial(material), names, icons, getCreativeTabs(creativetab)).setStepSound(getStepSound(material));
		}
		if (block != null)
		{
			GameRegistry.registerBlock(block, ItemCustomBlock.class, name);
			block.addNames();
			System.out.println("  Block added.");
			return block.blockID;
		}
		return -1;
	}

	@Override
	public String getName()
	{
		return "addblock";
	}

	public String getUsage()
	{
		return ">addBlock([blockID]i, \"[blockName]\", \"[iconName]\", [material]i, \"[creativetab]\") OR "
				+ ">addBlock([blockID]i, string{\"blockName1\", ...}, string{\"iconName1\", ...}, [material]i, string{\"creativetab1\", ...})";
	}
}
