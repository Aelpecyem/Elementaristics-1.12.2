package de.aelpecyem.elementaristics.blocks.tileentity.render;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityInfusionBasin;
import de.aelpecyem.elementaristics.util.MiscUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class TESRBasin extends TileEntitySpecialRenderer<TileEntityInfusionBasin> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Elementaristics.MODID, "textures/misc/fluid.png"); //may be dirty and hacky, but saves effort and one additional texture

    @Override
    public void render(TileEntityInfusionBasin te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        //Item influenced
        ItemStack stack = te.inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
            GlStateManager.enableBlend();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5, y + 0.45, z + 0.5);
            GlStateManager.rotate((te.getWorld().getTotalWorldTime() + partialTicks) * 4, 0, 1, 0);

            IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, te.getWorld(), null);
            model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);

            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);

            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
        if (te.fillCount > 0) {
            Tessellator tess = Tessellator.getInstance();
            BufferBuilder buffer = tess.getBuffer();

            buffer.setTranslation(x, y, z);

            //bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            // TextureAtlasSprite still = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(FluidRegistry.WATER.getStill().toString());
            // TextureAtlasSprite still = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(TEXTURE.toString());
            Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            //1-1;2-1;2-2;1;2

            buffer.pos(1F / 16F, te.fillCount / 4F * 0.7 + 0.1, 15F / 16F).tex(1, 1).color(getWaterColor(te).getRed(), getWaterColor(te).getGreen(), getWaterColor(te).getBlue(), getWaterColor(te).getAlpha()).endVertex();
            buffer.pos(15F / 16F, te.fillCount / 4F * 0.7 + 0.1, 15F / 16F).tex(2, 1).color(getWaterColor(te).getRed(), getWaterColor(te).getGreen(), getWaterColor(te).getBlue(), getWaterColor(te).getAlpha()).endVertex();
            buffer.pos(15F / 16F, te.fillCount / 4F * 0.7 + 0.1, 1F / 16F).tex(2, 2).color(getWaterColor(te).getRed(), getWaterColor(te).getGreen(), getWaterColor(te).getBlue(), getWaterColor(te).getAlpha()).endVertex();
            buffer.pos(1F / 16F, te.fillCount / 4F * 0.7 + 0.1, 1F / 16F).tex(1, 2).color(getWaterColor(te).getRed(), getWaterColor(te).getGreen(), getWaterColor(te).getBlue(), getWaterColor(te).getAlpha()).endVertex();

            tess.draw();


            buffer.setTranslation(0, 0, 0);
            //  Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        }
    }

    public Color getWaterColor(TileEntityInfusionBasin te) {
        return MiscUtil.blend(MiscUtil.getColorForEssenceIds(te.aspectIDs), new Color(47, 130, 232, 205), Math.min(0.1 * te.aspectIDs.size(), 0.9), 1 - Math.min(0.1 * te.aspectIDs.size(), 0.9));
    }

}