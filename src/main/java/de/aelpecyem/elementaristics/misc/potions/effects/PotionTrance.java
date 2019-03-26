package de.aelpecyem.elementaristics.misc.potions.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.MinecraftForge;

public class PotionTrance extends PotionBase {
    public PotionTrance() {
        super("trance", false, 3113431, 2);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }


    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        super.performEffect(entityLivingBaseIn, amplifier);
    }


}
