package de.aelpecyem.elementaristics.blocks.tileentity.render.models.pantheon;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelWitch - cybercat5555
 * Created using Tabula 7.0.1
 */
public class ModelWitch extends ModelBase {
    public ModelRenderer plinth00;
    public ModelRenderer pentacle00;
    public ModelRenderer pentacle01;
    public ModelRenderer pentacle02;
    public ModelRenderer plinth01;
    public ModelRenderer pentacle03;
    public ModelRenderer pentacle04;

    public ModelWitch() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.plinth00 = new ModelRenderer(this, 0, 24);
        this.plinth00.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.plinth00.addBox(-5.0F, -1.0F, -5.0F, 10, 3, 5, 0.0F);
        this.pentacle00 = new ModelRenderer(this, 0, 4);
        this.pentacle00.setRotationPoint(0.0F, 8.7F, 0.0F);
        this.pentacle00.addBox(-1.0F, 0.0F, -0.48F, 2, 13, 1, 0.0F);
        this.setRotateAngle(pentacle00, 0.0F, 0.0F, 0.2617993877991494F);
        this.pentacle02 = new ModelRenderer(this, 3, 0);
        this.pentacle02.setRotationPoint(0.0F, 14.1F, 0.0F);
        this.pentacle02.addBox(-6.5F, -1.0F, -0.52F, 13, 1, 1, 0.0F);
        this.pentacle03 = new ModelRenderer(this, 8, 4);
        this.pentacle03.setRotationPoint(5.7F, -0.1F, 0.0F);
        this.pentacle03.addBox(-0.5F, -0.5F, -0.51F, 1, 11, 1, 0.0F);
        this.setRotateAngle(pentacle03, 0.0F, 0.0F, 0.8726646259971648F);
        this.pentacle04 = new ModelRenderer(this, 8, 4);
        this.pentacle04.setRotationPoint(-5.7F, -0.1F, 0.0F);
        this.pentacle04.addBox(-0.5F, -0.5F, -0.49F, 1, 11, 1, 0.0F);
        this.setRotateAngle(pentacle04, 0.0F, 0.0F, -0.8726646259971648F);
        this.plinth01 = new ModelRenderer(this, 0, 24);
        this.plinth01.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.plinth01.addBox(-5.0F, -1.0F, -5.0F, 10, 3, 5, 0.0F);
        this.setRotateAngle(plinth01, 0.0F, 3.141592653589793F, 0.0F);
        this.pentacle01 = new ModelRenderer(this, 0, 4);
        this.pentacle01.setRotationPoint(0.0F, 8.7F, 0.0F);
        this.pentacle01.addBox(-1.0F, -0.0F, -0.5F, 2, 13, 1, 0.0F);
        this.setRotateAngle(pentacle01, 0.0F, 0.0F, -0.2617993877991494F);
        this.pentacle02.addChild(this.pentacle03);
        this.pentacle02.addChild(this.pentacle04);
        this.plinth00.addChild(this.plinth01);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.plinth00.render(f5);
        this.pentacle00.render(f5);
        this.pentacle02.render(f5);
        this.pentacle01.render(f5);
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
