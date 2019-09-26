package de.aelpecyem.elementaristics.particles;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.config.Config;
import de.aelpecyem.elementaristics.util.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

//snatched directly from https://github.com/Ellpeck/NaturesAura/blob/master/src/main/java/de/ellpeck/naturesaura/particles/ParticleHandler.java
@SideOnly(Side.CLIENT)
public final class ParticleHandler {
    public static final ResourceLocation PARTICLE_TEX = new ResourceLocation(Elementaristics.MODID, "textures/misc/particle_base_1.png");

    private static final List<Particle> PARTICLES = new CopyOnWriteArrayList<>();
    private static final List<Particle> PARTICLES_DARK = new CopyOnWriteArrayList<>(); //use a larger texture with indexes!
    public static int range = 32;

    public static void spawnParticle(Supplier<Particle> particle) {
        Minecraft mc = Minecraft.getMinecraft();
        Config.EnumParticles particleAmount = Config.client.particleAmount;
        switch (particleAmount) {
            case STANDARD:
                break;
            case REDUCED:
                if (mc.world.rand.nextInt(3) != 0) {
                    return;
                }
                break;
            case MINIMAL:
                if (mc.world.rand.nextInt(10) != 0) {
                    return;
                }
                break;
        }
        boolean isDark = ColorUtil.isDark(particle.get().getRedColorF(), particle.get().getGreenColorF(), particle.get().getBlueColorF());
        if (!isDark) {
            PARTICLES.add(particle.get());
        } else {
            PARTICLES_DARK.add(particle.get());
        }
    }

    public static void updateParticles() {
        updateList(PARTICLES);
        updateList(PARTICLES_DARK);

        range = 32;
    }

    private static void updateList(List<Particle> particles) {
        for (int i = particles.size() - 1; i >= 0; i--) {
            Particle particle = particles.get(i);
            particle.onUpdate();
            if (!particle.isAlive())
                particles.remove(i);
        }
    }

    public static void renderParticles(float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;

        //GlStateManager.DestFactor destFactor = GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA; //_ALPHA; //CONSTANT_COLOR;  //usually ONE
        //GlStateManager.SourceFactor sourceFactor = GlStateManager.SourceFactor.SRC_ALPHA; //usually SRC_ALPHA
        if (player != null) {
            float x = ActiveRenderInfo.getRotationX();
            float z = ActiveRenderInfo.getRotationZ();
            float yz = ActiveRenderInfo.getRotationYZ();
            float xy = ActiveRenderInfo.getRotationXY();
            float xz = ActiveRenderInfo.getRotationXZ();

            Particle.interpPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
            Particle.interpPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
            Particle.interpPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
            Particle.cameraViewDir = player.getLook(partialTicks);

            GlStateManager.pushMatrix();

            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.alphaFunc(516, 0.003921569F);
            GlStateManager.disableCull();
            GlStateManager.depthMask(false);
            mc.getTextureManager().bindTexture(PARTICLE_TEX);

            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            Tessellator t = Tessellator.getInstance();
            BufferBuilder buffer = t.getBuffer();
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
            for (Particle particle : PARTICLES) {
                particle.renderParticle(buffer, player, partialTicks, x, xz, z, yz, xy);
            }
            t.draw();


            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
            for (Particle particle : PARTICLES_DARK) {
                particle.renderParticle(buffer, player, partialTicks, x, xz, z, yz, xy);
            }
            t.draw();


            GlStateManager.enableCull();
            GlStateManager.depthMask(true);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA); //ONE_MINUS_SRC_ALPHA
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(516, 0.1F);

            GlStateManager.popMatrix();
        }
    }

    public static int getParticleAmount(boolean depth) {
        return depth ? PARTICLES.size() : PARTICLES_DARK.size();
    }

    public static void clearParticles() {
        if (!PARTICLES.isEmpty())
            PARTICLES.clear();
        if (!PARTICLES_DARK.isEmpty())
            PARTICLES_DARK.clear();
    }
}
