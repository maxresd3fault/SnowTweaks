package net.minecraft.src;

import java.util.Random;

public class BlockSnowBlock extends Block {
	protected BlockSnowBlock(int var1, int var2) {
		super(var1, var2, Material.craftedSnow);
		this.setTickRandomly(true);
		mod_SnowTweaks.BlockSnowBlockClassInstalled = true;
	}
	
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
	    ItemStack heldItem = player.getCurrentEquippedItem();

	    if (heldItem != null && heldItem.getItem() instanceof ItemSnowShovel) {
	    	dropBlockAsItem_do(world, x, y, z, new ItemStack(this, 1));
	    } else {
	    	dropBlockAsItem(world, x, y, z, meta, 0);
	    }
		world.setBlockWithNotify(x, y, z, 0);
		player.addStat(StatList.mineBlockStatArray[this.blockID], 1);
	}
	
	@Override
	public void onBlockPlaced(World world, int x, int y, int z, int meta) {
		if (world.worldProvider.worldType == -1) {
			world.setBlockWithNotify(x, y, z, Block.fire.blockID);
			world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
		}
	}

	public int idDropped(int var1, Random var2, int var3) {
		return Item.snowball.shiftedIndex;
	}

	public int quantityDropped(Random var1) {
		return 4;
	}

	public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
		if(mod_SnowTweaks.doesSnowMelt && var1.getSavedLightValue(EnumSkyBlock.Block, var2, var3, var4) > 11) {
			this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4), 0);
			var1.setBlockWithNotify(var2, var3, var4, 0);
		}

	}
}
