package de.aelpecyem.elementaristics.items.base.consumable;

import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.items.base.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemDimensionalNexus extends ItemBase {

    //Creates a connection to the Outer and the Inner Threshold
    public ItemDimensionalNexus() {
        super("nexus_dimensional");
        maxStackSize = 1;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        EntityDimensionalNexus nexus = new EntityDimensionalNexus(worldIn);
        player.setActiveHand(hand);
        nexus.setPosition(pos.getX() + hitX, pos.getY() + hitY + 0.5, pos.getZ() + hitZ);
        nexus.setOwnerUUID(player.getUniqueID());
        if (!worldIn.isRemote)
            worldIn.spawnEntity(nexus);
        ItemStack stack = player.getHeldItem(hand);
        stack.shrink(1);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        EntityDimensionalNexus nexus = new EntityDimensionalNexus(worldIn);
        playerIn.setActiveHand(handIn);
        nexus.setPosition(playerIn.posX, playerIn.posY + playerIn.getEyeHeight() - nexus.height / 2, playerIn.posZ);
        nexus.setOwnerUUID(playerIn.getUniqueID());
        if (!worldIn.isRemote)
            worldIn.spawnEntity(nexus);
        ItemStack stack = playerIn.getHeldItem(handIn);
        stack.shrink(1);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

}
