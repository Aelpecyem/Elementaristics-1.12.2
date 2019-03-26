package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.config.Config;
import de.aelpecyem.elementaristics.world.MindWorldProvider;
import de.aelpecyem.elementaristics.world.WorldTypeMind;
import de.aelpecyem.elementaristics.world.biomes.BiomeMind;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@GameRegistry.ObjectHolder(Elementaristics.MODID)
public class ModDimensions {
    public final static Biome mindBiome = new BiomeMind().setRegistryName(Elementaristics.MODID, "mind");
    public static DimensionType mindDimensionType; //usual id = 1103
    public static WorldType mindType;

    public static void init() {

        registerDimensionTypes();
        registerDimensions();
    }

    public static void registerBiomes(IForgeRegistry<Biome> event) {
        event.register(mindBiome);
        mindType = new WorldTypeMind();
    }

    private static void registerDimensionTypes() {
        mindDimensionType = DimensionType.register(Elementaristics.MODID, "_mind", Config.mindDimensionId, MindWorldProvider.class, false);
    }

    private static void registerDimensions() {
        DimensionManager.registerDimension(Config.mindDimensionId, mindDimensionType);
    }
}
