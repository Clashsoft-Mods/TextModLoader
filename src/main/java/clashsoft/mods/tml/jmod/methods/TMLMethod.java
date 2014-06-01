package clashsoft.mods.tml.jmod.methods;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public abstract class TMLMethod
{
	public abstract Object call(Object... args);
	
	public abstract String getName();
	
	public abstract String getUsage();
	
	public static boolean matches(Object[] parameters, Class... types)
	{
		if (parameters.length != types.length)
			return false;
		
		for (int i = 0; i < parameters.length; i++)
		{
			if (!parameters[i].getClass().equals(types[i]))
			{
				return false;
			}
		}
		return true;
	}
	
	public static Material getMaterial(int id)
	{
		if (id == 0)
			return Material.air;
		if (id == 1)
			return Material.ground;
		if (id == 2)
			return Material.rock;
		if (id == 3)
			return Material.grass;
		if (id == 4)
			return Material.wood;
		if (id == 5)
			return Material.cloth;
		if (id == 6)
			return Material.iron;
		return Material.rock;
	}
	
	public static SoundType getStepSound(int id)
	{
		if (id == 0)
			return Block.soundTypeSand;
		if (id == 1)
			return Block.soundTypeGravel;
		if (id == 2)
			return Block.soundTypeStone;
		if (id == 3)
			return Block.soundTypeGrass;
		if (id == 4)
			return Block.soundTypeWood;
		if (id == 5)
			return Block.soundTypeCloth;
		if (id == 6)
			return Block.soundTypeMetal;
		return Block.soundTypeStone;
	}
	
	public static CreativeTabs getCreativeTab(String name)
	{
		try
		{
			for (CreativeTabs ct : CreativeTabs.creativeTabArray)
			{
				if (ct.getTabLabel().trim().toLowerCase().equals(name.trim().toLowerCase()))
					return ct;
			}
		}
		catch (Throwable t)
		{
			;
		}
		return CreativeTabs.tabBlock;
	}
	
	public static CreativeTabs[] getCreativeTabs(String[] names)
	{
		CreativeTabs[] tabs = new CreativeTabs[names.length];
		for (int i = 0; i < names.length; i++)
		{
			tabs[i] = getCreativeTab(names[i]);
		}
		return tabs;
	}
}
