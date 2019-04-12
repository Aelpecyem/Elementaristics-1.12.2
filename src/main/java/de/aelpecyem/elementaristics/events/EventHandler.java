package de.aelpecyem.elementaristics.events;

import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.player.SleepingLocationCheckEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import vazkii.patchouli.api.PatchouliAPI;

public class EventHandler {

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (event.player.getActivePotionEffects().contains(event.player.getActivePotionEffect(PotionInit.potionIntoxicated))) {
                if (event.player.getActivePotionEffect(PotionInit.potionIntoxicated).getAmplifier() == 0) {
                    if (!event.player.inventory.hasItemStack(PatchouliAPI.instance.getBookStack("elementaristics:liber_elementium"))) {
                        if (event.player.getActivePotionEffect(PotionInit.potionIntoxicated).getDuration() == 3000) {
                            if (!event.player.world.isRemote) {
                                event.player.sendStatusMessage(new TextComponentString(I18n.format("message.vision.1")), true);
                            }
                        }
                        if (event.player.getActivePotionEffect(PotionInit.potionIntoxicated).getDuration() == 2800) {
                            if (!event.player.world.isRemote) {
                                event.player.sendStatusMessage(new TextComponentString(I18n.format("message.vision.2")), true);
                            }
                        }
                        if (event.player.getActivePotionEffect(PotionInit.potionIntoxicated).getDuration() == 2600) {
                            if (!event.player.world.isRemote) {
                                event.player.sendStatusMessage(new TextComponentString(I18n.format("message.vision.3")), true);
                            }
                        }
                        if (event.player.getActivePotionEffect(PotionInit.potionIntoxicated).getDuration() == 2400) {
                            if (!event.player.world.isRemote) {
                                event.player.sendStatusMessage(new TextComponentString(I18n.format("message.vision.4")), true);
                            }
                            event.player.inventory.addItemStackToInventory(PatchouliAPI.instance.getBookStack("elementaristics:liber_elementium"));
                        }
                        }
                    }
                }
            }
    }
    @SubscribeEvent
    public void continueSleep(SleepingLocationCheckEvent event) {
        EntityPlayer entityPlayer = event.getEntityPlayer();
        if (entityPlayer.getActivePotionEffects().contains(entityPlayer.getActivePotionEffect(PotionInit.potionIntoxicated))) {
            event.setResult(Event.Result.ALLOW);
        }

    }
}
