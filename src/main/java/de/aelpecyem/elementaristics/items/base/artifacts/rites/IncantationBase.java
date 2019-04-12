package de.aelpecyem.elementaristics.items.base.artifacts.rites;

import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

public class IncantationBase extends ItemAspects implements IHasRiteUse {
    RiteBase rite;
    public IncantationBase(String name, RiteBase rite, Aspect aspect, int power) {
        super(name, power, false, aspect);
        this.rite = rite;
        setMaxStackSize(1);

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
