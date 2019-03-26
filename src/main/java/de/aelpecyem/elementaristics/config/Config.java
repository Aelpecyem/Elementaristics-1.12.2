package de.aelpecyem.elementaristics.config;

import de.aelpecyem.elementaristics.Elementaristics;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class Config {

    private static final String CATEGORY_HUD = "hud";
    private static final String CATEGORY_SOULS = "souls";
    private static final String CATEGORY_DIM = "dimensions";
    //values- hud
    public static boolean useNumbersInsteadOfBar = false;
    //values- souls
    public static boolean normalGameplayBuffs = true;
    //values- dimensions
    public static int mindDimensionId = 1103;

    public static void readConfig() {
        Configuration cfg = Elementaristics.config;
        try {
            cfg.load();
            initDimConfig(cfg);
            initHudConfig(cfg);
            initSoulConfig(cfg);
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

    private static void initSoulConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_SOULS, "Configuration on Elementaristics' Soul Mechanic");
        // cfg.getBoolean() will get the value in the config if it is already specified there. If not it will create the value.
        normalGameplayBuffs = cfg.getBoolean("useGamplayBuffs", CATEGORY_SOULS, normalGameplayBuffs, "Determines whether players get gameplay buffs for a certain soul type");
    }

    private static void initDimConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_DIM, "Configuration on Elementaristics' dimensions");
        // cfg.getBoolean() will get the value in the config if it is already specified there. If not it will create the value.
        mindDimensionId = cfg.getInt("mindDimensionId", CATEGORY_DIM, mindDimensionId, -10000, 10000, "Determines the dimension id of 'The Mind'");

    }
}
