package de.aelpecyem.elementaristics.misc;

import de.aelpecyem.elementaristics.items.base.ItemEssence;
import de.aelpecyem.elementaristics.items.base.ItemScale;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ItemColorHandler implements IItemColor {
    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        if (stack.getItem() instanceof ItemEssence) {
            return Aspects.getElementById(stack.getItemDamage()).getColor();
        }
        if (stack.getItem() instanceof ItemScale) {
            return Aspects.getElementById(stack.getItemDamage()).getColor();
        }
        return -1;
    }
}
