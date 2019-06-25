package de.aelpecyem.elementaristics.misc.poisons;

import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class PoisonWintersBreath extends PoisonEffectBase {
    public PoisonWintersBreath() {
        super(PoisonInit.poisons.size(), Aspects.ice.getColor());
    }

    @Override
    public void performEffect(World world, EntityLivingBase player) {
        if(!world.isRemote) {
            player.attackEntityFrom(DamageSource.STARVE, 12);
            player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 1200, 2));
            player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 200, 0));
            if (player instanceof EntityPlayer) {
                ((EntityPlayer) player).getFoodStats().setFoodLevel(3);
            }
        }
        super.performEffect(world, player);
    }

    @Override
    public void drinkEffect(World world, EntityLivingBase player) {
        PoisonInit.performDelayedEffect(player, this, 1000 + world.rand.nextInt(1200));
    }
}
