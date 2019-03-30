package de.aelpecyem.elementaristics.items.base.artifacts.rites;


import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.recipe.ForgeRecipes;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ItemHammerHeat extends ItemAspects implements IHasRiteUse {
    public ItemHammerHeat() {
        super("hammer_heat", 6, false, Aspects.fire);
        maxStackSize = 1;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip.hammer_heat.name"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

}
