package com.chaosdev.textmodloader.methods.item;

import clashsoft.cslib.minecraft.CustomItem;

import com.chaosdev.textmodloader.methods.MethodExecutor;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class MethodAddItem extends MethodExecutor
{
	@Override
	public Object execute(Object... parameters)
	{
		CustomItem item = null;
		String name = "";
		int itemID = -1;
		if (parameters.length >= 4 && parameters[0] instanceof Integer && parameters[1] instanceof String && parameters[2] instanceof String && parameters[3] instanceof String)
		{
			itemID = (Integer) parameters[0];
			name = (String) parameters[1];
			String icon = (String) parameters[2];
			String creativetab = (String) parameters[3];
			
			item = (CustomItem) new CustomItem(itemID, name, icon, MethodExecutor.getCreativeTab(creativetab));
		}
		else if (parameters.length >= 5 && parameters[0] instanceof Integer && parameters[1] instanceof String && parameters[2] instanceof String && parameters[3] instanceof String && parameters[4] instanceof String)
		{
			itemID = (Integer) parameters[0];
			name = (String) parameters[1];
			String icon = (String) parameters[2];
			String desc = (String) parameters[3];
			String creativetab = (String) parameters[4];
			
			item = (CustomItem) new CustomItem(itemID, name, icon, desc, MethodExecutor.getCreativeTab(creativetab));
		}
		else if (parameters.length >= 4 && parameters[0] instanceof Integer && parameters[1] instanceof String[] && parameters[2] instanceof String[] && parameters[3] instanceof String)
		{
			itemID = (Integer) parameters[0];
			String[] names = (String[]) parameters[1];
			name = names[0];
			String[] icons = (String[]) parameters[2];
			String creativetab = (String) parameters[3];
			
			item = (CustomItem) new CustomItem(itemID, names, icons).setCreativeTab(MethodExecutor.getCreativeTab(creativetab));
			
		}
		else if (parameters.length >= 5 && parameters[0] instanceof Integer && parameters[1] instanceof String[] && parameters[2] instanceof String[] && parameters[3] instanceof String[] && parameters[4] instanceof String)
		{
			itemID = (Integer) parameters[0];
			String[] names = (String[]) parameters[1];
			name = names[0];
			String[] icons = (String[]) parameters[2];
			String[] descs = (String[]) parameters[3];
			String creativetab = (String) parameters[4];
			
			item = (CustomItem) new CustomItem(itemID, names, icons, descs).setCreativeTab(MethodExecutor.getCreativeTab(creativetab));
		}
		
		if (item != null)
		{
			GameRegistry.registerItem(item, name.toUpperCase().replace(" ", ""));
			LanguageRegistry.addName(item, name);
			System.out.println("  Item added.");
			return item.itemID;
		}
		return -1;
	}
	
	@Override
	public String getName()
	{
		return "addItem";
	}
	
	@Override
	public String getUsage()
	{
		return "addItem([itemID]i, \"[name]\", \"[icon]\", <\"[lore]\">, \"[creativetab]\") OR " + "addItem([itemID]i, string{\"[name1]\", \"[name2]\", ...}, string{\"[icon1]\", \"[icon2]\", ...}, <string{\"[lore1]\", \"[lore2]\", ...}>, \"[creativetab]\")";
	}
	
}
