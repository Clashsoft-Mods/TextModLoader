package com.chaosdev.textmodloader.methods.crafting;

import clashsoft.clashsoftapi.util.CSCrafting;

import com.chaosdev.textmodloader.methods.MethodExecutor;

import net.minecraft.item.ItemStack;

public class MethodSmelting extends MethodExecutor
{
	
	@Override
	public Object execute(Object... parameters)
	{
		if (parameters.length >= 3 && parameters[0] instanceof ItemStack && parameters[1] instanceof ItemStack && parameters[2] instanceof Float)
		{
			ItemStack input = (ItemStack) parameters[0];
			ItemStack output = (ItemStack) parameters[1];
			float exp = (Float) parameters[2];
			
			CSCrafting.addSmelting(input, output, exp);
			System.out.println("  Furnace Recipe added.");
			return output;
		}
		return null;
	}
	
	@Override
	public String getName()
	{
		return "addSmelting";
	}
	
	@Override
	public String getUsage()
	{
		return "addSmelting(new ItemStack([id]i, [amount]i, [damage]i), new ItemStack([id]i, [amount]i, [damage]i), [exp]f)";
	}
	
}
