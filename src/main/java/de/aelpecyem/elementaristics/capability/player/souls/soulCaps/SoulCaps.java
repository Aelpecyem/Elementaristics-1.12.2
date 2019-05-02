package de.aelpecyem.elementaristics.capability.player.souls.soulCaps;

import de.aelpecyem.elementaristics.capability.player.souls.Soul;

import java.util.HashMap;
import java.util.Map;

public class SoulCaps {
    private static Map<Soul, SoulCap> caps = new HashMap<>();
    private static SoulCap standardCap = new SoulCap(null);
    public static SoulCap soulCapAir;
    public static SoulCap soulCapFire;
    public static SoulCap soulCapWater;
    public static SoulCap soulCapEarth;
    public static SoulCap soulCapMagan;

    public static SoulCap soulCapMana;
    public static SoulCap soulCapImmutable;
    public static SoulCap soulCapUnstable;
    public static SoulCap soulCapBalanced;

    public static SoulCap soulCapAncient;
    public static SoulCap soulCapDragon;


    public static void init() {
        soulCapAir = new SoulCapAir();
        soulCapFire = new SoulCapFire();
        soulCapWater = new SoulCapWater();
        soulCapEarth = new SoulCapEarth();
        soulCapMagan = new SoulCapMagan();

        soulCapMana = new SoulCapMana();
        soulCapImmutable = new SoulCapImmutable();
        soulCapUnstable = new SoulCapUnstable();
        soulCapBalanced = new SoulCapBalanced();

        soulCapAncient = new SoulCapAncient();
        soulCapDragon = new SoulCapDragon();
    }

    public static SoulCap getCapForSoul(Soul soul) {
        return caps.getOrDefault(soul, standardCap);
    }

    public static void addSoulCapToSoul(SoulCap cap, Soul soul) {
        caps.put(soul, cap);
    }
}
