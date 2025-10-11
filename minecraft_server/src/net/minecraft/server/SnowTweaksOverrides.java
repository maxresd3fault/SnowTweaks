package net.minecraft.server;

import java.lang.reflect.Field;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.util.LongHash;
import org.bukkit.craftbukkit.util.LongHashset;
import org.bukkit.event.block.BlockFormEvent;

public class SnowTweaksOverrides {
	public static void injectSnowTweaks(World world) {
		try {
			Field positionsToUpdateSetField = World.class.getDeclaredField("chunkTickList");
			positionsToUpdateSetField.setAccessible(true);
			
			LongHashset proxySet = new LongHashset() {
				@Override
				public void add(long chunkKey) {
					super.add(chunkKey);
					int x = LongHash.msw(chunkKey);
					int z = LongHash.lsw(chunkKey);
					runSnowForChunk(world, x, z);
				}
			};
			positionsToUpdateSetField.set(world, proxySet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void runSnowForChunk(World world, int x, int z) {
		int var10 = x * 16;
		int var11 = z * 16;
		
		if(world.random.nextInt(16) == 0) {
			world.g = world.g * 3 + 1013904223;
			int var13 = world.g >> 2;
			int var14 = var13 & 15;
			int var15 = var13 >> 8 & 15;
			int var16 = world.f(var14 + var10, var15 + var11);
			BlockState var17;
			BlockFormEvent var18;
			if(!mod_SnowTweaks.doesIceMelt && c(world, var14 + var10, var16 - 1, var15 + var11)) {
				var17 = world.getWorld().getBlockAt(var14 + var10, var16 - 1, var15 + var11).getState();
				var17.setTypeId(Block.ICE.id);
				var18 = new BlockFormEvent(var17.getBlock(), var17);
				world.getServer().getPluginManager().callEvent(var18);
				if(!var18.isCancelled()) {
					var17.update(true);
				}
			}

			if(!mod_SnowTweaks.doesSnowMelt && world.x() && u(world, var14 + var10, var16, var15 + var11)) {
				var17 = world.getWorld().getBlockAt(var14 + var10, var16, var15 + var11).getState();
				var17.setTypeId(Block.SNOW.id);
				var18 = new BlockFormEvent(var17.getBlock(), var17);
				world.getServer().getPluginManager().callEvent(var18);
				if(!var18.isCancelled()) {
					var17.update(true);
				}
			}
		}
	}
	
	public static boolean u(World world, int var1, int var2, int var3) {
		BiomeBase var4 = world.getBiome(var1, var3);
		float var5 = var4.i();
		if(var5 > 0.15F) {
			return false;
		} else {
			if(var2 >= 0 && var2 < 256 && world.a(EnumSkyBlock.BLOCK, var1, var2, var3) >= 10) {
				int var6 = world.getTypeId(var1, var2 - 1, var3);
				int var7 = world.getTypeId(var1, var2, var3);
				if(var7 == 0 && Block.SNOW.canPlace(world, var1, var2, var3) && var6 != 0 && var6 != Block.ICE.id && Block.byId[var6].material.isSolid()) {
					return true;
				}
			}
			return false;
		}
	}
	
	public static boolean c(World world, int var1, int var2, int var3) {
		BiomeBase var5 = world.getBiome(var1, var3);
		float var6 = var5.i();
		if(var6 > 0.15F) {
			return false;
		} else {
			if(var2 >= 0 && var2 < 256 && world.a(EnumSkyBlock.BLOCK, var1, var2, var3) >= 10) {
				int var7 = world.getTypeId(var1, var2, var3);
				if((var7 == Block.STATIONARY_WATER.id || var7 == Block.WATER.id) && world.getData(var1, var2, var3) == 0) {
					boolean var8 = true;
					if(var8 && world.getMaterial(var1 - 1, var2, var3) != Material.WATER) {
						var8 = false;
					}

					if(var8 && world.getMaterial(var1 + 1, var2, var3) != Material.WATER) {
						var8 = false;
					}

					if(var8 && world.getMaterial(var1, var2, var3 - 1) != Material.WATER) {
						var8 = false;
					}

					if(var8 && world.getMaterial(var1, var2, var3 + 1) != Material.WATER) {
						var8 = false;
					}

					if(!var8) {
						return true;
					}
				}
			}
			return false;
		}
	}
}
