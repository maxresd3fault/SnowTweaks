package net.minecraft.src;

public class ItemSnowShovel extends ItemTool {
	public ItemSnowShovel(int id, EnumToolMaterial mat) {
		super(id, 1, mat, getEffectiveBlocksForMaterial(mat));
	}
	
    private static Block[] getEffectiveBlocksForMaterial(EnumToolMaterial mat) {
    	if(mat == EnumToolMaterial.EMERALD) {
    		return new Block[]{Block.snow, Block.blockSnow, Block.ice};
        }
    	else {
    		return new Block[]{Block.snow, Block.blockSnow};
    	}
    }
	
	public boolean canHarvestBlock(Block block) {
		return block == Block.snow ? true : block == Block.blockSnow;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
			MovingObjectPosition mop = ModLoader.getMinecraftInstance().objectMouseOver;
			if (mop != null && mop.typeOfHit == EnumMovingObjectType.TILE) {
				int x = mop.blockX;
				int y = mop.blockY;
				int z = mop.blockZ;
				Block block = Block.blocksList[world.getBlockId(x, y, z)];
				if (block.blockID == Block.snow.blockID) {
					player.swingItem();
					if (!world.multiplayerWorld) {
						itemStack.damageItem(1, player);
						world.setBlockWithNotify(x, y, z, mod_SnowTweaks.blockGhostSnow.blockID);
						float var8 = 0.7F;
						double var9 = (double)(world.rand.nextFloat() * var8) + (double)(1.0F - var8) * 0.5D;
						double var11 = (double)(world.rand.nextFloat() * var8) + (double)(1.0F - var8) * 0.5D;
						double var13 = (double)(world.rand.nextFloat() * var8) + (double)(1.0F - var8) * 0.5D;
						EntityItem var15 = new EntityItem(world, (double)x + var9, (double)y + var11, (double)z + var13, new ItemStack(Block.snow, 1));
						var15.delayBeforeCanPickup = 10;
						world.entityJoinedWorld(var15);
					}
					world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, Block.snow.stepSound.stepSoundDir(), (Block.snow.stepSound.getVolume() + 1.0F) / 2.0F, Block.snow.stepSound.getPitch() * 0.8F);
				}
			}
		player.addStat(StatList.mineBlockStatArray[Block.snow.blockID], 1);
		return itemStack;
	}
}
