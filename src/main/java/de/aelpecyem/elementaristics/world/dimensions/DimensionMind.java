package de.aelpecyem.elementaristics.world.dimensions;

import de.aelpecyem.elementaristics.init.BiomeInit;
import de.aelpecyem.elementaristics.init.ModDimensions;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.gen.ChunkGeneratorFlat;
import net.minecraft.world.gen.ChunkGeneratorHell;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DimensionMind extends WorldProvider{
    public DimensionMind(){
           this.biomeProvider = new BiomeProviderSingle(BiomeInit.MIND);
    }
    @Override
    public void init(){
        this.biomeProvider = new BiomeProviderSingle(BiomeInit.MIND);
    }



    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorMind(this.world, this.world.getWorldInfo().isMapFeaturesEnabled(), this.world.getSeed());
    }

    /**
     * Returns 'true' if in the "main surface world", but 'false' if in the Nether or End dimensions.
     */
    public boolean isSurfaceWorld()
    {
        return false;
    }

    /**
     * Will check if the x, z position specified is alright to be set as the map spawn point
     */
    public boolean canCoordinateBeSpawn(int x, int z)
    {
        return false;
    }

    @Override
    public boolean isDaytime() {
        return true;
    }


    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z)
    {
        return true;
    }

    public WorldBorder createWorldBorder()
    {
        return new WorldBorder()
        {
            public double getCenterX()
            {
                return super.getCenterX() / 8.0D;
            }
            public double getCenterZ()
            {
                return super.getCenterZ() / 8.0D;
            }
        };
    }


    @Override
    public long getWorldTime() {
        return 1000;
    }

    @Override
    public DimensionType getDimensionType() {
        return ModDimensions.MIND;
    }

    @Override
    public boolean shouldClientCheckLighting() {
        return false;
    }

    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return 0.5F;
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    protected void generateLightBrightnessTable() {
        float f = 0.1F;

        for (int i = 0; i <= 15; ++i) {
            float f1 = 1.0F - (float) i / 15.0F;
            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * 0.9F + 0.4F;
        }
    }
}
