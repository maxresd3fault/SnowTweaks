package net.minecraft.server;

public class ItemSnowShovel extends ItemTool {
	public ItemSnowShovel(int id, EnumToolMaterial mat) {
		super(id, 1, mat, getEffectiveBlocksForMaterial(mat));
	}
	
    private static Block[] getEffectiveBlocksForMaterial(EnumToolMaterial mat) {
    	if(mat == EnumToolMaterial.DIAMOND) {
    		return new Block[]{Block.SNOW, Block.SNOW_BLOCK, Block.ICE};
        }
    	else {
    		return new Block[]{Block.SNOW, Block.SNOW_BLOCK};
    	}
    }
	
	public boolean canDestroySpecialBlock(Block block) {
		return block == Block.SNOW ? true : block == Block.SNOW_BLOCK;
	}
	
	@Override
	public ItemStack a(ItemStack itemStack, World world, EntityHuman player) {
		MovingObjectPosition mop = mod_SnowTweaks.rayTrace(player, 5.0F, 0.0F);
		if (mop != null && mop.type == EnumMovingObjectType.TILE) {
			int x = mop.b;
			int y = mop.c;
			int z = mop.d;
			Block block = Block.byId[world.getTypeId(x, y, z)];
			if (block != null && block.id == Block.SNOW.id) {
				player.C_();
				if (!player.abilities.canInstantlyBuild) itemStack.damage(1, player);
				world.setTypeId(x, y, z, mod_SnowTweaks.blockGhostSnow.id);
				float var8 = 0.7F;
				double var9 = (double)(world.random.nextFloat() * var8) + (double)(1.0F - var8) * 0.5D;
				double var11 = (double)(world.random.nextFloat() * var8) + (double)(1.0F - var8) * 0.5D;
				double var13 = (double)(world.random.nextFloat() * var8) + (double)(1.0F - var8) * 0.5D;
				EntityItem var15 = new EntityItem(world, (double)x + var9, (double)y + var11, (double)z + var13, new ItemStack(Block.SNOW, 1));
				var15.pickupDelay = 10;
				world.addEntity(var15);
				world.makeSound(x + 0.5, y + 0.5, z + 0.5, Block.SNOW.stepSound.getName(), (Block.SNOW.stepSound.getVolume1() + 1.0F) / 2.0F, Block.SNOW.stepSound.getVolume2() * 0.8F);
			}
		}
		player.a((Statistic)StatisticList.C[Block.SNOW.id], 1);
		return itemStack;
	}
}
