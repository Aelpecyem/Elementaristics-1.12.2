package de.aelpecyem.elementaristics.events;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.aelpecyem.elementaristics.blocks.base.BlockFlowerBase;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.config.Config;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.AdvancementCommand;
import net.minecraft.command.CommandException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.Iterator;

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
                    if (event.getEntityPlayer().getSleepTimer() > 19 && event.getEntityPlayer().getSleepTimer() % 20 == 0) {
                        if (event.getEntityPlayer().getFoodStats().getFoodLevel() != 3) {
                            if (event.getEntityPlayer().getHealth() < event.getEntityPlayer().getMaxHealth()) {
                                event.getEntityPlayer().heal(6);
                                event.getEntityPlayer().getFoodStats().setFoodLevel(event.getEntityPlayer().getFoodStats().getFoodLevel() - 1);
                            }
                            if (cap.getMagan() < cap.getMaxMagan()) {
                                cap.fillMagan(50);
                                event.getEntityPlayer().getFoodStats().setFoodLevel(event.getEntityPlayer().getFoodStats().getFoodLevel() - 1);
                            }
                        }
                    }
                 /*   if (event.getEntityPlayer().getSleepTimer() > 90) {

                    }*/
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

        if (event.getEntityPlayer().getActivePotionEffects().contains(event.getEntityPlayer().getActivePotionEffect(PotionInit.potionTrance)) && event.getEntityPlayer().isPlayerFullyAsleep()) {
            if (event.getEntityPlayer().hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                IPlayerCapabilities cap = event.getEntityPlayer().getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                if (cap.getCultistCount() > 0) {
                    if (event.getEntityPlayer().dimension != Config.mindDimensionId) {
                        if (!event.getEntityPlayer().world.isRemote) {
                            event.getEntityPlayer().changeDimension(Config.mindDimensionId, new ITeleporter() {
                                @Override
                                public void placeEntity(World world, Entity entity, float yaw) {
                                    entity.setPosition(entity.posX, 36, entity.posZ);
                                }
                            });
                        }
                    }
                } else {
                    event.getEntityPlayer().sendStatusMessage(new TextComponentString(ChatFormatting.RED + I18n.format("message.no_cultists_secured.name")), false);

                }
            }
        }
    }

    public void performMoodAnalysis(EntityPlayer player) {
        if (player.getActivePotionEffects().contains(player.getActivePotionEffect(PotionInit.potionIntoxicated))) {
            World world = player.getEntityWorld();
            Potion potion = null;
            Iterable<BlockPos> blockPos = BlockPos.getAllInBox(Math.round((float) player.posX) - 10, Math.round((float) player.posY) - 5, Math.round((float) player.posZ) - 10, Math.round((float) player.posX) + 10, Math.round((float) player.posY) + 8, Math.round((float) player.posZ) + 10);
            Iterator iterator = blockPos.iterator();
            while (iterator.hasNext()) {
                BlockPos pos = (BlockPos) iterator.next();
                if (world.getBlockState(pos).getBlock() instanceof BlockFlowerBase) {
                    potion = world.rand.nextBoolean() ? ((BlockFlowerBase) world.getBlockState(pos).getBlock()).getEmotion() : null;
                }
            }
            if (potion == null)
                potion = PotionInit.emotions.get(player.world.rand.nextInt(PotionInit.emotions.size()));
            player.addPotionEffect(new PotionEffect(potion, 16000, player.getActivePotionEffect(PotionInit.potionIntoxicated).getAmplifier(), true, false));
            player.removePotionEffect(PotionInit.potionIntoxicated);
        }
        //to start meditation step
        if (player.getActivePotionEffects().contains(player.getActivePotionEffect(PotionInit.potionTrance)) && player.getActivePotionEffects().contains(player.getActivePotionEffect(PotionInit.ecstasy)) && player.getActivePotionEffect(PotionInit.ecstasy).getAmplifier() >= 2) {
            Potion potion = PotionInit.potionFocused;
            player.addPotionEffect(new PotionEffect(potion, 24000, 0, true, true));
            player.removePotionEffect(PotionInit.potionTrance);
            player.removePotionEffect(PotionInit.ecstasy);
            player.sendStatusMessage(new TextComponentString(TextFormatting.GOLD + I18n.format("message.meditation_to_contentment.name")), false);

        }
    }


   /* @SubscribeEvent
    public void canWakeUp(TickEvent.PlayerTickEvent event){
       if (event.player.isPlayerSleeping()){
       }
    }*/

}
