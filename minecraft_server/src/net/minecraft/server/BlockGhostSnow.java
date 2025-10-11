package net.minecraft.server;

import java.util.Random;

public class BlockGhostSnow extends BlockSnow {

	protected BlockGhostSnow(int var1, int var2) {
		super(var1, var2);
		this.c(0);
		this.f(0);
	}
	
	@Override
	public boolean interact(World world, int x, int y, int z, EntityHuman player) {
		ItemStack heldItem = player.U();
		if (heldItem != null && heldItem.getItem() instanceof ItemBlock) {
			ItemBlock itemBlock = (ItemBlock)heldItem.getItem();
			itemBlock.interactWith(heldItem, player, world, x, y - 1, z, 1);
			return true;
		}
		return false;
	}
	
	@Override
	public void a(World world, EntityHuman player, int x, int y, int z, int meta) {
		ItemStack heldItem = player.U();
		if (heldItem != null && heldItem.getItem() instanceof ItemTool && !player.abilities.canInstantlyBuild) {
			heldItem.damage(-1, player);
			if (player instanceof EntityPlayer) {
				EntityPlayer mpPlayer = (EntityPlayer) player;
				mpPlayer.netServerHandler.sendPacket(new Packet103SetSlot(0, player.inventory.itemInHandIndex + 36, heldItem));
	        }
		}
	}
	
	@Override
	public boolean canHarvestBlock(EntityHuman var1, int var2) {
		return true;
	}
	
	@Override
	public int getDropType(int var1, Random var2, int var3) {
		return 0;
	}
	
	@Override
	public boolean isBlockReplaceable(World world, int x, int y, int z) {
	    return true;
	}
	
	@Override
	public void a(World var1, int var2, int var3, int var4, Random var5) {
	}
}
