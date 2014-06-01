package clashsoft.mods.tml.methods.crafting;

import java.util.HashMap;
import java.util.Map;

import clashsoft.mods.tml.methods.TMLMethod;
import cpw.mods.fml.common.IFuelHandler;

import net.minecraft.item.ItemStack;

public class MethodAddFuel extends TMLMethod implements IFuelHandler
{
	public static Map<ItemStack, Integer> fuelValues = new HashMap<ItemStack, Integer>();

	@Override
	public int getBurnTime(ItemStack fuel)
	{
		return (fuelValues != null && fuelValues.containsKey(fuel)) ? fuelValues.get(fuel) : 0;
	}
	
	@Override
	public Object call(Object... args)
	{
		if (matches(args, ItemStack.class, int.class))
		{
			ItemStack fuel = (ItemStack) args[0];
			int ticks = (Integer) args[1];
			
			fuelValues.put(fuel, ticks);
			System.out.println("  Fuel added.");
			return fuel;
		}
		return null;
	}

	@Override
	public String getName()
	{
		return "addFuel";
	}

	@Override
	public String getUsage()
	{
		return "addFuel(new ItemStack([id]i, [amount]i, [damage]i, [ticks]i)";
	}

}
