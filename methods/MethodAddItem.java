package com.chaosdev.textmodloader.methods;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import clashsoft.clashsoftapi.CustomItem;

public class MethodAddItem implements IMethodExecuter
{

	@Override
	public Object execute(Object... parameters)
	{
		CustomItem item = null;
		String name = "";
		if (parameters.length >= 4 && parameters[0] instanceof Integer && parameters[1] instanceof String && parameters[2] instanceof String && parameters[3] instanceof String)
		{
			int itemID = (Integer) parameters[0];
			name = (String) parameters[1];
			String icon = (String) parameters[2];
			String creativetab = (String) parameters[3];

			item = (CustomItem) new CustomItem(itemID, name, icon).setCreativeTab(MethodAddBlock.getCreativeTab(creativetab));

		}
		else if (parameters.length >= 5 && parameters[0] instanceof Integer && parameters[1] instanceof String && parameters[2] instanceof String && parameters[3] instanceof String && parameters[4] instanceof String)
		{
			int itemID = (Integer) parameters[0];
			name = (String) parameters[1];
			String icon = (String) parameters[2];
			String desc = (String) parameters[3];
			String creativetab = (String) parameters[4];

			item = (CustomItem) new CustomItem(itemID, name, icon, desc).setCreativeTab(MethodAddBlock.getCreativeTab(creativetab));
		}
		else if (parameters.length >= 4 && parameters[0] instanceof Integer && parameters[1] instanceof String[] && parameters[2] instanceof String[] && parameters[3] instanceof String)
		{
			int itemID = (Integer) parameters[0];
			String[] names = (String[]) parameters[1];
			name = names[0];
			String[] icons = (String[]) parameters[2];
			String creativetab = (String) parameters[3];

			item = (CustomItem) new CustomItem(itemID, names, icons).setCreativeTab(MethodAddBlock.getCreativeTab(creativetab));

		}
		else if (parameters.length >= 5 && parameters[0] instanceof Integer && parameters[1] instanceof String[] && parameters[2] instanceof String[] && parameters[3] instanceof String[] && parameters[4] instanceof String)
		{
			int itemID = (Integer) parameters[0];
			String[] names = (String[]) parameters[1];
			name = names[0];
			String[] icons = (String[]) parameters[2];
			String[] descs = (String[]) parameters[3];
			String creativetab = (String) parameters[4];

			item = (CustomItem) new CustomItem(itemID, names, icons, descs).setCreativeTab(MethodAddBlock.getCreativeTab(creativetab));
		}
		
		if (item != null)
		{
			LanguageRegistry.addName(item, name);
			GameRegistry.registerItem(item, name);
			System.out.println("  Item added.");
			return item.itemID;
		}
		return -1;
	}

	@Override
	public String getName()
	{
		return "additem";
	}

	@Override
	public String getUsage()
	{
		return ">addItem([itemID]i, \"[name]\", \"[icon]\", <\"[lore]\">, \"[creativetab]\") OR "
				+ ">addItem([itemID]i, string{\"[name1]\", \"[name2]\", ...}, string{\"[icon1]\", \"[icon2]\", ...}, <string{\"[lore1]\", \"[lore2]\", ...}>, \"[creativetab]\")";
	}

}
