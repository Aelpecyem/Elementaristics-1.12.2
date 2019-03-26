package de.aelpecyem.elementaristics.capability.souls.soulCaps;

import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.souls.Soul;
import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SoulCapAir extends SoulCap {
    public SoulCapAir() {
        super(SoulInit.soulAir);
    }

    @Override
    public void onJumpEvent(LivingEvent.LivingJumpEvent event, EntityPlayer player, IPlayerCapabilities cap) {
        if (cap.knowsSoul()) {
            player.motionY *= 1.4;
        }
        super.onJumpEvent(event, player, cap);
    }

    @Override
    public void buffsOnSpawning(EntityPlayer player, IPlayerCapabilities cap) {
       if (cap.knowsSoul()) {
           if (player.capabilities.getWalkSpeed() < 0.14F) {
               player.capabilities.setPlayerWalkSpeed(0.14F);
           }
       }
        super.buffsOnSpawning(player, cap);
    }

    @Override
    public void onTickEvent(TickEvent.PlayerTickEvent event, EntityPlayer player, IPlayerCapabilities cap) {
        if (cap.knowsSoul()){
            event.player.jumpMovementFactor = 0.06F;
        }
        super.onTickEvent(event, player, cap);
    }
}
