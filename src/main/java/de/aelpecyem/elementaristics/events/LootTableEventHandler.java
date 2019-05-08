package de.aelpecyem.elementaristics.events;

import com.google.common.collect.ImmutableList;
import de.aelpecyem.elementaristics.Elementaristics;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class LootTableEventHandler {
    private static final List<String> TABLES = ImmutableList.of(
            "inject/abandoned_mineshaft",
            "inject/desert_pyramid",
            "inject/jungle_temple",
            "inject/simple_dungeon",
            "inject/stronghold_corridor",
            "inject/village_blacksmith",
            "inject/end_city_treasure",
            "inject/igloo_chest",
            "inject/nether_bridge",
            "inject/stronghold_crossing",
            "inject/stronghold_library",
            "inject/woodland_mansion"
    );

    public LootTableEventHandler() {
        for (String s : TABLES) {
            LootTableList.register(new ResourceLocation(Elementaristics.MODID, s));
        }
    }
    @SubscribeEvent
    public void lootLoad(LootTableLoadEvent evt) {
        String prefix = "minecraft:chests/";
        String name = evt.getName().toString();

        if (name.startsWith(prefix)) {
            String file = name.substring(name.indexOf(prefix) + prefix.length());
            switch (file) {
                case "abandoned_mineshaft":
                case "end_city_treasure":
                case "igloo_chest":
                case "nether_bridge":
                case "stronghold_crossing":
                case "stronghold_library":
                case "woodland_mansion":
                case "desert_pyramid":
                case "jungle_temple":
                case "simple_dungeon":
                case "stronghold_corridor":
                case "village_blacksmith": evt.getTable().addPool(getInjectPool(file)); break;
                default: break;
            }
        }
    }

    private LootPool getInjectPool(String entryName) {
        return new LootPool(new LootEntry[]{getInjectEntry(entryName, 1)}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "elementaristics_inject_pool");
    }

    private LootEntryTable getInjectEntry(String name, int weight) {
        return new LootEntryTable(new ResourceLocation(Elementaristics.MODID, "inject/" + name), weight, 0, new LootCondition[0], "elementaristics_inject_entry");
    }

}
