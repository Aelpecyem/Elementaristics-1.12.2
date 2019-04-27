package de.aelpecyem.elementaristics.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelProtoplasm extends ModelBase {
    /**
     * The slime's bodies, both the inside box and the outside box
     */
    ModelRenderer slimeBodies;
    /**
     * The slime's right eye
     */
    ModelRenderer slimeRightEye;
    /**
     * The slime's left eye
     */
    ModelRenderer slimeLeftEye;
    /**
     * The slime's mouth
     */
    ModelRenderer slimeMouth;

    public ModelProtoplasm() {
        this.slimeBodies = new ModelRenderer(this, 0, 0);
        this.slimeBodies.addBox(-3.0F, 17.0F, -3.0F, 6, 6, 6);
        this.slimeRightEye = new ModelRenderer(this, 32, 0);
        this.slimeRightEye.addBox(-3.25F, 18.0F, -3.5F, 2, 2, 2);
        this.slimeLeftEye = new ModelRenderer(this, 32, 4);
        this.slimeLeftEye.addBox(1.25F, 18.0F, -3.5F, 2, 2, 2);
        this.slimeMouth = new ModelRenderer(this, 32, 8);
        this.slimeMouth.addBox(0.0F, 21.0F, -3.5F, 1, 1, 1);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        GlStateManager.translate(0.0F, 0.001F, 0.0F);
        this.slimeBodies.render(scale);

        if (this.slimeRightEye != null) {
            this.slimeRightEye.render(scale);
            this.slimeLeftEye.render(scale);
            this.slimeMouth.render(scale);
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
        slimeBodies.render(limbSwing);
    }
}
