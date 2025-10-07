package net.minecraft.src;

import java.util.Random;

public class BlockGhostSnow extends BlockSnow {

	protected BlockGhostSnow(int var1, int var2) {
		super(var1, var2);
		this.setHardness(0);
		this.setLightOpacity(0);
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
	    return false;
	}
	
	@Override
	public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		ItemStack heldItem = player.getCurrentEquippedItem();
		if (heldItem != null && heldItem.getItem() instanceof ItemBlock) {
			ItemBlock itemBlock = (ItemBlock)heldItem.getItem();
			itemBlock.onItemUse(heldItem, player, world, x, y - 1, z, 1);
			return true;
		}
		return false;
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		ItemStack heldItem = player.getCurrentEquippedItem();
		if (heldItem != null && heldItem.getItem() instanceof ItemTool) {
			heldItem.damageItem(-1, player);
			if (player instanceof EntityPlayerMP) {
				EntityPlayerMP mpPlayer = (EntityPlayerMP) player;
				mpPlayer.playerNetServerHandler.sendPacket(new Packet103SetSlot(0, player.inventory.currentItem + 36, heldItem));
	        }
		}
	}
	
	@Override
	public int idDropped(int var1, Random var2) {
		return 0;
	}
	
	@Override
	public boolean isBlockReplaceable(World world, int x, int y, int z) {
	    return true;
	}
	
	@Override
	public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
	}
}
