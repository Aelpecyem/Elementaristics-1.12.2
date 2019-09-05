package de.aelpecyem.elementaristics.events;

import de.aelpecyem.elementaristics.blocks.base.BlockFlowerBase;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.config.Config;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.misc.potions.effects.emotion.PotionEmotion;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.player.PacketMessage;
import de.aelpecyem.elementaristics.util.CapabilityUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Iterator;

public class EmotionHandler {
    @SubscribeEvent
    public void stillSleeping(SleepingTimeCheckEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        if (event.getEntityPlayer().hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            if (cap.getPlayerAscensionStage() > 0) {
                if (!player.isPlayerFullyAsleep()) {
                    int sleepTimer = ObfuscationReflectionHelper.getPrivateValue(EntityPlayer.class, player, "field_71076_b", "sleepTimer");
                    if (sleepTimer > 19 && sleepTimer % 20 == 0) {
                        if (event.getEntityPlayer().getFoodStats().getFoodLevel() != 3) {
                            if (player.getHealth() < event.getEntityPlayer().getMaxHealth()) {
                                player.heal(6);
                                player.getFoodStats().setFoodLevel(event.getEntityPlayer().getFoodStats().getFoodLevel() - 1);
                            }
                            if (cap.getMagan() < cap.getMaxMagan()) {
                                cap.fillMagan(50);
                                player.getFoodStats().setFoodLevel(event.getEntityPlayer().getFoodStats().getFoodLevel() - 1);
                            }
                        }
                    }
                    event.setResult(Event.Result.ALLOW);
                } else {
                    if (!CapabilityUtil.hasEmotionActive(player)) {
                        performMoodAnalysis(player);
                    }

                    PotionEffect[] effects = new PotionEffect[]{
                    };
                    effects = event.getEntityPlayer().getActivePotionEffects().toArray(effects);
                    for (PotionEffect effect : effects) {
                        if (effect.getPotion().isBadEffect() && !(effect.getPotion() instanceof PotionEmotion)) {
                            event.getEntityPlayer().removePotionEffect(effect.getPotion());
                        }
                    }
                }
            }
        }

        if (player.getActivePotionEffects().contains(player.getActivePotionEffect(PotionInit.potionTrance)) && player.getActivePotionEffects().contains(player.getActivePotionEffect(PotionInit.ecstasy)) && player.getActivePotionEffect(PotionInit.ecstasy).getAmplifier() >= 2) {
            Potion potion = PotionInit.potionFocused;
            player.addPotionEffect(new PotionEffect(potion, 24000, 0, true, false));
            player.removePotionEffect(PotionInit.potionTrance);
            player.removePotionEffect(PotionInit.ecstasy);
            if (!player.world.isRemote) {
                PacketHandler.sendTo(player, new PacketMessage("message.meditation_to_contentment.name"));
            }
            return;
        }

        if (player.getActivePotionEffects().contains(player.getActivePotionEffect(PotionInit.potionTrance)) && player.isPlayerFullyAsleep()) {
            if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                if (event.getEntityPlayer().dimension != Config.mindDimensionId) {
                    if (!event.getEntityPlayer().world.isRemote) {
                        player.changeDimension(Config.mindDimensionId, new ITeleporter() {
                            @Override
                            public void placeEntity(World world, Entity entity, float yaw) {
                                entity.setPosition(entity.posX, 36, entity.posZ);
                            }
                        });
                    }
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
                    if (potion != null) {
                        break;
                    }
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
            player.addPotionEffect(new PotionEffect(potion, 24000, 0, true, false));
            player.removePotionEffect(PotionInit.potionTrance);
            player.removePotionEffect(PotionInit.ecstasy);
            player.sendStatusMessage(new TextComponentString(TextFormatting.GOLD + I18n.format("message.meditation_to_contentment.name")), false);

        }
    }
}
