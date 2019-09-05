package de.aelpecyem.elementaristics.blocks.tileentity.render.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelDragonAether - cybercat5555
 * Created using Tabula 7.0.1
 */
public class ModelDragonAether extends ModelBase {
    public ModelRenderer plinth00;
    public ModelRenderer body00;
    public ModelRenderer star00;
    public ModelRenderer plinth01;
    public ModelRenderer neck;
    public ModelRenderer body01;
    public ModelRenderer lArm00;
    public ModelRenderer rArm00;
    public ModelRenderer head;
    public ModelRenderer snout;
    public ModelRenderer lHorn00;
    public ModelRenderer rHorn00;
    public ModelRenderer lHorn01;
    public ModelRenderer rHorn01;
    public ModelRenderer body02;
    public ModelRenderer tail00;
    public ModelRenderer lLeg00;
    public ModelRenderer rLeg00;
    public ModelRenderer tail01;
    public ModelRenderer tail02;
    public ModelRenderer tail03;
    public ModelRenderer lArm01;
    public ModelRenderer rArm01;
    public ModelRenderer star01;
    public ModelRenderer star02;
    public ModelRenderer star03;

    public ModelDragonAether() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.tail03 = new ModelRenderer(this, 14, 5);
        this.tail03.setRotationPoint(0.0F, 0.0F, 4.3F);
        this.tail03.addBox(-0.5F, -1.0F, 0.0F, 1, 2, 4, 0.0F);
        this.setRotateAngle(tail03, -0.05235987755982988F, -1.2217304763960306F, 0.0F);
        this.star00 = new ModelRenderer(this, 26, 25);
        this.star00.setRotationPoint(2.6F, 15.0F, -5.9F);
        this.star00.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.setRotateAngle(star00, 0.0F, 0.7853981633974483F, 0.7853981633974483F);
        this.body00 = new ModelRenderer(this, 1, 13);
        this.body00.setRotationPoint(2.7F, 15.5F, -1.9F);
        this.body00.addBox(-1.5F, -1.5F, -2.5F, 3, 3, 4, 0.0F);
        this.setRotateAngle(body00, -0.9105382707654417F, 0.0F, 0.0F);
        this.rLeg00 = new ModelRenderer(this, 24, 11);
        this.rLeg00.mirror = true;
        this.rLeg00.setRotationPoint(-1.5F, 0.4F, 4.4F);
        this.rLeg00.addBox(-1.0F, -1.3F, -2.3F, 1, 2, 3, 0.0F);
        this.setRotateAngle(rLeg00, 0.22689280275926282F, 0.0F, -0.22689280275926282F);
        this.star02 = new ModelRenderer(this, 13, 18);
        this.star02.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.star02.addBox(-0.5F, -1.5F, -1.5F, 1, 3, 3, 0.0F);
        this.rHorn00 = new ModelRenderer(this, 21, 0);
        this.rHorn00.mirror = true;
        this.rHorn00.setRotationPoint(-1.1F, -2.0F, 0.3F);
        this.rHorn00.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(rHorn00, 0.45378560551852565F, -0.17453292519943295F, 0.0F);
        this.body02 = new ModelRenderer(this, 0, 12);
        this.body02.setRotationPoint(0.3F, 0.2F, 3.7F);
        this.body02.addBox(-1.5F, -1.51F, 0.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(body02, 0.6981317007977318F, -0.3665191429188092F, -0.3665191429188092F);
        this.body01 = new ModelRenderer(this, 0, 12);
        this.body01.setRotationPoint(0.0F, 0.0F, 0.5F);
        this.body01.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(body01, -0.03490658503988659F, -0.3490658503988659F, -0.22689280275926282F);
        this.snout = new ModelRenderer(this, 12, 0);
        this.snout.setRotationPoint(0.0F, -0.5F, -2.0F);
        this.snout.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 2, 0.0F);
        this.rArm00 = new ModelRenderer(this, 17, 11);
        this.rArm00.mirror = true;
        this.rArm00.setRotationPoint(-0.4F, -0.2F, -1.0F);
        this.rArm00.addBox(-1.7F, -0.5F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(rArm00, 0.6981317007977318F, 0.0F, 0.17453292519943295F);
        this.tail00 = new ModelRenderer(this, 1, 12);
        this.tail00.setRotationPoint(0.0F, 0.0F, 4.3F);
        this.tail00.addBox(-1.0F, -1.5F, 0.0F, 2, 3, 5, 0.0F);
        this.setRotateAngle(tail00, 0.7155849933176751F, -1.5707963267948966F, -1.0471975511965976F);
        this.lHorn01 = new ModelRenderer(this, 22, 1);
        this.lHorn01.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.lHorn01.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(lHorn01, 0.3141592653589793F, 0.2792526803190927F, 0.0F);
        this.neck = new ModelRenderer(this, 0, 6);
        this.neck.setRotationPoint(0.0F, 0.0F, -1.6F);
        this.neck.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(neck, 0.6981317007977318F, 0.0F, 0.0F);
        this.lArm01 = new ModelRenderer(this, 25, 4);
        this.lArm01.setRotationPoint(1.1F, 2.0F, 0.0F);
        this.lArm01.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(lArm01, -1.6755160819145563F, 0.0F, 0.0F);
        this.star01 = new ModelRenderer(this, 19, 16);
        this.star01.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.star01.addBox(-1.51F, -0.5F, -1.51F, 3, 1, 3, 0.0F);
        this.lLeg00 = new ModelRenderer(this, 24, 11);
        this.lLeg00.setRotationPoint(1.5F, 0.0F, 4.4F);
        this.lLeg00.addBox(0.0F, -1.3F, -2.3F, 1, 2, 3, 0.0F);
        this.setRotateAngle(lLeg00, 0.22689280275926282F, 0.0F, 0.0F);
        this.rArm01 = new ModelRenderer(this, 25, 4);
        this.rArm01.mirror = true;
        this.rArm01.setRotationPoint(-1.1F, 2.0F, 0.0F);
        this.rArm01.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(rArm01, -1.6755160819145563F, 0.0F, 0.0F);
        this.plinth01 = new ModelRenderer(this, 0, 24);
        this.plinth01.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.plinth01.addBox(-5.0F, -1.0F, -5.0F, 10, 3, 5, 0.0F);
        this.setRotateAngle(plinth01, 0.0F, 3.141592653589793F, 0.0F);
        this.plinth00 = new ModelRenderer(this, 0, 24);
        this.plinth00.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.plinth00.addBox(-5.0F, -1.0F, -5.0F, 10, 3, 5, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, -3.7F, -0.1F);
        this.head.addBox(-1.5F, -2.5F, -2.0F, 3, 3, 3, 0.0F);
        this.setRotateAngle(head, 0.22689280275926282F, 0.0F, 0.0F);
        this.tail02 = new ModelRenderer(this, 1, 12);
        this.tail02.setRotationPoint(0.0F, 0.0F, 4.3F);
        this.tail02.addBox(-1.0F, -1.01F, 0.0F, 2, 2, 5, 0.0F);
        this.setRotateAngle(tail02, -0.03490658503988659F, -0.8028514559173915F, 0.0F);
        this.star03 = new ModelRenderer(this, 22, 20);
        this.star03.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.star03.addBox(-1.5F, -1.49F, -0.5F, 3, 3, 1, 0.0F);
        this.tail01 = new ModelRenderer(this, 1, 12);
        this.tail01.setRotationPoint(0.0F, 0.0F, 4.3F);
        this.tail01.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 5, 0.0F);
        this.setRotateAngle(tail01, 0.06981317007977318F, -1.5009831567151235F, -0.2617993877991494F);
        this.lArm00 = new ModelRenderer(this, 17, 11);
        this.lArm00.setRotationPoint(0.4F, -0.2F, -1.0F);
        this.lArm00.addBox(0.7F, -0.5F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(lArm00, 0.6981317007977318F, 0.0F, -0.17453292519943295F);
        this.rHorn01 = new ModelRenderer(this, 22, 1);
        this.rHorn01.mirror = true;
        this.rHorn01.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.rHorn01.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(rHorn01, 0.3141592653589793F, -0.2792526803190927F, 0.0F);
        this.lHorn00 = new ModelRenderer(this, 21, 0);
        this.lHorn00.setRotationPoint(1.1F, -2.0F, 0.3F);
        this.lHorn00.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(lHorn00, 0.45378560551852565F, 0.17453292519943295F, 0.0F);
        this.tail02.addChild(this.tail03);
        this.body02.addChild(this.rLeg00);
        this.star00.addChild(this.star02);
        this.head.addChild(this.rHorn00);
        this.body01.addChild(this.body02);
        this.body00.addChild(this.body01);
        this.head.addChild(this.snout);
        this.body00.addChild(this.rArm00);
        this.body02.addChild(this.tail00);
        this.lHorn00.addChild(this.lHorn01);
        this.body00.addChild(this.neck);
        this.lArm00.addChild(this.lArm01);
        this.star00.addChild(this.star01);
        this.body02.addChild(this.lLeg00);
        this.rArm00.addChild(this.rArm01);
        this.plinth00.addChild(this.plinth01);
        this.neck.addChild(this.head);
        this.tail01.addChild(this.tail02);
        this.star00.addChild(this.star03);
        this.tail00.addChild(this.tail01);
        this.body00.addChild(this.lArm00);
        this.rHorn00.addChild(this.rHorn01);
        this.head.addChild(this.lHorn00);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.star00.render(f5);
        this.body00.render(f5);
        this.plinth00.render(f5);
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
