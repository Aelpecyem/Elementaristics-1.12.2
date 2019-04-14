package de.aelpecyem.elementaristics.util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

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
        return null;
    }

}
