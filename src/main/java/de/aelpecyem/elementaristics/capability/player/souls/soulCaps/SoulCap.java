package de.aelpecyem.elementaristics.capability.player.souls.soulCaps;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SoulCap {
    public Soul soul;

    public SoulCap(Soul soul) {
        this.soul = soul;
        SoulCaps.addSoulCapToSoul(this, soul);
    }

    public void normalize(EntityPlayer player, IPlayerCapabilities cap) {

    }

    public void buffsOnSpawning(EntityPlayer player, IPlayerCapabilities cap) {
        if (cap.knowsSoul()) {
            if (cap.getPlayerAscensionStage() >= 1) {
                if (cap.getMaxMagan() < 110) {
                    cap.setMaxMagan(110);
                }
            }
            if (cap.getPlayerAscensionStage() >= 3) {
                if (cap.getMaxMagan() < 120) {
                    cap.setMaxMagan(120);
                }
            }
            if (cap.getPlayerAscensionStage() >= 5) {
                if (cap.getMaxMagan() < 150) {
                    cap.setMaxMagan(150);
                }
            }
        }
    }

    public void onTickEvent(TickEvent.PlayerTickEvent event, EntityPlayer player, IPlayerCapabilities cap) {

    }

    public void onJumpEvent(LivingEvent.LivingJumpEvent event, EntityPlayer player, IPlayerCapabilities cap) {

    }

    public void onHit(LivingHurtEvent event, EntityPlayer player, IPlayerCapabilities cap) {

    }

    public void onHitting(LivingHurtEvent event, EntityPlayer attacker, IPlayerCapabilities cap) {

    }
}
