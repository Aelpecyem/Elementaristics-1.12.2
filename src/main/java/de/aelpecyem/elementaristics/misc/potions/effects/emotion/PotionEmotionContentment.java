package de.aelpecyem.elementaristics.misc.potions.effects.emotion;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.PlayerCapProvider;
import de.aelpecyem.elementaristics.misc.potions.effects.PotionBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionEmotionContentment extends PotionEmotion {
    public PotionEmotionContentment() {
        super("contentment", 11384275, 3);
        //  MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void effect(EntityLivingBase entity, IPlayerCapabilities cap, int amplifier) {
        if (cap.getTimeStunted() < 1) {
            cap.fillMagan(0.1F * amplifier + 0.1F);
        }
        entity.addPotionEffect(new PotionEffect(Potion.getPotionById(1), 40, amplifier, false, false));
        entity.addPotionEffect(new PotionEffect(Potion.getPotionById(3), 40, amplifier, false, false));
        entity.addPotionEffect(new PotionEffect(Potion.getPotionById(8), 40, amplifier, false, false));
        entity.addPotionEffect(new PotionEffect(Potion.getPotionById(26), 40, amplifier, false, false));
        super.effect(entity, cap, amplifier);
    }


}
