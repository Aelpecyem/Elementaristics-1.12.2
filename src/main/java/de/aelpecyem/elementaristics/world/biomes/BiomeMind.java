package de.aelpecyem.elementaristics.world.biomes;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class BiomeMind extends Biome {
    public BiomeMind() {
        super(new BiomeProperties("mind")
                .setBaseHeight(1)
                .setHeightVariation(0.2F)
                .setRainDisabled()
                .setTemperature(0.2F));
        spawnableCaveCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableMonsterList.clear();
        spawnableWaterCreatureList.clear();
    }



    @Override
    public BiomeDecorator createBiomeDecorator() {
        return super.createBiomeDecorator();
    }


    @Override
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
        int seaLevel = worldIn.getSeaLevel();
        IBlockState surfaceBlock = topBlock;
        IBlockState mainBlock = fillerBlock;
        int j = -1;
        int noise = (int) (noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int chunkX = x & 15;
        int chunkZ = z & 15;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int primerY = 255; primerY >= 0; --primerY) {
            // lay down bedrock layer
            if (primerY <= rand.nextInt(5)) {
                chunkPrimerIn.setBlockState(chunkX, primerY, chunkZ, BEDROCK);
            } else {
                IBlockState blockAtPosition = chunkPrimerIn.getBlockState(chunkX, primerY, chunkZ);

                if (blockAtPosition.getMaterial() == Material.AIR) {
                    j = -1;
                } else if (blockAtPosition.getBlock() == fillerBlock) {
                    if (j == -1) {
                        // create area for ocean
                        if (noise <= 0) {
                            surfaceBlock = AIR;
                            mainBlock = fillerBlock;
                        }
                        // handle near sea level
                        else if (primerY >= seaLevel - 4 && primerY <= seaLevel + 1) {
                            surfaceBlock = topBlock;
                            mainBlock = fillerBlock;
                        }

                        // area exposed to air will be ocean
                        if (primerY < seaLevel && (surfaceBlock == null || surfaceBlock.getMaterial() == Material.AIR)) {
                            if (getTemperature(pos.setPos(x, primerY, z)) < 0.15F) {
                                surfaceBlock = ICE;
                            } else {
                                surfaceBlock = WATER;
                            }
                        }

                        j = noise;

                        if (primerY >= seaLevel - 1) {
                            chunkPrimerIn.setBlockState(chunkX, primerY, chunkZ, surfaceBlock);
                        }
                        // fill in ocean bottom
                        else if (primerY < seaLevel - 7 - noise) {
                            surfaceBlock = AIR;
                            mainBlock = fillerBlock;
                            chunkPrimerIn.setBlockState(chunkX, primerY, chunkZ, fillerBlock);
                        } else {
                            chunkPrimerIn.setBlockState(chunkX, primerY, chunkZ, mainBlock);
                        }
                    } else if (j > 0) // fill in terrain with main block
                    {
                        --j;
                        chunkPrimerIn.setBlockState(chunkX, primerY, chunkZ, mainBlock);
                    }
                    super.genTerrainBlocks(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
                }
            }
        }

    }
}
