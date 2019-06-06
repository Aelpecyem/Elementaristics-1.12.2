package de.aelpecyem.elementaristics.items.base.consumable;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemNectar extends ItemDrinkBase {
    public ItemNectar() {
        super("phial_nectar");
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
            entityLiving.attackEntityFrom(Elementaristics.DAMAGE_PSYCHIC, 15);
            entityLiving.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2000, 3));
            entityLiving.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 2000, 4));
            entityLiving.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 2000, 0));
            entityLiving.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 2000, 0));
        stack.shrink(1);
        return stack;
    }
}
