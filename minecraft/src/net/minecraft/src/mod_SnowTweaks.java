package net.minecraft.src;

import java.io.File;
import forge.Configuration;
import forge.Property;
import net.minecraft.client.Minecraft;

public class mod_SnowTweaks extends BaseModMp {
	public static Block blockGhostSnow;
	public static Item woodSnowShovel;
	public static Item stoneSnowShovel;
	public static Item ironSnowShovel;
	public static Item goldSnowShovel;
	public static Item diamondSnowShovel;
	public static Item bronzeSnowShovel;
	
	public static boolean doesSnowMelt = false;
	public static boolean doesIceMelt = false;
	public static boolean maxresBaseFound = false;
	public static boolean icFound = false;
	public static World lastWorld = null;
	
	private Configuration config;
	private static Property bronzeSnowShovelID;

	public String Name() {
		return "Snow Tweaks";
	}
	
	public String Version() {
		return "R10625";
	}
	
	public String Description() {
		return "Adds a snow shovel!";
	}
	
	public String Icon() {
		return "/maxres/SnowTweaks/ModMenu.png";
	}
	
	public mod_SnowTweaks() {
		File configDir = new File(Minecraft.getMinecraftDir(), "/config/");
		config = new Configuration(new File(configDir, "SnowTweaks.cfg"));
		
		try {
			config.load();
			Property blockGhostSnowID = config.getOrCreateBlockIdProperty("blockGhostSnowID", 123);
			Property woodSnowShovelID = config.getOrCreateIntProperty("woodSnowShovelID", 2, 1246);
			Property stoneSnowShovelID = config.getOrCreateIntProperty("stoneSnowShovelID", 2, 1247);
			Property ironSnowShovelID = config.getOrCreateIntProperty("ironSnowShovelID", 2, 1248);
			Property goldSnowShovelID = config.getOrCreateIntProperty("goldSnowShovelID", 2, 1249);
			Property diamondSnowShovelID = config.getOrCreateIntProperty("diamondSnowShovelID", 2, 1250);
			bronzeSnowShovelID = config.getOrCreateIntProperty("bronzeSnowShovelID", 2, 1251);
			
			Property doesSnowMeltProperty = config.getOrCreateBooleanProperty("doesSnowMelt", 0, false);
			Property doesIceMeltProperty = config.getOrCreateBooleanProperty("doesIceMelt", 0, false);
			
			doesSnowMelt = Boolean.parseBoolean(doesSnowMeltProperty.value);
			doesIceMelt = Boolean.parseBoolean(doesIceMeltProperty.value);
			
			blockGhostSnow = (new BlockGhostSnow(Integer.parseInt(blockGhostSnowID.value), 66).setBlockName("blockGhostSnow"));
			ModLoader.AddName(blockGhostSnow, "Snow Won't Fall Here");
			ModLoader.RegisterBlock(blockGhostSnow);
			
			woodSnowShovel = (new ItemSnowShovel(Integer.parseInt(woodSnowShovelID.value) - 256, EnumToolMaterial.WOOD)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/maxres/SnowTweaks/woodSnowShovel.png")).setItemName("woodSnowShovel");
			ModLoader.AddName(woodSnowShovel, "Wood Snow Shovel");
			ModLoader.AddRecipe(new ItemStack(woodSnowShovel, 1), new Object[] {"0W0", "WSW", "0S0", Character.valueOf('W'), Block.planks, Character.valueOf('S'), Item.stick});
			
			stoneSnowShovel = (new ItemSnowShovel(Integer.parseInt(stoneSnowShovelID.value) - 256, EnumToolMaterial.STONE)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/maxres/SnowTweaks/stoneSnowShovel.png")).setItemName("stoneSnowShovel");
			ModLoader.AddName(stoneSnowShovel, "Stone Snow Shovel");
			ModLoader.AddRecipe(new ItemStack(stoneSnowShovel, 1), new Object[] {"0C0", "CSC", "0S0", Character.valueOf('C'), Block.cobblestone, Character.valueOf('S'), Item.stick});
			
			ironSnowShovel = (new ItemSnowShovel(Integer.parseInt(ironSnowShovelID.value) - 256, EnumToolMaterial.IRON)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/maxres/SnowTweaks/ironSnowShovel.png")).setItemName("ironSnowShovel");
			ModLoader.AddName(ironSnowShovel, "Iron Snow Shovel");
			ModLoader.AddRecipe(new ItemStack(ironSnowShovel, 1), new Object[] {"0I0", "ISI", "0S0", Character.valueOf('I'), Item.ingotIron, Character.valueOf('S'), Item.stick});
			
			goldSnowShovel = (new ItemSnowShovel(Integer.parseInt(goldSnowShovelID.value) - 256, EnumToolMaterial.GOLD)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/maxres/SnowTweaks/goldSnowShovel.png")).setItemName("goldSnowShovel");
			ModLoader.AddName(goldSnowShovel, "Gold Snow Shovel");
			ModLoader.AddRecipe(new ItemStack(goldSnowShovel, 1), new Object[] {"0G0", "GSG", "0S0", Character.valueOf('G'), Item.ingotGold, Character.valueOf('S'), Item.stick});
			
			diamondSnowShovel = (new ItemSnowShovel(Integer.parseInt(diamondSnowShovelID.value) - 256, EnumToolMaterial.EMERALD)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/maxres/SnowTweaks/diamondSnowShovel.png")).setItemName("diamondSnowShovel");
			ModLoader.AddName(diamondSnowShovel, "Diamond Snow Shovel");
			ModLoader.AddRecipe(new ItemStack(diamondSnowShovel, 1), new Object[] {"0D0", "DSD", "0S0", Character.valueOf('D'), Item.diamond, Character.valueOf('S'), Item.stick});
			
			ModLoader.AddShapelessRecipe(new ItemStack(Block.snow, 1), new Object[] {new ItemStack(Item.snowball)});
			ModLoader.AddShapelessRecipe(new ItemStack(Item.snowball, 1), new Object[] {new ItemStack(Block.snow)});
			ModLoader.AddShapelessRecipe(new ItemStack(Block.blockSnow, 1), new Object[] {new ItemStack(Block.snow), new ItemStack(Block.snow), new ItemStack(Block.snow), new ItemStack(Block.snow)});
		} finally {
			config.save();
		}
		ModLoader.SetInGameHook(this, true, true);
	}

    @Override
    public void ModsLoaded() {
        try {
            Class.forName("mod_MaxresBase");
            maxresBaseFound = true;
        } catch (Exception e) {
        	log("MaxresBase not installed! This mod will not function.", 2);
        }
        try {
            Class.forName("mod_IndustrialCraft");
            icFound = true;
        	log("IndustrialCraft found! The bronze snow shovel will be available.", 0);
        	bronzeSnowShovel = (new ItemSnowShovel(Integer.parseInt(bronzeSnowShovelID.value) - 256, EnumToolMaterial.IRON)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/maxres/SnowTweaks/bronzeSnowShovel.png")).setMaxDamage(350).setItemName("bronzeSnowShovel");
			ModLoader.AddName(bronzeSnowShovel, "Bronze Snow Shovel");
			ModLoader.AddRecipe(new ItemStack(bronzeSnowShovel, 1), new Object[] {"0B0", "BSB", "0S0", Character.valueOf('B'), mod_IndustrialCraft.ingotBronze, Character.valueOf('S'), Item.stick});
        } catch (Exception e) {
        }
    }
	
	@Override
	public boolean OnTickInGame(Minecraft client) {
		if (!client.theWorld.multiplayerWorld && (lastWorld != client.theWorld)) {
			if (!maxresBaseFound) client.thePlayer.addChatMessage("§4[SnowTweaks]: Please install MaxresBase!");
			lastWorld = client.theWorld;
		}
		return true;
	}
	
	public static Material materialSwitch(int id) {
		if (id == 78) {
			return Material.snow;
		} else {
			return Material.air;
		}
	}
	
	public static void log(String str, int messageCase) {
		if (messageCase == 0) {
			System.out.println("[SnowTweaks]: " + str);
		} else if (messageCase == 1) {
			System.out.println("[SnowTweaks][WARN]: " + str);
		} else if (messageCase == 2) {
			System.out.println("[SnowTweaks][FATAL ERROR]: " + str);
		}
	}
}
