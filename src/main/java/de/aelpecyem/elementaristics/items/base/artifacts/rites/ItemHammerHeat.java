package de.aelpecyem.elementaristics.items.base.artifacts.rites;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityForge;
import de.aelpecyem.elementaristics.items.base.ItemBase;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.ElementInit;
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

import java.util.ArrayList;
import java.util.List;

public class ItemHammerHeat extends ItemAspects implements IHasRiteUse {
    public ItemHammerHeat() {
        super("hammer_heat", ElementInit.fire, 6, false);
        maxStackSize = 1;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip.hammer_heat.name"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.getTileEntity(pos) instanceof TileEntityForge) {
            TileEntityForge forge = (TileEntityForge) worldIn.getTileEntity(pos);
            if (ForgeRecipes.getRecipeForInput(forge.inventory.getStackInSlot(0)) != null) {
                    forge.doParticleShow();
                    forge.hitCount += 1;
            }

        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

}
