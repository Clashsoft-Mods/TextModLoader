package clashsoft.mods.tml.methods.crafting;

import clashsoft.mods.tml.methods.TMLMethod;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MethodCrafting extends TMLMethod
{
	@Override
	public Object call(Object... args)
	{	
		if (matches(args, ItemStack.class, Object[].class))
		{
			ItemStack output = (ItemStack) args[0];

			Object[] inputs = (Object[]) args[1];

			GameRegistry.addRecipe(output, inputs);

			System.out.println("  Recipe added");
			return output;
		}
		return new ItemStack((Item) null, 0, 0);
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
