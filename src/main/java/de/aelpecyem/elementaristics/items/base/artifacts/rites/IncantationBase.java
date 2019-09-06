package de.aelpecyem.elementaristics.items.base.artifacts.rites;

import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class IncantationBase extends ItemAspects implements IHasRiteUse {
    RiteBase rite;
    public IncantationBase(String name, RiteBase rite, Aspect aspect, int power) {
        super(name, power, false, aspect);
        this.rite = rite;
        setMaxStackSize(1);

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        List<EntityDimensionalNexus> nexuses = worldIn.getEntitiesWithinAABB(EntityDimensionalNexus.class, playerIn.getEntityBoundingBox().grow(6));
        if (!worldIn.isRemote && !nexuses.isEmpty()) {
            for (EntityDimensionalNexus nexus : nexuses) {
                if (nexus.isValidUser(playerIn)) {
                    if (nexus.getRiteString().equals(rite.name.toString())) {
                        nexus.setRiteString("");
                    } else {
                        nexus.setRiteString(rite.name.toString());
                    }
                    nexus.setRiteTicks(0);
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add("---------------");
        tooltip.add(I18n.format("tooltip.incantation.rite") + " "  + I18n.format("rite." + rite.name + ".name"));
        tooltip.add(I18n.format("tooltip.incantation.magan") + " " + Math.round(rite.getTicksRequired() * rite.getMaganDrainedPerTick()));
        tooltip.add(I18n.format("tooltip.incantation.power") + " " + rite.getItemPowerRequired());
        if (rite.isSoulSpecific()) {
            tooltip.add(I18n.format("tooltip.incantation.soul") + " " + rite.getSoulRequired().getLocalizedName());
        }
        tooltip.add(I18n.format("tooltip.incantation.aspects"));
        for (Aspect aspect : rite.getAspectsRequired()){
            tooltip.add("-" + aspect.getLocalizedName());
        }
    }

    public RiteBase getRite() {
        return rite;
    }

}
