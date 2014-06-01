package clashsoft.mods.tml.jmod.methods.crafting;

import clashsoft.mods.tml.jmod.methods.TMLMethod;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MethodCraftingShapeless extends TMLMethod
{
	@Override
	public Object call(Object... args)
	{
		if (matches(args, ItemStack.class, Object[].class))
		{
			ItemStack output = (ItemStack) args[0];
			
			Object[] inputs = (Object[]) args[1];
			
			GameRegistry.addShapelessRecipe(output, inputs);
			
			System.out.println("  Shapeless Recipe added");
			return output;
		}
		return new ItemStack((Item) null, 0, 0);
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
