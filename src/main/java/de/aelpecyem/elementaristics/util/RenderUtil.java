package de.aelpecyem.elementaristics.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class RenderUtil {
    public static void renderItem(ItemStack stack, double x, double y, double z, float partTicks) {
        ItemStack is = stack.copy();
        is.setCount(1);
        EntityItem entityitem = new EntityItem(Minecraft.getMinecraft().world, 0.0D, 0.0D, 0.0D, is);
        entityitem.hoverStart = 0.0F;
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.renderEntity(entityitem, x, y, z, 0.0F, partTicks, false);
    }

    public static void renderItem(ItemStack stack) {
        renderItem(stack, 0, 0, 0, 0);
    }

    public static float[] calculateAngles(int amount, float anglePerPoint) {
        float[] angles = new float[amount];
        float anglePer = 360.0F / anglePerPoint;
        float totalAngle = 0.0F;

        for (int i = 0; i < angles.length; ++i) {
            angles[i] = totalAngle += anglePer;
        }
        return angles;
    }
}
