package clashsoft.mods.tml.methods.crafting;

import java.util.HashMap;
import java.util.Map;

import clashsoft.mods.tml.methods.MethodExecutor;
import cpw.mods.fml.common.IFuelHandler;

import net.minecraft.item.ItemStack;

public class MethodAddFuel extends MethodExecutor implements IFuelHandler
{
	public static Map<ItemStack, Integer> fuelValues = new HashMap<ItemStack, Integer>();

	@Override
	public int getBurnTime(ItemStack fuel)
	{
		return (fuelValues != null && fuelValues.containsKey(fuel)) ? fuelValues.get(fuel) : 0;
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
		return "addFuel";
	}

	@Override
	public String getUsage()
	{
		return "addFuel(new ItemStack([id]i, [amount]i, [damage]i, [ticks]i)";
	}

}
