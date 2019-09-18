package de.aelpecyem.elementaristics.blocks.tileentity.render;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.render.models.ModelBlock;
import de.aelpecyem.elementaristics.blocks.tileentity.tile.TileEntityGoldenThread;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.util.MiscUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class TESRGoldenThread extends TileEntitySpecialRenderer<TileEntityGoldenThread> {
    @Override
    public void render(TileEntityGoldenThread te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        ModelBlock model = new ModelBlock();
        GlStateManager.pushMatrix();
        if (te.activationStage == 0) {
            Color color = MiscUtil.reverseColor(new Color(Aspects.getElementById(te.aspect).getColor()));//14928384));, gold
            float colorFactor = (float) te.charge / (float) TileEntityGoldenThread.MAX_CHARGE;
            GlStateManager.color(1 - ((float) color.getRed() / 255F) * colorFactor, 1 - ((float) color.getGreen() / 255F) * colorFactor, 1 - ((float) color.getBlue() / 255F) * colorFactor, ((float) color.getAlpha() / 255F));
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

            GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
            GlStateManager.rotate(180, 0, 0, 1);
            bindTexture(new ResourceLocation(Elementaristics.MODID, "textures/blocks/block_golden_thread.png"));
            model.shape1.rotateAngleY = te.getRotation();
            te.setRotation(model.shape1.rotateAngleY + (float) te.charge / 80F);
            model.render(null, 0, 0, 0, 0, 0, 0.0625F);
            GlStateManager.disableBlend();
            GlStateManager.disableNormalize();
        } else {
            Color color = new Color(Aspects.getElementById(te.aspect).getColor());//14928384));, gold
            GlStateManager.color((float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F, (float) color.getAlpha() / 255F);
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
            GlStateManager.rotate(180, 0, 0, 1);
            bindTexture(new ResourceLocation(Elementaristics.MODID, "textures/blocks/block_golden_thread.png"));
            model.shape1.rotateAngleY = te.getRotation();
            model.shape1.offsetY = te.getHeight();
            model.render(null, 0, 0, 0, 0, 0, 0.0625F);
            GlStateManager.disableBlend();
            GlStateManager.disableNormalize();

            updateMotion(te);
        }
        GlStateManager.popMatrix();
    }

    //0 moves down, 1 moves up
    private void updateMotion(TileEntityGoldenThread te) {
        te.setRotation(te.getRotation() + (float) 5 / 80F);

        if (te.getHeight() < -1F && te.animPhase == 1)
            te.animPhase = 0;
        else if (te.getHeight() > 0 && te.animPhase == 0)
            te.animPhase = 1;

        if (te.animPhase == 0) {
            float motionDown = Math.max(Math.abs(te.getHeight()) / 100F, 0.005F);
            te.setHeight(te.getHeight() + motionDown);
        } else {
            float motionUp = Math.max(Math.abs((float) 1 + te.getHeight()) / 100F, 0.005F);
            te.setHeight(te.getHeight() - motionUp);
        }

    }
}
