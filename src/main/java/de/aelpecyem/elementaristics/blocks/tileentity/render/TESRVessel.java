package de.aelpecyem.elementaristics.blocks.tileentity.render;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.render.models.ModelBlock;
import de.aelpecyem.elementaristics.blocks.tileentity.tile.TileEntityVessel;
import de.aelpecyem.elementaristics.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class TESRVessel extends TileEntitySpecialRenderer<TileEntityVessel> {
    //todo, maybe some sort of entity pedestal as a separate TE?
    @Override
    public void render(TileEntityVessel te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        float time = getWorld().getWorldTime() + partialTicks;
        float renderAlpha = Math.min(te.craftingProcess / (float) TileEntityVessel.CRAFTING_FINISHED, 0.999F);
        if (te.getCagedEntity() != null) {
            EntityLiving entity = te.getCagedEntity();
            GlStateManager.pushMatrix();
            GlStateManager.color(1, 1, 1, 1 - renderAlpha);
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            if (renderAlpha > 0.5F)
                entity.setAlwaysRenderNameTag(false);
            Minecraft.getMinecraft().getRenderManager().renderEntity(entity, x + 0.5, y + 0.5 + 0.035F * Math.sin(time / 20.0D), z + 0.5, te.getRotationFromFacing(), 1, false);
            GlStateManager.disableBlend();
            GlStateManager.disableNormalize();
            GlStateManager.popMatrix();

            ModelBlock model = new ModelBlock();
            GlStateManager.pushMatrix();
            GlStateManager.color(1, 1, 1, renderAlpha);
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

            GlStateManager.translate(x + 0.5, y + 2.15, z + 0.5);
            GlStateManager.rotate(180, 0, 0, 1);
            bindTexture(new ResourceLocation(Elementaristics.MODID, "textures/blocks/block_golden_thread.png"));
            model.shape1.rotateAngleY = (time / 10F) * alpha;
            model.render(entity, 0, 0, 0, 0, 0, 0.0625F / 2);
            GlStateManager.disableBlend();
            GlStateManager.disableNormalize();
            GlStateManager.popMatrix();
        }

        int items = 0;
        for (int i = 0; i < te.inventory.getSlots(); i++) {
            if (!te.inventory.getStackInSlot(i).isEmpty())
                items++;
        }
        float[] angles = RenderUtil.calculateAngles(te.inventory.getSlots(), items);

        for (int i = 0; i < te.inventory.getSlots(); ++i) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5F, y + 1.25F, z + 0.5F);
            GlStateManager.rotate((angles[i] + time) * (1 + renderAlpha * 0.5F), 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.75F, 0.0F, 0.025F);  //x = radius or so 0.75 - (renderAlpha * 0.5F)
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0D, (0.05F + (renderAlpha / 100F)) * Math.sin((time + (double) (i * 10)) / 10.0D), 0.0D); //if it doesn't move faster put the alpha stuff in the sine

            if (!te.inventory.getStackInSlot(i).isEmpty()) {
                RenderUtil.renderItem(te.inventory.getStackInSlot(i));
            }

            GlStateManager.popMatrix();
        }
    }
}
