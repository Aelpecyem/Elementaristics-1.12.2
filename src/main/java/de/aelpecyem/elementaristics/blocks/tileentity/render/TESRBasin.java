package de.aelpecyem.elementaristics.blocks.tileentity.render;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.tile.TileEntityInfusionBasin;
import de.aelpecyem.elementaristics.util.MiscUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class TESRBasin extends TileEntitySpecialRenderer<TileEntityInfusionBasin> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Elementaristics.MODID, "blocks/fluid/gray_fluid");

    @Override
    public void render(TileEntityInfusionBasin te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        //Item influenced
        ItemStack stack = te.inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            EntityItem entityitem = null;
            float ticks = (float) Minecraft.getMinecraft().getRenderViewEntity().ticksExisted + partialTicks;
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
            GL11.glScaled(1.25D, 1.25D, 1.25D);
            GL11.glRotatef((ticks % 360.0F) * 4, 0.0F, 1F, 0.0F);
            ItemStack is = stack.copy();
            is.setCount(1);
            entityitem = new EntityItem(Minecraft.getMinecraft().world, 0.0D, 0.0D, 0.0D, is);
            entityitem.hoverStart = 0.0F;
            RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
            rendermanager.renderEntity(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
            GL11.glPopMatrix();
        }
        if (te.fillCount > 0) {
            renderWater(te, x, y, z);
        }
    }

    public void renderWater(TileEntityInfusionBasin te, double x, double y, double z) {
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.translate(x + 0.125, y + te.fillCount / 4F * 0.7 + 0.1, z + 0.125);
        GlStateManager.rotate(90, 1, 0, 0);
        GlStateManager.scale(0.0460425, 0.0460425, 0.0460425);
        //boolean water = stack.getFluid() == FluidRegistry.WATER;
        //if (water)
        Color color = getWaterColor(te);
        GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(TEXTURE.toString());
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        tessellator.getBuffer().pos(0, 16, 0).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();
        tessellator.getBuffer().pos(16, 16, 0).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();
        tessellator.getBuffer().pos(16, 0, 0).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();
        tessellator.getBuffer().pos(0, 0, 0).tex(sprite.getMinU(), sprite.getMinV()).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    public Color getWaterColor(TileEntityInfusionBasin te) {
        return MiscUtil.blend(MiscUtil.getColorForEssenceIds(te.aspectIDs), new Color(47, 130, 232, 205), Math.min(0.2 * te.aspectIDs.size(), 0.9), 1 - Math.min(0.33 * te.aspectIDs.size(), 1));
    }

}