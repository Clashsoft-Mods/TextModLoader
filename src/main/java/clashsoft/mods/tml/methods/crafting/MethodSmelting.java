package clashsoft.mods.tml.methods.crafting;

import clashsoft.cslib.minecraft.crafting.CSCrafting;
import clashsoft.mods.tml.methods.TMLMethod;

import net.minecraft.item.ItemStack;

public class MethodSmelting extends TMLMethod
{
	@Override
	public Object call(Object... args)
	{
		if (matches(args, ItemStack.class, ItemStack.class, float.class))
		{
			ItemStack input = (ItemStack) args[0];
			ItemStack output = (ItemStack) args[1];
			float exp = (Float) args[2];
			
			CSCrafting.addFurnaceRecipe(input, output, exp);
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
