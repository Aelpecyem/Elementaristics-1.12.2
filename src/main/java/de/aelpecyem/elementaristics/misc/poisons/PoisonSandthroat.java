package de.aelpecyem.elementaristics.misc.poisons;

import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.effects.emotion.PotionEmotion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class PoisonSandthroat extends PoisonEffectBase {
    public PoisonSandthroat() {
        super(PoisonInit.poisons.size(), Aspects.fire.getColor());
    }

    @Override
    public void performEffect(World world, EntityLivingBase player) {
        if(!world.isRemote) {
            PotionEffect[] effects = new PotionEffect[]{
            };
            effects = player.getActivePotionEffects().toArray(effects);
            for (PotionEffect effect : effects) {
                if (!(effect.getPotion() instanceof PotionEmotion)) {
                    player.removePotionEffect(effect.getPotion());
                }
            }
            player.attackEntityFrom(DamageSource.IN_FIRE, 2);
            player.setFire(1);
            player.addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 0));
            if (player instanceof EntityPlayer) {
                ((EntityPlayer) player).getFoodStats().addStats(2, 1.5F);
            }
        }
        super.performEffect(world, player);
    }

    @Override
    public void drinkEffect(World world, EntityLivingBase player) {
        PoisonInit.performDelayedEffect(player, this, 10);
    }
}
