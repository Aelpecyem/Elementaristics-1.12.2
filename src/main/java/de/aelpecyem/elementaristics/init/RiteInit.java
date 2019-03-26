package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.misc.elements.ElementInit;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import de.aelpecyem.elementaristics.misc.rites.RiteChaos;
import de.aelpecyem.elementaristics.misc.rites.RiteFeast;
import de.aelpecyem.elementaristics.misc.rites.RiteKnowledge;
import de.aelpecyem.elementaristics.misc.rites.killing.RiteSacrifice;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class RiteInit {
    private static Map<ResourceLocation, RiteBase> RITES = new HashMap<>();

    public static RiteKnowledge riteKnowledge = new RiteKnowledge();
    public static RiteFeast riteFeast = new RiteFeast();
    public static RiteChaos riteChaos = new RiteChaos();
    public static RiteSacrifice riteConflagration = new RiteSacrifice("conflagration_soul", ElementInit.fire, SoulInit.soulFire);
    public static RiteSacrifice riteShredding = new RiteSacrifice("rage_wind", ElementInit.air, SoulInit.soulAir);
    public static RiteSacrifice riteGaiasGaze = new RiteSacrifice("gaias_gaze", ElementInit.earth, SoulInit.soulEarth);
    public static RiteSacrifice riteDrowningAstral = new RiteSacrifice("drowning_astral", ElementInit.water, SoulInit.soulWater);
    public static RiteSacrifice riteSpaceExilation = new RiteSacrifice("space_exilation", ElementInit.aether, SoulInit.soulAether);
    public static void registerRite(ResourceLocation name, RiteBase rite){
        RITES.put(name, rite);
    }
    public static void init(){
        registerRite(riteConflagration.name, riteConflagration);
        registerRite(riteShredding.name, riteShredding);
        registerRite(riteGaiasGaze.name, riteGaiasGaze);
        registerRite(riteDrowningAstral.name, riteDrowningAstral);
        registerRite(riteSpaceExilation.name, riteSpaceExilation);

        registerRite(riteFeast.name, RiteInit.riteFeast);
        registerRite(riteKnowledge.name, RiteInit.riteKnowledge);
        registerRite(riteChaos.name, RiteInit.riteChaos);
    }
    public static RiteBase getRiteForResLoc(String rite) {
        if (RITES.get(new ResourceLocation(rite)) != null) {
            return RITES.get(new ResourceLocation(rite));
        }
        return null;
    }
}
