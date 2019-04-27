package de.aelpecyem.elementaristics.capability.player.souls.soulCaps;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
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

    }

    public void onTickEvent(TickEvent.PlayerTickEvent event, EntityPlayer player, IPlayerCapabilities cap) {

    }

    public void onJumpEvent(LivingEvent.LivingJumpEvent event, EntityPlayer player, IPlayerCapabilities cap) {

    }
}
