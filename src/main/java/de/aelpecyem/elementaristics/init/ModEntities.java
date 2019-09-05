package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.entity.EntitySilverThread;
import de.aelpecyem.elementaristics.entity.elementals.EntityAetherElemental;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.entity.projectile.EntityElementalSpell;
import de.aelpecyem.elementaristics.entity.projectile.EntityExplosionProjectile;
import de.aelpecyem.elementaristics.entity.projectile.EntitySpellProjectile;
import de.aelpecyem.elementaristics.entity.protoplasm.EntityProtoplasm;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {
    public static int entityId = 0;
    public static void init(){
        registerEntity("dimensional_nexus", EntityDimensionalNexus.class, 40, 8073887, 14315008);
        registerEntity("silver_thread", EntitySilverThread.class, 200, 14474460, 10801103);
        registerEntity("cultist", EntityCultist.class, 50, 1638451, 1638424);
        registerEntity("protoplasm", EntityProtoplasm.class, 50, 327728, 337968);
        registerEntity("elemental_aether", EntityAetherElemental.class, 50, 1415, 267888); //TODO, work on the AI and stuff later on
        registerEntity("projectile_spell", EntitySpellProjectile.class, 50);
        registerEntity("projectile_explosion", EntityExplosionProjectile.class, 50);
        registerEntity("projectile_elemental", EntityElementalSpell.class, 50);
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int range, int color1, int color2) {
        EntityRegistry.registerModEntity(new ResourceLocation(Elementaristics.MODID, name), entity, name, entityId++, Elementaristics.instance, range, 1, false, color1, color2);
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int range) {
        EntityRegistry.registerModEntity(new ResourceLocation(Elementaristics.MODID, name), entity, name, entityId++, Elementaristics.instance, range, 1, true);
    }
}
