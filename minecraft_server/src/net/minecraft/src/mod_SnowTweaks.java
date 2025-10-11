package net.minecraft.src;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import forge.Configuration;
import forge.NetworkMod;
import forge.Property;
import net.minecraft.server.MinecraftServer;

public class mod_SnowTweaks extends NetworkMod {
	public static Block blockGhostSnow;
	public static Item woodSnowShovel;
	public static Item stoneSnowShovel;
	public static Item ironSnowShovel;
	public static Item diamondSnowShovel;
	public static Item goldSnowShovel;
	public static Item bronzeSnowShovel;
	public static Item rubySnowShovel;
	public static Item emeraldSnowShovel;
	public static Item sapphireSnowShovel;
	
	public static boolean doesSnowMelt = false;
	public static boolean doesIceMelt = false;
	public static boolean ic2Found = false;
	public static boolean redPowerFound = false;
	public static boolean eeFound = false;
	public static boolean isLoaded = false;
	protected static boolean BlockIceClassInstalled = false;
	protected static boolean BlockSnowClassInstalled = false;
	protected static boolean BlockSnowBlockClassInstalled = false;
	private static Map<Item, Integer> emcVals = new HashMap<Item, Integer>();
	
	private Configuration config;
	private static Property bronzeSnowShovelID;
	private static Property rubySnowShovelID;
	private static Property emeraldSnowShovelID;
	private static Property sapphireSnowShovelID;
	
	public String getVersion() {
		return "R101125";
	}
	
	public void load() {
		if (!BlockIceClassInstalled || !BlockSnowClassInstalled || !BlockSnowBlockClassInstalled) {
			log("One or more of the base classes has not been injected, please install this mod as a jarmod.", Level.SEVERE);
			return;
		}
		
		File configDir = new File("config");
		config = new Configuration(new File(configDir, "SnowTweaks.cfg"));
		
		try {
			config.load();
			Property blockGhostSnowID = config.getOrCreateBlockIdProperty("blockGhostSnowID", 182);
			Property woodSnowShovelID = config.getOrCreateIntProperty("woodSnowShovelID", "item", 26474);
			Property stoneSnowShovelID = config.getOrCreateIntProperty("stoneSnowShovelID", "item", 26475);
			Property ironSnowShovelID = config.getOrCreateIntProperty("ironSnowShovelID", "item", 26476);
			Property diamondSnowShovelID = config.getOrCreateIntProperty("diamondSnowShovelID", "item", 26477);
			Property goldSnowShovelID = config.getOrCreateIntProperty("goldSnowShovelID", "item", 26478);
			bronzeSnowShovelID = config.getOrCreateIntProperty("bronzeSnowShovelID", "item", 26479);
			rubySnowShovelID = config.getOrCreateIntProperty("rubySnowShovelID", "item", 26480);
			emeraldSnowShovelID = config.getOrCreateIntProperty("emeraldSnowShovelID", "item", 26481);
			sapphireSnowShovelID = config.getOrCreateIntProperty("sapphireSnowShovelID", "item", 26482);
			
			Property doesSnowMeltProperty = config.getOrCreateBooleanProperty("doesSnowMelt", "general", false);
			Property doesIceMeltProperty = config.getOrCreateBooleanProperty("doesIceMelt", "general", false);
			
			doesSnowMelt = Boolean.parseBoolean(doesSnowMeltProperty.value);
			doesIceMelt = Boolean.parseBoolean(doesIceMeltProperty.value);
			
			blockGhostSnow = (new BlockGhostSnow(Integer.parseInt(blockGhostSnowID.value), 180).setBlockName("blockGhostSnow"));
			ModLoader.registerBlock(blockGhostSnow);
			ModLoader.addName(blockGhostSnow, "Snow Won't Fall Here");
			
			woodSnowShovel = (new ItemSnowShovel(Integer.parseInt(woodSnowShovelID.value) - 256, EnumToolMaterial.WOOD)).setItemName("woodSnowShovel");
			ModLoader.addRecipe(new ItemStack(woodSnowShovel, 1), new Object[] {"0W0", "WSW", "0S0", Character.valueOf('W'), Block.planks, Character.valueOf('S'), Item.stick});
			ModLoader.addName(woodSnowShovel, "Wood Snow Shovel");
			emcVals.put(woodSnowShovel, 32);
			
			stoneSnowShovel = (new ItemSnowShovel(Integer.parseInt(stoneSnowShovelID.value) - 256, EnumToolMaterial.STONE)).setItemName("stoneSnowShovel");
			ModLoader.addRecipe(new ItemStack(stoneSnowShovel, 1), new Object[] {"0C0", "CSC", "0S0", Character.valueOf('C'), Block.cobblestone, Character.valueOf('S'), Item.stick});
			ModLoader.addName(stoneSnowShovel, "Stone Snow Shovel");
			emcVals.put(stoneSnowShovel, 11);
			
			ironSnowShovel = (new ItemSnowShovel(Integer.parseInt(ironSnowShovelID.value) - 256, EnumToolMaterial.IRON)).setItemName("ironSnowShovel");
			ModLoader.addRecipe(new ItemStack(ironSnowShovel, 1), new Object[] {"0I0", "ISI", "0S0", Character.valueOf('I'), Item.ingotIron, Character.valueOf('S'), Item.stick});
			ModLoader.addName(ironSnowShovel, "Iron Snow Shovel");
			emcVals.put(ironSnowShovel, 776);
			
			diamondSnowShovel = (new ItemSnowShovel(Integer.parseInt(diamondSnowShovelID.value) - 256, EnumToolMaterial.EMERALD)).setItemName("diamondSnowShovel");
			ModLoader.addRecipe(new ItemStack(diamondSnowShovel, 1), new Object[] {"0D0", "DSD", "0S0", Character.valueOf('D'), Item.diamond, Character.valueOf('S'), Item.stick});
			ModLoader.addName(diamondSnowShovel, "Diamond Snow Shovel");
			emcVals.put(diamondSnowShovel, 24584);
			
			goldSnowShovel = (new ItemSnowShovel(Integer.parseInt(goldSnowShovelID.value) - 256, EnumToolMaterial.GOLD)).setItemName("goldSnowShovel");
			ModLoader.addRecipe(new ItemStack(goldSnowShovel, 1), new Object[] {"0G0", "GSG", "0S0", Character.valueOf('G'), Item.ingotGold, Character.valueOf('S'), Item.stick});
			ModLoader.addName(goldSnowShovel, "Gold Snow Shovel");
			emcVals.put(goldSnowShovel, 6152);
			
			ModLoader.addShapelessRecipe(new ItemStack(Block.snow, 1), new Object[] {new ItemStack(Item.snowball)});
			ModLoader.addShapelessRecipe(new ItemStack(Item.snowball, 1), new Object[] {new ItemStack(Block.snow)});
			ModLoader.addShapelessRecipe(new ItemStack(Block.blockSnow, 1), new Object[] {new ItemStack(Block.snow), new ItemStack(Block.snow), new ItemStack(Block.snow), new ItemStack(Block.snow)});
		} finally {
			config.save();
		}
		ModLoader.setInGameHook(this, true, true);
	}
	
