package de.aelpecyem.elementaristics.blocks.tileentity.render.models.pantheon;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelMoth - cybercat5555
 * Created using Tabula 7.0.1
 */
public class ModelMoth extends ModelBase {
    public ModelRenderer plinth00;
    public ModelRenderer abdomen;
    public ModelRenderer plinth01;
    public ModelRenderer plinth02;
    public ModelRenderer plinth03;
    public ModelRenderer plinth04;
    public ModelRenderer thoraxa;
    public ModelRenderer head;
    public ModelRenderer lWing00a;
    public ModelRenderer rWing00a;
    public ModelRenderer lWing01a;
    public ModelRenderer rWing01a;
    public ModelRenderer lLeg00;
    public ModelRenderer rLeg00;
    public ModelRenderer lLeg01;
    public ModelRenderer rLeg01;
    public ModelRenderer lLeg02;
    public ModelRenderer rLeg02;
    public ModelRenderer thoraxb;
    public ModelRenderer lAntenna00;
    public ModelRenderer rAntenna00;
    public ModelRenderer lAntenna01;
    public ModelRenderer rAntenna01;

    public ModelMoth() {
        this.textureWidth = 36;
        this.textureHeight = 32;
        this.plinth00 = new ModelRenderer(this, 0, 24);
        this.plinth00.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.plinth00.addBox(-5.0F, -1.0F, -5.0F, 10, 3, 5, 0.0F);
        this.plinth02 = new ModelRenderer(this, 0, 1);
        this.plinth02.setRotationPoint(0.0F, -1.0F, 1.0F);
        this.plinth02.addBox(-3.0F, -6.0F, -3.0F, 6, 6, 6, 0.0F);
        this.rAntenna00 = new ModelRenderer(this, 25, 24);
        this.rAntenna00.mirror = true;
        this.rAntenna00.setRotationPoint(-1.0F, -2.2F, -0.9F);
        this.rAntenna00.addBox(-3.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
        this.setRotateAngle(rAntenna00, 0.0F, 0.0F, -0.17453292519943295F);
        this.thoraxb = new ModelRenderer(this, 25, 7);
        this.thoraxb.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.thoraxb.addBox(-0.2F, 0.0F, -1.7F, 2, 5, 3, 0.0F);
        this.lAntenna01 = new ModelRenderer(this, 25, 27);
        this.lAntenna01.setRotationPoint(2.7F, 0.0F, 0.0F);
        this.lAntenna01.addBox(0.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
        this.setRotateAngle(lAntenna01, 0.0F, 0.0F, 0.24434609527920614F);
        this.rWing00a = new ModelRenderer(this, 0, 13);
        this.rWing00a.mirror = true;
        this.rWing00a.setRotationPoint(-0.9F, -0.6F, -2.0F);
        this.rWing00a.addBox(-3.5F, 0.0F, -0.51F, 6, 10, 1, 0.0F);
        this.setRotateAngle(rWing00a, 0.0F, 0.0F, 0.7853981633974483F);
        this.lLeg00 = new ModelRenderer(this, 0, 0);
        this.lLeg00.setRotationPoint(1.1F, -0.9F, 0.0F);
        this.lLeg00.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(lLeg00, -0.2792526803190927F, 0.0F, 0.7853981633974483F);
        this.rLeg02 = new ModelRenderer(this, 0, 0);
        this.rLeg02.mirror = true;
        this.rLeg02.setRotationPoint(-1.1F, 0.3F, 0.0F);
        this.rLeg02.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(rLeg02, -0.2792526803190927F, 0.0F, -2.0943951023931953F);
        this.plinth03 = new ModelRenderer(this, 0, 1);
        this.plinth03.mirror = true;
        this.plinth03.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.plinth03.addBox(-3.0F, -6.0F, -3.0F, 6, 6, 6, 0.0F);
        this.thoraxa = new ModelRenderer(this, 25, 7);
        this.thoraxa.setRotationPoint(0.0F, 0.8F, -0.5F);
        this.thoraxa.addBox(-1.8F, 0.0F, -1.7F, 2, 5, 3, 0.0F);
        this.rLeg01 = new ModelRenderer(this, 0, 0);
        this.rLeg01.mirror = true;
        this.rLeg01.setRotationPoint(-1.1F, -0.3F, 0.0F);
        this.rLeg01.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(rLeg01, -0.2792526803190927F, 0.0F, -1.5707963267948966F);
        this.plinth04 = new ModelRenderer(this, 0, 1);
        this.plinth04.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.plinth04.addBox(-3.0F, -3.0F, -3.0F, 6, 3, 6, 0.0F);
        this.rAntenna01 = new ModelRenderer(this, 25, 27);
        this.rAntenna01.mirror = true;
        this.rAntenna01.setRotationPoint(-2.7F, 0.0F, 0.0F);
        this.rAntenna01.addBox(-3.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
        this.setRotateAngle(rAntenna01, 0.0F, 0.0F, -0.24434609527920614F);
        this.rLeg00 = new ModelRenderer(this, 0, 0);
        this.rLeg00.mirror = true;
        this.rLeg00.setRotationPoint(-1.1F, -0.9F, 0.0F);
        this.rLeg00.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(rLeg00, -0.2792526803190927F, 0.0F, -0.7853981633974483F);
        this.rWing01a = new ModelRenderer(this, 14, 13);
        this.rWing01a.mirror = true;
        this.rWing01a.setRotationPoint(-0.5F, 0.9F, -2.2F);
        this.rWing01a.addBox(-3.0F, 0.0F, -0.5F, 4, 6, 1, 0.0F);
        this.setRotateAngle(rWing01a, 0.0F, 0.0F, 0.20943951023931953F);
        this.abdomen = new ModelRenderer(this, 23, 0);
        this.abdomen.setRotationPoint(0.0F, 12.0F, -2.7F);
        this.abdomen.addBox(-1.5F, -2.0F, -2.5F, 3, 3, 3, 0.0F);
        this.head = new ModelRenderer(this, 24, 16);
        this.head.setRotationPoint(0.0F, -1.9F, -1.0F);
        this.head.addBox(-1.5F, -2.5F, -1.5F, 3, 3, 3, 0.0F);
        this.setRotateAngle(head, 0.3490658503988659F, 0.0F, 0.0F);
        this.lWing00a = new ModelRenderer(this, 0, 13);
        this.lWing00a.setRotationPoint(0.9F, -0.6F, -2.0F);
        this.lWing00a.addBox(-3.0F, 0.0F, -0.51F, 6, 10, 1, 0.0F);
        this.setRotateAngle(lWing00a, 0.0F, 0.0F, -0.9599310885968813F);
        this.lLeg01 = new ModelRenderer(this, 0, 0);
        this.lLeg01.setRotationPoint(1.1F, -0.3F, 0.0F);
        this.lLeg01.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(lLeg01, -0.2792526803190927F, 0.0F, 1.5707963267948966F);
        this.lAntenna00 = new ModelRenderer(this, 25, 24);
        this.lAntenna00.setRotationPoint(1.0F, -2.2F, -1.2F);
        this.lAntenna00.addBox(0.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
        this.setRotateAngle(lAntenna00, 0.0F, 0.0F, 0.17453292519943295F);
        this.lLeg02 = new ModelRenderer(this, 0, 0);
        this.lLeg02.setRotationPoint(1.1F, 0.3F, 0.0F);
        this.lLeg02.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(lLeg02, -0.2792526803190927F, 0.0F, 2.0943951023931953F);
        this.plinth01 = new ModelRenderer(this, 0, 24);
        this.plinth01.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.plinth01.addBox(-5.0F, -1.0F, -5.0F, 10, 3, 5, 0.0F);
        this.setRotateAngle(plinth01, 0.0F, 3.141592653589793F, 0.0F);
        this.lWing01a = new ModelRenderer(this, 14, 13);
        this.lWing01a.setRotationPoint(0.5F, 0.9F, -2.2F);
        this.lWing01a.addBox(-1.5F, 0.0F, -0.51F, 4, 6, 1, 0.0F);
        this.setRotateAngle(lWing01a, 0.0F, 0.0F, -0.20943951023931953F);
        this.plinth00.addChild(this.plinth02);
        this.head.addChild(this.rAntenna00);
        this.thoraxa.addChild(this.thoraxb);
        this.lAntenna00.addChild(this.lAntenna01);
        this.abdomen.addChild(this.rWing00a);
        this.abdomen.addChild(this.lLeg00);
        this.abdomen.addChild(this.rLeg02);
        this.plinth02.addChild(this.plinth03);
        this.abdomen.addChild(this.thoraxa);
        this.abdomen.addChild(this.rLeg01);
        this.plinth03.addChild(this.plinth04);
        this.rAntenna00.addChild(this.rAntenna01);
        this.abdomen.addChild(this.rLeg00);
        this.abdomen.addChild(this.rWing01a);
        this.abdomen.addChild(this.head);
        this.abdomen.addChild(this.lWing00a);
        this.abdomen.addChild(this.lLeg01);
        this.head.addChild(this.lAntenna00);
        this.abdomen.addChild(this.lLeg02);
        this.plinth00.addChild(this.plinth01);
        this.abdomen.addChild(this.lWing01a);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.plinth00.render(f5);
        this.abdomen.render(f5);
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
