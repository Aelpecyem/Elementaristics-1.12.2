package de.aelpecyem.elementaristics.misc.potions.effects.emotion;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionEmotionDread extends PotionEmotion {
    public PotionEmotionDread() {
        super("dread", 2038323, 4);
    }


    public void effect(EntityLivingBase living, IPlayerCapabilities cap, int amplifier) {
        if (cap.getTimeStunted() < 1) {
            cap.fillMagan(0.1F * amplifier + 0.2F);
        }
        living.addPotionEffect(new PotionEffect(Potion.getPotionById(11), 40, 0, false, false));
        living.addPotionEffect(new PotionEffect(Potion.getPotionById(18), 40, 0, false, false));
        living.addPotionEffect(new PotionEffect(Potion.getPotionById(27), 40, 0, false, false));
    }

}
