package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.pantheon.Deity;
import de.aelpecyem.elementaristics.misc.pantheon.DeitySupplyEffectBase;
import de.aelpecyem.elementaristics.util.TimeUtil;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class Deities {
    public static Map<ResourceLocation, Deity> deities = new HashMap<>();
    public static Deity deityNothingness;
    public static Deity deityAzathoth;
    public static DeitySupplyEffectBase deityDragonAether;
    public static DeitySupplyEffectBase deityDragonFire;
    public static DeitySupplyEffectBase deityDragonEarth;
    public static DeitySupplyEffectBase deityDragonWater;
    public static DeitySupplyEffectBase deityDragonAir;
    public static DeitySupplyEffectBase deityGateAndKey;
    public static DeitySupplyEffectBase deityDreamer;
    public static DeitySupplyEffectBase deityAngel;
    public static DeitySupplyEffectBase deityStorm;
    public static DeitySupplyEffectBase deityFighter;
    public static DeitySupplyEffectBase deitySun;
    public static DeitySupplyEffectBase deityHarbinger;
    public static DeitySupplyEffectBase deityQueen;
    public static DeitySupplyEffectBase deityGoat;
    public static DeitySupplyEffectBase deityMoth;
    public static DeitySupplyEffectBase deityThread;
    public static DeitySupplyEffectBase deityMirror;
    public static DeitySupplyEffectBase deityDancer;
    public static DeitySupplyEffectBase deityKing;
    public static DeitySupplyEffectBase deityMother;
    public static DeitySupplyEffectBase deityMoon;
    public static DeitySupplyEffectBase deityWitch;

    //todo: inworld rite to create the symbols (and upgrade them to statues) involving smooth enriched stone (with recipe class etc.)
    //todo add more use to a whole bunch of them (see notes) ---the ones with magan as aspect will not supply aspects later on (exception: the Witch)
    public static void initDeities() {
        deityNothingness = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(0), new ResourceLocation(Elementaristics.MODID, "deity_nothingness"), Aspects.magan, 13553358); //pretty white color- might make it dark instead
        deityAzathoth = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(1), new ResourceLocation(Elementaristics.MODID, "deity_azathoth"), Aspects.vacuum, 5065806);
        deityDragonAether = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(2), new ResourceLocation(Elementaristics.MODID, "dragon_aether"), Aspects.aether, 4986465);
        deityDragonFire = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(3), new ResourceLocation(Elementaristics.MODID, "dragon_fire"), Aspects.fire, 16139267);
        deityDragonEarth = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(4), new ResourceLocation(Elementaristics.MODID, "dragon_earth"), Aspects.earth, 15375);
        deityDragonWater = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(5), new ResourceLocation(Elementaristics.MODID, "dragon_water"), Aspects.water, 1049560);
        deityDragonAir = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(6), new ResourceLocation(Elementaristics.MODID, "dragon_air"), Aspects.air, 1300735);
        deityGateAndKey = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(7), new ResourceLocation(Elementaristics.MODID, "gate_and_key"), Aspects.mind, 1131335);
        deityDreamer = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(8), new ResourceLocation(Elementaristics.MODID, "deity_dreamer"), Aspects.ice, 8497580);
        deityAngel = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(9), new ResourceLocation(Elementaristics.MODID, "deity_angel"), Aspects.magan, 4757545);
        deityStorm = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(10), new ResourceLocation(Elementaristics.MODID, "deity_storm"), Aspects.electricity, 1729436);
        deityFighter = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(11), new ResourceLocation(Elementaristics.MODID, "deity_fighter"), Aspects.magan, 1845376);
        deitySun = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(12), new ResourceLocation(Elementaristics.MODID, "deity_sun"), Aspects.light, 15194144);
        deityHarbinger = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(13), new ResourceLocation(Elementaristics.MODID, "deity_harbinger"), Aspects.magan, 664599);
        deityQueen = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(14), new ResourceLocation(Elementaristics.MODID, "deity_queen"), Aspects.magan, 6302785);
        deityGoat = new DeitySupplyEffectBase(TimeUtil.getTickTimeStartForHour(15), new ResourceLocation(Elementaristics.MODID, "deity_goat"), Aspects.magan, 7819310);
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
