package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.rites.*;
import de.aelpecyem.elementaristics.misc.rites.ascension.RiteReforging;
import de.aelpecyem.elementaristics.misc.rites.crafting.RiteRecruiting;
import de.aelpecyem.elementaristics.misc.rites.ascension.RiteKnowledge;
import de.aelpecyem.elementaristics.misc.rites.crafting.RiteChaos;
import de.aelpecyem.elementaristics.misc.rites.crafting.RiteForging;
import de.aelpecyem.elementaristics.misc.rites.killing.RiteSacrifice;
import de.aelpecyem.elementaristics.misc.rites.misc.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class RiteInit {
    private static Map<ResourceLocation, RiteBase> RITES = new HashMap<>();

    public static RiteKnowledge riteKnowledge = new RiteKnowledge();
    public static RiteReforging riteReforging = new RiteReforging();

    public static RiteChaos riteChaos = new RiteChaos();
    public static RiteForging riteForging = new RiteForging();

    public static RiteFeast riteFeast = new RiteFeast();
    public static RiteDay riteDay = new RiteDay();
    public static RiteNight riteNight = new RiteNight();
    public static RiteRecruiting riteRecruiting = new RiteRecruiting();
    public static RiteWeather riteWeather = new RiteWeather();
    public static RiteBinding riteBinding = new RiteBinding();
    public static RiteUnbinding riteUnbinding = new RiteUnbinding();
    public static RiteHealthShare riteHealthShare = new RiteHealthShare();

    public static RiteSacrifice riteConflagration = new RiteSacrifice("conflagration_soul", Aspects.fire, SoulInit.soulFire, DamageSource.ON_FIRE);
    public static RiteSacrifice riteShredding = new RiteSacrifice("rage_wind", Aspects.air, SoulInit.soulAir, Elementaristics.DAMAGE_AIR);
    public static RiteSacrifice riteGaiasGaze = new RiteSacrifice("gaias_gaze", Aspects.earth, SoulInit.soulEarth, DamageSource.FLY_INTO_WALL);
    public static RiteSacrifice riteDrowningAstral = new RiteSacrifice("drowning_astral", Aspects.water, SoulInit.soulWater, DamageSource.DROWN);
    public static RiteSacrifice riteCompression = new RiteSacrifice("soul_compression", Aspects.aether, DamageSource.MAGIC);

    public static void registerRite(ResourceLocation name, RiteBase rite){
        RITES.put(name, rite);
    }

    public static void init(){
        registerRite(riteKnowledge.name, RiteInit.riteKnowledge);
        registerRite(riteReforging.name, RiteInit.riteReforging);

        registerRite(riteChaos.name, RiteInit.riteChaos);
        registerRite(riteForging.name, RiteInit.riteForging);

        registerRite(riteFeast.name, RiteInit.riteFeast);
        registerRite(riteRecruiting.name, RiteInit.riteRecruiting);
        registerRite(riteWeather.name, RiteInit.riteWeather);
        registerRite(riteDay.name, RiteInit.riteDay);
        registerRite(riteNight.name, RiteInit.riteNight);
        registerRite(riteBinding.name, RiteInit.riteBinding);
        registerRite(riteUnbinding.name, RiteInit.riteUnbinding);
        registerRite(riteHealthShare.name, RiteInit.riteHealthShare);

        registerRite(riteConflagration.name, riteConflagration);
        registerRite(riteShredding.name, riteShredding);
        registerRite(riteGaiasGaze.name, riteGaiasGaze);
        registerRite(riteDrowningAstral.name, riteDrowningAstral);
        registerRite(riteCompression.name, riteCompression);
    }

    public static RiteBase getRiteForResLoc(String rite) {
        if (RITES.get(new ResourceLocation(rite)) != null) {
            return RITES.get(new ResourceLocation(rite));
        }
        return null;
    }
}
