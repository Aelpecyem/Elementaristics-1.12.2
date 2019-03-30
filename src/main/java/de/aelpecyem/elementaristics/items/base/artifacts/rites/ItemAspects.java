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
    protected List<Aspect> aspects = new ArrayList<>();
    protected int itemPower;
    protected boolean consumed;
    protected String extraLore = null;
    public ItemAspects(String name, int itemPower, boolean consumed, Aspect... aspects) {
        super(name);
        for (Aspect aspect : aspects){
            this.aspects.add(aspect);
        }
        this.itemPower = itemPower;
        this.consumed = consumed;
    }
    public ItemAspects(String name, int itemPower, boolean consumed, String extraLore, Aspect... aspects) {
        super(name);
        for (Aspect aspect : aspects){
            this.aspects.add(aspect);
        }
        this.itemPower = itemPower;
        this.consumed = consumed;
        this.extraLore = extraLore;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (extraLore != null){
            tooltip.add(I18n.format(extraLore));
            tooltip.add(" ");
        }
        tooltip.add(I18n.format("tooltip.aspect_tool.power") + " " + getPower());

        tooltip.add(I18n.format("tooltip.aspect_tool.aspects"));
        for (Aspect aspect : this.getAspects()){
            tooltip.add("-" + aspect.getLocalizedName());
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public List<Aspect> getAspects() {
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
