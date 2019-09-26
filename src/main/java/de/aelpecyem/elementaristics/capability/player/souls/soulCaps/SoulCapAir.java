package de.aelpecyem.elementaristics.capability.player.souls.soulCaps;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SoulCapAir extends SoulCap {
    private static final AttributeModifier HITSPEED_MOD_KNOW = new AttributeModifier("elementaristics_hit_speed_know", 0.03, 0);
    private static final AttributeModifier HITSPEED_MOD_STAGE1 = new AttributeModifier("elementaristics_hit_speed_1", 0.07, 0);
    private static final AttributeModifier HITSPEED_MOD_STAGE3 = new AttributeModifier("elementaristics_hit_speed_3", 0.03, 0);
    private static final AttributeModifier HITSPEED_MOD_STAGE5 = new AttributeModifier("elementaristics_hit_speed_5", 0.07, 0);

    private static final AttributeModifier SPEED_MOD_KNOW = new AttributeModifier("elementaristics_speed_know", 0.02, 0);
    private static final AttributeModifier SPEED_MOD_STAGE1 = new AttributeModifier("elementaristics_speed_1", 0.025, 0);
    private static final AttributeModifier SPEED_MOD_STAGE3 = new AttributeModifier("elementaristics_speed_3", 0.03, 0);
    private static final AttributeModifier SPEED_MOD_STAGE5 = new AttributeModifier("elementaristics_speed_5", 0.04, 0);

    public SoulCapAir() {
        super(SoulInit.soulAir);
    }

    @Override
    public void onJumpEvent(LivingEvent.LivingJumpEvent event, EntityPlayer player, IPlayerCapabilities cap) {
        if (cap.knowsSoul()) {
            player.motionY *= 1.2;
            if (cap.getPlayerAscensionStage() == 1) {
                player.motionY *= 1.4;
            } else if (cap.getPlayerAscensionStage() == 3) {
                player.motionY *= 1.6;
            } else if (cap.getPlayerAscensionStage() >= 5) {
                player.motionY *= 1.8;
            }
        }
        super.onJumpEvent(event, player, cap);
    }

    @Override
    public void normalize(EntityPlayer player, IPlayerCapabilities cap) {
        player.stepHeight = 0.5F;
        player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeModifier(HITSPEED_MOD_KNOW);
        player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeModifier(HITSPEED_MOD_STAGE1);
        player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeModifier(HITSPEED_MOD_STAGE3);
        player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeModifier(HITSPEED_MOD_STAGE5);

        player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(SPEED_MOD_KNOW);
        player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(SPEED_MOD_STAGE1);
        player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(SPEED_MOD_STAGE3);
        player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(SPEED_MOD_STAGE5);

        super.normalize(player, cap);
    }
    @Override
    public void buffsOnSpawning(EntityPlayer player, IPlayerCapabilities cap) {
        if (cap.knowsSoul()) {
            if (!player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(SPEED_MOD_KNOW))
                player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(SPEED_MOD_KNOW);
            if (!player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).hasModifier(HITSPEED_MOD_KNOW))
                player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(HITSPEED_MOD_KNOW);
        }
        if (cap.getPlayerAscensionStage() >= 1) {
            if (!player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(SPEED_MOD_STAGE1))
                player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(SPEED_MOD_STAGE1);
            if (!player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).hasModifier(HITSPEED_MOD_STAGE1))
                player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(HITSPEED_MOD_STAGE1);
        }
        if (cap.getPlayerAscensionStage() >= 3) {
            if (!player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(SPEED_MOD_STAGE3))
                player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(SPEED_MOD_STAGE3);
            if (!player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).hasModifier(HITSPEED_MOD_STAGE3))
                player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(HITSPEED_MOD_STAGE3);
        }
        if (cap.getPlayerAscensionStage() >= 5) {
            if (!player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(SPEED_MOD_STAGE5))
                player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(SPEED_MOD_STAGE5);
            if (!player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).hasModifier(HITSPEED_MOD_STAGE5))
                player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(HITSPEED_MOD_STAGE5);
        }
        super.buffsOnSpawning(player, cap);
    }

    @Override
    public void onTickEvent(TickEvent.PlayerTickEvent event, EntityPlayer player, IPlayerCapabilities cap) {
        if (cap.knowsSoul()) {
            player.jumpMovementFactor = 0.05F;
            player.fallDistance *= 0.93F - ((float) cap.getPlayerAscensionStage() / 100F);
            player.stepHeight = 1;
        }
        super.onTickEvent(event, player, cap);
    }
}
