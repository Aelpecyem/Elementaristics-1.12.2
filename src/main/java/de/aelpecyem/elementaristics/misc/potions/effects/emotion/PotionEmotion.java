package de.aelpecyem.elementaristics.misc.potions.effects.emotion;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.misc.potions.effects.PotionBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import scala.collection.parallel.ParIterableLike;

import java.util.Collection;
import java.util.Set;

public class PotionEmotion extends PotionBase {
    public PotionEmotion(String name, int color, int index) {
        super(name, false, color, index);

        PotionInit.emotions.add(this);
        //  MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    public void effect(EntityLivingBase entity, IPlayerCapabilities cap, int amplifier) {

    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = entityLivingBaseIn.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            if (cap.getSoulId() != SoulInit.soulMana.getId())
                effect(entityLivingBaseIn, cap, amplifier);
        }


        PotionEffect[] effects = new PotionEffect[]{

        };

        effects = entityLivingBaseIn.getActivePotionEffects().toArray(effects);
        int emotionCount = 0;
        for (PotionEffect effect : effects) {
            if (effect.getPotion() instanceof PotionEmotion) {
                emotionCount++;
                if (emotionCount > 1) {
                    entityLivingBaseIn.removePotionEffect(this);
                    emotionCount--;
                }
            }
        }

        super.performEffect(entityLivingBaseIn, amplifier);
    }


}
