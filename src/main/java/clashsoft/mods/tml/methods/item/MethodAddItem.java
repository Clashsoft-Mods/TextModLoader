package clashsoft.mods.tml.methods.item;

import clashsoft.cslib.minecraft.item.CSItems;
import clashsoft.cslib.minecraft.item.CustomItem;
import clashsoft.mods.tml.methods.TMLMethod;

import net.minecraft.item.Item;

public class MethodAddItem extends TMLMethod
{
	@Override
	public Object call(Object... args)
	{
		Item item = null;
		String name = "";
		
		if (matches(args, String.class, String.class, String.class))
		{
			name = (String) args[0];
			String icon = (String) args[1];
			String creativetab = (String) args[2];
			
			item = new CustomItem(name, icon, null).setCreativeTab(getCreativeTab(creativetab));
		}
		else if (matches(args, String.class, String[].class, String[].class, String.class))
		{
			name = (String) args[0];
			String[] names = (String[]) args[1];
			String[] icons = (String[]) args[2];
			String creativetab = (String) args[3];
			
			item = new CustomItem(names, icons).setCreativeTab(getCreativeTab(creativetab));
		}
		
		if (item != null)
		{
			CSItems.addItem(item, name);
			System.out.println("  Item added.");
			return true;
		}
		return false;
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
