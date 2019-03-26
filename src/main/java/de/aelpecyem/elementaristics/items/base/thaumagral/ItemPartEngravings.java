package de.aelpecyem.elementaristics.items.base.thaumagral;

import de.aelpecyem.elementaristics.items.base.ItemBase;
import de.aelpecyem.elementaristics.misc.IHasColor;

public class ItemPartEngravings extends ItemBase implements IHasColor{
    private int color;
    private float magicDmgBonus;
    private float voidDmgBonus;

    public ItemPartEngravings(String name, int color, float magicDmgBonus, float voidDmgBonus) {
        super(name);
        this.color = color;
        this.magicDmgBonus = magicDmgBonus;
        this.voidDmgBonus = voidDmgBonus;
    }

    public float getMagicDmgBonus() {
        return magicDmgBonus;
    }

    public float getVoidDmgBonus() {
        return voidDmgBonus;
    }

    public int getColor() {
        return 0;
    }
}
