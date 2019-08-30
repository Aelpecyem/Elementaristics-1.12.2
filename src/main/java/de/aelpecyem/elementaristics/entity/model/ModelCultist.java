package de.aelpecyem.elementaristics.entity.model;

import de.aelpecyem.elementaristics.entity.EntityCultist;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelCultist extends ModelBase {
    public ModelRenderer head;
    public ModelRenderer leg0;
    public ModelRenderer leg1;
    public ModelRenderer rightArm;
    public ModelRenderer leftArm;
    public ModelRenderer body0;
    public ModelRenderer body1;
    public ModelRenderer headChild;
    public ModelRenderer headChild_1;

    public ModelCultist() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.body1 = new ModelRenderer(this, 0, 38);
        this.body1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body1.addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, 0.5F);
        this.body0 = new ModelRenderer(this, 16, 20);
        this.body0.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body0.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F);
        this.headChild_1 = new ModelRenderer(this, 24, 0);
        this.headChild_1.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.headChild_1.addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, 0.0F);
        this.leg0 = new ModelRenderer(this, 0, 22);
        this.leg0.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.leg0.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.headChild = new ModelRenderer(this, 32, 0);
        this.headChild.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.headChild.addBox(-4.0F, -10.0F, -4.0F, 8, 12, 8, 0.1F);

        this.rightArm = new ModelRenderer(this, 40, 46);
        this.rightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);

        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);

        this.leftArm = new ModelRenderer(this, 40, 46);
        this.leftArm.mirror = true;
        this.leftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);

        this.leg1 = new ModelRenderer(this, 0, 22);
        this.leg1.mirror = true;
        this.leg1.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.head.addChild(this.headChild_1);
        // this.head.addChild(this.headChild);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.body1.render(f5);
        this.body0.render(f5);
        this.leg0.render(f5);
        this.rightArm.render(f5);
        this.head.render(f5);
        this.leftArm.render(f5);
        this.leg1.render(f5);
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
        if (entityIn instanceof EntityCultist) {
            boolean flag = entityIn instanceof EntityLivingBase && ((EntityLivingBase) entityIn).getTicksElytraFlying() > 4;
            this.head.rotateAngleY = netHeadYaw * 0.017453292F;

            if (flag) {
                this.head.rotateAngleX = -((float) Math.PI / 4F);
            } else {
                this.head.rotateAngleX = headPitch * 0.017453292F;
            }

            this.body0.rotateAngleY = 0.0F;
            this.rightArm.rotationPointZ = 0.0F;
            this.rightArm.rotationPointX = -5.0F;
            this.leftArm.rotationPointZ = 0.0F;
            this.leftArm.rotationPointX = 5.0F;
            float f = 1.0F;

            if (flag) {
                f = (float) (entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
                f = f / 0.2F;
                f = f * f * f;
            }

            if (f < 1.0F) {
                f = 1.0F;
            }

            this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
            this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
            this.rightArm.rotateAngleZ = 0.0F;
            this.leftArm.rotateAngleZ = 0.0F;
            this.leg0.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
            this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / f;
            this.leg0.rotateAngleY = 0.0F;
            this.leg1.rotateAngleY = 0.0F;
            this.leg0.rotateAngleZ = 0.0F;
            this.leg1.rotateAngleZ = 0.0F;

            if (this.isRiding) {
                this.rightArm.rotateAngleX += -((float) Math.PI / 5F);
                this.leftArm.rotateAngleX += -((float) Math.PI / 5F);
                this.leg0.rotateAngleX = -1.4137167F;
                this.leg0.rotateAngleY = ((float) Math.PI / 10F);
                this.leg0.rotateAngleZ = 0.07853982F;
                this.leg1.rotateAngleX = -1.4137167F;
                this.leg1.rotateAngleY = -((float) Math.PI / 10F);
                this.leg1.rotateAngleZ = -0.07853982F;
            }

            this.rightArm.rotateAngleY = 0.0F;
            this.rightArm.rotateAngleZ = 0.0F;


            if (this.swingProgress > 0.0F) {

                float f1 = this.swingProgress;
                this.body0.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float) Math.PI * 2F)) * 0.2F;


                //    this.body0.rotateAngleY *= -1.0F;

                this.rightArm.rotationPointZ = MathHelper.sin(this.body0.rotateAngleY) * 5.0F;
                this.rightArm.rotationPointX = -MathHelper.cos(this.body0.rotateAngleY) * 5.0F;
                this.leftArm.rotationPointZ = -MathHelper.sin(this.body0.rotateAngleY) * 5.0F;
                this.leftArm.rotationPointX = MathHelper.cos(this.body0.rotateAngleY) * 5.0F;
                this.rightArm.rotateAngleY += this.body0.rotateAngleY;
                this.leftArm.rotateAngleY += this.body0.rotateAngleY;
                this.leftArm.rotateAngleX += this.body0.rotateAngleY;
                f1 = 1.0F - this.swingProgress;
                f1 = f1 * f1;
                f1 = f1 * f1;
                f1 = 1.0F - f1;
                float f2 = MathHelper.sin(f1 * (float) Math.PI);
                float f3 = MathHelper.sin(this.swingProgress * (float) Math.PI) * -(this.head.rotateAngleX - 0.7F) * 0.75F;

            }


            this.body0.rotateAngleX = 0.0F;
            this.leg0.rotationPointZ = 0.1F;
            this.leg1.rotationPointZ = 0.1F;
            this.leg0.rotationPointY = 12.0F;
            this.leg1.rotationPointY = 12.0F;
            this.head.rotationPointY = 0.0F;
            if (((EntityCultist) entityIn).isCasting()) {
                doSpellCastingMovement(ageInTicks);
            }
        }

    }

    private void doSpellCastingMovement(float ageInTicks) {
        this.rightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.05F) * 0.05F + 0.05F;
        this.leftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.05F) * 0.05F + 0.05F;
        this.rightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.027F) * 0.05F;
        this.leftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.027F) * 0.05F;

        this.rightArm.rotationPointZ = 0.0F;
        this.rightArm.rotationPointX = -5.0F;
        this.leftArm.rotationPointZ = 0.0F;
        this.leftArm.rotationPointX = 5.0F;
        this.rightArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
        this.leftArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
        this.rightArm.rotateAngleZ = 2.3561945F;
        this.leftArm.rotateAngleZ = -2.3561945F;
        this.rightArm.rotateAngleY = 0.0F;
        this.leftArm.rotateAngleY = 0.0F;
    }

}
