package de.aelpecyem.elementaristics.items.base.artifacts.rites;

import de.aelpecyem.elementaristics.items.base.ItemBase;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ItemAspects extends ItemBase implements IHasRiteUse {
    Aspect aspect;
    int itemPower;
    boolean consumed;
    public ItemAspects(String name, Aspect aspect, int itemPower, boolean consumed) {
        super(name);
        this.aspect = aspect;
        this.itemPower = itemPower;
        this.consumed = consumed;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip.aspect_tool.power") + " " + getPower());

        tooltip.add(I18n.format("tooltip.aspect_tool.aspects"));
        for (Aspect aspect : this.getAspects()){
            tooltip.add("-" + aspect.getLocalizedName());
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public List<Aspect> getAspects() {
        List<Aspect> aspects = new ArrayList<>();
        aspects.add(aspect);
        return aspects;
    }

    @Override
    public int getPower() {
        return itemPower;
    }

    @Override
    public boolean isConsumed() {
        return consumed;
    }
}
