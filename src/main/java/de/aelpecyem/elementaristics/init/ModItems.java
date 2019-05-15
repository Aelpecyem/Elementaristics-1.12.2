package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.items.base.*;

import de.aelpecyem.elementaristics.items.base.artifacts.*;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IncantationBase;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.ItemAspects;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.materials.*;
import de.aelpecyem.elementaristics.items.base.bauble.ItemBaubleCharger;
import de.aelpecyem.elementaristics.items.base.bauble.ItemWaterBody;
import de.aelpecyem.elementaristics.items.base.burnable.ItemHerbBundle;
import de.aelpecyem.elementaristics.items.base.burnable.ItemOpiumTincture;
import de.aelpecyem.elementaristics.items.base.consumable.ItemBaseSeed;
import de.aelpecyem.elementaristics.items.base.consumable.ItemDrinkBase;
import de.aelpecyem.elementaristics.items.base.consumable.ItemFoodBase;
import de.aelpecyem.elementaristics.items.base.consumable.ItemTinctureArcane;
import de.aelpecyem.elementaristics.items.base.thaumagral.*;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.util.IHasModel;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static List<Item> ITEMS = new ArrayList<>();

    public static ItemBase bundle_herbs;
    public static ItemBase opium_tincture;
    public static ItemDrinkBase tincture_arcane;

    public static ItemBase petal_opium;
    public static ItemSeedBase seed_herb;
    public static ItemBase ash;
    public static ItemBase chaotic_matter;
    public static ItemBase maganized_matter;
    public static ItemBase protoplasm;
    public static ItemFoodBase base_seed;
    public static ItemBase vibrant_quartz;
    public static ItemBase matter_accelerating_module;
    public static ItemBase head_hammer;

    public static ItemBase cluster_aether;
    public static ItemBase cluster_air;
    public static ItemBase cluster_earth;
    public static ItemBase cluster_water;
    public static ItemBase cluster_fire;

    public static ItemBase essence_blank;


    public static ItemSoulChanger soul_changer;
    public static ItemSoulMirror soul_mirror;

    public static ItemPickaxe hammer_heat;
    public static ItemSword dagger_sacrificial;
    public static ItemThaumagral thaumagral_iron;
    public static ItemThaumagral thaumagral_gold;
    public static ItemThaumagral thaumagral_diamond;
    public static ItemThaumagral thaumagral_stone;
    public static ItemThaumagral thaumagral_wood;

    public static ItemAspects heart_stone;
    public static ItemAspects tool_channeling;

    public static IncantationBase incantation_chaos;
    public static IncantationBase incantation_light;
    public static IncantationBase incantation_feast;
    public static IncantationBase incantation_conflagration;
    public static IncantationBase incantation_wind;
    public static IncantationBase incantation_gaia;
    public static IncantationBase incantation_depths;
    public static IncantationBase incantation_compression;
    public static IncantationBase incantation_forging;
    public static IncantationBase incantation_recruiting;
    public static IncantationBase incantation_weather;
    public static IncantationBase incantation_day;
    public static IncantationBase incantation_night;
    public static IncantationBase incantation_binding;
    public static IncantationBase incantation_unbinding;

    public static ItemDrinkBase water_purest;
    public static ItemAspects sparks_living;
    public static ItemAspects earth_purest;
    public static ItemAspects motion_captured;
    public static ItemAspects stardust;

    public static ItemAspects sparks_brightest;
    public static ItemDrinkBase wine_redmost;
    public static ItemAspects thoughts_battling; //after entering the mind
    public static ItemAspects vacuum_selfsustaining;
    public static ItemBase lightning_tangible;
    public static ItemAspects moss_everchaning;
    public static ItemAspects gem_triangular;
    public static ItemAspects catalyst_ordering;
    public static ItemAspects catalyst_entropizing;
    public static ItemFoodBase flesh_lamb;

    public static ItemAspects soul_dead; //Mana
    public static ItemAspects matter_astral_body; //weaker soul aspect
    public static ItemAspects sands_soul;

    //Baubles
    public static ItemBase charger_soul;
    public static ItemBase body_water;

   /* public static ItemArmor hood_cultist = new RobesCultist("hood_cultist",1, EntityEquipmentSlot.HEAD);
    public static ItemArmor garb_cultist = new RobesCultist("garb_cultist",1, EntityEquipmentSlot.CHEST);
    public static ItemArmor legwear_cultist = new RobesCultist("legwear_cultist",2, EntityEquipmentSlot.LEGS);
    public static ItemArmor boots_cultist = new RobesCultist("boots_cultist",1, EntityEquipmentSlot.FEET);
*/

    public static ItemEssence essence = new ItemEssence();


    public static void init() {
        bundle_herbs = new ItemHerbBundle();
        petal_opium = new ItemBase("petal_opium");
        seed_herb = new ItemSeedBase("seeds_opium", ModBlocks.crop_opium, Blocks.FARMLAND);
        opium_tincture = new ItemOpiumTincture();
        tincture_arcane = new ItemTinctureArcane();

        vibrant_quartz = new ItemBase("quartz_vibrant");
        chaotic_matter = new ItemBase("matter_chaotic");
        maganized_matter = new ItemBase("matter_maganized"); //1 of each primal essence 1 chaotic matter = 8 Maganized

        cluster_aether = new ItemClusterUnrefined("cluster_aether", new ItemStack(essence, 1, Aspects.aether.getId()));
        cluster_earth = new ItemClusterUnrefined("cluster_earth", new ItemStack(essence, 1, Aspects.earth.getId()));
        cluster_air = new ItemClusterUnrefined("cluster_air", new ItemStack(essence, 1, Aspects.air.getId()));
        cluster_water = new ItemClusterUnrefined("cluster_water", new ItemStack(essence, 1, Aspects.water.getId()));
        cluster_fire = new ItemClusterUnrefined("cluster_fire", new ItemStack(essence, 1, Aspects.fire.getId()));

        head_hammer = new ItemBase("head_hammer");
        essence_blank = new ItemBase("essence_blank");


        matter_accelerating_module = new ItemBase("module_matter_accelerating");

        soul_changer = new ItemSoulChanger();

        ash = new ItemBase("ash");
        protoplasm = new ItemBase("protoplasm");
        base_seed = new ItemBaseSeed(); //make it possible to craft many different plants/seeds with this
        //book_liber_elementium = new LiberElementiumItem();

        initRiteTools();
        initRiteMaterials();
        thaumagral_iron = new ItemThaumagral("thaumagral_iron", Item.ToolMaterial.IRON, 1, 1);
        thaumagral_gold = new ItemThaumagral("thaumagral_gold", Item.ToolMaterial.GOLD, 0.8F, 1.3F);
        thaumagral_diamond = new ItemThaumagral("thaumagral_diamond", Item.ToolMaterial.DIAMOND, 1.25F, 1.25F);
        thaumagral_stone = new ItemThaumagral("thaumagral_stone", Item.ToolMaterial.STONE, 1.2F, 0.8F);
        thaumagral_wood = new ItemThaumagral("thaumagral_wood", Item.ToolMaterial.WOOD, 1.5F, 0.5F);

        charger_soul = new ItemBaubleCharger();
        body_water = new ItemWaterBody();
    }

    private static void initRiteMaterials() {
        water_purest = new ItemWaterPurest();
        sparks_living = new ItemSparksLiving();
        earth_purest = new ItemAspects("earth_purest", 6, true, Aspects.earth);
        motion_captured = new ItemAspects("motion_captured", 6, true, Aspects.air);
        stardust = new ItemAspects("stardust", 6, true, Aspects.aether);
        
        sparks_brightest = new ItemAspects("sparks_brightest", 6, true, Aspects.light);
        wine_redmost = new ItemWineRedmost();
        thoughts_battling = new ItemAspects("thoughts_battling", 6, true, "tooltip.thoughts_battling.name", Aspects.mind);
        vacuum_selfsustaining = new ItemAspects("vacuum_selfsustaining", 6, true, Aspects.vacuum);
        lightning_tangible = new ItemLightningTangible();
        moss_everchaning = new ItemAspects("moss_everchanging", 6, true, Aspects.life);
        gem_triangular = new ItemAspects("gem_arcane", 6, true, Aspects.crystal);
        catalyst_ordering = new ItemAspects("catalyst_ordering", 6, true, Aspects.order);
        catalyst_entropizing = new ItemAspects("catalyst_entropizing", 6, true, Aspects.chaos); //1 aether essence 3 chaotic matter pieces
        flesh_lamb = new ItemFleshLamb();

        soul_dead = new ItemAspects("soul_dead", 4, true, Aspects.mana);
        matter_astral_body = new ItemAspects("matter_astral_body", 3, true, Aspects.aether);
        sands_soul = new ItemAspects("powder_soul", 6, true, Aspects.soul); //out of the 5 primal materials + astral stuff + dead soul

    }

    private static void initRiteTools() {
        incantation_chaos = new IncantationBase("incantation_chaos", RiteInit.riteChaos, Aspects.chaos, 4);
        incantation_light = new IncantationBase("incantation_light", RiteInit.riteKnowledge, Aspects.light, 4);
        incantation_feast = new IncantationBase("incantation_feast", RiteInit.riteFeast, Aspects.life, 4);
        incantation_conflagration = new IncantationBase("incantation_conflagration", RiteInit.riteConflagration, Aspects.fire, 4);
        incantation_wind = new IncantationBase("incantation_wind", RiteInit.riteShredding, Aspects.air, 4);
        incantation_gaia = new IncantationBase("incantation_gaia", RiteInit.riteGaiasGaze, Aspects.earth, 4);
        incantation_depths = new IncantationBase("incantation_depths", RiteInit.riteDrowningAstral, Aspects.water, 4);
        incantation_compression = new IncantationBase("incantation_compression", RiteInit.riteCompression, Aspects.aether, 4);
        incantation_forging = new IncantationBase("incantation_forging", RiteInit.riteForging, Aspects.fire, 4);
        incantation_recruiting = new IncantationBase("incantation_recruiting", RiteInit.riteRecruiting, Aspects.soul, 2);
        incantation_weather = new IncantationBase("incantation_weather", RiteInit.riteWeather, Aspects.water, 2);
        incantation_day = new IncantationBase("incantation_day", RiteInit.riteDay, Aspects.light, 1);
        incantation_night = new IncantationBase("incantation_night", RiteInit.riteNight, Aspects.ice, 1);
        incantation_binding = new IncantationBase("incantation_binding", RiteInit.riteBinding, Aspects.earth, 1);
        incantation_unbinding = new IncantationBase("incantation_unbinding", RiteInit.riteUnbinding, Aspects.earth, 1);
        hammer_heat = new ItemHammerHeat();
        dagger_sacrificial = new ItemDaggerSacrificial();
        soul_mirror = new ItemSoulMirror();
        heart_stone = new ItemHeartStone();
        tool_channeling = new ItemChannelingTool();
    }

    public static void register(IForgeRegistry<Item> registry) {
        for (Item item : ITEMS) {
            registry.register(item);
        }

    }

    public static void registerModels() {
        for (Item item : ITEMS) {
            if (item instanceof IHasModel) {
                ((IHasModel) item).registerItemModel();
            }
        }

    }
}
