package com.chaosdev.textmodloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import com.chaosdev.textmodloader.methods.*;
import com.chaosdev.textmodloader.methods.block.MethodAddBlock;
import com.chaosdev.textmodloader.methods.block.MethodAddSpecialBlock;
import com.chaosdev.textmodloader.methods.crafting.MethodAddFuel;
import com.chaosdev.textmodloader.methods.crafting.MethodCrafting;
import com.chaosdev.textmodloader.methods.crafting.MethodCraftingShapeless;
import com.chaosdev.textmodloader.methods.crafting.MethodSmelting;
import com.chaosdev.textmodloader.methods.item.MethodAddItem;
import com.chaosdev.textmodloader.methods.item.MethodAddSpecialItem;
import com.chaosdev.textmodloader.methods.util.MethodAddLocalization;
import com.chaosdev.textmodloader.methods.util.MethodGetID;
import com.chaosdev.textmodloader.methods.util.MethodHelp;
import com.chaosdev.textmodloader.methods.util.MethodMath;
import com.chaosdev.textmodloader.methods.util.MethodToString;
import com.chaosdev.textmodloader.util.TextModHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.Configuration;
import clashsoft.clashsoftapi.util.CSUtil;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "TextModLoader", name = "Text Mod Loader", version = CSUtil.CURRENT_VERION)
@NetworkMod(channels = { "TextModLoader" }, serverSideRequired = false, clientSideRequired = true)
public class TextModLoader
{
	@Instance("TextModLoader")
	public static TextModLoader instance;

	public static final String MOD_CLASS_SUFFIX = ".textmod";

	public static List<TextMod> loadedTextMods = new LinkedList<TextMod>();

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
			System.out.println(files.size() + " TextMod" + (files.size() == 1 ? "" : "s") + " loaded.");
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

	private void loadModClass(File modClass)
	{
		if (modClass.getName().endsWith(MOD_CLASS_SUFFIX))
		{
			System.out.println(" Loading TextMod: " + modClass);
			try
			{
				TextMod tm = TextMod.fromFile(modClass);
				tm.execute();
				loadedTextMods.add(tm);
				System.out.println(" TextMod " + modClass + " loaded.");
			}
			catch (Exception ex)
			{
				System.out.println(" Unable to load TextMod: " + ex.getMessage());
			}
		}
	}
}