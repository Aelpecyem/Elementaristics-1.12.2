package de.aelpecyem.elementaristics.world;

import de.aelpecyem.elementaristics.init.ModDimensions;
import de.aelpecyem.elementaristics.world.dimensions.MindChunkGenerator;
import net.minecraft.entity.Entity;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;

public class MindWorldProvider extends WorldProvider {
    @Override
    public DimensionType getDimensionType() {
        return ModDimensions.mindDimensionType;
    }

    @Override
    public String getSaveFolder() {
        return "MIND";
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new MindChunkGenerator(world);
    }

    @Override
    public BiomeProvider getBiomeProvider() {
        return new BiomeProviderSingle(Biomes.DEFAULT);
    }

    //other stuff to the dim

    @Override
    public IRenderHandler getCloudRenderer() {
        return null;
    }

    @Override
    public boolean shouldClientCheckLighting() {
        return true;
    }

    @Override
    public float getCloudHeight() {
        return 251;
    }

    @Override
    public float getSunBrightness(float par1) {
        return 1;
    }

    @Override
    public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
        return new Vec3d(0, 172, 206);
    }

    @Override
    public boolean isSkyColored() {
        return true;
    }

    @Override
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
        return new Vec3d(0, 172, 206);
    }

    @Override
    public boolean hasSkyLight() {
        return true;
    }
}
