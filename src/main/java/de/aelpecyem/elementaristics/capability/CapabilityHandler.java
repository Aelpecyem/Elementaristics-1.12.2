package de.aelpecyem.elementaristics.capability;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityPedestal;
import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.souls.soulCaps.SoulCap;
import de.aelpecyem.elementaristics.capability.souls.soulCaps.SoulCaps;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.cap.CapabilitySync;
import de.aelpecyem.elementaristics.networking.pedestal.PacketUpdatePedestal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CapabilityHandler {

    public static final ResourceLocation PLAYER_CAP = new ResourceLocation(Elementaristics.MODID, "player_caps");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent event) {
        if (!(event.getObject() instanceof EntityPlayer)) {
            return;

        }
        EntityPlayer player = (EntityPlayer) event.getObject();
            event.addCapability(PLAYER_CAP, new PlayerCapProvider());
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        EntityPlayer player = event.getEntityPlayer();
        if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities caps = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            IPlayerCapabilities oldCaps = event.getOriginal().getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);

            caps.setSoulId(oldCaps.getSoulId());
            caps.setKnowsSoul(oldCaps.knowsSoul());
            caps.setMaxMagan(oldCaps.getMaxMagan());
            caps.setMaganRegenPerTick(oldCaps.getMaganRegenPerTick());
            caps.setMagan(caps.getMaxMagan());

        }
    }


    @SubscribeEvent
    public void applyBuffsOnSpawning(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent event) {

        if (event.player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = event.player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            EntityPlayer player = event.player;
            SoulCaps.getCapForSoul(SoulInit.getSoulFromId(cap.getSoulId())).buffsOnSpawning(player, cap);
        }
    }

    @SubscribeEvent
    public void onPlayerJumpEvent(LivingEvent.LivingJumpEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);

                SoulCaps.getCapForSoul(SoulInit.getSoulFromId(cap.getSoulId())).onJumpEvent(event, player, cap);

            }
        }
    }

    @SubscribeEvent
    public void onPlayerUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof EntityPlayerMP) {
            final EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
            final IPlayerCapabilities cap =  player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);



            PacketHandler.sendTo(player, new CapabilitySync(cap.getSoulId(), cap.getTimeStunted(), cap.knowsSoul(), cap.getMaxMagan(),
                    cap.getMagan(), cap.getMaganRegenPerTick(), cap.getPlayerAscensionStage()));
        }


    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player != null && event.player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = event.player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);


            //Stuff with mana stunting etc.etc.
            if (!(cap.getTimeStunted() > 0)) {
                cap.fillMagan(cap.getMaganRegenPerTick());
            } else {
                cap.stuntMagan(cap.getTimeStunted() - 1);
            }
            //...
            SoulCaps.getCapForSoul(SoulInit.getSoulFromId(cap.getSoulId())).onTickEvent(event, event.player, cap);


        }

    }


}

