package de.aelpecyem.elementaristics.config;

import de.aelpecyem.elementaristics.Elementaristics;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class Config {

    private static final String CATEGORY_HUD = "hud";
    private static final String CATEGORY_COMPAT = "souls";
    private static final String CATEGORY_DIM = "dimensions";
    private static final String CATEGORY_MISC = "misc";
    //values- hud
    public static boolean useNumbersInsteadOfBar = true;
    //values- compat
    public static boolean useTcCompat = true;
    //values- dimensions
    public static int mindDimensionId = 1103;

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
        cfg.addCustomCategoryComment(CATEGORY_HUD, "Configuration on HUD elements of Elementaristics");

        useNumbersInsteadOfBar = cfg.getBoolean("useNumbersInsteadOfBar", CATEGORY_HUD, false, "Determines whether a bar or numbers should be shown for the Magan Hud");
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
    }
}
