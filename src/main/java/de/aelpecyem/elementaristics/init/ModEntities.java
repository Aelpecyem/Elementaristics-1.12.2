package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.config.Config;
import de.aelpecyem.elementaristics.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {
    public static final int SILVER_THREAD_ID = Config.silverThreadId;
    public static final int CULTIST_ID = Config.cultistId;
    public static final int PROTOPLASM_ID = Config.protoplasmId;
    public static final int SPELL_ID = Config.spellId;
    public static final int EXPLOSION_ID = 4145;
    public static void init(){
        registerEntity("silver_thread", EntitySilverThread.class, SILVER_THREAD_ID, 200, 14474460, 10801103);
        registerEntity("cultist", EntityCultist.class, CULTIST_ID, 50, 1638451, 1638424);
        registerEntity("protoplasm", EntityProtoplasm.class, PROTOPLASM_ID, 50, 327728, 337968);
        registerEntity("projectile_spell", EntitySpellProjectile.class, SPELL_ID, 50, 1, 1);
        registerEntity("projectile_explosion", EntityExplosionProjectile.class, EXPLOSION_ID, 50, 1, 1);

    }
    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2) {
        EntityRegistry.registerModEntity(new ResourceLocation(Elementaristics.MODID, name), entity, name, id, Elementaristics.instance, range, 1, true, color1, color2);
    }
}
