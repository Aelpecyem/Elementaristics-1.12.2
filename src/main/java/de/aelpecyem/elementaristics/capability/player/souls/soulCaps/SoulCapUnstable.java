package de.aelpecyem.elementaristics.capability.player.souls.soulCaps;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

public class SoulCapUnstable extends SoulCap {
    public SoulCapUnstable() {
        super(SoulInit.soulUnstable);
        MinecraftForge.EVENT_BUS.register(this);
    }


    @Override
    public void buffsOnSpawning(EntityPlayer player, IPlayerCapabilities cap) {
        if (cap.knowsSoul()) {
            if (Loader.isModLoaded("thaumcraft")) {
                if (ThaumcraftCapabilities.getWarp(player).get(IPlayerWarp.EnumWarpType.PERMANENT) < 15)
                    ThaumcraftCapabilities.getWarp(player).add(IPlayerWarp.EnumWarpType.PERMANENT, 15);
            }
        }
        super.buffsOnSpawning(player, cap);
    }

    @SubscribeEvent
    public void onAttack(LivingAttackEvent event) {
        if (event.getEntityLiving().hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = event.getEntityLiving().getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            if (cap.getSoulId() == SoulInit.soulUnstable.getId()) {
                if (event.getSource() == Elementaristics.DAMAGE_PSYCHIC) {
                    event.getEntityLiving().attackEntityFrom(DamageSource.GENERIC, 2);
                }
            }
        }
    }
    @Override
    public void onTickEvent(TickEvent.PlayerTickEvent event, EntityPlayer player, IPlayerCapabilities cap) {
        super.onTickEvent(event, player, cap);
    }
}
