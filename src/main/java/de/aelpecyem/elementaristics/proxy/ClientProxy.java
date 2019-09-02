package de.aelpecyem.elementaristics.proxy;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.*;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.blocks.tileentity.render.*;
import de.aelpecyem.elementaristics.entity.render.RenderHandler;
import de.aelpecyem.elementaristics.events.ClientEventHandler;
import de.aelpecyem.elementaristics.events.HUDRenderHandler;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.misc.ItemColorHandler;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import de.aelpecyem.elementaristics.particles.ParticleHandler;
import de.aelpecyem.elementaristics.util.ClientTickHandler;
import de.aelpecyem.elementaristics.util.Keybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        registerRenderers();
        RenderHandler.registerEntityRenderers();
        Keybinds.register();

    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        registerColoring();
        registerKeyBinds();
        MinecraftForge.EVENT_BUS.register(new HUDRenderHandler());
        MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());

    }

    @Override
    public void registerColoring() {
        super.registerColoring();
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemColorHandler(), ModItems.essence, ModItems.scale);
    }

    @Override
    public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPurifier.class, new TESRPurifier());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal.class, new TESRPedestal());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityConcentrator.class, new TESRConcentrator());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTunneler.class, new TESRTunneler());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFilterHolder.class, new TESRFilterHolder());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityReactor.class, new TESRReactor());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityInfusionBasin.class, new TESRBasin());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGoldenThread.class, new TESRGoldenThread());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDeityShrine.class, new TESRShrine());
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
                color, scale, maxAge, gravity, collision, fade, fade, false, 0, 0, 0);
        ParticleHandler.spawnParticle(() -> particleGeneric);
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
                color, scale, maxAge, gravity, collision, fade, fade, false, 0, 0, 0);
        ParticleHandler.spawnParticle(() -> particleGeneric);
    }

    public void generateGenericParticles(World world, double x, double y, double z, double velX, double velY, double velZ, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade) {
        Particle particleGeneric = new ParticleGeneric(world,
                x, y, z,
                velX,
                velY,
                velZ,
                color, scale, maxAge, gravity, collision, fade, fade, false, 0, 0, 0);
        ParticleHandler.spawnParticle(() -> particleGeneric);
    }

    public void generateGenericParticles(World world, BlockPos pos, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade) {
        double motionX = world.rand.nextGaussian() * 0.02D;
        double motionY = world.rand.nextGaussian() * 0.02D;
        double motionZ = world.rand.nextGaussian() * 0.02D;
        Particle particleGeneric = new ParticleGeneric(world,
                pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F,
                motionX,
                motionY,
                motionZ,
                color, scale, maxAge, gravity, collision, fade, fade, false, 0, 0, 0);
        ParticleHandler.spawnParticle(() -> particleGeneric);
    }


    @Override
    public void generateGenericParticles(ParticleGeneric particleGeneric) {
        ParticleHandler.spawnParticle(() -> particleGeneric);
    }

    @Override
    public void registerKeyBinds() {
        super.registerKeyBinds();
    }

    @Override
    public EntityPlayer getPlayer(final MessageContext context) {
        if (context.side.isClient()) {
            return Minecraft.getMinecraft().player;
        } else {
            return context.getServerHandler().player;
        }
    }

    @Override
    public IThreadListener getThreadListener(final MessageContext context) {
        if (context.side.isClient()) {
            return Minecraft.getMinecraft();
        } else {
            return context.getServerHandler().player.mcServer;
        }
    }
}

