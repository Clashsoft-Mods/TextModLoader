package com.chaosdev.textmodloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import com.chaosdev.textmodloader.methods.*;
import com.chaosdev.textmodloader.util.TextModHelper;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.Configuration;
import clashsoft.clashsoftapi.util.CSUtil;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "TextModLoader", name = "Text Mod Loader", version = CSUtil.CURRENT_VERION)
@NetworkMod(channels = { "TextModLoader" }, serverSideRequired = false, clientSideRequired = true)
public class TextModLoader
{
	@Instance("TextModLoader")
	public static TextModLoader instance;
	
	//@SidedProxy(clientSide = Reference.CLIENT_PROXY_LOCATION, serverSide = Reference.COMMON_PROXY_LOCATION)
	//public static CommonProxy proxy;

	public static final String MOD_CLASS_SUFFIX = ".textmod";
	
	public static List<TextMod> loadedTextMods = new LinkedList<TextMod>();
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		config.save();
	}
	
	@Init
	public void init(FMLInitializationEvent event)
	{	
		System.out.println("Loading TextModLoader");
		
		TextModHelper.registerMethodExecuter(new MethodAddBlock());
		TextModHelper.registerMethodExecuter(new MethodHelp());
		TextModHelper.registerMethodExecuter(new MethodToString());
		TextModHelper.registerMethodExecuter(new MethodMath());
		TextModHelper.registerMethodExecuter(new MethodCrafting());
		
		List<File> files = getTextModDirectories(new File(Minecraft.getMinecraft().mcDataDir.getPath(), "mods"));
		for (File f : files)
		{
			for (File g : f.listFiles())
			{
				loadModClass(g);
			}
		}
		System.out.println(files.size() + " TextMods loaded.");
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
			System.out.println("  Reading Mod Class: " + modClass);
			try
			{
				TextMod tm = TextMod.fromFile(modClass);
				tm.init();
				loadedTextMods.add(tm);
			}
			catch (Exception ex)
			{
				System.out.println("  Unable to load TextMod: " + ex.getMessage());
			}
		}
	}
}