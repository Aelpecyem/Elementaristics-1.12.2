package de.aelpecyem.elementaristics.blocks.tileentity.render;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityInfusionBasin;
import de.aelpecyem.elementaristics.util.MiscUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class TESRBasin extends TileEntitySpecialRenderer<TileEntityInfusionBasin> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Elementaristics.MODID, "textures/misc/fluid.png"); //may be dirty and hacky, but saves effort and one additional texture

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
            Tessellator tess = Tessellator.getInstance();
            BufferBuilder buffer = tess.getBuffer();

            buffer.setTranslation(x, y, z);

            buffer.begin(GL11.GL_QUADS, new VertexFormat()
                    .addElement(DefaultVertexFormats.POSITION_3F)
                    .addElement(DefaultVertexFormats.TEX_2F)
                    .addElement(DefaultVertexFormats.TEX_2S)
                    //.addElement(DefaultVertexFormats.NORMAL_3B)
                    .addElement(DefaultVertexFormats.COLOR_4UB));

            buffer.pos(1F / 16F, te.fillCount / 4F * 0.7 + 0.1, 15F / 16F).tex(1, 1).lightmap(240, 240).color(getWaterColor(te).getRed(), getWaterColor(te).getGreen(), getWaterColor(te).getBlue(), 255).endVertex();
            buffer.pos(15F / 16F, te.fillCount / 4F * 0.7 + 0.1, 15F / 16F).tex(2, 1).lightmap(240, 240).color(getWaterColor(te).getRed(), getWaterColor(te).getGreen(), getWaterColor(te).getBlue(), 255).endVertex();
            buffer.pos(15F / 16F, te.fillCount / 4F * 0.7 + 0.1, 1F / 16F).tex(2, 2).lightmap(240, 240).color(getWaterColor(te).getRed(), getWaterColor(te).getGreen(), getWaterColor(te).getBlue(), 255).endVertex();
            buffer.pos(1F / 16F, te.fillCount / 4F * 0.7 + 0.1, 1F / 16F).tex(1, 2).lightmap(240, 240).color(getWaterColor(te).getRed(), getWaterColor(te).getGreen(), getWaterColor(te).getBlue(), 255).endVertex();

            Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
            tess.draw();


            buffer.setTranslation(0, 0, 0);

            //  Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        }
    }

    public Color getWaterColor(TileEntityInfusionBasin te) {
        return MiscUtil.blend(MiscUtil.getColorForEssenceIds(te.aspectIDs), new Color(47, 130, 232, 205), Math.min(0.2 * te.aspectIDs.size(), 0.9), 1 - Math.min(0.2 * te.aspectIDs.size(), 0.9));
    }

}