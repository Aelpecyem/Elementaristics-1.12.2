package de.aelpecyem.elementaristics.util;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.advancements.CustomAdvancements;
import de.aelpecyem.elementaristics.misc.potions.effects.emotion.PotionEmotion;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class PlayerUtil {
    public static Block getBlockLookingAt(EntityPlayer player, double distance) {
        RayTraceResult r = Minecraft.getMinecraft().getRenderViewEntity().rayTrace(distance, 1.0F);
        if (r != null) {
            return player.world.getBlockState(r.getBlockPos()).getBlock();
        }
        return Blocks.AIR;
    }

    public static BlockPos getBlockPosLookingAt(double distance) {
        RayTraceResult r = Minecraft.getMinecraft().getRenderViewEntity().rayTrace(distance, 1.0F);
        if (r != null) {
            return r.getBlockPos();
        }
        return BlockPos.ORIGIN;
    }


    public static RayTraceResult rayTrace(EntityPlayer player, double blockReachDistance, float partialTicks) {
        Vec3d vec3d = player.getPositionEyes(partialTicks);
        Vec3d vec3d1 = player.getLook(partialTicks);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
        return player.world.rayTraceBlocks(vec3d, vec3d2, false, false, true);
    }

    public static boolean hasSoul(EntityPlayer player, Soul soul){
        if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)){
            return player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null).getSoulId() == soul.getId();
        }
        return false;
    }

    public static boolean hasEmotionActive(EntityPlayer player){
        for (PotionEffect effect : player.getActivePotionEffects()){
            if (effect.getPotion() instanceof PotionEmotion){
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