	@Override
	public void modsLoaded() {
		if (!BlockIceClassInstalled || !BlockSnowClassInstalled || !BlockSnowBlockClassInstalled) return;
		try { // IndustrialCraft Stuff
			Class<?> Ic2Items = Class.forName("ic2.common.Ic2Items");
			ic2Found = true;
			
			Field bronzeField = Ic2Items.getField("bronzeIngot");
			ItemStack bronzeIngot = (ItemStack) bronzeField.get(null);
			
			bronzeSnowShovel = (new ItemSnowShovel(Integer.parseInt(bronzeSnowShovelID.value) - 256, EnumToolMaterial.IRON)).setMaxDamage(350).setItemName("bronzeSnowShovel");
			ModLoader.addRecipe(new ItemStack(bronzeSnowShovel, 1), new Object[] {"0B0", "BSB", "0S0", Character.valueOf('B'), bronzeIngot, Character.valueOf('S'), Item.stick});
			ModLoader.addName(bronzeSnowShovel, "Bronze Snow Shovel");
			emcVals.put(bronzeSnowShovel, 773);
			
			log("IndustrialCraft2 found! The bronze snow shovel will be available.", Level.INFO);
		} catch (Exception e) {
		}
		try { // RedPower Stuff
			Class<?> RedPowerBase = Class.forName("RedPowerBase");
			redPowerFound = true;
			
			Field rubyField = RedPowerBase.getField("itemRuby");
			ItemStack itemRuby = (ItemStack) rubyField.get(null);
			Field emeraldField = RedPowerBase.getField("itemEmerald");
			ItemStack itemEmerald = (ItemStack) emeraldField.get(null);
			Field sapphireField = RedPowerBase.getField("itemSapphire");
			ItemStack itemSapphire = (ItemStack) sapphireField.get(null);
			
			rubySnowShovel = (new ItemSnowShovel(Integer.parseInt(rubySnowShovelID.value) - 256, EnumToolMaterial.IRON)).setMaxDamage(500).setItemName("rubySnowShovel");
			ModLoader.addRecipe(new ItemStack(rubySnowShovel, 1), new Object[] {"0R0", "RSR", "0S0", Character.valueOf('R'), itemRuby, Character.valueOf('S'), Item.stick});
			ModLoader.addName(rubySnowShovel, "Ruby Snow Shovel");
			emcVals.put(rubySnowShovel, 3080);
			
			emeraldSnowShovel = (new ItemSnowShovel(Integer.parseInt(emeraldSnowShovelID.value) - 256, EnumToolMaterial.IRON)).setMaxDamage(500).setItemName("emeraldSnowShovel");
			ModLoader.addRecipe(new ItemStack(emeraldSnowShovel, 1), new Object[] {"0E0", "ESE", "0S0", Character.valueOf('E'), itemEmerald, Character.valueOf('S'), Item.stick});
			ModLoader.addName(emeraldSnowShovel, "Emerald Snow Shovel");
			emcVals.put(emeraldSnowShovel, 3080);
			
			sapphireSnowShovel = (new ItemSnowShovel(Integer.parseInt(sapphireSnowShovelID.value) - 256, EnumToolMaterial.IRON)).setMaxDamage(500).setItemName("sapphireSnowShovel");
			ModLoader.addRecipe(new ItemStack(sapphireSnowShovel, 1), new Object[] {"0P0", "PSP", "0S0", Character.valueOf('P'), itemSapphire, Character.valueOf('S'), Item.stick});
			ModLoader.addName(sapphireSnowShovel, "Sapphire Snow Shovel");
			emcVals.put(sapphireSnowShovel, 3080);
			
			log("RedPower found! The ruby, emerald, & sapphire snow shovel will be available.", Level.INFO);
		} catch (Exception e) {
		}
		try { // EE Stuff
			Class<?> EEMaps = Class.forName("ee.EEMaps");
			Class<?> EEItem = Class.forName("ee.EEItem");
			eeFound = true;
			
			Field covalenceDustField = EEItem.getField("covalenceDust");
			Item covalenceDust = (Item) covalenceDustField.get(null);
			ItemStack lowDust = new ItemStack(covalenceDust, 1, 0);
			ItemStack medDust = new ItemStack(covalenceDust, 1, 1);
			ItemStack highDust = new ItemStack(covalenceDust, 1, 2);
			
			Method addEMC = EEMaps.getMethod("addEMC", Integer.TYPE, Integer.TYPE, Integer.TYPE);
			Method addRepair = EEMaps.getMethod("AddRepairRecipe", ItemStack.class, Object[].class);
			
			for (Map.Entry<Item, Integer> entry : emcVals.entrySet()) {
			    Item item = entry.getKey();
			    int value = entry.getValue();
				addEMC.invoke(null, item.shiftedIndex, 0, value);
			}
			
			addRepair.invoke(null, new ItemStack(woodSnowShovel, 1, -1), new Object[] {lowDust});
			addRepair.invoke(null, new ItemStack(stoneSnowShovel, 1, -1), new Object[] {lowDust, lowDust, lowDust});
			addRepair.invoke(null, new ItemStack(ironSnowShovel, 1, -1), new Object[] {medDust, medDust, medDust});
			addRepair.invoke(null, new ItemStack(diamondSnowShovel, 1, -1), new Object[] {highDust, highDust, highDust});
			addRepair.invoke(null, new ItemStack(goldSnowShovel, 1, -1), new Object[] {medDust, medDust, medDust});
			if (ic2Found) {
				addRepair.invoke(null, new ItemStack(bronzeSnowShovel, 1, -1), new Object[] {medDust, medDust, medDust});
			}
			if (redPowerFound) {
				addRepair.invoke(null, new ItemStack(rubySnowShovel, 1, -1), new Object[] {medDust, medDust, medDust});
				addRepair.invoke(null, new ItemStack(emeraldSnowShovel, 1, -1), new Object[] {medDust, medDust, medDust});
				addRepair.invoke(null, new ItemStack(sapphireSnowShovel, 1, -1), new Object[] {medDust, medDust, medDust});
			}
			
			log("Registered item values with Equivalent Exchange!", Level.INFO);
			emcVals.clear();
			emcVals = null;
		} catch (Exception e) {
			emcVals.clear();
			emcVals = null;
		}
	}
	
	@Override
	public boolean onTickInGame(MinecraftServer server) {
		if (!isLoaded && (!doesSnowMelt || !doesIceMelt)) {
			SnowTweaksOverrides.injectSnowTweaks(server.getWorldManager(0));
			isLoaded = true;
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
