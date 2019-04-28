package de.aelpecyem.elementaristics.misc.potions.effects;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionCrystalProtection extends PotionBase {
    public PotionCrystalProtection() {
        super("crystal_protection", false, 7733416, 11);
        //  MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }


    @SubscribeEvent
    public void hitEntity(LivingHurtEvent event) {
        //play some sound effect idk
        if (event.getEntityLiving().getActivePotionEffects().contains(event.getEntityLiving().getActivePotionEffect(this))) {
            if (event.getAmount() > 1) {
                int amplifier = event.getEntityLiving().getActivePotionEffect(this).getAmplifier();
                int duration = event.getEntityLiving().getActivePotionEffect(this).getDuration();
                event.getEntityLiving().attackEntityFrom(event.getSource(), 1);
                event.getEntityLiving().removePotionEffect(this);
                //    if (duration > 1000) {
                if (!event.getEntityLiving().hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null))
                    event.getEntityLiving().addPotionEffect(new PotionEffect(this, duration - 1000, amplifier));
                else {
                    IPlayerCapabilities cap = event.getEntityLiving().getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                    if (cap.drainMagan(40 / (1 + amplifier))) {
                        cap.stuntMagan(40 / (1 + amplifier));
                        event.getEntityLiving().addPotionEffect(new PotionEffect(this, duration - 1000, amplifier, false, false));
                    }
                }
                //  }
                event.setCanceled(true);
            }


        }
    }
}
