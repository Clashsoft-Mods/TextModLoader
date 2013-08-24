package com.chaosdev.textmodloader.methods;

import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public abstract class MethodExecuter
{
	public abstract Object execute(Object... parameters);
	
	public abstract String getName();
	
	public abstract String getUsage();
	
	public static boolean matches(Object[] parameters, Class... types)
	{
		for (int i = 0; i < parameters.length; i++)
		{
			if (i < types.length)
			{
				if (!parameters[i].getClass().equals(types[i]))
				{
					return false;
				}
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
	
	public static StepSound getStepSound(int id)
	{
		if (id == 0)
			return Block.soundPowderFootstep;
		if (id == 1)
			return Block.soundGravelFootstep;
		if (id == 2)
			return Block.soundStoneFootstep;
		if (id == 3)
			return Block.soundGrassFootstep;
		if (id == 4)
			return Block.soundWoodFootstep;
		if (id == 5)
			return Block.soundClothFootstep;
		if (id == 6)
			return Block.soundMetalFootstep;
		return Block.soundStoneFootstep;
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
