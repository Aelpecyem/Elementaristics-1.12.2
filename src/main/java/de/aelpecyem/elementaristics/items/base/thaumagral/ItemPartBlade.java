package de.aelpecyem.elementaristics.items.base.thaumagral;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.items.base.ItemBase;
import de.aelpecyem.elementaristics.misc.IHasColor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPartBlade extends ItemBase implements IHasColor{
    private int name;
    private float magicDmgBonus;
    private float voidDmgBonus;
    private float dmgBonus;
    private int durabilityBonus;

    public ItemPartBlade(String name, int color, float dmgBonus, int durabilityBonus, float magicDmgBonus, float voidDmgBonus) {
        super(name);
        this.name = color;
        this.dmgBonus = dmgBonus;
        this.durabilityBonus = durabilityBonus;
        this.magicDmgBonus = magicDmgBonus;
        this.voidDmgBonus = voidDmgBonus;

    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerItemModel() {
        Elementaristics.proxy.registerItemRenderer(this, 0, "blade_thaumagral");
    }


    public float getDmgBonus() {
        return dmgBonus;
    }

    public int getDurabilityBonus() {
        return durabilityBonus;
    }

    public float getMagicDmgBonus() {
        return magicDmgBonus;
    }

    public float getVoidDmgBonus() {
        return voidDmgBonus;
    }

    @Override
    public int getColor() {
        return name;
    }
}
