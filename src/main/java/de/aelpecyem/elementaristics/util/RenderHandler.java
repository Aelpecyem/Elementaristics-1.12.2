package de.aelpecyem.elementaristics.util;

import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.entity.EntitySilverThread;
import de.aelpecyem.elementaristics.entity.projectile.EntityExplosionProjectile;
import de.aelpecyem.elementaristics.entity.projectile.EntitySpellProjectile;
import de.aelpecyem.elementaristics.entity.protoplasm.EntityProtoplasm;
import de.aelpecyem.elementaristics.entity.render.RenderCultist;
import de.aelpecyem.elementaristics.entity.render.RenderNone;
import de.aelpecyem.elementaristics.entity.render.RenderProtoplasm;
import de.aelpecyem.elementaristics.entity.render.RenderSilverThread;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {
    public static void registerEntityRenderers(){
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


    }
}
