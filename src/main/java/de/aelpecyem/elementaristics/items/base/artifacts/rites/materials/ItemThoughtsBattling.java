package de.aelpecyem.elementaristics.items.base.artifacts.rites.materials;

import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.ItemAspects;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemThoughtsBattling extends ItemAspects implements IHasRiteUse {
    public ItemThoughtsBattling() {
        super("thoughts_battling", 6, true, "tooltip.thoughts_battling.name", Aspects.mind);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack result = playerIn.getHeldItem(handIn);
        result.shrink(1);
        if (!worldIn.isRemote) {
            if (worldIn.rand.nextBoolean()) {
                playerIn.addExperienceLevel(1);
            } else {
                playerIn.experienceLevel -= 1;
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, result);
    }
}
