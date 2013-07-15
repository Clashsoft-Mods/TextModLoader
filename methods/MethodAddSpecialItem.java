package com.chaosdev.textmodloader.methods;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;

import com.chaosdev.textmodloader.util.TextModHelper;

public class MethodAddSpecialItem extends MethodExecuter
{
	@Override
	public Object execute(Object... parameters)
	{
		int itemID = -1;
		Item item = null;
		if (matches(parameters, String.class, Integer.class))
		{
			String type = (String) parameters[0];
			itemID = (Integer) parameters[1];

			if (type.startsWith("tool"))
			{
				if (matches(parameters, String.class, Integer.class, String.class))
				{
					EnumToolMaterial material = getToolMaterial((String)parameters[2]);
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
				else if (matches(parameters, String.class, Integer.class, Float.class, String.class, Integer[].class))
				{
					float toolDamage = (Float) parameters[2];
					EnumToolMaterial material = getToolMaterial((String)parameters[3]);
					int[] blocks = (int[]) parameters[4];
				}
			}
		}
		return itemID;
	}

	public EnumToolMaterial getToolMaterial(String name)
	{
		name = TextModHelper.changeName(name);
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
		name = TextModHelper.changeName(name);
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
		return "addspecialitem";
	}

	@Override
	public String getUsage()
	{
		return ">addSpecialItem(\"[itemtype]\", [id]i, [parameters]...)";
	}
}
