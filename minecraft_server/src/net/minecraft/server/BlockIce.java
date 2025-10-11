package net.minecraft.server;

import java.util.Random;
import org.bukkit.craftbukkit.event.CraftEventFactory;

public class BlockIce extends BlockHalfTransparant {
	public BlockIce(int var1, int var2) {
		super(var1, var2, Material.ICE, false);
		this.frictionFactor = 0.98F;
		this.a(true);
		mod_SnowTweaks.BlockIceClassInstalled = true;
	}

	public void a(World world, EntityHuman player, int x, int y, int z, int meta) {
		ItemStack heldItem = player.U();
	    
		if (heldItem != null && 
		(heldItem.id == mod_SnowTweaks.diamondSnowShovel.id ||
		(mod_SnowTweaks.ic2Found && heldItem.id == mod_SnowTweaks.bronzeSnowShovel.id))) {
	    	heldItem.damage(15, player);
	    	a(world, x, y, z, new ItemStack(this, 1));
	    	world.setTypeId(x, y, z, 0);
		} else {
			Material var7 = world.getMaterial(x, y - 1, z);
			if(var7.isSolid() || var7.isLiquid()) {
				world.setTypeId(x, y, z, Block.WATER.id);
			}
		}
		player.a((Statistic)StatisticList.C[this.id], 1);
	}
	
	@Override
	public void postPlace(World world, int x, int y, int z, int meta) {
		if (world.worldProvider.dimension == -1) {
			world.setTypeId(x, y, z, Block.FIRE.id);
			world.makeSound(x + 0.5, y + 0.5, z + 0.5, "random.fizz", 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
		}
	}

	public int a(Random var1) {
		return 0;
	}

	public void a(World var1, int var2, int var3, int var4, Random var5) {
		if(mod_SnowTweaks.doesIceMelt && var1.a(EnumSkyBlock.BLOCK, var2, var3, var4) > 11 - Block.lightBlock[this.id]) {
			if(CraftEventFactory.callBlockFadeEvent(var1.getWorld().getBlockAt(var2, var3, var4), Block.STATIONARY_WATER.id).isCancelled()) {
				return;
			}

			this.b(var1, var2, var3, var4, var1.getData(var2, var3, var4), 0);
			var1.setTypeId(var2, var3, var4, Block.STATIONARY_WATER.id);
		}

	}

	public int g() {
		return 0;
	}

	protected ItemStack a_(int var1) {
		return null;
	}
}
