package com.chaosdev.textmodloader.methods.crafting;

import com.chaosdev.textmodloader.methods.MethodExecutor;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.item.ItemStack;

public class MethodCraftingShapeless extends MethodExecutor
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
		return "addShapelessRecipe";
	}
	
	@Override
	public String getUsage()
	{
		return "addShapelessRecipe([ItemStack], object{...}) //Note: See MinecraftForge addShapelessRecipe for array items.//";
	}
	
}
