package com.chaosdev.textmodloader.methods.item;

import clashsoft.clashsoftapi.metatools.*;

import com.chaosdev.textmodloader.methods.MethodExecutor;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.item.*;

public class MethodAddSpecialItem extends MethodExecutor
{
	@Override
	public Object execute(Object... parameters)
	{
		Item item = null;
		String name = "";
		if (matches(parameters, String.class, Integer.class))
		{
			String type = (String) parameters[0];
			int itemID = (Integer) parameters[1];

			if (type.startsWith("tool"))
			{
				if (matches(parameters, String.class, Integer.class, String.class, String.class))
				{
					name = (String) parameters[2];
					EnumToolMaterial material = getToolMaterial((String)parameters[3]);
					if (type.equals("tool:pickaxe"))
						item = new ItemPickaxe(itemID, material);
					else if (type.equals("tool:shovel") || type.equals("tool:spade"))
						item = new ItemSpade(itemID, material);
					else if (type.equals("tool:axe"))
						item = new ItemAxe(itemID, material);
					else if (type.equals("tool:sword"))
						item = new ItemSword(itemID, material);
					else if (type.equals("tool:hoe"))
						item = new ItemHoe(itemID, material);
				}
				//															Type		  ID			 Names			 Material	   Icons		   Colors
				else if (type.startsWith("toolmeta") && matches(parameters, String.class, Integer.class, String[].class, String.class, String[].class, int[].class))
				{
					String[] names = (String[]) parameters[2];
					name = names[0];
					EnumToolMaterial material = getToolMaterial((String)parameters[3]);
					String[] icons = (String[]) parameters[4];
					int[] colors = (int[]) parameters[5];
					
					if (type.equals("toolmeta:pickaxe"))
						item = new ItemMetaPickaxe(itemID, material, names, icons, colors);
					else if (type.equals("toolmeta:shovel") || type.equals("toolmeta:spade"))
						item = new ItemMetaSpade(itemID, material, names, icons, colors);
					else if (type.equals("toolmeta:axe"))
						item = new ItemMetaAxe(itemID, material, names, icons, colors);
					else if (type.equals("toolmeta:sword"))
						item = new ItemMetaSword(itemID, material, names, icons, colors);
					else if (type.equals("toolmeta:hoe"))
						item = new ItemMetaHoe(itemID, material, names, icons, colors);
				}
			}
			else if (type.startsWith("armor"))
			{
				if (matches(parameters, String.class, Integer.class, String.class, String.class, Integer.class))
				{
					EnumArmorMaterial material = getArmorMaterial((String)parameters[3]);
					int renderIndex = (Integer) parameters[4];
					if (type.equals("armor:helmet"))
						item = new ItemArmor(itemID, material, renderIndex, 0);
					else if (type.equals("armor:chestplate"))
						item = new ItemArmor(itemID, material, renderIndex, 1);
					else if (type.equals("armor:leggings") || type.equals("armor:legs"))
						item = new ItemArmor(itemID, material, renderIndex, 2);
					else if (type.equals("armor:boots"))
						item = new ItemArmor(itemID, material, renderIndex, 3);
				}
			}
		}
		if (item != null)
		{
			GameRegistry.registerItem(item, name.toUpperCase().replace(" ", ""));
			LanguageRegistry.addName(item, name);
			return item.itemID;
		}
		return -1;
	}

	public EnumToolMaterial getToolMaterial(String name)
	{
		name = name.trim().toLowerCase();
		if (name.equals("wood"))
			return EnumToolMaterial.WOOD;
		else if (name.equals("stone"))
			return EnumToolMaterial.STONE;
		else if (name.equals("iron"))
			return EnumToolMaterial.IRON;
		else if (name.equals("gold"))
			return EnumToolMaterial.GOLD;
		else if (name.equals("diamond"))
			return EnumToolMaterial.EMERALD;
		else
			return EnumToolMaterial.valueOf(name);
	}

	public EnumArmorMaterial getArmorMaterial(String name)
	{
		name = name.trim().toLowerCase();
		if (name.equals("leather") || name.equals("cloth"))
			return EnumArmorMaterial.CLOTH;
		else if (name.equals("chain"))
			return EnumArmorMaterial.CHAIN;
		else if (name.equals("iron"))
			return EnumArmorMaterial.IRON;
		else if (name.equals("gold"))
			return EnumArmorMaterial.GOLD;
		else if (name.equals("diamond"))
			return EnumArmorMaterial.DIAMOND;
		else
			return EnumArmorMaterial.valueOf(name);
	}

	@Override
	public String getName()
	{
		return "addSpecialItem";
	}

	@Override
	public String getUsage()
	{
		return "addSpecialItem(\"[itemtype]\", [id]i, [parameters]...)";
	}
}
