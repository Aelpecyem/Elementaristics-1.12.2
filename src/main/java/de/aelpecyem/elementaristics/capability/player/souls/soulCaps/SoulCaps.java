package de.aelpecyem.elementaristics.capability.player.souls.soulCaps;

import de.aelpecyem.elementaristics.capability.player.souls.Soul;

import java.util.HashMap;
import java.util.Map;

public class SoulCaps {
    private static Map<Soul, SoulCap> caps = new HashMap<>();
    private static SoulCap standardCap = new SoulCap(null);
    public static SoulCap soulCapAir;
    public static SoulCap soulCapInstable;

    public static void init() {
        soulCapAir = new SoulCapAir();
        soulCapInstable = new SoulCapInstable();
    }

    public static SoulCap getCapForSoul(Soul soul) {
        return caps.getOrDefault(soul, standardCap);
    }

    public static void addSoulCapToSoul(SoulCap cap, Soul soul) {
        caps.put(soul, cap);
    }
}
