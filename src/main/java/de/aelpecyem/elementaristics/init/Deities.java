package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.pantheon.Deity;
import de.aelpecyem.elementaristics.misc.pantheon.DeityBase;
import de.aelpecyem.elementaristics.misc.pantheon.DeitySupplyEffectBase;
import de.aelpecyem.elementaristics.misc.pantheon.advanced.DeityAzathoth;
import de.aelpecyem.elementaristics.misc.pantheon.advanced.DeityDragon;
import de.aelpecyem.elementaristics.util.TimeUtil;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class Deities {
    public static Map<ResourceLocation, Deity> deities = new HashMap<>();
    public static Deity deityNothingness;
    public static Deity deityAzathoth;
    public static Deity deityDragonAether;
    public static Deity deityDragonFire;
    public static Deity deityDragonEarth;
    public static Deity deityDragonWater;
    public static Deity deityDragonAir;
    public static Deity deityGateAndKey;
    public static Deity deityDreamer;
    public static Deity deityAngel;
    public static Deity deityStorm;
    public static Deity deityFighter;
    public static Deity deitySun;
    public static Deity deityHarbinger;
    public static Deity deityQueen;
    public static Deity deityGoat;
    public static Deity deityMoth;
    public static Deity deityThread;
    public static Deity deityMirror;
    public static Deity deityDancer;
    public static Deity deityKing;
    public static Deity deityMother;
    public static Deity deityMoon;
    public static Deity deityWitch;

    //todo add more use to a whole bunch of them (see notes) ---the ones with magan as aspect will not supply aspects later on (exception: the Witch)
    //todo add overhauled doc to updated use for all deities.
    public static void initDeities() {
        deityNothingness = new DeityBase(TimeUtil.getTickTimeStartForHour(0), new ResourceLocation(Elementaristics.MODID, "deity_nothingness"), 13553358); //pretty white color- might make it dark instead
        deityAzathoth = new DeityAzathoth();
        deityDragonAether = new DeityDragon(2, Aspects.aether, SoulInit.soulAncient, 4986465);
        deityDragonFire = new DeityDragon(3, Aspects.fire, SoulInit.soulFire, 16139267);
        deityDragonEarth = new DeityDragon(4, Aspects.earth, SoulInit.soulEarth, 15375);
        deityDragonWater = new DeityDragon(5, Aspects.water, SoulInit.soulWater, 1049560);
        deityDragonAir = new DeityDragon(6, Aspects.air, SoulInit.soulAir, 1300735);

       /* deityDragonEarth = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(4), new ResourceLocation(Elementaristics.MODID, "dragon_earth"), Aspects.earth, 15375);
        deityDragonWater = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(5), new ResourceLocation(Elementaristics.MODID, "dragon_water"), Aspects.water, 1049560);
        deityDragonAir = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(6), new ResourceLocation(Elementaristics.MODID, "dragon_air"), Aspects.air, 1300735);
      */
        deityGateAndKey = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(7), new ResourceLocation(Elementaristics.MODID, "gate_and_key"), Aspects.mind, 1131335);
        deityDreamer = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(8), new ResourceLocation(Elementaristics.MODID, "deity_dreamer"), Aspects.ice, 8497580);
        deityAngel = new DeityBase(TimeUtil.getTickTimeStartForHour(9), new ResourceLocation(Elementaristics.MODID, "deity_angel"), 4757545);
        deityStorm = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(10), new ResourceLocation(Elementaristics.MODID, "deity_storm"), Aspects.electricity, 1729436);
        deityFighter = new DeityBase(TimeUtil.getTickTimeStartForHour(11), new ResourceLocation(Elementaristics.MODID, "deity_fighter"), 1845376);
        deitySun = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(12), new ResourceLocation(Elementaristics.MODID, "deity_sun"), Aspects.light, 15194144);
        deityHarbinger = new DeityBase(TimeUtil.getTickTimeStartForHour(13), new ResourceLocation(Elementaristics.MODID, "deity_harbinger"), 664599);
        deityQueen = new DeityBase(TimeUtil.getTickTimeStartForHour(14), new ResourceLocation(Elementaristics.MODID, "deity_queen"), 6302785);
        deityGoat = new DeityBase(TimeUtil.getTickTimeStartForHour(15), new ResourceLocation(Elementaristics.MODID, "deity_goat"), 7819310);
        deityMoth = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(16), new ResourceLocation(Elementaristics.MODID, "deity_moth"), Aspects.chaos, 1052431);
        deityThread = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(17), new ResourceLocation(Elementaristics.MODID, "deity_thread"), Aspects.mana, 13442512);
        deityMirror = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(18), new ResourceLocation(Elementaristics.MODID, "deity_mirror"), Aspects.crystal, 2143412);
        deityDancer = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(19), new ResourceLocation(Elementaristics.MODID, "deity_dancer"), Aspects.soul, 11700900);
        deityKing = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(20), new ResourceLocation(Elementaristics.MODID, "deity_king"), Aspects.body, 6296600);
        deityMother = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(21), new ResourceLocation(Elementaristics.MODID, "deity_mother"), Aspects.life, 15481145);
        deityMoon = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(22), new ResourceLocation(Elementaristics.MODID, "deity_moon"), Aspects.order, 16508803);
        deityWitch = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(23), new ResourceLocation(Elementaristics.MODID, "deity_witch"), Aspects.magan, 15887104);


    }

    public static Deity getDeityByWorldTime(long time) {
        for (Deity deity : deities.values()) {
            if (TimeUtil.getTimeUnfalsified(time) >= deity.getTickTimeBegin() && TimeUtil.getTimeUnfalsified(time) <= deity.getTickTimeBegin() + 1000) {
                return deity;
            }
        }
        return null;
    }
}
