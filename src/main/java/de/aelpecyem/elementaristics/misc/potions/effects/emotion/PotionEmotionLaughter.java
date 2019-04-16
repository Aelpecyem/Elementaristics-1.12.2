package de.aelpecyem.elementaristics.misc.potions.effects.emotion;

import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionEmotionLaughter extends PotionEmotion {
    public PotionEmotionLaughter() {
        super("laughter", 16776960, 5);
    }

    @Override
    public void effect(EntityLivingBase living, IPlayerCapabilities cap, int amplifier) {
        if (cap.getTimeStunted() < 1) {
            cap.fillMagan(0.3F * amplifier + 0.5F);
        }
        living.addPotionEffect(new PotionEffect(Potion.getPotionById(16), 40, 0, false, false));

        super.effect(living, cap, amplifier);
    }
}
