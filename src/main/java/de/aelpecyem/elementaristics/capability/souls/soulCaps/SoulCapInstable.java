package de.aelpecyem.elementaristics.capability.souls.soulCaps;

import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

public class SoulCapInstable extends SoulCap {
    public SoulCapInstable() {

        super(SoulInit.soulInstable);
    }


    @Override
    public void buffsOnSpawning(EntityPlayer player, IPlayerCapabilities cap) {
       if (cap.knowsSoul()) {
           if(Loader.isModLoaded("thaumcraft")){
               if (ThaumcraftCapabilities.getWarp(player).get(IPlayerWarp.EnumWarpType.PERMANENT) < 15)
               ThaumcraftCapabilities.getWarp(player).add(IPlayerWarp.EnumWarpType.PERMANENT, 15);
           }
       }
        super.buffsOnSpawning(player, cap);
    }

    @Override
    public void onTickEvent(TickEvent.PlayerTickEvent event, EntityPlayer player, IPlayerCapabilities cap) {
        if (cap.knowsSoul()){
            if (Loader.isModLoaded("thaumcraft")){

            }
            if (player.world.rand.nextInt(1000) == 9){
                player.addExperience(2);
            }
        }
        super.onTickEvent(event, player, cap);
    }
}
