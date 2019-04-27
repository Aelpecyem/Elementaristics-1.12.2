package de.aelpecyem.elementaristics.misc.potions.effects.emotion;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionEmotionFear extends PotionEmotion {
    public PotionEmotionFear() {
        super("fear", 4474180, 8);
    }

    @Override
    public void effect(EntityLivingBase living, IPlayerCapabilities cap, int amplifier) {
        living.addPotionEffect(new PotionEffect(Potion.getPotionById(8), 40, amplifier + 1, false, false));
        living.addPotionEffect(new PotionEffect(Potion.getPotionById(1), 40, amplifier, false, false));
        super.effect(living, cap, amplifier);
    }
}
