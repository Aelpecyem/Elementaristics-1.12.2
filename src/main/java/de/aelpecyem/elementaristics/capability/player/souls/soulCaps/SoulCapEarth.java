package de.aelpecyem.elementaristics.capability.player.souls.soulCaps;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SoulCapEarth extends SoulCap {
    private static final AttributeModifier ARMOR_MOD_KNOW = new AttributeModifier("elementaristics_armor_know", 4, 0);
    private static final AttributeModifier ARMOR_MOD_STAGE1 = new AttributeModifier("elementaristics_armor_stage_1", 4, 0);
    private static final AttributeModifier ARMOR_MOD_STAGE3 = new AttributeModifier("elementaristics_armor_stage_3", 4, 0);
    private static final AttributeModifier ARMOR_MOD_STAGE5 = new AttributeModifier("elementaristics_armor_stage_5", 4, 0);

    private static final AttributeModifier DAMAGE_MOD_STAGE3 = new AttributeModifier("elementaristics_damage_stage_3", 1, 0);
    private static final AttributeModifier DAMAGE_MOD_STAGE5 = new AttributeModifier("elementaristics_damage_stage_5", 1, 0);

    public SoulCapEarth() {
        super(SoulInit.soulEarth);
    }


    @Override
    public void normalize(EntityPlayer player, IPlayerCapabilities cap) {
        player.getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(ARMOR_MOD_KNOW);
        player.getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(ARMOR_MOD_STAGE1);
        player.getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(ARMOR_MOD_STAGE3);
        player.getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(ARMOR_MOD_STAGE5);
        player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(DAMAGE_MOD_STAGE3);
        player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(DAMAGE_MOD_STAGE5);


        super.normalize(player, cap);
    }

    @Override
    public void buffsOnSpawning(EntityPlayer player, IPlayerCapabilities cap) {
        if (cap.knowsSoul()) {
            if (!player.getEntityAttribute(SharedMonsterAttributes.ARMOR).hasModifier(ARMOR_MOD_KNOW))
                player.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(ARMOR_MOD_KNOW);
        }
        if (cap.getPlayerAscensionStage() >= 1) {
            if (!player.getEntityAttribute(SharedMonsterAttributes.ARMOR).hasModifier(ARMOR_MOD_STAGE1))
                player.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(ARMOR_MOD_STAGE1);

        }
        if (cap.getPlayerAscensionStage() >= 3) {
            if (!player.getEntityAttribute(SharedMonsterAttributes.ARMOR).hasModifier(ARMOR_MOD_STAGE3))
                player.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(ARMOR_MOD_STAGE3);
            if (!player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(DAMAGE_MOD_STAGE3))
                player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(DAMAGE_MOD_STAGE3);

        }
        if (cap.getPlayerAscensionStage() >= 5) {
            if (!player.getEntityAttribute(SharedMonsterAttributes.ARMOR).hasModifier(ARMOR_MOD_STAGE5))
                player.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(ARMOR_MOD_STAGE5);
            if (!player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(DAMAGE_MOD_STAGE5))
                player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(DAMAGE_MOD_STAGE5);
        }
        super.buffsOnSpawning(player, cap);
    }
}
