package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.recipe.*;
import de.aelpecyem.elementaristics.recipe.base.*;
import de.aelpecyem.elementaristics.util.TimeUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class InitRecipes {

    public static void init() {
        initForge();
        initGlory();
        initPurifier();
        initEntropizer();
        initTunneler();
        initConcentrator();
        initReactor();
        initBasin(); //todo add doc to all added stuff (Energized Gunpowder, Ascension stage reshaping) and ofc recipes
    }

    private static void initGlory() { //todo add additonal effects to the statues where it's noted; update doc for that, then, too
        GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "nothingness"), TimeUtil.getHourForTimeBegin(Deities.deityNothingness.getTickTimeBegin()), Ingredient.fromItem(ModItems.flesh_lamb), ModBlocks.symbol_nothingness));
        GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "azathoth"), TimeUtil.getHourForTimeBegin(Deities.deityAzathoth.getTickTimeBegin()), Ingredient.fromItem(ModItems.tympanum_empty), ModBlocks.symbol_azathoth));
        GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "fire"), TimeUtil.getHourForTimeBegin(Deities.deityDragonFire.getTickTimeBegin()), Ingredient.fromStacks(new ItemStack(ModItems.scale, 1, Aspects.fire.getId())), ModBlocks.symbol_dragon_fire));
        GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "aether"), TimeUtil.getHourForTimeBegin(Deities.deityDragonAether.getTickTimeBegin()), Ingredient.fromStacks(new ItemStack(ModItems.scale, 1, Aspects.aether.getId())), ModBlocks.symbol_dragon_aether));
        GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "air"), TimeUtil.getHourForTimeBegin(Deities.deityDragonAir.getTickTimeBegin()), Ingredient.fromStacks(new ItemStack(ModItems.scale, 1, Aspects.air.getId())), ModBlocks.symbol_dragon_air));
        GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "earth"), TimeUtil.getHourForTimeBegin(Deities.deityDragonEarth.getTickTimeBegin()), Ingredient.fromStacks(new ItemStack(ModItems.scale, 1, Aspects.earth.getId())), ModBlocks.symbol_dragon_earth));
        GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "water"), TimeUtil.getHourForTimeBegin(Deities.deityDragonWater.getTickTimeBegin()), Ingredient.fromStacks(new ItemStack(ModItems.scale, 1, Aspects.water.getId())), ModBlocks.symbol_dragon_water));
        GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "gate"), TimeUtil.getHourForTimeBegin(Deities.deityGateAndKey.getTickTimeBegin()), Ingredient.fromItem(ModItems.item_lock), ModBlocks.symbol_gate));
        GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "dreamer"), TimeUtil.getHourForTimeBegin(Deities.deityDreamer.getTickTimeBegin()), Ingredient.fromItem(ModItems.dagger_sacrificial), ModBlocks.symbol_dreamer));
        GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "angel"), TimeUtil.getHourForTimeBegin(Deities.deityAngel.getTickTimeBegin()), Ingredient.fromItem(ModItems.key_winged), ModBlocks.symbol_angel));
        GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "storm"), TimeUtil.getHourForTimeBegin(Deities.deityStorm.getTickTimeBegin()), Ingredient.fromItem(ModItems.tool_channeling), ModBlocks.symbol_storm));
        GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "fighter"), TimeUtil.getHourForTimeBegin(Deities.deityFighter.getTickTimeBegin()), Ingredient.fromItem(Items.DIAMOND_SWORD), ModBlocks.symbol_fighter));
        GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "sun"), TimeUtil.getHourForTimeBegin(Deities.deitySun.getTickTimeBegin()), Ingredient.fromItem(ModItems.splendor_eye), ModBlocks.symbol_sun));
        GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "harbinger"), TimeUtil.getHourForTimeBegin(Deities.deityHarbinger.getTickTimeBegin()), Ingredient.fromItem(Items.DIAMOND_BOOTS), ModBlocks.symbol_harbinger));
        GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "queen"), TimeUtil.getHourForTimeBegin(Deities.deityQueen.getTickTimeBegin()), Ingredient.fromItem(Items.RABBIT_FOOT), ModBlocks.symbol_queen));
        GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "goat"), TimeUtil.getHourForTimeBegin(Deities.deityGoat.getTickTimeBegin()), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.wood.getId())), ModBlocks.symbol_goat));
        // GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "moth"), TimeUtil.getHourForTimeBegin(Deities.deityMoth.getTickTimeBegin()), Ingredient.fromItem(ModItems.ash), ModBlocks.symbol_moth));       todo: add these once fitting items are in
        //  GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "thread"), TimeUtil.getHourForTimeBegin(Deities.deityThread.getTickTimeBegin()), Ingredient.fromItem(ModItems.ash), ModBlocks.symbol_thread));
        GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "mirror"), TimeUtil.getHourForTimeBegin(Deities.deityMirror.getTickTimeBegin()), Ingredient.fromItem(ModItems.soul_mirror), ModBlocks.symbol_mirror));
        //  GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "dancer"), TimeUtil.getHourForTimeBegin(Deities.deityDancer.getTickTimeBegin()), Ingredient.fromItem(ModItems.ash), ModBlocks.symbol_dancer));
        //   GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "king"), TimeUtil.getHourForTimeBegin(Deities.deityKing.getTickTimeBegin()), Ingredient.fromItem(ModItems.ash), ModBlocks.symbol_king));
        //  GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "mother"), TimeUtil.getHourForTimeBegin(Deities.deityMother.getTickTimeBegin()), Ingredient.fromItem(ModItems.ash), ModBlocks.symbol_mother));
        //   GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "moon"), TimeUtil.getHourForTimeBegin(Deities.deityMoon.getTickTimeBegin()), Ingredient.fromItem(ModItems.ash), ModBlocks.symbol_moon));
        //   GloriousRecipes.addRecipe(new GloriousRecipe(new ResourceLocation(Elementaristics.MODID, "witch"), TimeUtil.getHourForTimeBegin(Deities.deityWitch.getTickTimeBegin()), Ingredient.fromItem(ModItems.ash), ModBlocks.symbol_witch));

    }

    private static void initBasin() {
        InfusionRecipes.addRecipe(new InfusionRecipe(new ResourceLocation(Elementaristics.MODID, "essence_magan"), Ingredient.fromItem(ModItems.ash), new ItemStack(ModItems.essence, 5, Aspects.magan.getId()), Aspects.earth, Aspects.water, Aspects.air, Aspects.aether, Aspects.fire, Aspects.mana));
        InfusionRecipes.addRecipe(new InfusionRecipe(new ResourceLocation(Elementaristics.MODID, "wine_redmost"), Ingredient.fromItem(ModItems.tincture_arcane), new ItemStack(ModItems.wine_redmost), Aspects.earth, Aspects.water, Aspects.mind, Aspects.body));
        InfusionRecipes.addRecipe(new InfusionRecipe(new ResourceLocation(Elementaristics.MODID, "flesh_lamb"), Ingredient.fromItem(ModItems.thoughts_battling), new ItemStack(ModItems.flesh_lamb), Aspects.ice, Aspects.body));

        InfusionRecipes.addRecipe(new InfusionRecipe(new ResourceLocation(Elementaristics.MODID, "body_water"), Ingredient.fromItem(ModItems.maganized_matter), new ItemStack(ModItems.body_water), Aspects.water, Aspects.body, Aspects.air));

        InfusionRecipes.addRecipe(new InfusionRecipe(new ResourceLocation(Elementaristics.MODID, "gunpowder_energized"), Ingredient.fromItem(Items.GUNPOWDER), new ItemStack(ModItems.gunpowder_energized, 3), Aspects.electricity));
        InfusionRecipes.addRecipe(new InfusionRecipe(new ResourceLocation(Elementaristics.MODID, "essence_wood"), Ingredient.fromItems(ModItems.flesh_lamb), new ItemStack(ModItems.essence, 1, Aspects.wood.getId()), Aspects.life, Aspects.soul, Aspects.light, Aspects.mind, Aspects.chaos));

        InfusionRecipes.addRecipe(new InfusionRecipe(new ResourceLocation(Elementaristics.MODID, "toxin_glassfinger"), Ingredient.fromItems(ModItems.sandthroat_concentrated), new ItemStack(ModItems.poison_glassblood, 1), Aspects.light, Aspects.ice));

    }
    private static void initForge() {
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "thaumagral_iron"), Ingredient.fromStacks(new ItemStack(Items.IRON_SWORD)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.fire.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.mana.getId())), Ingredient.fromStacks(new ItemStack(Items.IRON_INGOT)), Ingredient.fromStacks(new ItemStack(ModItems.gem_arcane)), new ItemStack(ModItems.thaumagral_iron)));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "thaumagral_wood"), Ingredient.fromStacks(new ItemStack(Items.WOODEN_SWORD)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.earth.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.mana.getId())), Ingredient.fromStacks(new ItemStack(Blocks.LOG, 1, 1)), Ingredient.fromStacks(new ItemStack(ModItems.moss_everchaning)), new ItemStack(ModItems.thaumagral_wood)));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "thaumagral_stone"), Ingredient.fromStacks(new ItemStack(Items.STONE_SWORD)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.water.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.mana.getId())), Ingredient.fromStacks(new ItemStack(ModItems.cluster_earth)), Ingredient.fromStacks(new ItemStack(ModItems.gem_arcane)), new ItemStack(ModItems.thaumagral_stone)));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "thaumagral_gold"), Ingredient.fromStacks(new ItemStack(Items.GOLDEN_SWORD)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.air.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.mana.getId())), Ingredient.fromStacks(new ItemStack(Items.GOLD_INGOT)), Ingredient.fromStacks(new ItemStack(ModItems.gem_arcane)), new ItemStack(ModItems.thaumagral_gold)));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "thaumagral_diamond"), Ingredient.fromStacks(new ItemStack(Items.DIAMOND_SWORD)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.aether.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.mana.getId())), Ingredient.fromStacks(new ItemStack(Items.DIAMOND)), Ingredient.fromStacks(new ItemStack(ModItems.gem_arcane)), new ItemStack(ModItems.thaumagral_diamond)));


        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "hammer_heat"), Ingredient.fromItem(ModItems.head_hammer), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.fire.getId())), Ingredient.fromItem(ModItems.sparks_living), Ingredient.fromStacks(new ItemStack(Blocks.OBSIDIAN)), Ingredient.fromItem(Items.STICK), new ItemStack(ModItems.hammer_heat)));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "mirror_soul"), Ingredient.fromStacks(new ItemStack(Blocks.GLASS)), Ingredient.fromItem(Items.DIAMOND), Ingredient.fromStacks(new ItemStack(ModItems.sands_soul)), Ingredient.fromStacks(new ItemStack(ModItems.thoughts_battling)), Ingredient.fromItem(Items.STICK), new ItemStack(ModItems.soul_mirror)));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "dagger"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.ice.getId())), Ingredient.fromItem(ModItems.gem_arcane), Ingredient.fromStacks(new ItemStack(ModItems.motion_captured)), Ingredient.fromStacks(new ItemStack(Items.DIAMOND_SWORD)), Ingredient.fromItem(ModItems.earth_purest), new ItemStack(ModItems.dagger_sacrificial)));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "heart_stone"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.earth.getId())), Ingredient.fromItem(ModItems.earth_purest), Ingredient.fromStacks(new ItemStack(ModItems.soul_dead)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.body.getId())), Ingredient.fromStacks(new ItemStack(Blocks.STONE, 1, 0)), new ItemStack(ModItems.heart_stone)));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "gem_arcane"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.crystal.getId())), Ingredient.fromItem(ModItems.maganized_matter), Ingredient.fromStacks(new ItemStack(Items.DIAMOND)), Ingredient.fromStacks(new ItemStack(Items.GLOWSTONE_DUST)), Ingredient.fromStacks(new ItemStack(Items.REDSTONE, 1, 0)), new ItemStack(ModItems.gem_arcane)));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "lightning_tangible"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.electricity.getId())), Ingredient.fromItem(Items.IRON_INGOT), Ingredient.fromStacks(new ItemStack(Items.IRON_NUGGET)), Ingredient.fromStacks(new ItemStack(Items.IRON_NUGGET)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.electricity.getId())), new ItemStack(ModItems.lightning_tangible)));

        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "tool_channeling"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.electricity.getId())), Ingredient.fromItem(Items.IRON_INGOT), Ingredient.fromStacks(new ItemStack(ModItems.gem_arcane)), Ingredient.fromStacks(new ItemStack(Items.IRON_NUGGET)), Ingredient.fromStacks(new ItemStack(ModItems.lightning_tangible)), new ItemStack(ModItems.tool_channeling)));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "key_winged"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.air.getId())), Ingredient.fromItem(Items.IRON_INGOT), Ingredient.fromStacks(new ItemStack(ModItems.gem_arcane)), Ingredient.fromStacks(new ItemStack(Items.IRON_NUGGET)), Ingredient.fromStacks(new ItemStack(ModItems.motion_captured)), new ItemStack(ModItems.key_winged)));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "hammer_head"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.earth.getId())), Ingredient.fromStacks(new ItemStack(Blocks.OBSIDIAN)), Ingredient.fromStacks(new ItemStack(ModItems.gem_arcane)), Ingredient.fromStacks(new ItemStack(Blocks.OBSIDIAN)), Ingredient.fromStacks(new ItemStack(ModItems.earth_purest)), new ItemStack(ModItems.head_hammer)));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "splendor_eye"), Ingredient.fromItems(Items.DIAMOND, Items.EMERALD), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.soul.getId())), Ingredient.fromStacks(new ItemStack(Blocks.OBSIDIAN)), Ingredient.fromStacks(new ItemStack(ModBlocks.stone_enriched)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.light.getId())), new ItemStack(ModItems.splendor_eye)));

        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "incantation_reshaping"), Ingredient.fromItems(Items.PAPER), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.fire.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.light.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.body.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.earth.getId())), new ItemStack(ModItems.incantation_reforging)));

        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "stone_runed"), Ingredient.fromStacks(new ItemStack(ModBlocks.stone_enriched)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.light.getId())), Ingredient.fromItems(Items.ENDER_EYE), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.vacuum.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.soul.getId())), new ItemStack(ModBlocks.stone_runed)));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "lock"), Ingredient.fromStacks(new ItemStack(ModItems.ash)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.mind.getId())), Ingredient.fromItems(Items.ENDER_EYE), Ingredient.fromItem(Items.IRON_INGOT), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.air.getId())), new ItemStack(ModItems.item_lock)));

    }

    private static void initReactor() {
        ReactorRecipes.addRecipe(new ReactorRecipe(new ResourceLocation(Elementaristics.MODID, "essence_life"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1 , Aspects.water.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence,1,  Aspects.earth.getId())), new ItemStack(ModItems.essence, 1, Aspects.life.getId())));
        ReactorRecipes.addRecipe(new ReactorRecipe(new ResourceLocation(Elementaristics.MODID, "essence_electricity"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.air.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.fire.getId())), new ItemStack(ModItems.essence, 1, Aspects.electricity.getId())));

        ReactorRecipes.addRecipe(new ReactorRecipe(new ResourceLocation(Elementaristics.MODID, "incantation_forging"), Ingredient.fromStacks(new ItemStack(Items.PAPER)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.fire.getId())), new ItemStack(ModItems.incantation_forging)));

        ReactorRecipes.addRecipe(new ReactorRecipe(new ResourceLocation(Elementaristics.MODID, "seeds_opium"), Ingredient.fromStacks(new ItemStack(Blocks.RED_FLOWER)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.mind.getId())), new ItemStack(ModItems.seed_herb)));
        ReactorRecipes.addRecipe(new ReactorRecipe(new ResourceLocation(Elementaristics.MODID, "sparks_living"), Ingredient.fromItem(Items.BLAZE_POWDER), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.fire.getId())), new ItemStack(ModItems.sparks_living)));
        ReactorRecipes.addRecipe(new ReactorRecipe(new ResourceLocation(Elementaristics.MODID, "sparks_brightest"), Ingredient.fromItem(Items.GLOWSTONE_DUST), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.light.getId())), new ItemStack(ModItems.sparks_brightest)));
        ReactorRecipes.addRecipe(new ReactorRecipe(new ResourceLocation(Elementaristics.MODID, "thoughts_battling"), Ingredient.fromStacks(new ItemStack(ModBlocks.fabric_passion)), Ingredient.fromStacks(new ItemStack(ModBlocks.fabric_reason)), new ItemStack(ModItems.thoughts_battling)));
        ReactorRecipes.addRecipe(new ReactorRecipe(new ResourceLocation(Elementaristics.MODID, "vacuum_selfsustaining"), Ingredient.fromStacks(new ItemStack(Items.BUCKET)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.vacuum.getId())), new ItemStack(ModItems.vacuum_selfsustaining)));

    }

    private static void initPurifier() {
        PurifierRecipes.addRecipe(new PurifierRecipe(new ResourceLocation(Elementaristics.MODID, "essence_crystal"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.earth.getId())), 3, new ItemStack(ModItems.essence, 1, Aspects.crystal.getId()), 100));
        PurifierRecipes.addRecipe(new PurifierRecipe(new ResourceLocation(Elementaristics.MODID, "essence_order"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.aether.getId())), 3,new ItemStack(ModItems.essence, 1, Aspects.order.getId()), 200));

        PurifierRecipes.addRecipe(new PurifierRecipe(new ResourceLocation(Elementaristics.MODID, "water_purest"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.water.getId())), 2, new ItemStack(ModItems.water_purest), 600));

        PurifierRecipes.addRecipe(new PurifierRecipe(new ResourceLocation(Elementaristics.MODID, "flint"), Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)), 1, new ItemStack(Items.FLINT), 60));

        PurifierRecipes.addRecipe(new PurifierRecipe(new ResourceLocation(Elementaristics.MODID, "poison_glassfinger_base"), Ingredient.fromStacks(new ItemStack(ModItems.poison_sandthroat)), 3, new ItemStack(ModItems.sandthroat_concentrated), 2400));

    }

    private static void initEntropizer() {
        EntropizerRecipes.addRecipe(new EntropizerRecipe(new ResourceLocation(Elementaristics.MODID, "essence_chaos"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.aether.getId())), new ItemStack(ModItems.essence, 1, Aspects.chaos.getId())));
        EntropizerRecipes.addRecipe(new EntropizerRecipe(new ResourceLocation(Elementaristics.MODID, "essence_vacuum"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.air.getId())), new ItemStack(ModItems.essence, 1, Aspects.vacuum.getId())));

        EntropizerRecipes.addRecipe(new EntropizerRecipe(new ResourceLocation(Elementaristics.MODID, "matter_astral_body"), Ingredient.fromItem(ModItems.stardust), new ItemStack(ModItems.matter_astral_body)));
        EntropizerRecipes.addRecipe(new EntropizerRecipe(new ResourceLocation(Elementaristics.MODID, "dead_soul"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.soul.getId())), new ItemStack(ModItems.soul_dead)));
        EntropizerRecipes.addRecipe(new EntropizerRecipe(new ResourceLocation(Elementaristics.MODID, "stardust"), Ingredient.fromItem(Items.GLOWSTONE_DUST), new ItemStack(ModItems.stardust)));

        EntropizerRecipes.addRecipe(new EntropizerRecipe(new ResourceLocation(Elementaristics.MODID, "rite_glory"), Ingredient.fromItem(ModItems.incantation_day), new ItemStack(ModItems.incantation_glorious)));

    }

    private static void initTunneler() {
        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "essence_body"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.earth.getId())), new ItemStack(ModItems.essence, 1, Aspects.water.getId()), new ItemStack(ModItems.essence, 1, Aspects.air.getId()), new ItemStack(ModItems.essence, 1, Aspects.fire.getId()), new ItemStack(ModItems.essence, 1, Aspects.body.getId())));
        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "essence_mind"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.fire.getId())), new ItemStack(ModItems.essence, 1, Aspects.water.getId()), new ItemStack(ModItems.essence, 1, Aspects.air.getId()), new ItemStack(ModItems.essence, 1, Aspects.earth.getId()), new ItemStack(ModItems.essence, 1, Aspects.mind.getId())));

        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "essence_soul"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.magan.getId())), new ItemStack(ModItems.essence, 1, Aspects.order.getId()), new ItemStack(ModItems.essence, 1, Aspects.chaos.getId()), new ItemStack(ModItems.essence, 1, Aspects.aether.getId()), new ItemStack(ModItems.essence, 1, Aspects.soul.getId())));

        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "powder_soul"), Ingredient.fromItem(ModItems.soul_dead), new ItemStack(ModItems.essence, 1, Aspects.soul.getId()), new ItemStack(ModItems.matter_astral_body), new ItemStack(ModItems.essence, 1, Aspects.magan.getId()), new ItemStack(ModItems.sands_soul)));
        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "laudanum"), Ingredient.fromItem(ModItems.water_purest), new ItemStack(ModItems.petal_opium), new ItemStack(ModItems.seed_herb), new ItemStack(ModItems.essence, 1, Aspects.mind.getId()), new ItemStack(ModItems.opium_tincture)));
        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "catalyst_entropizing"), Ingredient.fromStacks(new ItemStack(Blocks.STONE)), new ItemStack(ModItems.chaotic_matter), new ItemStack(ModItems.essence, 1, Aspects.aether.getId()), new ItemStack(ModItems.gem_arcane), new ItemStack(ModItems.catalyst_entropizing)));
        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "catalyst_ordering"), Ingredient.fromStacks(new ItemStack(Blocks.OBSIDIAN)), new ItemStack(ModItems.sparks_brightest), new ItemStack(ModItems.essence, 1, Aspects.order.getId()), new ItemStack(ModItems.gem_arcane), new ItemStack(ModItems.catalyst_ordering)));

        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "incantation_feast"), Ingredient.fromStacks(new ItemStack(Items.PAPER)), new ItemStack(ModItems.wine_redmost), new ItemStack(ModItems.essence, 1, Aspects.body.getId()), new ItemStack(ModItems.moss_everchaning), new ItemStack(ModItems.incantation_feast)));

        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "motion_captured"), Ingredient.fromStacks(new ItemStack(Items.FEATHER)), new ItemStack(ModItems.essence, 1, Aspects.air.getId()), new ItemStack(ModItems.essence, 1, Aspects.air.getId()), new ItemStack(ModItems.essence, 1, Aspects.earth.getId()), new ItemStack(ModItems.motion_captured)));

        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "incantation_weather"), Ingredient.fromStacks(new ItemStack(Items.PAPER)), new ItemStack(ModItems.sparks_brightest), new ItemStack(ModItems.lightning_tangible), new ItemStack(ModItems.essence, 1, Aspects.earth.getId()), new ItemStack(ModItems.motion_captured)));

    }

    private static void initConcentrator() {
        ConcentratorRecipes.addRecipe(new ConcentratorRecipe(new ResourceLocation(Elementaristics.MODID, "essence_light"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.fire.getId())), new ItemStack(ModItems.essence, 1, Aspects.air.getId()), new ItemStack(ModItems.essence, 1, Aspects.light.getId())));
        ConcentratorRecipes.addRecipe(new ConcentratorRecipe(new ResourceLocation(Elementaristics.MODID, "essence_ice"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.water.getId())), new ItemStack(ModItems.essence, 1, Aspects.earth.getId()), new ItemStack(ModItems.essence, 1, Aspects.ice.getId())));

        ConcentratorRecipes.addRecipe(new ConcentratorRecipe(new ResourceLocation(Elementaristics.MODID, "seed_dormant_1"), Ingredient.fromItem(ModItems.base_seed), new ItemStack(ModItems.essence, 1, Aspects.life.getId()), new ItemStack(Items.PUMPKIN_SEEDS)));
        ConcentratorRecipes.addRecipe(new ConcentratorRecipe(new ResourceLocation(Elementaristics.MODID, "seed_dormant_2"), Ingredient.fromItem(ModItems.base_seed), new ItemStack(ModItems.essence, 1, Aspects.body.getId()), new ItemStack(Items.MELON_SEEDS)));
        ConcentratorRecipes.addRecipe(new ConcentratorRecipe(new ResourceLocation(Elementaristics.MODID, "seed_dormant_3"), Ingredient.fromItem(ModItems.base_seed), new ItemStack(ModItems.essence, 1, Aspects.fire.getId()), new ItemStack(Items.BEETROOT_SEEDS)));
        ConcentratorRecipes.addRecipe(new ConcentratorRecipe(new ResourceLocation(Elementaristics.MODID, "seed_dormant_4"), Ingredient.fromItem(ModItems.base_seed), new ItemStack(ModItems.essence, 1, Aspects.air.getId()), new ItemStack(Items.WHEAT_SEEDS)));
        ConcentratorRecipes.addRecipe(new ConcentratorRecipe(new ResourceLocation(Elementaristics.MODID, "seed_dormant_5"), Ingredient.fromItem(ModItems.base_seed), new ItemStack(ModItems.essence, 1, Aspects.earth.getId()), new ItemStack(Items.POTATO)));
        ConcentratorRecipes.addRecipe(new ConcentratorRecipe(new ResourceLocation(Elementaristics.MODID, "seed_dormant_6"), Ingredient.fromItem(ModItems.base_seed), new ItemStack(ModItems.essence, 1, Aspects.water.getId()), new ItemStack(Items.CARROT)));
        ConcentratorRecipes.addRecipe(new ConcentratorRecipe(new ResourceLocation(Elementaristics.MODID, "seed_dormant_7"), Ingredient.fromItem(ModItems.base_seed), new ItemStack(ModItems.essence, 1, Aspects.aether.getId()), new ItemStack(Items.DYE, 1, 3)));

        ConcentratorRecipes.addRecipe(new ConcentratorRecipe(new ResourceLocation(Elementaristics.MODID, "dead_soul"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.soul.getId())), new ItemStack(ModItems.essence, 1, Aspects.mana.getId()), new ItemStack(ModItems.soul_dead)));
        ConcentratorRecipes.addRecipe(new ConcentratorRecipe(new ResourceLocation(Elementaristics.MODID, "incantation_chaos"), Ingredient.fromStacks(new ItemStack(Items.PAPER)), new ItemStack(ModItems.catalyst_entropizing), new ItemStack(ModItems.incantation_chaos)));
        ConcentratorRecipes.addRecipe(new ConcentratorRecipe(new ResourceLocation(Elementaristics.MODID, "incantation_light"), Ingredient.fromStacks(new ItemStack(Items.PAPER)), new ItemStack(ModItems.sparks_brightest), new ItemStack(ModItems.incantation_light)));

        ConcentratorRecipes.addRecipe(new ConcentratorRecipe(new ResourceLocation(Elementaristics.MODID, "incantation_health_share"), Ingredient.fromStacks(new ItemStack(Items.PAPER)), new ItemStack(ModItems.moss_everchaning), new ItemStack(ModItems.incantation_health_share)));



    }
}
