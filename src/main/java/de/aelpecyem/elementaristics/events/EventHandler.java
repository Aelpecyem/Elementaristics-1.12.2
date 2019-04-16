package de.aelpecyem.elementaristics.events;

import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import net.minecraft.block.BlockBed;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.SleepingLocationCheckEvent;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import scala.collection.parallel.ParIterableLike;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.entities.EntityFluxRift;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class EventHandler {

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (event.player.getActivePotionEffects().contains(event.player.getActivePotionEffect(PotionInit.potionIntoxicated))) {
                if (event.player.getActivePotionEffect(PotionInit.potionIntoxicated).getAmplifier() == 1) {
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
    public void stillSleeping(SleepingTimeCheckEvent event) {
        if (event.getEntityPlayer().hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = event.getEntityPlayer().getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            if (cap.getPlayerAscensionStage() > 0) {
                if (!event.getEntityPlayer().isPlayerFullyAsleep()) {
                    System.out.println(event.getEntityPlayer().getSleepTimer());
                    if (event.getEntityPlayer().getSleepTimer() > 19 && event.getEntityPlayer().getSleepTimer() % 20 == 0) {
                        if (event.getEntityPlayer().getFoodStats().getFoodLevel() != 0)
                            event.getEntityPlayer().getFoodStats().setFoodLevel(event.getEntityPlayer().getFoodStats().getFoodLevel() - 1);

                        event.getEntityPlayer().heal(6);
                        cap.fillMagan(50);
                    }
                    if (event.getEntityPlayer().getSleepTimer() > 90) {

                    }
                    event.setResult(Event.Result.ALLOW);
                } else {
                    performMoodAnalysis(event.getEntityPlayer());

                    PotionEffect[] effects = new PotionEffect[]{

                    };
                    effects = event.getEntityPlayer().getActivePotionEffects().toArray(effects);
                    for (PotionEffect effect : effects) {
                        if (effect.getPotion().isBadEffect()) {
                            event.getEntityPlayer().removePotionEffect(effect.getPotion());
                        }
                    }
                }
            }
        }
    }

    public void performMoodAnalysis(EntityPlayer player) {
        if (player.getActivePotionEffects().contains(player.getActivePotionEffect(PotionInit.potionIntoxicated))) {
            Potion potion = PotionInit.emotions.get(player.world.rand.nextInt(PotionInit.emotions.size()));
            player.addPotionEffect(new PotionEffect(potion, 16000, player.getActivePotionEffect(PotionInit.potionIntoxicated).getAmplifier(), true, false));
            player.removePotionEffect(PotionInit.potionIntoxicated);
        }
    }

   /* @SubscribeEvent
    public void canWakeUp(TickEvent.PlayerTickEvent event){
       if (event.player.isPlayerSleeping()){
       }
    }*/

}
