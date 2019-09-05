package de.aelpecyem.elementaristics.util;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.advancements.CustomAdvancements;
import de.aelpecyem.elementaristics.misc.potions.effects.emotion.PotionEmotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;

public class CapabilityUtil {
    public static boolean hasSoul(EntityPlayer player, Soul soul) {
        if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            return player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null).getSoulId() == soul.getId();
        }
        return false;
    }

    public static boolean hasEmotionActive(EntityPlayer player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            if (effect.getPotion() instanceof PotionEmotion) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasPositiveEmotion(EntityPlayer player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            if (effect.getPotion() instanceof PotionEmotion) {
                return !((PotionEmotion) effect.getPotion()).isEmotionNegative();
            }
        }
        return false;
    }

    public static boolean hasNegativeEmotion(EntityPlayer player) {
        if (hasEmotionActive(player)) {
            return !hasPositiveEmotion(player);
        }
        return false;
    }

    public static Soul getSoul(EntityPlayer player) {
        if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            return player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null).getSoul();
        }
        return null;
    }

    public static boolean ascend(int to, Entity player) {
        if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            cap.setPlayerAscensionStage(to);
            if (!player.world.isRemote) {
                CustomAdvancements.Advancements.ASCEND.trigger((EntityPlayerMP) player);
            }
            if (player instanceof EntityPlayer)
                SoulInit.updateSoulInformation((EntityPlayer) player, cap);
            return true;
        }
        return false;
    }
}
