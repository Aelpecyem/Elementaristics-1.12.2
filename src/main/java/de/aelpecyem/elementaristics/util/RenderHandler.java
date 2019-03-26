package de.aelpecyem.elementaristics.util;

import de.aelpecyem.elementaristics.entity.EntitySilverThread;
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
    }
}
