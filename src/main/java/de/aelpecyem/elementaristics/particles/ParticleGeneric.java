package de.aelpecyem.elementaristics.particles;

import de.aelpecyem.elementaristics.Elementaristics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//see https://github.com/Ellpeck/NaturesAura/blob/master/src/main/java/de/ellpeck/naturesaura/particles/ParticleMagic.java
@SideOnly(Side.CLIENT)
public class ParticleGeneric extends Particle {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Elementaristics.MODID, "items/essence_blank"); //may be dirty and hacky, but saves effort and one additional texture

    private final float desiredScale;
    private final boolean fade;
    private final boolean followPosition;
    private final float xTo, yTo, zTo;

    public ParticleGeneric(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade, float alpha) {
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
        xTo = 0;
        yTo = 0;
        zTo = 0;

        float r = (((color >> 16) & 255) / 255F) * (1F - this.rand.nextFloat() * 0.25F);
        float g = (((color >> 8) & 255) / 255F) * (1F - this.rand.nextFloat() * 0.25F);
        float b = ((color & 255) / 255F) * (1F - this.rand.nextFloat() * 0.25F);
        this.setRBGColorF(r, g, b);

        TextureMap map = Minecraft.getMinecraft().getTextureMapBlocks();
        this.setParticleTexture(map.getAtlasSprite(TEXTURE.toString()));

        this.particleAlpha = alpha;
        this.particleScale = this.desiredScale;
    }

    public ParticleGeneric(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade, boolean followPosition, float xTo, float yTo, float zTo) {
        super(world, posX, posY, posZ);
        this.desiredScale = scale;
        this.particleMaxAge = maxAge;
        this.canCollide = collision;
        this.particleGravity = gravity;
        this.fade = fade;

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

        TextureMap map = Minecraft.getMinecraft().getTextureMapBlocks();
        this.setParticleTexture(map.getAtlasSprite(TEXTURE.toString()));

        this.particleAlpha = 0.5F;
        this.particleScale = this.desiredScale;
    }

    @Override
    public void onUpdate() {
        if (followPosition) {
            motionX = (xTo - posX) / 20;
            motionY = (yTo - posY) / 20;
            motionZ = (zTo - posZ) / 20;
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

            if (this.fade) {
                float lifeRatio = (float) this.particleAge / (float) this.particleMaxAge;
                this.particleAlpha = 0.5F - (lifeRatio * 0.5F);
                this.particleScale = this.desiredScale - (this.desiredScale * lifeRatio);
            }
        }
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public int getBrightnessForRender(float f) {
        return 15 << 20 | 15 << 4;
    }
}
