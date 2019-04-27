package de.aelpecyem.elementaristics.misc.potions.effects;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionIntoxicated extends PotionBase {
    public PotionIntoxicated() {
        super("intoxicated", false, 8636302, 1);
        //  MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }


    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        Elementaristics.proxy.generateGenericParticles(entityLivingBaseIn, 65280, 0.5F, 5, 0, false, true);
        Elementaristics.proxy.generateGenericParticles(entityLivingBaseIn, 255, 0.5F, 5, 0, false, true);
        Elementaristics.proxy.generateGenericParticles(entityLivingBaseIn, 16711680, 0.5F, 5, 0, false, true);
        Elementaristics.proxy.generateGenericParticles(entityLivingBaseIn, 16776960, 0.5F, 5, 0, false, true);
        Elementaristics.proxy.generateGenericParticles(entityLivingBaseIn, 11272428, 0.5F, 5, 0, false, true);
        Elementaristics.proxy.generateGenericParticles(entityLivingBaseIn, 16777215, 1F, 10, 0, false, true);
        if (!entityLivingBaseIn.world.isRemote) {
            entityLivingBaseIn.addPotionEffect(new PotionEffect(Potion.getPotionById(15), 120, amplifier, false, false));
            entityLivingBaseIn.addPotionEffect(new PotionEffect(Potion.getPotionById(9), 120, amplifier, false, false));
            entityLivingBaseIn.addPotionEffect(new PotionEffect(Potion.getPotionById(2), 120, amplifier, false, false));
            entityLivingBaseIn.addPotionEffect(new PotionEffect(Potion.getPotionById(4), 120, amplifier, false, false));
            entityLivingBaseIn.addPotionEffect(new PotionEffect(Potion.getPotionById(18), 120, amplifier, false, false));
        }
        if (entityLivingBaseIn.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)){
            IPlayerCapabilities cap = entityLivingBaseIn.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            if (cap.getTimeStunted() < 1) {
                cap.fillMagan(0.2F);
            }
        }

        super.performEffect(entityLivingBaseIn, amplifier);
    }


}
