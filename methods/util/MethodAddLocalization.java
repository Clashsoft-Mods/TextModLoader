package com.chaosdev.textmodloader.methods.util;

import com.chaosdev.textmodloader.methods.MethodExecuter;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.item.ItemStack;

public class MethodAddLocalization extends MethodExecuter
{
	
	@Override
	public Object execute(Object... parameters)
	{
		if (parameters.length >= 2 && parameters[0] instanceof ItemStack && parameters[1] instanceof String)
		{
			String langFile = "en_US";
			String localization = (String) parameters[1];
			if (parameters.length >= 3 && parameters[1] instanceof String && parameters[2] instanceof String)
			{
				langFile = (String) parameters[1];
				localization = (String) parameters[2];
			}
			LanguageRegistry.instance().addNameForObject(parameters[0], langFile, localization);
			System.out.println("  Localization Added.");
			return localization;
		}
		else if (parameters.length >= 2 && parameters[0] instanceof String && parameters[1] instanceof String)
		{
			String langFile = "en_US";
			String localization = (String) parameters[1];
			if (parameters.length >= 3 && parameters[1] instanceof String && parameters[2] instanceof String)
			{
				langFile = (String) parameters[1];
				localization = (String) parameters[2];
			}
			LanguageRegistry.instance().addNameForObject(parameters[0], langFile, localization);
			System.out.println("  Localization Added.");
			return localization;
		}
		return "";
	}
	
	@Override
	public String getName()
	{
		return "addName|addLocalization";
	}
	
	@Override
	public String getUsage()
	{
		return "[addLocalization OR addName]([new ItemStack([id]i, [amount]i, [damage]i) OR \"[name]\"], <\"[langfile]\">, \"[localization]\"";
	}
	
}
