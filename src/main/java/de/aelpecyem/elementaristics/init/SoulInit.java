package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.souls.Soul;
import de.aelpecyem.elementaristics.capability.souls.soulCaps.SoulCaps;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SoulInit {


    public static List<Soul> souls = new ArrayList<Soul>();
    public static Soul soulMagan;
    public static Soul soulAir;
    public static Soul soulEarth;
    public static Soul soulWater;
    public static Soul soulFire;
    public static Soul soulAether;
    public static Soul soulMana;
    public static Soul soulInstable;
    public static Soul soulImmutable;
    public static Soul soulBalanced;
    public static Soul soulDragon;
    public static Soul soulAncient;

    public static Soul soulApostleGod;
    public static Soul soulTrue;
    public static Soul soulFirst;

    public static Soul soulMachnie;
    public static Soul soulSentient;
    public static Soul soulServant;

    public static void init() {
        soulMagan = new Soul("soul_magan", 15702784, EnumSoulRarity.COMMON, 110, 0.2F);
        soulAir = new Soul("soul_air", 40948,EnumSoulRarity.COMMON);
        soulEarth = new Soul("soul_earth",26368, EnumSoulRarity.COMMON);
        soulWater = new Soul("soul_water", 1279, EnumSoulRarity.COMMON);
        soulFire = new Soul("soul_fire", 15157504, EnumSoulRarity.COMMON);
        soulAether = new Soul("soul_aether", 10354943, EnumSoulRarity.COMMON);

        soulMana = new Soul("soul_mana", 15597809, EnumSoulRarity.RARE, 120, 0.4F);
        soulInstable = new Soul("soul_instable", 7208960, EnumSoulRarity.RARE);
        soulImmutable = new Soul("soul_immutable", 5922140, EnumSoulRarity.RARE, 90, 0.12F);
        soulBalanced = new Soul("soul_balanced", 14015459, EnumSoulRarity.RARE, 115, 0.2F);

        soulDragon = new Soul("soul_dragon", 16772352, EnumSoulRarity.EXTRAODINARY, 130, 0.42F);
        soulAncient = new Soul("soul_ancient", 399112, EnumSoulRarity.EXTRAODINARY, 140, 0.5F);

        soulApostleGod = new Soul("soul_apostle", 16373760, EnumSoulRarity.ASCENDED, 130, 0.5F);
        soulTrue = new Soul("soul_true", 16777215, EnumSoulRarity.ASCENDED, 130, 0.4F);
        soulFirst = new Soul("soul_first", 9111320, EnumSoulRarity.ASCENDED, 180, 0.7F);

        soulMachnie = new Soul("soul_machine", 3634072, EnumSoulRarity.ASCENDED, 110,0.1F);
        soulSentient = new Soul("soul_sentient", 730980, EnumSoulRarity.ASCENDED, 120, 0.4F);
        soulServant = new Soul("soul_servant", 4163316, EnumSoulRarity.ASCENDED, 160, 0.5F);

    }

    public static void addSoul(String name, int color, int id, EnumSoulRarity rarity) {
        souls.add(new Soul(id, name, color, rarity));
    }

    public static void addSoul(Soul soul) {
        souls.add(soul);
    }

    public static Soul getSoulFromName(String name) {
        for (Soul soul : souls) {
            if (soul.getName().equals(name)) {
                return soul;
            }
        }
        return null;
    }

    public static Soul getSoulFromId(int id) {
        for (Soul soul : souls) {
            if (soul.getId() == id) {
                return soul;
            }
        }
        return soulMagan;
    }

    public static void updateSoulInformation(EntityPlayer player, IPlayerCapabilities caps) {
        SoulCaps.getCapForSoul(SoulInit.getSoulFromId(caps.getSoulId())).buffsOnSpawning(player, caps);
        caps.setMaganRegenPerTick(SoulInit.getSoulFromId(caps.getSoulId()).getMaganRegenPerTick());
        caps.setMaxMagan(SoulInit.getSoulFromId(caps.getSoulId()).getMaxMagan());
    }

    public static Soul generateSoulType() {
        boolean notApproved = true;
        Random random = new Random();
        Soul soul = null;
        while (notApproved) {
            int num = random.nextInt(souls.size());

            soul = getSoulFromId(num);

            if (soul.getRarity() == EnumSoulRarity.COMMON) {
                notApproved = false;
            }
            if (soul.getRarity() == EnumSoulRarity.RARE) {
                if (random.nextInt(3) == 0) {
                    notApproved = false;
                }
            }
            if (soul.getRarity() == EnumSoulRarity.EXTRAODINARY) {
                if (random.nextInt(7) == 0) {
                    notApproved = false;
                }

            }
        }
        return soul;

    }

    public enum EnumSoulRarity {
        COMMON,
        RARE,
        EXTRAODINARY,
        ASCENDED
    }
}
