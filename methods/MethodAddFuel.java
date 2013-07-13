package com.chaosdev.textmodloader.methods;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class MethodAddFuel implements IMethodExecuter, IFuelHandler
{
	public static Map<ItemStack, Integer> fuelValues = new HashMap<ItemStack, Integer>();

	@Override
	public int getBurnTime(ItemStack fuel)
	{
		return fuelValues.get(fuel);
	}
	
	@Override
	public Object execute(Object... parameters)
	{
		if (parameters.length >= 2 && parameters[0] instanceof ItemStack && parameters[1] instanceof Integer)
		{
			ItemStack fuel = (ItemStack) parameters[0];
			int ticks = (Integer) parameters[1];
			
			fuelValues.put(fuel, ticks);
			System.out.println("  Fuel added.");
			return fuel;
		}
		return null;
	}

	@Override
	public String getName()
	{
		return "addfuel";
	}

	@Override
	public String getUsage()
	{
		return "addFuel(new ItemStack([id]i, [amount]i, [damage]i, [ticks]i)";
	}

}
