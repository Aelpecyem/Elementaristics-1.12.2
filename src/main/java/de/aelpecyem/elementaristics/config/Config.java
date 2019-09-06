package de.aelpecyem.elementaristics.config;

import de.aelpecyem.elementaristics.Elementaristics;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class Config {

    public static final String CATEGORY_COMPAT = "compat";
    public static final String CATEGORY_DIM = "dimensions";
    public static final String CATEGORY_MISC = "misc";
    public static final String CATEGORY_HUD = "client";
    //values- hud
    public static boolean showBar = true;
    //values- compat
    public static boolean useTcCompat = true;
    //values- dimensions
    public static int mindDimensionId = 1103;
    //values- misc
    public static int nexusUpdateInterval = 10;

    public static void readConfig() {
        Configuration cfg = Elementaristics.config;
        try {
            cfg.load();
            initDimConfig(cfg);
            initHudConfig(cfg);
            initCompatConfig(cfg);
            initMiscConfig(cfg);
        } catch (Exception e1) {
            Elementaristics.LOGGER.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void initHudConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_HUD, "Configuration on client elements of Elementaristics");

        showBar = cfg.getBoolean("showBar", CATEGORY_HUD, true, "Determines whether a bar of the Magan HUD should be shown");
    }

    private static void initCompatConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_COMPAT, "Configuration on Elementaristics' cross-compat");
        useTcCompat = cfg.getBoolean("useTcCompat", CATEGORY_COMPAT, useTcCompat, "Determines whether Elementaristics should add its own section in Thaumcraft or not");
    }

    private static void initDimConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_DIM, "Configuration on Elementaristics' dimensions");
        mindDimensionId = cfg.getInt("mindDimensionId", CATEGORY_DIM, mindDimensionId, -10000, 10000, "Determines the dimension id of 'The Mind'");
    }

    private static void initMiscConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_MISC, "Misc Configuration");
        nexusUpdateInterval = cfg.getInt("nexusUpdateInterval", CATEGORY_MISC, nexusUpdateInterval, 1, 200, "Determines the amount of ticks to pass to update the Dimensional Nexus' local aspects and item power.");
    }
}
