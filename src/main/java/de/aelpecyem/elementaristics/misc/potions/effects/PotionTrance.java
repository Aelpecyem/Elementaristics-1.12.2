package de.aelpecyem.elementaristics.misc.potions.effects;

import de.aelpecyem.elementaristics.Elementaristics;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.MinecraftForge;

public class PotionTrance extends PotionBase {
    public PotionTrance() {
        super("trance", false, 3113431, 2);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }


    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        Elementaristics.proxy.generateGenericParticles(entityLivingBaseIn, 3112418, 0.5F, 20, 0, false, true);
        super.performEffect(entityLivingBaseIn, amplifier);
    }


}
