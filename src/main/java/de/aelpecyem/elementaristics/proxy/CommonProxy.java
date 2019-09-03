package de.aelpecyem.elementaristics.proxy;

import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {

    }

    public void registerRenderers() {

    }


    public void registerItemRenderer(Item item, int meta, String id) {
    }

    public void generateGenericParticles(Entity entityIn, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade) {
    }

    public void generateGenericParticles(World world, double x, double y, double z, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade) {
    }

    public void generateGenericParticles(World world, double x, double y, double z, double velX, double velY, double velZ, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade) {
    }

    public void generateGenericParticles(ParticleGeneric particleGeneric) {
    }


    public void generateGenericParticles(World world, BlockPos pos, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade) {
    }

    public void generateParticleRay(World world, float xFrom, float yFrom, float zFrom, float xTo, float yTo, float zTo, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade) {

    }

    public void registerKeyBinds() {

    }


    public void registerColoring() {

    }

    public EntityPlayer getPlayer(final MessageContext context) {
        if (context.side.isServer()) {
            return context.getServerHandler().player;
        } else {
            throw new WrongSideException("Tried to get the player from a client-side MessageContext on the dedicated server");
        }
    }

    public IThreadListener getThreadListener(final MessageContext context) {
        if (context.side.isServer()) {
            return context.getServerHandler().player.mcServer;
        } else {
            throw new WrongSideException("Tried to get the IThreadListener from a client-side MessageContext on the dedicated server");
        }
    }

    class WrongSideException extends RuntimeException {
        public WrongSideException(final String message) {
            super(message);
        }

        public WrongSideException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
