package net.minecraft.src;

import java.util.Random;

public class BlockIce extends BlockBreakable {
	public BlockIce(int var1, int var2) {
		super(var1, var2, Material.ice, false);
		this.slipperiness = 0.98F;
		this.setTickOnLoad(true);
	}

	public int getRenderBlockPass() {
		return 1;
	}

	public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		return super.shouldSideBeRendered(var1, var2, var3, var4, 1 - var5);
	}

	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		ItemStack heldItem = player.getCurrentEquippedItem();
	    
		if (heldItem != null && 
		(heldItem.itemID == mod_SnowTweaks.diamondSnowShovel.shiftedIndex ||
		(mod_SnowTweaks.icFound && heldItem.itemID == mod_SnowTweaks.bronzeSnowShovel.shiftedIndex))) {
	    	heldItem.damageItem(15, player);
	    	dropBlockAsItem_do(world, x, y, z, new ItemStack(this, 1));
	    	world.setBlockWithNotify(x, y, z, 0);
	    } else {
			Material blockUnderMaterial = world.getBlockMaterial(x, y - 1, z);
			if(blockUnderMaterial.getIsSolid() || blockUnderMaterial.getIsLiquid()) {
				world.setBlockWithNotify(x, y, z, Block.waterMoving.blockID);
			}
	    }
		player.addStat(StatList.mineBlockStatArray[this.blockID], 1);
	}
	
	@Override
	public void onBlockPlaced(World world, int x, int y, int z, int meta) {
		if (world.worldProvider.worldType == -1) {
			world.setBlockWithNotify(x, y, z, Block.fire.blockID);
			world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
		}
	}

	public int quantityDropped(Random var1) {
		return 0;
	}

	public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
		if(mod_SnowTweaks.doesIceMelt && var1.getSavedLightValue(EnumSkyBlock.Block, var2, var3, var4) > 11 - Block.lightOpacity[this.blockID]) {
			this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
			var1.setBlockWithNotify(var2, var3, var4, Block.waterStill.blockID);
		}

	}

	public int getMobilityFlag() {
		return 0;
	}
}
