package de.aelpecyem.elementaristics.capability.player.souls.soulCaps;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SoulCapFire extends SoulCap {
    private static final AttributeModifier DAMAGE_MOD_KNOW = new AttributeModifier("elementaristics_damage_know", 1, 0);
    private static final AttributeModifier DAMAGE_MOD_STAGE1 = new AttributeModifier("elementaristics_damage_1", 1, 0);
    private static final AttributeModifier DAMAGE_MOD_STAGE3 = new AttributeModifier("elementaristics_damage__3", 1, 0);

    public SoulCapFire() {
        super(SoulInit.soulFire);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityHit(LivingAttackEvent event) {
        if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource().hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            if (event.getSource().getTrueSource().getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null).getSoulId() == SoulInit.soulFire.getId() &&
                    event.getSource().getTrueSource().getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null).getPlayerAscensionStage() >= 3) {
                if (event.getEntityLiving().world.rand.nextInt(10) == 1) {
                    event.getEntityLiving().setFire(3);
                }
            }
        }
        if (event.getEntityLiving().hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            if (event.getEntityLiving().getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null).getSoulId() == SoulInit.soulFire.getId()) {
                int reductionAmount = Math.max(2, 8 - event.getEntityLiving().getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null).getPlayerAscensionStage());
                if ((event.getSource() == DamageSource.IN_FIRE || event.getSource() == DamageSource.ON_FIRE) && event.getAmount() > reductionAmount) {
                    event.getEntityLiving().attackEntityFrom(event.getSource(), reductionAmount);
                    event.setCanceled(true);
                }
            }
        }
    }

    @Override
    public void normalize(EntityPlayer player, IPlayerCapabilities cap) {
        player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(DAMAGE_MOD_KNOW);
        player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(DAMAGE_MOD_STAGE1);
        player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(DAMAGE_MOD_STAGE3);

        super.normalize(player, cap);
    }

    @Override
    public void buffsOnSpawning(EntityPlayer player, IPlayerCapabilities cap) {
        if (cap.knowsSoul()) {
            if (!player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(DAMAGE_MOD_KNOW))
                player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(DAMAGE_MOD_KNOW);
        }
        if (cap.getPlayerAscensionStage() >= 1) {
            if (!player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(DAMAGE_MOD_STAGE1))
                player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(DAMAGE_MOD_STAGE1);
        }
        if (cap.getPlayerAscensionStage() >= 3) {
            if (!player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(DAMAGE_MOD_STAGE3))
                player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(DAMAGE_MOD_STAGE3);
        }
        super.buffsOnSpawning(player, cap);
    }
}
