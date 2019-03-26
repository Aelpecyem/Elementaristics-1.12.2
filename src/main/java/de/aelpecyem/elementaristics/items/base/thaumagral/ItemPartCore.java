package de.aelpecyem.elementaristics.items.base.thaumagral;

import de.aelpecyem.elementaristics.items.base.ItemBase;
import de.aelpecyem.elementaristics.misc.IHasColor;

public class ItemPartCore extends ItemBase implements IHasColor{
    private int color;
    private float magicDmgBonus;
    private float voidDmgBonus;
    private int spellSlots;

    public ItemPartCore(String name, int color, float magicDmgBonus, float voidDmgBonus, int spellSlots) {
        super(name);
        this.color = color;
        this.magicDmgBonus = magicDmgBonus;
        this.voidDmgBonus = voidDmgBonus;
        this.spellSlots = spellSlots;
    }


    public float getMagicDmgBonus() {
        return magicDmgBonus;
    }

    public float getVoidDmgBonus() {
        return voidDmgBonus;
    }

    public int getSpellSlots() {
        return spellSlots;
    }

    @Override
    public int getColor() {
        return color;
    }
}
