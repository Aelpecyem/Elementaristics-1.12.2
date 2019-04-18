package de.aelpecyem.elementaristics.misc.potions.effects.emotion;

import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionEmotionEcstasy extends PotionEmotion {
    public PotionEmotionEcstasy() {
        super("ecstasy", 16711737, 6);
    }

    @Override
    public void effect(EntityLivingBase living, IPlayerCapabilities cap, int amplifier) {
        if (cap.getTimeStunted() < 1) {
            cap.fillMagan(0.2F * amplifier);
        }

        living.addPotionEffect(new PotionEffect(Potion.getPotionById(17), 80, 0, false, false));
        if (living.getEntityWorld().getTotalWorldTime() % 300 == 0) {
            living.heal(amplifier + 2);
        }
        super.effect(living, cap, amplifier);
    }
}
