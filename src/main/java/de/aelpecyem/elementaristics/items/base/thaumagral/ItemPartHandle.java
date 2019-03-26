package de.aelpecyem.elementaristics.items.base.thaumagral;

import de.aelpecyem.elementaristics.items.base.ItemBase;
import de.aelpecyem.elementaristics.misc.IHasColor;

public class ItemPartHandle extends ItemBase implements IHasColor{
    private int color;
    private int durabilityBonus;

    public ItemPartHandle(String name, int color, int durabilityBonus) {
        super(name);
        this.color = color;
        this.durabilityBonus = durabilityBonus;

    }

    public int getDurabilityBonus() {
        return durabilityBonus;
    }


    @Override
    public int getColor() {
        return color;
    }
}
