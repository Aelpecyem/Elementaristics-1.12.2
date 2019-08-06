package de.aelpecyem.elementaristics.misc.potions.effects;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.player.PacketMessage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class PotionFatherBlessing extends PotionBase {
    public PotionFatherBlessing() {
        super("fatherblessing", false, 3342422, 16);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }


    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn instanceof EntityPlayer) {
            if (entityLivingBaseIn.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)){
                entityLivingBaseIn.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null).fillMagan(0.5F + 0.5F * amplifier);
            }
            entityLivingBaseIn.addPotionEffect(new PotionEffect(MobEffects.LUCK, 210, 1, false, false));
            entityLivingBaseIn.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 205, 0, false, false));
            entityLivingBaseIn.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 201, 0, false, false));
            super.performEffect(entityLivingBaseIn, amplifier);
        }
    }
}
