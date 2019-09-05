package de.aelpecyem.elementaristics.blocks.tileentity.render;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityConcentrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class TESRConcentrator extends TileEntitySpecialRenderer<TileEntityConcentrator> {

    @Override
    public void render(TileEntityConcentrator te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        //Item influencing
        ItemStack stack = te.inventory.getStackInSlot(1);
        if (!stack.isEmpty()) {
            EntityItem entityitem = null;
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5F, (float) y + 0.75F, (float) z - 0.1F);
            GL11.glScaled(1.25D, 1.25D, 1.25D);
            GL11.glRotatef(90, 1, 0, 0);
            ItemStack is = stack.copy();
            is.setCount(1);
            entityitem = new EntityItem(Minecraft.getMinecraft().world, 0.0D, 0.0D, 0.0D, is);
            entityitem.hoverStart = 0.0F;
            RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
            rendermanager.renderEntity(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
            GL11.glPopMatrix();


            /*EntityItem entityitem = null;
            float ticks = (float)Minecraft.getMinecraft().getRenderViewEntity().ticksExisted + partialTicks;
            GL11.glPushMatrix();
            GL11.glScaled(1.25D, 1.25D, 1.25D);
            GL11.glTranslatef((float)x + 0.5F, (float)y + 0.65F, (float)z + 0.5F);
            GlStateManager.rotate(90, 1, 0, 0);
            ItemStack is = stack.copy();
            is.setCount(1);
            entityitem = new EntityItem(Minecraft.getMinecraft().world, 0.0D, 0.0D, 0.0D, is);
            entityitem.hoverStart = 0.0F;
            RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
            rendermanager.renderEntity(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
            GL11.glPopMatrix(); //if that is dumb, change again*/

            /*GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
            GlStateManager.enableBlend();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5, y + 0.75, z + 0.5);
            if (te.tickCount > 3) {
                GlStateManager.rotate((te.getWorld().getTotalWorldTime() + partialTicks) * 4, 0, 1, 0);

            }
            GlStateManager.rotate(90, 1, 0, 0);

            IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, te.getWorld(), null);
            model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);

            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);

            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();*/
        }

        ItemStack stack2 = te.inventory.getStackInSlot(0);
        if (!stack2.isEmpty()) {
            EntityItem entityitem = null;
            float ticks = (float) Minecraft.getMinecraft().getRenderViewEntity().ticksExisted + partialTicks;
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5F, (float) y + 0.55F, (float) z + 0.5F);
            GL11.glScaled(1.25D, 1.25D, 1.25D);
            GL11.glRotatef((ticks % 360.0F) * 4, 0.0F, 1, 0.0F);
            ItemStack is = stack2.copy();
            is.setCount(1);
            entityitem = new EntityItem(Minecraft.getMinecraft().world, 0.0D, 0.0D, 0.0D, is);
            entityitem.hoverStart = 0.0F;
            RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
            rendermanager.renderEntity(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
            GL11.glPopMatrix();

           /* GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
            GlStateManager.enableBlend();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5, y + 0.9, z + 0.5);
            GlStateManager.rotate((te.getWorld().getTotalWorldTime() + partialTicks) * 4, 0, -1, 0);


            IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack2, te.getWorld(), null);
            model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);

            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack2, model);

            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();*/
        }
    }
}