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
		if (parameters.length >= 3 && parameters[0] instanceof Integer && parameters[1] instanceof String && parameters[2] instanceof String)
		{
			int itemID = (Integer) parameters[0];
			name = (String) parameters[1];
			String icon = (String) parameters[2];

			item = new CustomItem(itemID, name, icon);

		}
		else if (parameters.length >= 3 && parameters[0] instanceof Integer && parameters[1] instanceof String && parameters[2] instanceof String && parameters[3] instanceof String)
		{
			int itemID = (Integer) parameters[0];
			name = (String) parameters[1];
			String icon = (String) parameters[2];
			String desc = (String) parameters[3];

			item = new CustomItem(itemID, name, icon, desc);
		}
		else if (parameters.length >= 3 && parameters[0] instanceof Integer && parameters[1] instanceof String[] && parameters[2] instanceof String[])
		{
			int itemID = (Integer) parameters[0];
			String[] names = (String[]) parameters[1];
			name = names[0];
			String[] icons = (String[]) parameters[2];

			item = new CustomItem(itemID, names, icons);

		}
		else if (parameters.length >= 3 && parameters[0] instanceof Integer && parameters[1] instanceof String[] && parameters[2] instanceof String[] && parameters[3] instanceof String[])
		{
			int itemID = (Integer) parameters[0];
			String[] names = (String[]) parameters[1];
			name = names[0];
			String[] icons = (String[]) parameters[2];
			String[] descs = (String[]) parameters[3];

			item = new CustomItem(itemID, names, icons, descs);
		}
		
		if (item != null)
		{
			LanguageRegistry.addName(item, name);
			GameRegistry.registerItem(item, name);
			return item.itemID;
		}
		return -1;
	}

	@Override
	public String getName()
	{
		return null;
	}

	@Override
	public String getUsage()
	{
		return null;
	}

}
