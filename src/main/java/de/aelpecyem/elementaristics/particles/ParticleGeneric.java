package de.aelpecyem.elementaristics.particles;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//see https://github.com/Ellpeck/NaturesAura/blob/master/src/main/java/de/ellpeck/naturesaura/particles/ParticleMagic.java

@SideOnly(Side.CLIENT)
public class ParticleGeneric extends Particle {
    //the rendering with the ParticleHandler class might not be needed in 1.14, but idk, we'll see
    //also, for the 1.14 port, change the constructors and add option for also fading IN and not only out; this should be false for spells etc, but true for statues
    private final float desiredScale;
    private final boolean fade;
    private final boolean followPosition;
    private final double xTo, yTo, zTo;
    private final boolean shrink;

    public ParticleGeneric(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade, float alpha, boolean shrink) {
        super(world, posX, posY, posZ);
        this.desiredScale = scale;
        this.particleMaxAge = maxAge;
        this.canCollide = collision;
        this.particleGravity = gravity;
        this.fade = fade;

        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;

        followPosition = false;
        this.shrink = shrink;
        xTo = 0;
        yTo = 0;
        zTo = 0;

        float r = (((color >> 16) & 255) / 255F) * (1F - this.rand.nextFloat() * 0.25F);
        float g = (((color >> 8) & 255) / 255F) * (1F - this.rand.nextFloat() * 0.25F);
        float b = ((color & 255) / 255F) * (1F - this.rand.nextFloat() * 0.25F);
        this.setRBGColorF(r, g, b);

        this.particleAlpha = alpha;
        this.particleScale = this.desiredScale;
        setParticleTextureIndex(0);
    }

    public ParticleGeneric(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade, boolean shrink, boolean followPosition, double xTo, double yTo, double zTo) {
        super(world, posX, posY, posZ);
        this.desiredScale = scale;
        this.particleMaxAge = maxAge;
        this.canCollide = collision;
        this.particleGravity = gravity;
        this.fade = fade;
        this.shrink = shrink;

        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.followPosition = followPosition;
        this.xTo = xTo;
        this.yTo = yTo;
        this.zTo = zTo;

        float r = (((color >> 16) & 255) / 255F) * (1F - this.rand.nextFloat() * 0.25F);
        float g = (((color >> 8) & 255) / 255F) * (1F - this.rand.nextFloat() * 0.25F);
        float b = ((color & 255) / 255F) * (1F - this.rand.nextFloat() * 0.25F);
        this.setRBGColorF(r, g, b);


        this.particleAlpha = 0.5F;
        this.particleScale = this.desiredScale;
        setParticleTextureIndex(0);
    }


    @Override
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        double x = this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX;
        double y = this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY;
        double z = this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ;
        float sc = 0.1F * this.particleScale;

        int brightness = this.getBrightnessForRender(partialTicks);
        int sky = brightness >> 16 & 0xFFFF;
        int block = brightness & 0xFFFF;

        float f = 0;//(float) this.particleTextureIndexX / 16.0F;
        float f1 = 1;//f + 0.0624375F * 2;
        float f2 = 0; //(float) this.particleTextureIndexY / 16.0F;
        float f3 = 1; //f2 + 0.0624375F * 2;
        // float f4 = 0.1F * this.particleScale;

        buffer.pos(x + (-rotationX * sc - rotationXY * sc), y + -rotationZ * sc, z + (-rotationYZ * sc - rotationXZ * sc))
                .tex(f1, f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
                .lightmap(sky, block).endVertex();
        buffer.pos(x + (-rotationX * sc + rotationXY * sc), y + (rotationZ * sc), z + (-rotationYZ * sc + rotationXZ * sc))
                .tex(f1, f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
                .lightmap(sky, block).endVertex();
        buffer.pos(x + (rotationX * sc + rotationXY * sc), y + (rotationZ * sc), z + (rotationYZ * sc + rotationXZ * sc))
                .tex(f, f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
                .lightmap(sky, block).endVertex();
        buffer.pos(x + (rotationX * sc - rotationXY * sc), y + (-rotationZ * sc), z + (rotationYZ * sc - rotationXZ * sc))
                .tex(f, f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
                .lightmap(sky, block).endVertex();
    }


    @Override
    public void onUpdate() {
        if (followPosition) {
            double velX = motionX = (xTo - posX) / 20;
            double velY = motionY = (yTo - posY) / 20;
            double velZ = motionZ = (zTo - posZ) / 20;
            double vel = Math.sqrt(Math.abs(velX) + Math.abs(velY) + Math.abs(velZ));
            if (vel > 0.1) {
                double percentageX = velX / vel;
                double percentageY = velY / vel;
                double percentageZ = velZ / vel;
                motionX = 0.1 * percentageX;
                motionY = 0.1 * percentageY;
                motionZ = 0.1 * percentageZ;
            } else {
                motionX = velX;
                motionY = velY;
                motionZ = velZ;
            }
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        this.particleAge++;
        if (this.particleAge >= this.particleMaxAge) {
            this.setExpired();
        } else {
            this.motionY -= 0.04D * (double) this.particleGravity;
            this.move(this.motionX, this.motionY, this.motionZ);

            float lifeRatio = (float) this.particleAge / (float) this.particleMaxAge;
            if (this.fade) {
                this.particleAlpha = particleAlpha - (lifeRatio * particleAlpha);
            }
            if (this.shrink) {
                this.particleScale = this.desiredScale - (this.desiredScale * lifeRatio);
            }

        }
    }

    @Override
    public int getBrightnessForRender(float f) {
        return 15 << 20 | 15 << 4;
    }

}
