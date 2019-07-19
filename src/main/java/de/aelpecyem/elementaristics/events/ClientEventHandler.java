package de.aelpecyem.elementaristics.events;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.particles.ParticleHandler;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import scala.collection.parallel.ParIterableLike;
import thaumcraft.common.config.ModConfig;

public class ClientEventHandler {
    @SubscribeEvent
    public void onRenderLast(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.mcProfiler.func_194340_a(() -> Elementaristics.MODID + ":renderParticles");
        ParticleHandler.renderParticles(event.getPartialTicks());
        mc.mcProfiler.endSection();
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().world == null) {
            ParticleHandler.clearParticles();
        } else if (!Minecraft.getMinecraft().isGamePaused()) {
            Minecraft.getMinecraft().mcProfiler.func_194340_a(() -> Elementaristics.MODID + ":updateParticles");
            ParticleHandler.updateParticles();
            Minecraft.getMinecraft().mcProfiler.endSection();
        }
    }
}
