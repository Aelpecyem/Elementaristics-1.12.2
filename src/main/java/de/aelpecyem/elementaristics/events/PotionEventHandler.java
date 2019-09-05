package de.aelpecyem.elementaristics.events;

import de.aelpecyem.elementaristics.items.base.burnable.ItemPoisonBase;
import de.aelpecyem.elementaristics.misc.poisons.PoisonEffectBase;
import de.aelpecyem.elementaristics.misc.poisons.PoisonInit;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.player.PacketGiveVision;
import de.aelpecyem.elementaristics.networking.player.PacketMessage;
import de.aelpecyem.elementaristics.util.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.items.ItemHandlerHelper;
import vazkii.patchouli.api.PatchouliAPI;

public class PotionEventHandler {

    @SubscribeEvent
    public void decreasePoisonCountdown(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving().getEntityData().hasKey("poison_ticks_left")) {
            event.getEntityLiving().getEntityData().setInteger("poison_ticks_left", event.getEntityLiving().getEntityData().getInteger("poison_ticks_left") - 1);
            if (event.getEntityLiving().getEntityData().getInteger("poison_ticks_left") <= 0) {
                event.getEntityLiving().getEntityData().removeTag("poison_ticks_left");
                PoisonEffectBase effect = PoisonInit.poisons.getOrDefault(event.getEntityLiving().getEntityData().getInteger(ItemPoisonBase.POISON_TAG), null);
                if (effect != null) {
                    effect.performEffect(event.getEntityLiving().world, event.getEntityLiving());
                }
                event.getEntityLiving().getEntityData().removeTag(ItemPoisonBase.POISON_TAG);
            }
        }
    }

    @SubscribeEvent
    public void onItemUsed(LivingEntityUseItemEvent.Finish event) {
        ItemStack stack = event.getItem();
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(ItemPoisonBase.POISON_TAG)) {
            PoisonEffectBase effect = PoisonInit.poisons.getOrDefault(stack.getTagCompound().getInteger(ItemPoisonBase.POISON_TAG), null);
            if (effect != null) {
                effect.drinkEffect(event.getEntityLiving().world, event.getEntityLiving());
            }

        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (!event.player.world.isDaytime() && event.player.getActivePotionEffects().contains(event.player.getActivePotionEffect(PotionInit.potionIntoxicated))) {
                if (event.player.getActivePotionEffect(PotionInit.potionIntoxicated).getAmplifier() == 1) {
                    if (InventoryUtil.containsItem(event.player.inventory, Items.BOOK) != null) {
                        if (event.player.getActivePotionEffect(PotionInit.potionIntoxicated).getDuration() == 3000) {
                            if (!event.player.world.isRemote) {
                                PacketHandler.sendTo(event.player, new PacketGiveVision("visionSevenThoughts"));
                                PacketHandler.sendTo(event.player, new PacketMessage("message.vision.1", true));
                            }
                        }
                        if (event.player.getActivePotionEffect(PotionInit.potionIntoxicated).getDuration() == 2800) {
                            if (!event.player.world.isRemote) {
                                PacketHandler.sendTo(event.player, new PacketGiveVision("visionThreadBirth"));
                                PacketHandler.sendTo(event.player, new PacketMessage("message.vision.2", true));
                            }
                        }
                        if (event.player.getActivePotionEffect(PotionInit.potionIntoxicated).getDuration() == 2600) {
                            if (!event.player.world.isRemote) {
                                PacketHandler.sendTo(event.player, new PacketGiveVision("visionAnguish"));
                                PacketHandler.sendTo(event.player, new PacketMessage("message.vision.3", true));
                            }
                        }
                        if (event.player.getActivePotionEffect(PotionInit.potionIntoxicated).getDuration() == 2400) {
                            if (!event.player.world.isRemote) {
                                PacketHandler.sendTo(event.player, new PacketMessage("message.vision.4", true));
                                event.player.inventory.getStackInSlot(event.player.inventory.getSlotFor(new ItemStack(Items.BOOK))).shrink(1);
                                ItemHandlerHelper.giveItemToPlayer(event.player, PatchouliAPI.instance.getBookStack("elementaristics:liber_elementium"), 24);
                            }
                            event.player.removePotionEffect(PotionInit.potionIntoxicated);
                        }
                    }
                }
            }
        }
    }
}
