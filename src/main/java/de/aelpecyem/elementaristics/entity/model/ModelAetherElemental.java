package de.aelpecyem.elementaristics.entity.model;

import de.aelpecyem.elementaristics.entity.elementals.EntityAetherElemental;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ModelAetherElemental extends ModelPlayer {


    public ModelAetherElemental(boolean smolArms) {
        super(0, smolArms);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        if (entity instanceof EntityAetherElemental) {
            GlStateManager.pushMatrix();
            Color color = new Color(Aspects.aether.getColor());
            GlStateManager.color((float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F, (float) (75 - ((EntityAetherElemental) entity).getWarpState()) / 100F); //replace that one 100 int with 90
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_BLEND);
            GlStateManager.scale(0.85, 0.85, 0.85);
            GlStateManager.translate(0, 0.28, 0);
            this.bipedRightLeg.render(f5);
            this.bipedHead.render(f5);
            this.bipedRightArm.render(f5);
            this.bipedLeftLeg.render(f5);
            this.bipedBody.render(f5);
            this.bipedLeftArm.render(f5);
            GlStateManager.disableBlend();
            GlStateManager.disableNormalize();
            GlStateManager.popMatrix();
            return;
        }
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    }
}
