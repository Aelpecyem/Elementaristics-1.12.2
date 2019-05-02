package de.aelpecyem.elementaristics.capability.player.souls.soulCaps;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class SoulCapBalanced extends SoulCap {
    private static final AttributeModifier HEALTH_KNOW = new AttributeModifier("elementaristics_health_know", 4, 0);
    private static final AttributeModifier DAMAGE_STAGE1 = new AttributeModifier("elementaristics_damage_stage_1", 1, 0);
    private static final AttributeModifier SPEED_STAGE2 = new AttributeModifier("elementaristics_speed_stage_2", 0.02, 0);
    private static final AttributeModifier ARMOR_STAGE3 = new AttributeModifier("elementaristics_armor_stage_3", 4, 0);
    private static final AttributeModifier TOUGHNESS_STAGE4 = new AttributeModifier("elementaristics_armor_toughness_stage_4", 4, 0);

    private static final AttributeModifier HEALTH_STAGE5 = new AttributeModifier("elementaristics_health_stage_5", 4, 0);
    private static final AttributeModifier DAMAGE_STAGE5 = new AttributeModifier("elementaristics_damage_stage_5", 2, 0);
    private static final AttributeModifier SPEED_STAGE5 = new AttributeModifier("elementaristics_speed_stage_5", 0.03, 0);
    private static final AttributeModifier ARMOR_STAGE5 = new AttributeModifier("elementaristics_armor_stage_5", 4, 0);


    public SoulCapBalanced() {
        super(SoulInit.soulBalanced);
    }


    @Override
    public void normalize(EntityPlayer player, IPlayerCapabilities cap) {
        player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(HEALTH_KNOW);
        player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(DAMAGE_STAGE1);
        player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(SPEED_STAGE2);
        player.getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(ARMOR_STAGE3);
        player.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).removeModifier(TOUGHNESS_STAGE4);

        player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(HEALTH_STAGE5);
        player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(DAMAGE_STAGE5);
        player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(SPEED_STAGE5);
        player.getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(ARMOR_STAGE5);

        super.normalize(player, cap);
    }

    @Override
    public void buffsOnSpawning(EntityPlayer player, IPlayerCapabilities cap) {
        if (cap.knowsSoul()) {
            if (!player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(HEALTH_KNOW)) {
                player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(HEALTH_KNOW);
            }
            if (cap.getPlayerAscensionStage() >= 1) {
                if (!player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(DAMAGE_STAGE1)) {
                    player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(DAMAGE_STAGE1);
                }
            }
            if (cap.getPlayerAscensionStage() >= 2) {
                if (!player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(SPEED_STAGE2)) {
                    player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(SPEED_STAGE2);
                }
            }
            if (cap.getPlayerAscensionStage() >= 3) {
                if (!player.getEntityAttribute(SharedMonsterAttributes.ARMOR).hasModifier(ARMOR_STAGE3)) {
                    player.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(ARMOR_STAGE3);
                }
            }
            if (cap.getPlayerAscensionStage() >= 4) {
                if (!player.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).hasModifier(TOUGHNESS_STAGE4)) {
                    player.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).applyModifier(TOUGHNESS_STAGE4);
                }
            }
            if (cap.getPlayerAscensionStage() >= 5) {
                if (!player.getEntityAttribute(SharedMonsterAttributes.ARMOR).hasModifier(ARMOR_STAGE5)) {
                    player.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(ARMOR_STAGE5);
                }
                if (!player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(HEALTH_STAGE5)) {
                    player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(HEALTH_STAGE5);
                }
                if (!player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(DAMAGE_STAGE5)) {
                    player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(DAMAGE_STAGE5);
                }
                if (!player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(SPEED_STAGE5)) {
                    player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(SPEED_STAGE5);
                }
            }
        }
        super.buffsOnSpawning(player, cap);
    }
}
