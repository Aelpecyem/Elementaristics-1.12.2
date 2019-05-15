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
    public static boolean useNumbersInsteadOfBar = false;
    //values- compat
    public static boolean useTcCompat = true;
    //values- dimensions
    public static int mindDimensionId = 1103;
    //values- misc
    public static int silverThreadId = 1103;
    public static int cultistId = 77766;
    public static int protoplasmId = 2602;
    public static int spellId = 7251;

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
        cultistId = cfg.getInt("cultistId", CATEGORY_MISC, cultistId, -10000, 10000, "Determines the ID for the Cultist Entity");
        silverThreadId = cfg.getInt("silverThreadId", CATEGORY_MISC, silverThreadId, -10000, 10000, "Determines the ID for the Silver Thread Entity");
        protoplasmId = cfg.getInt("protoplasmId", CATEGORY_MISC, protoplasmId, -10000, 10000, "Determines the ID for the Protoplasm Entity");
        spellId = cfg.getInt("spellId", CATEGORY_MISC, spellId, -10000, 10000, "Determines the ID for the Spell Projectile Entity");

    }
}
