package de.aelpecyem.elementaristics.items.base.armor;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

/**
 * ModelRobes - Aelpecyem
 * Created using Tabula 7.0.0
 */
public class ModelRobes extends ModelBiped {
    public ModelRenderer chest_base;
    public ModelRenderer arm_right;
    public ModelRenderer arm_left;
    public ModelRenderer chest_head_1;
    public ModelRenderer chest_head_2;
    public ModelRenderer chest_head_sur_1;
    public ModelRenderer chest_head_sur_2;

    public ModelRobes() {
        this.textureWidth = 128;
        this.textureHeight = 128;
       this.chest_head_2 = new ModelRenderer(this, 64, 30);
        this.chest_head_2.setRotationPoint(-4.0F, 0.0F, -2.0F);
        this.chest_head_2.addBox(6.8F, -0.1F, -0.7F, 2, 1, 5, 0.0F);
        this.arm_right = new ModelRenderer(this, 82, 5);
        this.arm_right.setRotationPoint(-3.0F, -2.0F, -2.0F);
        this.arm_right.addBox(-4.9F, 1.6F, -0.7F, 4, 13, 5, 0.0F);
       this.chest_head_1 = new ModelRenderer(this, 64, 41);
        this.chest_head_1.setRotationPoint(-4.0F, 0.0F, -2.0F);
        this.chest_head_1.addBox(-0.5F, -0.1F, -0.7F, 2, 1, 5, 0.0F);
       this.chest_head_sur_2 = new ModelRenderer(this, 64, 26);
        this.chest_head_sur_2.setRotationPoint(-4.0F, 0.0F, -2.0F);
        this.chest_head_sur_2.addBox(0.9F, -0.1F, 3.2F, 6, 1, 1, 0.0F);
        this.chest_base = new ModelRenderer(this, 100, 5);
        this.chest_base.setRotationPoint(-4.0F, 0.0F, -2.0F);
        this.chest_base.addBox(-0.3F, 0.9F, -0.7F, 9, 11, 5, 0.0F);
        this.chest_head_sur_1 = new ModelRenderer(this, 64, 21);
        this.chest_head_sur_1.setRotationPoint(-4.0F, 0.0F, -2.0F);
        this.chest_head_sur_1.addBox(0.9F, -0.1F, -0.7F, 6, 1, 1, 0.0F);
       this.arm_left = new ModelRenderer(this, 64, 4);
        this.arm_left.setRotationPoint(-1.0F, -2.0F, -2.0F);
        this.arm_left.addBox(4.5F, 1.6F, -0.7F, 4, 13, 5, 0.0F);

        this.bipedBody.addChild(chest_base);
        this.bipedBody.addChild(chest_head_1);
        this.bipedBody.addChild(chest_head_2);
        this.bipedBody.addChild(chest_head_sur_1);
        this.bipedBody.addChild(chest_head_sur_2);

        this.bipedLeftArm.addChild(arm_left);
        this.bipedRightArm.addChild(arm_right);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
     super.render(entity, f, f1, f2, f3, f4, f5);
       this.bipedLeftArm.render(f5);
        this.chest_head_2.render(f5);
        this.bipedRightArm.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
