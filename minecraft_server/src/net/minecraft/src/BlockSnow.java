package net.minecraft.src;

import java.util.Random;

public class BlockSnow extends Block {
	protected BlockSnow(int var1, int var2) {
		super(var1, var2, mod_SnowTweaks.materialSwitch(var1));
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		this.setTickOnLoad(true);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		int var5 = var1.getBlockMetadata(var2, var3, var4) & 7;
		return var5 >= 3 ? AxisAlignedBB.getBoundingBoxFromPool((double)var2 + this.minX, (double)var3 + this.minY, (double)var4 + this.minZ, (double)var2 + this.maxX, (double)((float)var3 + 0.5F), (double)var4 + this.maxZ) : null;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean isACube() {
		return false;
	}

	public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4) {
		int var5 = var1.getBlockMetadata(var2, var3, var4) & 7;
		float var6 = (float)(2 * (1 + var5)) / 16.0F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var6, 1.0F);
	}

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		int var5 = var1.getBlockId(var2, var3 - 1, var4);
		return var5 != 0 && Block.blocksList[var5].isOpaqueCube() ? var1.getBlockMaterial(var2, var3 - 1, var4).getIsSolid() : false;
	}
	
	@Override
	public void onBlockPlaced(World world, int x, int y, int z, int meta) {
		if (world.worldProvider.worldType == -1) {
			world.setBlockWithNotify(x, y, z, Block.fire.blockID);
			world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
		}
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		this.func_275_g(var1, var2, var3, var4);
	}

	private boolean func_275_g(World var1, int var2, int var3, int var4) {
		if(!this.canPlaceBlockAt(var1, var2, var3, var4)) {
			this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
			var1.setBlockWithNotify(var2, var3, var4, 0);
			return false;
		} else {
			return true;
		}
	}

	public void harvestBlock(World var1, EntityPlayer var2, int var3, int var4, int var5, int var6) {
	    ItemStack heldItem = var2.getCurrentEquippedItem();
	    ItemStack dropedItem;

	    if (heldItem != null && heldItem.getItem() instanceof ItemSnowShovel) {
	    	dropedItem = new ItemStack(this, 1);
	    } else {
	    	dropedItem = new ItemStack(Item.snowball, 1);
	    }
		
		float var8 = 0.7F;
		double var9 = (double)(var1.rand.nextFloat() * var8) + (double)(1.0F - var8) * 0.5D;
		double var11 = (double)(var1.rand.nextFloat() * var8) + (double)(1.0F - var8) * 0.5D;
		double var13 = (double)(var1.rand.nextFloat() * var8) + (double)(1.0F - var8) * 0.5D;
		EntityItem var15 = new EntityItem(var1, (double)var3 + var9, (double)var4 + var11, (double)var5 + var13, dropedItem);
		var15.delayBeforeCanPickup = 10;
		var1.entityJoinedWorld(var15);
		var2.addStat(StatList.mineBlockStatArray[this.blockID], 1);
	}

	public int idDropped(int var1, Random var2) {
		return Item.snowball.shiftedIndex;
	}

	public int quantityDropped(Random var1) {
		return 0;
	}

	public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
		if(mod_SnowTweaks.doesSnowMelt && var1.getSavedLightValue(EnumSkyBlock.Block, var2, var3, var4) > 11) {
			this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
			var1.setBlockWithNotify(var2, var3, var4, 0);
		}

	}
}
