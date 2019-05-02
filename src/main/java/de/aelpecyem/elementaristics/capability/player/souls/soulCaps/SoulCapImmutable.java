package de.aelpecyem.elementaristics.capability.player.souls.soulCaps;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class SoulCapImmutable extends SoulCap {
    private static final AttributeModifier ARMOR_TOUGHNESS_STAGE3 = new AttributeModifier("elementaristics_armor_toughness_stage_3", 4, 0);
    private static final AttributeModifier ARMOR_TOUGHNESS_STAGE5 = new AttributeModifier("elementaristics_armor_toughness_stage_5", 4, 0);


    public SoulCapImmutable() {
        super(SoulInit.soulImmutable);
    }

    @Override
    public void onHitting(LivingHurtEvent event, EntityPlayer attacker, IPlayerCapabilities cap) {
        if (cap.knowsSoul()) {
            if (event.getEntityLiving().hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                IPlayerCapabilities capHurt = event.getEntityLiving().getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                capHurt.drainMagan(5 * (1 + cap.getPlayerAscensionStage()));

            }
        }
        super.onHitting(event, attacker, cap);
    }

    @Override
    public void onHit(LivingHurtEvent event, EntityPlayer player, IPlayerCapabilities cap) {
        if (cap.knowsSoul()) {
            if (event.getSource() == DamageSource.MAGIC && event.getAmount() > 8 - (cap.getPlayerAscensionStage() < 5 ? cap.getPlayerAscensionStage() : 6)) {
                player.attackEntityFrom(DamageSource.MAGIC, event.getAmount() - (cap.getPlayerAscensionStage() < 5 ? cap.getPlayerAscensionStage() : 6));
                event.setCanceled(true);
            }
        }
        super.onHit(event, player, cap);
    }

    @Override
    public void normalize(EntityPlayer player, IPlayerCapabilities cap) {
        player.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).removeModifier(ARMOR_TOUGHNESS_STAGE3);
        player.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).removeModifier(ARMOR_TOUGHNESS_STAGE5);
        super.normalize(player, cap);
    }

    @Override
    public void buffsOnSpawning(EntityPlayer player, IPlayerCapabilities cap) {
        if (cap.getPlayerAscensionStage() >= 3) {
            if (!player.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).hasModifier(ARMOR_TOUGHNESS_STAGE3)) {
                player.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).applyModifier(ARMOR_TOUGHNESS_STAGE3);
            }
        }
        if (cap.getPlayerAscensionStage() >= 5) {
            if (!player.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).hasModifier(ARMOR_TOUGHNESS_STAGE5)) {
                player.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).applyModifier(ARMOR_TOUGHNESS_STAGE5);
            }
        }

        if (cap.knowsSoul()) {
            if (cap.getMaxMagan() < 95) {
                cap.setMaxMagan(95);
            }
            if (cap.getPlayerAscensionStage() >= 1) {
                if (cap.getMaxMagan() < 100) {
                    cap.setMaxMagan(100);
                }
            }
            if (cap.getPlayerAscensionStage() >= 3) {
                if (cap.getMaxMagan() < 110) {
                    cap.setMaxMagan(110);
                }
            }
            if (cap.getPlayerAscensionStage() >= 5) {
                if (cap.getMaxMagan() < 120) {
                    cap.setMaxMagan(120);
                }
            }
        }
    }
}
