package de.aelpecyem.elementaristics.proxy;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.CapabilityHandler;
import de.aelpecyem.elementaristics.capability.player.souls.soulCaps.SoulCaps;
import de.aelpecyem.elementaristics.compat.thaumcraft.ThaumcraftCompat;
import de.aelpecyem.elementaristics.events.EventHandler;
import de.aelpecyem.elementaristics.events.HUDRenderHandler;
import de.aelpecyem.elementaristics.events.LootTableEventHandler;
import de.aelpecyem.elementaristics.gui.GuiHandler;
import de.aelpecyem.elementaristics.init.*;
import de.aelpecyem.elementaristics.items.base.thaumagral.ItemThaumagral;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import de.aelpecyem.elementaristics.util.ClientTickHandler;
import de.aelpecyem.elementaristics.world.WorldGen;
import de.aelpecyem.elementaristics.world.structures.WorldGenAnomaly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.util.function.IntFunction;

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
