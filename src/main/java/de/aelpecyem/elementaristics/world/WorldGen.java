package de.aelpecyem.elementaristics.world;

import de.aelpecyem.elementaristics.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGen implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.getDimension() == 0) { // the overworld
            generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
    }

    //TODO adjust values
    private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        generateOre(ModBlocks.ore_prismarine.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 15, 40, random.nextInt(3) + 2, 4);
        generateOre(ModBlocks.ore_hydrogen.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 10, 30, 3, random.nextInt(2) + 4);
        generateOre(ModBlocks.ore_helium.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 10, 30, 3, random.nextInt(2) + 4);
    }

    //chances = veins per chunk
    private void generateOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances) {
        int deltaY = maxY - minY;

        for (int i = 0; i < chances; i++) {
            BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16));

            WorldGenMinable generator = new WorldGenMinable(ore, size);
            generator.generate(world, random, pos);
        }
    }
}
