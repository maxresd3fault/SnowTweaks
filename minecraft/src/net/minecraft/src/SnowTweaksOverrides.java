package net.minecraft.src;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class SnowTweaksOverrides {
	public static void injectSnowTweaks(World world) {
		try {
			Field positionsToUpdateSetField = World.class.getDeclaredField("D"); // activeChunkSet
			positionsToUpdateSetField.setAccessible(true);
			
			Set<ChunkCoordIntPair> proxySet = new HashSet<ChunkCoordIntPair>() {
				@Override
				public boolean add(ChunkCoordIntPair chunk) {
					super.add(chunk);
					runSnowForChunk(world, chunk);
				    return true;
				}
			};
			positionsToUpdateSetField.set(world, proxySet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void runSnowForChunk(World world, ChunkCoordIntPair chunkPair) {
		int var5 = chunkPair.chunkXPos * 16;
		int var6 = chunkPair.chunkZPos * 16;
		
		if(world.rand.nextInt(16) == 0) {
			world.updateLCG = world.updateLCG * 3 + 1013904223;
			int var8 = world.updateLCG >> 2;
			int var9 = var8 & 15;
			int var10 = var8 >> 8 & 15;
			int var11 = world.getPrecipitationHeight(var9 + var5, var10 + var6);
			if(!mod_SnowTweaks.doesIceMelt && isBlockHydratedIndirectly(world, var9 + var5, var11 - 1, var10 + var6)) {
				world.setBlockWithNotify(var9 + var5, var11 - 1, var10 + var6, Block.ice.blockID);
			}

			if(!mod_SnowTweaks.doesSnowMelt && world.isRaining() && canSnowAt(world, var9 + var5, var11, var10 + var6)) {
				world.setBlockWithNotify(var9 + var5, var11, var10 + var6, Block.snow.blockID);
			}
		}
	}
	
	public static boolean canSnowAt(World world, int var1, int var2, int var3) {
		BiomeGenBase var4 = world.getBiomeGenForCoords(var1, var3);
		float var5 = var4.getFloatTemperature();
		if(var5 > 0.15F) {
			return false;
		} else {
			if(var2 >= 0 && var2 < 256 && world.getSavedLightValue(EnumSkyBlock.Block, var1, var2, var3) >= 10) {
				int var6 = world.getBlockId(var1, var2 - 1, var3);
				int var7 = world.getBlockId(var1, var2, var3);
				if(var7 == 0 && Block.snow.canPlaceBlockAt(world, var1, var2, var3) && var6 != 0 && var6 != Block.ice.blockID && Block.blocksList[var6].blockMaterial.blocksMovement()) {
					return true;
				}
			}
			return false;
		}
	}
	
	public static boolean isBlockHydratedIndirectly(World world, int var1, int var2, int var3) {
		BiomeGenBase var5 = world.getBiomeGenForCoords(var1, var3);
		float var6 = var5.getFloatTemperature();
		if(var6 > 0.15F) {
			return false;
		} else {
			if(var2 >= 0 && var2 < 256 && world.getSavedLightValue(EnumSkyBlock.Block, var1, var2, var3) >= 10) {
				int var7 = world.getBlockId(var1, var2, var3);
				if((var7 == Block.waterStill.blockID || var7 == Block.waterMoving.blockID) && world.getBlockMetadata(var1, var2, var3) == 0) {
					boolean var8 = true;
					if(var8 && world.getBlockMaterial(var1 - 1, var2, var3) != Material.water) {
						var8 = false;
					}

					if(var8 && world.getBlockMaterial(var1 + 1, var2, var3) != Material.water) {
						var8 = false;
					}

					if(var8 && world.getBlockMaterial(var1, var2, var3 - 1) != Material.water) {
						var8 = false;
					}

					if(var8 && world.getBlockMaterial(var1, var2, var3 + 1) != Material.water) {
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
