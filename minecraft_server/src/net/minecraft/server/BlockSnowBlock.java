package net.minecraft.server;

import java.util.Random;

public class BlockSnowBlock extends Block {
	protected BlockSnowBlock(int var1, int var2) {
		super(var1, var2, Material.SNOW_BLOCK);
		this.a(true);
		mod_SnowTweaks.BlockSnowBlockClassInstalled = true;
	}
	
	public void a(World world, EntityHuman player, int x, int y, int z, int meta) {
	    ItemStack heldItem = player.U();

	    if (heldItem != null && heldItem.getItem() instanceof ItemSnowShovel) {
	    	a(world, x, y, z, new ItemStack(this, 1));
	    } else {
	    	b(world, x, y, z, meta, 0);
	    }
		world.setTypeId(x, y, z, 0);
		player.a((Statistic)StatisticList.C[this.id], 1);
	}
	
	@Override
	public void postPlace(World world, int x, int y, int z, int meta) {
		if (world.worldProvider.dimension == -1) {
			world.setTypeId(x, y, z, Block.FIRE.id);
			world.makeSound(x + 0.5, y + 0.5, z + 0.5, "random.fizz", 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
		}
	}

	public int getDropType(int var1, Random var2, int var3) {
		return Item.SNOW_BALL.id;
	}

	public int a(Random var1) {
		return 4;
	}

	public void a(World var1, int var2, int var3, int var4, Random var5) {
		if(mod_SnowTweaks.doesSnowMelt && var1.a(EnumSkyBlock.BLOCK, var2, var3, var4) > 11) {
			this.b(var1, var2, var3, var4, var1.getData(var2, var3, var4), 0);
			var1.setTypeId(var2, var3, var4, 0);
		}

	}
}
