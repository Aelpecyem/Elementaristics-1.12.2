package de.aelpecyem.elementaristics.entity.render.nexus;

import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.util.MiscUtil;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Random;

public class RenderDimensionalNexus extends Render<EntityDimensionalNexus> {
    public RenderDimensionalNexus(RenderManager rendermanagerIn) {
        super(rendermanagerIn);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityDimensionalNexus entity) {
        return null;
    }

    @Override
    public void doRender(EntityDimensionalNexus entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (!entity.isInvisible()) {
            Random random = new Random(432L);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            RenderHelper.disableStandardItemLighting();
            float animState = 50F - Math.abs(50F - ((float) entity.ticksExisted % 100F));//ranges from 0 - 50
            //float f = ((float) entity.ticksExisted + partialTicks) / 200.0F;
            float transparency = 0.1F + animState / 1000F;// + animState*0.01F; //Float.valueOf(I18n.format("temp.1"));//1F;//0.0F;, currently 80
            float rotation = ((float) entity.ticksExisted + partialTicks) / 200.0F;
            int amount = 8;
            float length = 1F + animState * 0.03F; //20.0F
            Color color = new Color(4391011);
            float f = Math.min((float) entity.getRiteTicks() / 100F, 1);

            if (entity.getRite() != null) {
                System.out.println("blend tat shiz");
                color = MiscUtil.blend(new Color(entity.getRite().getColor()), color, f, 1 - f);
            } else if (entity.getRiteString().equals("") && entity.getRiteTicks() > 0) {
                System.out.println("rad");
                color = MiscUtil.blend(Color.RED, color, f, 1 - f);
            }
            Color shades = new Color(16724725);
            GlStateManager.disableTexture2D();
            GlStateManager.shadeModel(7425);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            GlStateManager.disableAlpha();
            GlStateManager.enableCull();
            GlStateManager.depthMask(false);
            GlStateManager.pushMatrix();

            GlStateManager.translate((float) x, (float) y, (float) z);
            for (int i = 0; i < amount; ++i) { //(float)i < (f + f * f) / 2.0F * 60.0F //might vary the amount
                //float varyLength = 0.4F + animState * 0.002F;
                float distance = random.nextFloat() * length + 0.5F;//random.nextFloat() * length + 5.0F + varyLength * 10.0F;
                if (entity.getRite() != null) {
                    distance += Math.min((float) entity.getRiteTicks() / 100F, 5);
                }
                float width = random.nextFloat() * distance;
                GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(random.nextFloat() * 360.0F + rotation * i * 360F / 6F, 0.0F, 0.0F, 1.0F);
                //
                bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
                bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), (int) (255.0F * (1.0F - transparency))).endVertex();
                bufferbuilder.pos(-0.866D * (double) width, (double) distance, (double) (-0.5F * width)).color(shades.getRed(), shades.getGreen(), shades.getBlue(), 0).endVertex();
                bufferbuilder.pos(0.866D * (double) width, (double) distance, (double) (-0.5F * width)).color(shades.getRed(), shades.getGreen(), shades.getBlue(), 0).endVertex();
                bufferbuilder.pos(0.0D, (double) distance, (double) (1.0F * width)).color(shades.getRed(), shades.getGreen(), shades.getBlue(), 0).endVertex();
                bufferbuilder.pos(-0.866D * (double) width, (double) distance, (double) (-0.5F * width)).color(shades.getRed(), shades.getGreen(), shades.getBlue(), 0).endVertex();
                tessellator.draw();
            }

            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
            GlStateManager.disableCull();
            GlStateManager.disableBlend();
            GlStateManager.shadeModel(7424);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableTexture2D();
            GlStateManager.enableAlpha();
            RenderHelper.enableStandardItemLighting();
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        }
    }
}
