package de.aelpecyem.elementaristics.capability.player.souls.soulCaps;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SoulCapWater extends SoulCap {
    private static final AttributeModifier HEALTH_MOD_KNOW = new AttributeModifier("elementaristics_health_know", 4, 0);
    private static final AttributeModifier HEALTH_MOD_STAGE1 = new AttributeModifier("elementaristics_health_1", 4, 0);
    private static final AttributeModifier HEALTH_MOD_STAGE3 = new AttributeModifier("elementaristics_health_3", 6, 0);
    private static final AttributeModifier HEALTH_MOD_STAGE5 = new AttributeModifier("elementaristics_health_5", 6, 0);

    public SoulCapWater() {
        super(SoulInit.soulWater);
    }


    @Override
    public void normalize(EntityPlayer player, IPlayerCapabilities cap) {
        player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(HEALTH_MOD_KNOW);
        player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(HEALTH_MOD_STAGE1);
        player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(HEALTH_MOD_STAGE3);
        player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(HEALTH_MOD_STAGE5);
        super.normalize(player, cap);
    }

    @Override
    public void buffsOnSpawning(EntityPlayer player, IPlayerCapabilities cap) {
        if (cap.knowsSoul()) {
            if (!player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(HEALTH_MOD_KNOW))
                player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(HEALTH_MOD_KNOW);
        }
        if (cap.getPlayerAscensionStage() >= 1) {
            if (!player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(HEALTH_MOD_STAGE1))
                player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(HEALTH_MOD_STAGE1);
        }
        if (cap.getPlayerAscensionStage() >= 3) {
            if (!player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(HEALTH_MOD_STAGE3))
                player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(HEALTH_MOD_STAGE3);
        }
        if (cap.getPlayerAscensionStage() >= 5) {
            if (!player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(HEALTH_MOD_STAGE5))
                player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(HEALTH_MOD_STAGE5);
        }
        super.buffsOnSpawning(player, cap);
    }

    @Override
    public void onTickEvent(TickEvent.PlayerTickEvent event, EntityPlayer player, IPlayerCapabilities cap) {
        if (cap.getPlayerAscensionStage() > 0) {
            if (player.world.rand.nextInt(player.isInWater() ? 1000 : 3000) == 1) {
                player.heal(cap.getPlayerAscensionStage() < 5 ? cap.getPlayerAscensionStage() * 2 : 10);
            }
        }

        super.onTickEvent(event, player, cap);
    }
}
