package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.EntitySilverThread;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {
    public static final int SILVER_THREAD_ID = 1103;
    public static void init(){
        registerEntity("silver_thread", EntitySilverThread.class, SILVER_THREAD_ID, 50, 14474460, 10801103);
    }
    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2) {
        EntityRegistry.registerModEntity(new ResourceLocation(Elementaristics.MODID, name), entity, name, id, Elementaristics.instance, range, 1, true, color1, color2);
    }
}
