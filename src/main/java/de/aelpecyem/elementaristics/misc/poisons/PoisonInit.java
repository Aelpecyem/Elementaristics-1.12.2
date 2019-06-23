package de.aelpecyem.elementaristics.misc.poisons;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PoisonInit {
    public static Map<Integer, PoisonEffectBase> poisons = new HashMap<>();

    public static PoisonEffectBase poisonGlassfinger;

    public static void init() {
        poisonGlassfinger = new PoisonGlassfinger();
    }
}
