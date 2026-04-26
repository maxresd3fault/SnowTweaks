package net.minecraft.src;

import java.io.File;
import java.util.logging.Level;

import forge.Configuration;
import forge.Property;

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
	protected static boolean BlockIceClassInstalled = false;
	protected static boolean BlockSnowClassInstalled = false;
	protected static boolean BlockSnowBlockClassInstalled = false;
	
	private Configuration config;
	private static Property bronzeSnowShovelID;
	
	public String Version() {
		return "R042626";
	}
	
	public mod_SnowTweaks() {
		if (!BlockIceClassInstalled || !BlockSnowClassInstalled || !BlockSnowBlockClassInstalled) {
			log("One or more of the base classes has not been injected, please install this mod as a jarmod.", Level.SEVERE);
			return;
		}
		
		File configDir = new File("config");
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
			ModLoader.RegisterBlock(blockGhostSnow);
			
			woodSnowShovel = (new ItemSnowShovel(Integer.parseInt(woodSnowShovelID.value) - 256, EnumToolMaterial.WOOD)).setItemName("woodSnowShovel");
			ModLoader.AddRecipe(new ItemStack(woodSnowShovel, 1), new Object[] {"0W0", "WSW", "0S0", Character.valueOf('W'), Block.planks, Character.valueOf('S'), Item.stick});
			
			stoneSnowShovel = (new ItemSnowShovel(Integer.parseInt(stoneSnowShovelID.value) - 256, EnumToolMaterial.STONE)).setItemName("stoneSnowShovel");
			ModLoader.AddRecipe(new ItemStack(stoneSnowShovel, 1), new Object[] {"0C0", "CSC", "0S0", Character.valueOf('C'), Block.cobblestone, Character.valueOf('S'), Item.stick});
			
			ironSnowShovel = (new ItemSnowShovel(Integer.parseInt(ironSnowShovelID.value) - 256, EnumToolMaterial.IRON)).setItemName("ironSnowShovel");
			ModLoader.AddRecipe(new ItemStack(ironSnowShovel, 1), new Object[] {"0I0", "ISI", "0S0", Character.valueOf('I'), Item.ingotIron, Character.valueOf('S'), Item.stick});
			
			goldSnowShovel = (new ItemSnowShovel(Integer.parseInt(goldSnowShovelID.value) - 256, EnumToolMaterial.GOLD)).setItemName("goldSnowShovel");
			ModLoader.AddRecipe(new ItemStack(goldSnowShovel, 1), new Object[] {"0G0", "GSG", "0S0", Character.valueOf('G'), Item.ingotGold, Character.valueOf('S'), Item.stick});
			
			diamondSnowShovel = (new ItemSnowShovel(Integer.parseInt(diamondSnowShovelID.value) - 256, EnumToolMaterial.EMERALD)).setItemName("diamondSnowShovel");
			ModLoader.AddRecipe(new ItemStack(diamondSnowShovel, 1), new Object[] {"0D0", "DSD", "0S0", Character.valueOf('D'), Item.diamond, Character.valueOf('S'), Item.stick});
			
			ModLoader.AddShapelessRecipe(new ItemStack(Block.snow, 1), new Object[] {new ItemStack(Item.snowball)});
			ModLoader.AddShapelessRecipe(new ItemStack(Item.snowball, 1), new Object[] {new ItemStack(Block.snow)});
			ModLoader.AddShapelessRecipe(new ItemStack(Block.blockSnow, 1), new Object[] {new ItemStack(Block.snow), new ItemStack(Block.snow), new ItemStack(Block.snow), new ItemStack(Block.snow)});
		} finally {
			config.save();
		}
	}

    @Override
    public void ModsLoaded() {
    	if (!BlockIceClassInstalled || !BlockSnowClassInstalled || !BlockSnowBlockClassInstalled) return;
        try {
            Class.forName("mod_MaxresBase");
            maxresBaseFound = true;
        } catch (Exception e) {
        	log("MaxresBase not installed! This mod will not function.", Level.SEVERE);
        }
        try {
            Class.forName("mod_IndustrialCraft");
            icFound = true;
        	
        	bronzeSnowShovel = (new ItemSnowShovel(Integer.parseInt(bronzeSnowShovelID.value) - 256, EnumToolMaterial.IRON)).setMaxDamage(350).setItemName("bronzeSnowShovel");
			ModLoader.AddRecipe(new ItemStack(bronzeSnowShovel, 1), new Object[] {"0B0", "BSB", "0S0", Character.valueOf('B'), mod_IndustrialCraft.ingotBronze, Character.valueOf('S'), Item.stick});
			
			log("IndustrialCraft found! The bronze snow shovel will be available.", Level.INFO);
        } catch (Exception e) {
        }
    }
	
	public static Material materialSwitch(int id) {
		if (id == 78) {
			return Material.snow;
		} else {
			return Material.air;
		}
	}
	
	public static MovingObjectPosition rayTrace(EntityLiving player, double reach, float partialTicks) {
		Vec3D eyePosition = Vec3D.createVector(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		Vec3D lookDirection = player.getLook(partialTicks);
		Vec3D rayEnd = eyePosition.addVector(lookDirection.xCoord * reach, lookDirection.yCoord * reach, lookDirection.zCoord * reach);
		return player.worldObj.rayTraceBlocks_do(eyePosition, rayEnd, true);
	}
	
	public static void log(String str, Level level) {
		ModLoader.getLogger().log(level, "[SnowTweaks]: " + str);
	}
}
