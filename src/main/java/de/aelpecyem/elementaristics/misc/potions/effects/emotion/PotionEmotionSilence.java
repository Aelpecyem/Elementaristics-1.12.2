package de.aelpecyem.elementaristics.misc.potions.effects.emotion;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionEmotionSilence extends PotionEmotion {
    public PotionEmotionSilence() {
        super("silence", 10724007, 7);
    }

    @Override
    public void effect(EntityLivingBase living, IPlayerCapabilities cap, int amplifier) {
        cap.stuntMagan(20);
        living.addPotionEffect(new PotionEffect(Potion.getPotionById(11), 40, 1, false, false));
        living.addPotionEffect(new PotionEffect(Potion.getPotionById(5), 40, amplifier, false, false));
        super.effect(living, cap, amplifier);
    }
}
