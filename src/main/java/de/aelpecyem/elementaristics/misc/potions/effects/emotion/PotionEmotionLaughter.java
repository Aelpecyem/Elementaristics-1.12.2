package de.aelpecyem.elementaristics.misc.potions.effects.emotion;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import net.minecraft.entity.EntityLivingBase;

public class PotionEmotionLaughter extends PotionEmotion {
    public PotionEmotionLaughter() {
        super("laughter", 16776960, 5);
    }

    @Override
    public void effect(EntityLivingBase living, IPlayerCapabilities cap, int amplifier) {
        if (cap.getTimeStunted() < 1) {
            cap.fillMagan(0.3F * amplifier + 0.5F);
        }
        if (living.world.getTotalWorldTime() % 600 == 0) {
            if (living.getAbsorptionAmount() < 2)
                living.setAbsorptionAmount(2);
        }
        super.effect(living, cap, amplifier);
    }
}
