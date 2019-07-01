package de.aelpecyem.elementaristics.misc.poisons;

import de.aelpecyem.elementaristics.items.base.burnable.ItemPoisonBase;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;
import java.util.Map;

public class PoisonInit {
    public static Map<Integer, PoisonEffectBase> poisons = new HashMap<>();

    public static PoisonEffectBase poisonGlassblood;
    public static PoisonEffectBase poisonWintersBreath;
    public static PoisonEffectBase poisonSandthroat;

    public static void init() {
        poisonGlassblood = new PoisonGlassblood();
        poisonWintersBreath = new PoisonWintersBreath();
        poisonSandthroat = new PoisonSandthroat();
    }

    public static void performDelayedEffect(EntityLivingBase entity, PoisonEffectBase poison, int ticksDelayed){
        entity.getEntityData().setInteger(ItemPoisonBase.POISON_TAG, poison.getId());
        if (entity.getEntityData().hasKey("poison_ticks_left") && entity.getEntityData().getInteger("poison_ticks_left") > 100) {
            entity.getEntityData().setInteger("poison_ticks_left", ticksDelayed - 100);
        }else if (!entity.getEntityData().hasKey("poison_ticks_left") || entity.getEntityData().getInteger("poison_ticks_left") < 0){
            entity.getEntityData().setInteger("poison_ticks_left", entity.getEntityData().getInteger("poison_ticks_left") - 200);
        }
    }
}
