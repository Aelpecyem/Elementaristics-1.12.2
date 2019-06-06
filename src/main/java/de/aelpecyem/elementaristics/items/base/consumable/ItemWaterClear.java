package de.aelpecyem.elementaristics.items.base.consumable;

import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemWaterClear extends ItemDrinkBase {
    public ItemWaterClear() {
        super("water_clear");
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
            entityLiving.addPotionEffect(new PotionEffect(PotionInit.potionPotential, 2000, 0));
        stack.shrink(1);
        return stack;
    }
}
