package clashsoft.mods.tml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import clashsoft.cslib.minecraft.init.BaseMod;
import clashsoft.cslib.minecraft.update.CSUpdate;
import clashsoft.mods.tml.jmod.JavaTextMod;
import clashsoft.mods.tml.jmod.methods.block.MethodAddBlock;
import clashsoft.mods.tml.jmod.methods.block.MethodAddSpecialBlock;
import clashsoft.mods.tml.jmod.methods.crafting.MethodAddFuel;
import clashsoft.mods.tml.jmod.methods.crafting.MethodCrafting;
import clashsoft.mods.tml.jmod.methods.crafting.MethodCraftingShapeless;
import clashsoft.mods.tml.jmod.methods.crafting.MethodSmelting;
import clashsoft.mods.tml.jmod.methods.item.MethodAddItem;
import clashsoft.mods.tml.jmod.methods.util.MethodHelp;
import clashsoft.mods.tml.jmod.methods.util.MethodToString;
import clashsoft.mods.tml.jmod.util.TextModHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.common.MinecraftForge;

@Mod(modid = TextModLoader.MODID, name = TextModLoader.NAME, version = TextModLoader.VERSION)
public class TextModLoader extends BaseMod implements TextModConstants
{
	public static final String		MODID			= "textmodloader";
	public static final String		ACRONYM			= "tml";
	public static final String		NAME			= "Text Mod Loader";
	public static final String		VERSION			= CSUpdate.CURRENT_VERSION + "-0.0.0";
	
	@Instance(MODID)
	public static TextModConstants	instance;
	
	public static List<IMod>		loadedTextMods	= new ArrayList<IMod>();
	
	public TextModLoader()
	{
		super(null, MODID, NAME, ACRONYM, VERSION);
	}
	
	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		
		System.out.println("Loading TextModLoader");
		System.out.println("Registering Method Executers");
		
		GameRegistry.registerFuelHandler(new MethodAddFuel());
		
		TextModHelper.registerMethodExecuter(new MethodAddBlock());
		TextModHelper.registerMethodExecuter(new MethodAddSpecialBlock());
		TextModHelper.registerMethodExecuter(new MethodAddItem());
		
		TextModHelper.registerMethodExecuter(new MethodCrafting());
		TextModHelper.registerMethodExecuter(new MethodCraftingShapeless());
		TextModHelper.registerMethodExecuter(new MethodSmelting());
		TextModHelper.registerMethodExecuter(new MethodAddFuel());
		
		TextModHelper.registerMethodExecuter(new MethodHelp());
		TextModHelper.registerMethodExecuter(new MethodToString());
		
		System.out.println(TextModHelper.methods.size() + " Method Executors registered.");
		
		File modsDir = new File(event.getModConfigurationDirectory().getParentFile(), "mods");
		
		loadMods(modsDir);
	}
	
	@Override
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);
	}
	
	protected static void loadMods(File modsDir)
	{
		for (File f : modsDir.listFiles())
		{
			if (!f.isDirectory())
			{
				loadMod(f);
			}
			else
			{
				for (File g : f.listFiles())
				{
					loadMod(g);
				}
			}
		}
		
		float averageLoadTime = 0L;
		
		for (IMod tm : loadedTextMods)
		{
			averageLoadTime += tm.getLoadTime();
		}
		
		averageLoadTime /= loadedTextMods.size();
		System.out.println(loadedTextMods.size() + " TextMods loaded. Average loading time: " + averageLoadTime);
	}
	
	public static void loadMod(final File modClass)
	{
		if (ENABLE_THREADING)
		{
			new Thread()
			{
				@Override
				public void run()
				{
					doLoadMod(modClass);
				}
			}.start();
		}
		else
		{
			doLoadMod(modClass);
		}
	}
	
	public static void doLoadMod(File modClass)
	{
		
	}
}