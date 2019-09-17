package de.aelpecyem.elementaristics.config;

import de.aelpecyem.elementaristics.Elementaristics;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@net.minecraftforge.common.config.Config(modid = Elementaristics.MODID)
public class Config {
    @net.minecraftforge.common.config.Config.Comment("Determines the dimension ID of \"the Mind\".")
    @net.minecraftforge.common.config.Config.LangKey(value = "elementaristics.config.mind_dimension_id")
    public static int mindDimensionId = 1103;

    @net.minecraftforge.common.config.Config.Comment("Determines the amount of ticks to pass until the Dimensional Nexus updates its information concerning rites. Do note that all information is also updated before the rite ends.")
    @net.minecraftforge.common.config.Config.LangKey(value = "elementaristics.config.nexus_update_interval")
    public static int nexusUpdateInterval = 10;

    @net.minecraftforge.common.config.Config.LangKey(value = "elementaristics.config.category_client")
    @net.minecraftforge.common.config.Config.Comment("Client-side settings of Elementaristics.")
    public static final Client client = new Client();

    public enum EnumParticles {
        STANDARD,
        REDUCED,
        MINIMAL
    }

    public static class Client {
        @net.minecraftforge.common.config.Config.LangKey(value = "elementaristics.config.show_magan_bar")
        @net.minecraftforge.common.config.Config.Comment("Determines whether the Magan bar should be shown.")
        public boolean showBar = true;

        @net.minecraftforge.common.config.Config.LangKey(value = "elementaristics.config.cultist_textures")
        @net.minecraftforge.common.config.Config.Comment("Set this to false to disable aspect-based Cultist textures. Instead, the standard texture for Cultists will be used.")
        public boolean cultistTextures = true;

        @net.minecraftforge.common.config.Config.LangKey(value = "elementaristics.config.particle_amount")
        @net.minecraftforge.common.config.Config.Comment("This will set the amount of particles Elementaristics spawns. Personal recommendation: do not change this, as the majority of the mod's effects are based on particles.")
        public EnumParticles particleAmount = EnumParticles.STANDARD;

    }

    @Mod.EventBusSubscriber(modid = Elementaristics.MODID)
    private static class EventHandler {
        /**
         * Inject the new values and save to the config file when the config has been changed from the GUI.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Elementaristics.MODID)) {
                ConfigManager.sync(Elementaristics.MODID, net.minecraftforge.common.config.Config.Type.INSTANCE);
            }
        }
    }
}
