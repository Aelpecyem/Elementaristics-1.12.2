package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.config.Config;
import de.aelpecyem.elementaristics.world.biomes.BiomeMind;
import de.aelpecyem.elementaristics.world.dimensions.DimensionMind;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;


public class ModDimensions {
    public static final DimensionType MIND = DimensionType.register("Mind", "_mind", Config.mindDimensionId, DimensionMind.class, false);

    public static void init() {
        registerDimensionTypes();
        registerDimensions();
    }

    private static void registerDimensionTypes() {

    }

    private static void registerDimensions() {
        DimensionManager.registerDimension(MIND.getId(), MIND);
    }
}
