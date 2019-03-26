package de.aelpecyem.elementaristics.misc;

import de.aelpecyem.elementaristics.items.base.ItemEssence;
import de.aelpecyem.elementaristics.items.base.thaumagral.ItemPartHandle;
import de.aelpecyem.elementaristics.items.base.thaumagral.ItemThaumagral;
import de.aelpecyem.elementaristics.misc.elements.ElementInit;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ItemColorHandler implements IItemColor {
    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        if (stack.getItem() instanceof ItemEssence) {
            return ElementInit.getElementById(stack.getItemDamage()).getColor();
        }
        if (stack.getItem() instanceof ItemThaumagral){
            if (stack.hasTagCompound()) {
                switch (tintIndex) {
                    case 0: //blades
                        return stack.getTagCompound().getInteger("blade");

                    case 1: //runes
                        return stack.getTagCompound().getInteger("engravings");

                    case 2: //core
                        return stack.getTagCompound().getInteger("core");

                    case 3: //handle
                        return stack.getTagCompound().getInteger("handle");
                }
            }
        }
        if (stack.getItem() instanceof ItemPartHandle){
            if (tintIndex == 0) {
                return ((IHasColor) stack.getItem()).getColor();
            }else{
                return 16777215;
            }
        }
        return 0;
    }
}
