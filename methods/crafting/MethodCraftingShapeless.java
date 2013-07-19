package com.chaosdev.textmodloader.methods.crafting;

import net.minecraft.item.ItemStack;

import com.chaosdev.textmodloader.methods.MethodExecuter;

import cpw.mods.fml.common.registry.GameRegistry;

public class MethodCraftingShapeless extends MethodExecuter
{

	@Override
	public Object execute(Object... parameters)
	{
		if (matches(parameters, ItemStack.class, Object[].class))
		{
			ItemStack output = (ItemStack) parameters[0];

			Object[] inputs = (Object[]) parameters[1];

			GameRegistry.addShapelessRecipe(output, inputs);

			System.out.println("  Shapeless Recipe added");
			return output;
		}
		return new ItemStack(0, 0, 0);
	}

	@Override
	public String getName()
	{
		return "addshapelessrecipe";
	}

	@Override
	public String getUsage()
	{
		return ">addShapelessRecipe([ItemStack], object{...}) //Note: See MinecraftForge addShapelessRecipe for array items.//";
	}

}
