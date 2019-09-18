package de.aelpecyem.elementaristics.blocks.tileentity.render;

import de.aelpecyem.elementaristics.blocks.tileentity.tile.TileEntityReactor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;

public class TESRReactor extends TileEntitySpecialRenderer<TileEntityReactor> {

    @Override
    public void render(TileEntityReactor te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        ItemStack stack = te.inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            EntityItem entityitem = null;
            float ticks = (float) Minecraft.getMinecraft().getRenderViewEntity().ticksExisted + partialTicks;
            GL11.glPushMatrix();
            if (te.getBlockFacing() == EnumFacing.NORTH || te.getBlockFacing() == EnumFacing.UP || te.getBlockFacing() == EnumFacing.DOWN) {
                GL11.glTranslatef((float) x + 0.75F, (float) y + 0.75F, (float) z + 0.5F);
            }
            if (te.getBlockFacing() == EnumFacing.SOUTH) {
                GL11.glTranslatef((float) x + 0.25F, (float) y + 0.75F, (float) z + 0.5F);
            }
            if (te.getBlockFacing() == EnumFacing.WEST) {
                GL11.glTranslatef((float) x + 0.5F, (float) y + 0.75F, (float) z + 0.25F);
            }
            if (te.getBlockFacing() == EnumFacing.EAST) {
                GL11.glTranslatef((float) x + 0.5F, (float) y + 0.75F, (float) z + 0.75F);
            }
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

        ItemStack stack2 = te.inventory.getStackInSlot(1);
        if (!stack2.isEmpty()) {
            EntityItem entityitem = null;
            float ticks = (float) Minecraft.getMinecraft().getRenderViewEntity().ticksExisted + partialTicks;
            GL11.glPushMatrix();
            if (te.getBlockFacing() == EnumFacing.NORTH || te.getBlockFacing() == EnumFacing.UP || te.getBlockFacing() == EnumFacing.DOWN) {
                GL11.glTranslatef((float) x + 0.25F, (float) y + 0.75F, (float) z + 0.5F);
            }
            if (te.getBlockFacing() == EnumFacing.SOUTH) {
                GL11.glTranslatef((float) x + 0.75F, (float) y + 0.75F, (float) z + 0.5F);
            }
            if (te.getBlockFacing() == EnumFacing.WEST) {
                GL11.glTranslatef((float) x + 0.5F, (float) y + 0.75F, (float) z + 0.75F);
            }
            if (te.getBlockFacing() == EnumFacing.EAST) {
                GL11.glTranslatef((float) x + 0.5F, (float) y + 0.75F, (float) z + 0.25F);
            }
            GL11.glScaled(1.25D, 1.25D, 1.25D);
            GL11.glRotatef((ticks % 360.0F) * 4, 0.0F, 4, 0.0F);
            ItemStack is = stack2.copy();
            is.setCount(1);
            entityitem = new EntityItem(Minecraft.getMinecraft().world, 0.0D, 0.0D, 0.0D, is);
            entityitem.hoverStart = 0.0F;
            RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
            rendermanager.renderEntity(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
            GL11.glPopMatrix();
        }
    }
}