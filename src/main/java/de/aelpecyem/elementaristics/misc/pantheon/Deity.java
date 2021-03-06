package de.aelpecyem.elementaristics.misc.pantheon;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.render.models.ModelBlock;
import de.aelpecyem.elementaristics.blocks.tileentity.tile.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.init.Deities;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public abstract class Deity {
    private long tickTimeBegin;
    private ResourceLocation name;
    private Aspect aspect;
    private int color;
    private ModelBase model;
    private ResourceLocation tex;

    public Deity(long tickTimeBegin, ResourceLocation name, @Nullable Aspect aspect, int color) {
        this.tickTimeBegin = tickTimeBegin;
        this.name = name;
        this.aspect = aspect;
        this.color = color;
        this.model = new ModelBlock();
        this.tex = new ResourceLocation(Elementaristics.MODID, "textures/blocks/stone_enriched.png");
        Deities.deities.put(name, this);
    }

    public Deity(long tickTimeBegin, ResourceLocation name, @Nullable Aspect aspect, int color, ModelBase model, ResourceLocation tex) {
        this.tickTimeBegin = tickTimeBegin;
        this.name = name;
        this.aspect = aspect;
        this.color = color;
        this.model = model;
        this.tex = tex;
        Deities.deities.put(name, this);
    }

    public ModelBase getModel() {
        return model;
    }

    public ResourceLocation getTexture() {
        return tex;
    }

    public int getColor() {
        return color;
    }

    public long getTickTimeBegin() {
        return tickTimeBegin;
    }

    public void setUpTile(TileEntityDeityShrine te){

    }
    public ResourceLocation getName() {
        return name;
    }

    public Aspect getAspect() {
        return aspect;
    }

    public void symbolEffect(TileEntityDeityShrine te){
        passiveParticles(te);
    }

    public void statueEffect(TileEntityDeityShrine te){
        passiveParticles(te);
    }

    public void normalize(TileEntityDeityShrine te) {
    }

    public void passiveParticles(TileEntityDeityShrine te){
        if (te.getWorld().getWorldTime() % 10 == 0 && te.getWorld().isRemote) {
            double motionX = te.getWorld().rand.nextGaussian() * 0.002D;
            double motionY = te.getWorld().rand.nextGaussian() * 0.002D;
            double motionZ = te.getWorld().rand.nextGaussian() * 0.002D;
            Elementaristics.proxy.generateGenericParticles(te.getWorld(), te.getPos().getX() + te.getWorld().rand.nextFloat(), te.getPos().getY() + te.getWorld().rand.nextFloat(), te.getPos().getZ() + te.getWorld().rand.nextFloat(), motionX, motionY, motionZ, getColor(), 1, 120, 0, false, false);
        }
    }

    public abstract void sacrificeEffect(TileEntityDeityShrine te);

    public void activeStatueEffect(TileEntityDeityShrine te, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    }

    public void activeStatueEffect(TileEntityDeityShrine te) {
    }

}
