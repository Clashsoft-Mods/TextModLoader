package com.chaosdev.textmodloader.methods;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MethodCrafting implements IMethodExecuter
{
	@Override
	public Object execute(Object... parameters)
	{
		int inputArrayPos = 1;
		
		ItemStack output = null;
		
		if (parameters[0] instanceof Integer)
		{
			int id = (Integer) parameters[0];
			int amount = 1;
			int damage = 0;
			if (parameters.length >= 2 && parameters[1] instanceof Integer)
			{
				amount = (Integer) parameters[1];
				inputArrayPos++;
			}
			if (parameters.length >= 3 && parameters[2] instanceof Integer)
			{
				damage = (Integer) parameters[2];
				inputArrayPos++;
			}
			output = new ItemStack(id, amount, damage);
		}
		else if (parameters[0] instanceof ItemStack)
		{
			output = (ItemStack) parameters[0];
		}
		
		Object[] inputs = (Object[]) parameters[inputArrayPos];
		
		GameRegistry.addRecipe(output, inputs);
		
		System.out.println("  Recipe added");
		return output;
	}

	@Override
	public String getName()
	{
		return "addrecipe";
	}

	@Override
	public String getUsage()
	{
		return ">addRecipe([id]i, <[amount]i>, <[damage]i>, object{...}) OR >addRecipe(new ItemStack([id]i, [amount]i, [damage]i), object{...}) //Note: See MinecraftForge addRecipe for array items.//";
	}

}
