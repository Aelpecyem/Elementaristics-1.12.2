package de.aelpecyem.elementaristics.world.biomes;

import de.aelpecyem.elementaristics.init.ModBlocks;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class BiomeMind extends Biome {
    public BiomeMind() {
        super(new BiomeProperties("Mind")
                .setBaseHeight(1)
                .setHeightVariation(0.2F)
                .setRainDisabled()
                .setTemperature(0.5F));
        spawnableCaveCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableMonsterList.clear();
        spawnableWaterCreatureList.clear();
        topBlock = ModBlocks.fabric_reason.getDefaultState();
        fillerBlock = ModBlocks.fabric_passion.getDefaultState();
    }


    @Override
    public boolean getEnableSnow() {
        return false;
    }
}
