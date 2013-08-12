package com.chaosdev.textmodloader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import clashsoft.clashsoftapi.util.CSUtil;

import com.chaosdev.textmodloader.methods.block.MethodAddBlock;
import com.chaosdev.textmodloader.methods.block.MethodAddSpecialBlock;
import com.chaosdev.textmodloader.methods.crafting.MethodAddFuel;
import com.chaosdev.textmodloader.methods.crafting.MethodCrafting;
import com.chaosdev.textmodloader.methods.crafting.MethodCraftingShapeless;
import com.chaosdev.textmodloader.methods.crafting.MethodSmelting;
import com.chaosdev.textmodloader.methods.item.MethodAddItem;
import com.chaosdev.textmodloader.methods.item.MethodAddSpecialItem;
import com.chaosdev.textmodloader.methods.util.*;
import com.chaosdev.textmodloader.util.TextModConstants;
import com.chaosdev.textmodloader.util.TextModHelper;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.Configuration;

@Mod(modid = "TextModLoader", name = "Text Mod Loader", version = CSUtil.CURRENT_VERION)
@NetworkMod(channels = { "TextModLoader" }, serverSideRequired = false, clientSideRequired = true)
public class TextModLoader implements TextModConstants
{
	@Instance("TextModLoader")
	public static TextModConstants	instance;
	
	public static List<TextMod>	loadedTextMods		= new LinkedList<TextMod>();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		config.save();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		System.out.println("Loading TextModLoader");
		System.out.println("Registering Method Executers");
		
		GameRegistry.registerFuelHandler(new MethodAddFuel());
		
		TextModHelper.registerMethodExecuter(new MethodAddBlock());
		TextModHelper.registerMethodExecuter(new MethodAddSpecialBlock());
		TextModHelper.registerMethodExecuter(new MethodAddItem());
		TextModHelper.registerMethodExecuter(new MethodAddSpecialItem());
		
		TextModHelper.registerMethodExecuter(new MethodCrafting());
		TextModHelper.registerMethodExecuter(new MethodCraftingShapeless());
		TextModHelper.registerMethodExecuter(new MethodSmelting());
		TextModHelper.registerMethodExecuter(new MethodAddFuel());
		
		TextModHelper.registerMethodExecuter(new MethodAddLocalization());
		TextModHelper.registerMethodExecuter(new MethodHelp());
		TextModHelper.registerMethodExecuter(new MethodGetID());
		TextModHelper.registerMethodExecuter(new MethodMath());
		TextModHelper.registerMethodExecuter(new MethodToString());
		
		System.out.println(TextModHelper.methods.size() + " Method Executers registered.");
		
		try
		{
			File file;
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
				file = new File(Minecraft.getMinecraft().mcDataDir.getPath(), "mods");
			else
				file = new File(MinecraftServer.getServer().getFolderName(), "mods");
			List<File> files = getTextModDirectories(file);
			for (File f : files)
			{
				loadModClass(f);
				for (File g : f.listFiles())
				{
					loadModClass(g);
				}
			}
			float averageLoadTime = 0L;
			if (loadedTextMods.size() > 0)
			{
				for (TextMod tm : loadedTextMods)
					averageLoadTime += tm.getTotalLoadingTime();
				averageLoadTime /= loadedTextMods.size();
			}
			System.out.println(files.size() + " TextMod" + (files.size() == 1 ? "" : "s") + " loaded. Average loading time: " + averageLoadTime);
		}
		catch (NoClassDefFoundError error)
		{
			error.printStackTrace();
		}
	}
	
	private List<File> getTextModDirectories(File path)
	{
		List<File> files = new LinkedList<File>();
		for (File f : path.listFiles())
		{
			if (f != null && f.isDirectory())
				files.add(f);
		}
		return files;
	}
	
	private void loadModClass(final File modClass)
	{
		if (modClass.getName().endsWith(MOD_CLASS_SUFFIX))
		{
			if (ENABLE_THREADING)
				new Thread(new Runnable()
				{
					public void run()
					{
						TextMod.load(modClass);
					}
				}).start();
			else
				TextMod.load(modClass);
		}
	}
}