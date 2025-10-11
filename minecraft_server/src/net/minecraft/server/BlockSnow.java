package net.minecraft.server;

import java.util.Random;
import org.bukkit.craftbukkit.event.CraftEventFactory;

public class BlockSnow extends Block {
	protected BlockSnow(int var1, int var2) {
		super(var1, var2, mod_SnowTweaks.materialSwitch(var1));
		this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		this.a(true);
		mod_SnowTweaks.BlockSnowClassInstalled = true;
	}

	public AxisAlignedBB e(World var1, int var2, int var3, int var4) {
		int var5 = var1.getData(var2, var3, var4) & 7;
		return var5 >= 3 ? AxisAlignedBB.b((double)var2 + this.minX, (double)var3 + this.minY, (double)var4 + this.minZ, (double)var2 + this.maxX, (double)((float)var3 + 0.5F), (double)var4 + this.maxZ) : null;
	}

	public boolean a() {
		return false;
	}

	public boolean b() {
		return false;
	}

	public void updateShape(IBlockAccess var1, int var2, int var3, int var4) {
		int var5 = var1.getData(var2, var3, var4) & 7;
		float var6 = (float)(2 * (1 + var5)) / 16.0F;
		this.a(0.0F, 0.0F, 0.0F, 1.0F, var6, 1.0F);
	}

	public boolean canPlace(World var1, int var2, int var3, int var4) {
		int var5 = var1.getTypeId(var2, var3 - 1, var4);
		Block var6 = Block.byId[var5];
		return var5 == 0 || (var6 == null || !var6.isLeaves(var1, var2, var3 - 1, var4)) && !Block.byId[var5].a() ? false : var1.getMaterial(var2, var3 - 1, var4).isSolid();
	}
	
	@Override
	public void postPlace(World world, int x, int y, int z, int meta) {
		if (world.worldProvider.dimension == -1) {
			world.setTypeId(x, y, z, Block.FIRE.id);
			world.makeSound(x + 0.5, y + 0.5, z + 0.5, "random.fizz", 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
		}
	}

	public void doPhysics(World var1, int var2, int var3, int var4, int var5) {
		this.g(var1, var2, var3, var4);
	}
	
	private boolean g(World var1, int var2, int var3, int var4) {
		if(!this.canPlace(var1, var2, var3, var4)) {
			var1.setTypeId(var2, var3, var4, 0);
			return false;
		} else {
			return true;
		}
	}

	public void a(World world, EntityHuman player, int x, int y, int z, int meta) {
	    ItemStack heldItem = player.U();
	    ItemStack dropedItem;

	    if (heldItem != null && heldItem.getItem() instanceof ItemSnowShovel) {
	    	dropedItem = new ItemStack(this, 1);
	    } else {
	    	dropedItem = new ItemStack(Item.SNOW_BALL, 1);
	    }
	    
		float var8 = 0.7F;
		double var9 = (double)(world.random.nextFloat() * var8) + (double)(1.0F - var8) * 0.5D;
		double var11 = (double)(world.random.nextFloat() * var8) + (double)(1.0F - var8) * 0.5D;
		double var13 = (double)(world.random.nextFloat() * var8) + (double)(1.0F - var8) * 0.5D;
		EntityItem var15 = new EntityItem(world, (double)x + var9, (double)y + var11, (double)z + var13, dropedItem);
		var15.pickupDelay = 10;
		world.addEntity(var15);
		player.a((Statistic)StatisticList.C[this.id], 1);
	}

	public int getDropType(int var1, Random var2, int var3) {
		return Item.SNOW_BALL.id;
	}

	public int a(Random var1) {
		return 1;
	}

	public void a(World var1, int var2, int var3, int var4, Random var5) {
		if(mod_SnowTweaks.doesSnowMelt && var1.a(EnumSkyBlock.BLOCK, var2, var3, var4) > 11) {
			if(CraftEventFactory.callBlockFadeEvent(var1.getWorld().getBlockAt(var2, var3, var4), 0).isCancelled()) {
				return;
			}
			var1.setTypeId(var2, var3, var4, 0);
		}
	}
}
