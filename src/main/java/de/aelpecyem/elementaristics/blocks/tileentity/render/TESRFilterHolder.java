package de.aelpecyem.elementaristics.blocks.tileentity.render;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityFilterHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class TESRFilterHolder extends TileEntitySpecialRenderer<TileEntityFilterHolder> {
    //literally just a pedestal
    @Override
    public void render(TileEntityFilterHolder te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        ItemStack stack = te.inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            EntityItem entityitem = null;
            float ticks = (float) Minecraft.getMinecraft().getRenderViewEntity().ticksExisted + partialTicks;
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
            GL11.glScaled(1.25D, 1.25D, 1.25D);
            GL11.glRotatef((ticks % 360.0F) * 4, 0.0F, 1, 0.0F);
            ItemStack is = stack.copy();
            is.setCount(1);
            entityitem = new EntityItem(Minecraft.getMinecraft().world, 0.0D, 0.0D, 0.0D, is);
            entityitem.hoverStart = 0.0F;
            RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
            rendermanager.renderEntity(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
            GL11.glPopMatrix();
        }

    }
}
