package com.chaosdev.textmodloader.methods.crafting;

import com.chaosdev.textmodloader.methods.MethodExecutor;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.item.ItemStack;

public class MethodCrafting extends MethodExecutor
{
	@Override
	public Object execute(Object... parameters)
	{	
		if (matches(parameters, ItemStack.class, Object[].class))
		{
			ItemStack output = (ItemStack) parameters[0];

			Object[] inputs = (Object[]) parameters[1];

			GameRegistry.addRecipe(output, inputs);

			System.out.println("  Recipe added");
			return output;
		}
		return new ItemStack(0, 0, 0);
	}

	@Override
	public String getName()
	{
		return "addRecipe";
	}

	@Override
	public String getUsage()
	{
		return "addRecipe([ItemStack], object{...}) //Note: See MinecraftForge addRecipe for array items.//";
	}

}
