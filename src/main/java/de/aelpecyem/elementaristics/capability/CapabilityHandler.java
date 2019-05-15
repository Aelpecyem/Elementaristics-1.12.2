package de.aelpecyem.elementaristics.capability;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.player.souls.soulCaps.SoulCaps;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.cap.CapabilitySync;
import de.aelpecyem.elementaristics.networking.player.PacketPressSpellKey;
import de.aelpecyem.elementaristics.util.Keybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.collection.parallel.ParIterableLike;

public class CapabilityHandler {

    public static final ResourceLocation PLAYER_CAP = new ResourceLocation(Elementaristics.MODID, "player_caps");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent event) {
        if (event.getObject() instanceof EntityPlayer) {
            // EntityPlayer player = (EntityPlayer) event.getObject();
            event.addCapability(PLAYER_CAP, new PlayerCapProvider());
        }

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
            caps.setMagan(oldCaps.getMaxMagan());
            caps.setPlayerAscensionStage(oldCaps.getPlayerAscensionStage());
            caps.setCultistCount(oldCaps.getCultistCount());
            caps.setAscensionRoute(oldCaps.getAscensionRoute());
            caps.setSpellSlot(oldCaps.getSpellSlot());
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
            final IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);

            PacketHandler.sendTo(player, new CapabilitySync(cap));
        }

    }

    @SubscribeEvent
    public void onKeyInput(TickEvent.PlayerTickEvent event) {
        if (event.player.world.isRemote) {
            if (event.player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                if (Keybinds.spellUp.isPressed()) {
                    PacketHandler.sendToServer(new PacketPressSpellKey(event.player, true));
                } else if (Keybinds.spellDown.isPressed()) {
                    PacketHandler.sendToServer(new PacketPressSpellKey(event.player, false));
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player != null && event.player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = event.player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            if (!(cap.getTimeStunted() > 0)) {
                cap.fillMagan(cap.getMaganRegenPerTick());
            } else {
                cap.stuntMagan(cap.getTimeStunted() - 1);
            }
            if (event.player.capabilities.isCreativeMode) {
                cap.setMagan(cap.getMaxMagan());
            }
            //...
            SoulCaps.getCapForSoul(SoulInit.getSoulFromId(cap.getSoulId())).onTickEvent(event, event.player, cap);
        }

    }

    @SubscribeEvent
    public void onHit(LivingHurtEvent event) {
        if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource().hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = event.getSource().getTrueSource().getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            SoulCaps.getCapForSoul(SoulInit.getSoulFromId(cap.getSoulId())).onHitting(event, (EntityPlayer) event.getSource().getTrueSource(), cap);

        }

        if (event.getEntityLiving().hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            if (event.getEntityLiving().getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null).getSoulId() == SoulInit.soulFire.getId()) {
                IPlayerCapabilities cap = event.getEntityLiving().getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                SoulCaps.getCapForSoul(SoulInit.getSoulFromId(cap.getSoulId())).onHit(event, (EntityPlayer) event.getEntityLiving(), cap);

            }
        }
    }


}

