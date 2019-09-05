package de.aelpecyem.elementaristics.util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.UsernameCache;

import java.util.UUID;

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

    public static Entity getEntityLookingAt() {
        if (Minecraft.getMinecraft().objectMouseOver != null) {
            return Minecraft.getMinecraft().objectMouseOver.entityHit;
        }
        return null;
    }
    public static RayTraceResult rayTrace(EntityPlayer player, double blockReachDistance, float partialTicks) {
        Vec3d vec3d = player.getPositionEyes(partialTicks);
        Vec3d vec3d1 = player.getLook(partialTicks);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
        return player.world.rayTraceBlocks(vec3d, vec3d2, false, false, true);
    }

    public static String getUsernameFromUUID(UUID uuid) {
        return UsernameCache.getLastKnownUsername(uuid);
    }

    public static String getUsernameFromUUID(String uuid) {
        if (!uuid.isEmpty()) {
            return UsernameCache.getLastKnownUsername(UUID.fromString(uuid));
        }
        return "";
    }
}
