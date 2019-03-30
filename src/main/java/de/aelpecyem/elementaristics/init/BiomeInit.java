package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.world.biomes.BiomeMind;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@GameRegistry.ObjectHolder(Elementaristics.MODID)
public class BiomeInit {
    public static final Biome MIND = new BiomeMind();
    public static void registerBiomes(){
        initBiome(MIND, "mind", false, BiomeManager.BiomeType.DESERT, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.MAGICAL);
    }

    private static Biome initBiome(Biome biome, String name, boolean genInWorld, BiomeManager.BiomeType biomeType, BiomeDictionary.Type... type){
        biome.setRegistryName(Elementaristics.MODID, name);
        ForgeRegistries.BIOMES.register(biome);
        BiomeDictionary.addTypes(biome, type);
        BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(biome, 10));
        if (genInWorld) {
            BiomeManager.addSpawnBiome(biome);
        }
        return biome;
    }


}
