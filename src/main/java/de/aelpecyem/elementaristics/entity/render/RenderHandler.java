package de.aelpecyem.elementaristics.entity.render;

import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.entity.EntitySilverThread;
import de.aelpecyem.elementaristics.entity.elementals.EntityAetherElemental;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.entity.projectile.EntityElementalSpell;
import de.aelpecyem.elementaristics.entity.projectile.EntityExplosionProjectile;
import de.aelpecyem.elementaristics.entity.projectile.EntitySpellProjectile;
import de.aelpecyem.elementaristics.entity.protoplasm.EntityProtoplasm;
import de.aelpecyem.elementaristics.entity.render.elemental.RenderElementalAether;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {
    public static void registerEntityRenderers(){
        RenderingRegistry.registerEntityRenderingHandler(EntityDimensionalNexus.class, new IRenderFactory<EntityDimensionalNexus>() {
            @Override
            public Render<? super EntityDimensionalNexus> createRenderFor(RenderManager manager) {
                return new RenderNone(manager);
            }
        });

        RenderingRegistry.registerEntityRenderingHandler(EntitySilverThread.class, new IRenderFactory<EntitySilverThread>() {
            @Override
            public Render<? super EntitySilverThread> createRenderFor(RenderManager manager) {
                return new RenderSilverThread(manager);
            }
        });

        RenderingRegistry.registerEntityRenderingHandler(EntityCultist.class, new IRenderFactory<EntityCultist>() {
            @Override
            public Render<? super EntityCultist> createRenderFor(RenderManager manager) {
                return new RenderCultist(manager);
            }
        });

        RenderingRegistry.registerEntityRenderingHandler(EntityProtoplasm.class, new IRenderFactory<EntityProtoplasm>() {
            @Override
            public Render<? super EntityProtoplasm> createRenderFor(RenderManager manager) {
                return new RenderProtoplasm(manager);
            }
        });

        RenderingRegistry.registerEntityRenderingHandler(EntityAetherElemental.class, new IRenderFactory<EntityAetherElemental>() {
            @Override
            public Render<? super EntityAetherElemental> createRenderFor(RenderManager manager) {
                return new RenderElementalAether(manager);
            }
        });

        RenderingRegistry.registerEntityRenderingHandler(EntitySpellProjectile.class, new IRenderFactory<EntitySpellProjectile>() {
            @Override
            public Render<? super EntitySpellProjectile> createRenderFor(RenderManager manager) {
                return new RenderNone(manager);
            }
        });

        RenderingRegistry.registerEntityRenderingHandler(EntityExplosionProjectile.class, new IRenderFactory<EntityExplosionProjectile>() {
            @Override
            public Render<? super EntityExplosionProjectile> createRenderFor(RenderManager manager) {
                return new RenderNone(manager);
            }
        });

        RenderingRegistry.registerEntityRenderingHandler(EntityElementalSpell.class, new IRenderFactory<EntityElementalSpell>() {
            @Override
            public Render<? super EntityElementalSpell> createRenderFor(RenderManager manager) {
                return new RenderNone(manager);
            }
        });


    }
}
