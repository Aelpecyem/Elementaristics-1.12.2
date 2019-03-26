package de.aelpecyem.elementaristics.world;

import de.aelpecyem.elementaristics.init.ModDimensions;
import de.aelpecyem.elementaristics.world.dimensions.MindChunkGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.init.Biomes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class WorldTypeMind extends WorldType {

    public WorldTypeMind() {
        super("mind");
    }


    @Override
    public BiomeProvider getBiomeProvider(World world) {
        return new BiomeProviderSingle(Biomes.PLAINS);
    }

    /* (non-Javadoc)
     * @see net.minecraft.world.WorldType#getChunkGenerator(net.minecraft.world.World, java.lang.String)
     */
    @Override
    public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
        return new MindChunkGenerator(world);
    }

    /* (non-Javadoc)
     * @see net.minecraft.world.WorldType#getMinimumSpawnHeight(net.minecraft.world.World)
     */
    @Override
    public int getMinimumSpawnHeight(World world) {
        return world.getSeaLevel() + 1;
    }

    /* (non-Javadoc)
     * @see net.minecraft.world.WorldType#getHorizon(net.minecraft.world.World)
     */
    @Override
    public double getHorizon(World world) {
        return 63.0D;
    }

    /* (non-Javadoc)
     * @see net.minecraft.world.WorldType#voidFadeMagnitude()
     */
    @Override
    public double voidFadeMagnitude() {
        return 0.03125D;
    }

    /* (non-Javadoc)
     * @see net.minecraft.world.WorldType#handleSlimeSpawnReduction(java.util.Random, net.minecraft.world.World)
     */
    @Override
    public boolean handleSlimeSpawnReduction(Random random, World world) {
        return false;
    }

    /**
     * Called when 'Create New World' button is pressed before starting game.
     */
    @Override
    public void onGUICreateWorldPress() {
    }

    /**
     * Gets the spawn fuzz for players who join the world.
     * Useful for void world types.
     *
     * @param world  the world
     * @param server the server
     * @return Fuzz for entity initial spawn in blocks.
     */
    @Override
    public int getSpawnFuzz(WorldServer world, net.minecraft.server.MinecraftServer server) {
        return Math.max(0, server.getSpawnRadius(world));
    }

    /**
     * Called when the 'Customize' button is pressed on world creation GUI.
     *
     * @param mc             The Minecraft instance
     * @param guiCreateWorld the createworld GUI
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void onCustomizeButton(Minecraft mc, GuiCreateWorld guiCreateWorld) {
    }

    /**
     * Should world creation GUI show 'Customize' button for this world type?.
     *
     * @return if this world type has customization parameters
     */
    @Override
    public boolean isCustomizable() {
        return false;
    }

    /**
     * returns true if selecting this worldtype from the customize menu should display the generator.[worldtype].info
     * message
     *
     * @return true, if successful
     */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasInfoNotice() {
        return true;
    }

    /**
     * Get the height to render the clouds for this world type.
     *
     * @return The height to render clouds at
     */
    @Override
    public float getCloudHeight() {
        return 128.0F;
    }
}
