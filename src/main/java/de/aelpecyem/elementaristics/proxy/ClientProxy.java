package de.aelpecyem.elementaristics.proxy;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.*;
import de.aelpecyem.elementaristics.blocks.tileentity.render.*;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.Vector;
import java.util.function.IntFunction;

public class ClientProxy extends CommonProxy {


    @Override
    public void registerRenderers() {


        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPurifier.class, new TESRPurifier());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLightningPedestal.class, new TESRLightningPedestal());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal.class, new TESRPedestal());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityConcentrator.class, new TESRConcentrator());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTunneler.class, new TESRTunneler());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFilterHolder.class, new TESRFilterHolder());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityReactor.class, new TESRReactor());
    }


    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Elementaristics.MODID + ":" + id, "inventory"));
    }


    @Override
    public void generateGenericParticles(Entity entityIn, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade) {
        double motionX = entityIn.world.rand.nextGaussian() * 0.02D;
        double motionY = entityIn.world.rand.nextGaussian() * 0.02D;
        double motionZ = entityIn.world.rand.nextGaussian() * 0.02D;
        Particle particleGeneric = new ParticleGeneric(
                entityIn.world,
                entityIn.posX + entityIn.world.rand.nextFloat() * entityIn.width
                        * 2.0F - entityIn.width,
                entityIn.posY + 0.5D + entityIn.world.rand.nextFloat()
                        * entityIn.height,
                entityIn.posZ + entityIn.world.rand.nextFloat() * entityIn.width
                        * 2.0F - entityIn.width,
                motionX,
                motionY,
                motionZ,
                color, scale, maxAge, gravity, collision, fade, false, 0, 0, 0);
        Minecraft.getMinecraft().effectRenderer.addEffect(particleGeneric);
    }

    public void generateGenericParticles(World world, double x, double y, double z, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade) {
        double motionX = world.rand.nextGaussian() * 0.02D;
        double motionY = world.rand.nextGaussian() * 0.02D;
        double motionZ = world.rand.nextGaussian() * 0.02D;
        Particle particleGeneric = new ParticleGeneric(world,
                x, y, z,
                motionX,
                motionY,
                motionZ,
                color, scale, maxAge, gravity, collision, fade, false, 0, 0, 0);
        Minecraft.getMinecraft().effectRenderer.addEffect(particleGeneric);
    }

    public void generateGenericParticles(World world, double x, double y, double z, double velX, double velY, double velZ, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade) {
        Particle particleGeneric = new ParticleGeneric(world,
                x, y, z,
                velX,
                velY,
                velZ,
                color, scale, maxAge, gravity, collision, fade, false, 0, 0, 0);
        Minecraft.getMinecraft().effectRenderer.addEffect(particleGeneric);
    }

    public void generateGenericParticles(World world, BlockPos pos, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade) {
        double motionX = world.rand.nextGaussian() * 0.02D;
        double motionY = world.rand.nextGaussian() * 0.02D;
        double motionZ = world.rand.nextGaussian() * 0.02D;
        Particle particleGeneric = new ParticleGeneric(world,
                pos.getX(), pos.getY(), pos.getZ(),
                motionX,
                motionY,
                motionZ,
                color, scale, maxAge, gravity, collision, fade, false, 0, 0, 0);
        Minecraft.getMinecraft().effectRenderer.addEffect(particleGeneric);
    }


    @Override
    public void generateGenericParticles(ParticleGeneric particleGeneric) {
        Minecraft.getMinecraft().effectRenderer.addEffect(particleGeneric);
    }
}

