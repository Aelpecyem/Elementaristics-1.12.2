package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.recipe.*;
import de.aelpecyem.elementaristics.recipe.base.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class InitRecipes {

    public static void init() {
        initForge();
        // initPedestal();
        initPurifier();
        initEntropizer();
        initTunneler();
        initConcentrator();
        initReactor();
        initBasin();
    }

    private static void initBasin() {
        InfusionRecipes.addRecipe(new InfusionRecipe(new ResourceLocation(Elementaristics.MODID, "essence_magan"), Ingredient.fromItem(ModItems.ash), new ItemStack(ModItems.essence, 5, Aspects.magan.getId()), Aspects.earth, Aspects.water, Aspects.air, Aspects.aether, Aspects.fire, Aspects.mana));
        InfusionRecipes.addRecipe(new InfusionRecipe(new ResourceLocation(Elementaristics.MODID, "wine_redmost"), Ingredient.fromItem(ModItems.tincture_arcane), ModItems.wine_redmost.getDefaultInstance(), Aspects.earth, Aspects.water, Aspects.mind, Aspects.body));
        InfusionRecipes.addRecipe(new InfusionRecipe(new ResourceLocation(Elementaristics.MODID, "flesh_lamb"), Ingredient.fromItem(ModItems.thoughts_battling), ModItems.flesh_lamb.getDefaultInstance(), Aspects.ice, Aspects.ice, Aspects.ice));

    }
    private static void initForge() {
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "thaumagral_iron"), Ingredient.fromStacks(new ItemStack(Items.IRON_SWORD)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.fire.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.mana.getId())), Ingredient.fromStacks(new ItemStack(Items.IRON_INGOT)), Ingredient.fromStacks(new ItemStack(ModItems.gem_triangular)),  ModItems.thaumagral_iron.getDefaultInstance()));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "thaumagral_wood"), Ingredient.fromStacks(new ItemStack(Items.WOODEN_SWORD)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.earth.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.mana.getId())), Ingredient.fromStacks(new ItemStack(Blocks.LOG, 1, 1)), Ingredient.fromStacks(new ItemStack(ModItems.moss_everchaning)), ModItems.thaumagral_wood.getDefaultInstance()));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "thaumagral_stone"), Ingredient.fromStacks(new ItemStack(Items.STONE_SWORD)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.water.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.mana.getId())), Ingredient.fromStacks(new ItemStack(ModItems.cluster_earth)), Ingredient.fromStacks(new ItemStack(ModItems.gem_triangular)), ModItems.thaumagral_stone.getDefaultInstance()));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "thaumagral_gold"), Ingredient.fromStacks(new ItemStack(Items.GOLDEN_SWORD)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.air.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.mana.getId())), Ingredient.fromStacks(new ItemStack(Items.GOLD_INGOT)), Ingredient.fromStacks(new ItemStack(ModItems.gem_triangular)), ModItems.thaumagral_gold.getDefaultInstance()));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "thaumagral_diamond"), Ingredient.fromStacks(new ItemStack(Items.DIAMOND_SWORD)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.aether.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.mana.getId())), Ingredient.fromStacks(new ItemStack(Items.DIAMOND)), Ingredient.fromStacks(new ItemStack(ModItems.gem_triangular)), ModItems.thaumagral_diamond.getDefaultInstance()));


        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "hammer_heat"), Ingredient.fromItem(ModItems.head_hammer), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.fire.getId())), Ingredient.fromItem(ModItems.sparks_living),Ingredient.fromStacks(new ItemStack(Blocks.OBSIDIAN)), Ingredient.fromItem(Items.STICK), ModItems.hammer_heat.getDefaultInstance()));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "mirror_soul"), Ingredient.fromStacks(new ItemStack(Blocks.GLASS)),  Ingredient.fromItem(Items.DIAMOND), Ingredient.fromStacks(new ItemStack(ModItems.sands_soul)), Ingredient.fromStacks(new ItemStack(ModItems.thoughts_battling)), Ingredient.fromItem(Items.STICK),ModItems.soul_mirror.getDefaultInstance()));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "dagger"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.ice.getId())), Ingredient.fromItem(ModItems.gem_triangular), Ingredient.fromStacks(new ItemStack(ModItems.motion_captured)), Ingredient.fromStacks(new ItemStack(Items.DIAMOND_SWORD)), Ingredient.fromItem(ModItems.earth_purest), ModItems.dagger_sacrificial.getDefaultInstance()));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "heart_stone"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.earth.getId())), Ingredient.fromItem(ModItems.earth_purest), Ingredient.fromStacks(new ItemStack(ModItems.soul_dead)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.body.getId())), Ingredient.fromStacks(new ItemStack(Blocks.STONE, 1, 0)), ModItems.heart_stone.getDefaultInstance()));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "gem_arcane"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.crystal.getId())), Ingredient.fromItem(ModItems.maganized_matter), Ingredient.fromStacks(new ItemStack(Items.DIAMOND)), Ingredient.fromStacks(new ItemStack(Items.GLOWSTONE_DUST)), Ingredient.fromStacks(new ItemStack(Items.REDSTONE, 1, 0)), ModItems.gem_triangular.getDefaultInstance()));
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "lightning_tangible"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.electricity.getId())), Ingredient.fromItem(Items.IRON_INGOT), Ingredient.fromStacks(new ItemStack(Items.IRON_NUGGET)), Ingredient.fromStacks(new ItemStack(Items.IRON_NUGGET)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.electricity.getId())), ModItems.lightning_tangible.getDefaultInstance()));

    }

  /*  private static void initPedestal() {
        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "essence_aether"), Ingredient.fromStacks(new ItemStack(ModItems.cluster_aether)), new ItemStack(ModItems.essence, 1, Aspects.aether.getId())));
        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "essence_fire"), Ingredient.fromStacks(new ItemStack(ModItems.cluster_fire)), new ItemStack(ModItems.essence, 1, Aspects.fire.getId())));
        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "essence_air"), Ingredient.fromStacks(new ItemStack(ModItems.cluster_air)), new ItemStack(ModItems.essence, 1, Aspects.air.getId())));
        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "essence_water"), Ingredient.fromStacks(new ItemStack(ModItems.cluster_water)), new ItemStack(ModItems.essence, 1, Aspects.water.getId())));
        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "essence_earth"), Ingredient.fromStacks(new ItemStack(ModItems.cluster_earth)), new ItemStack(ModItems.essence, 1, Aspects.earth.getId())));

        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "vibrant_quartz"), Ingredient.fromStacks(new ItemStack(Items.QUARTZ)), new ItemStack(ModItems.vibrant_quartz)));
        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "chaotic_matter_pedestal"), Ingredient.fromStacks(new ItemStack(Blocks.STONE)), new ItemStack(ModItems.chaotic_matter)));
    }*/

    private static void initReactor() {
        ReactorRecipes.addRecipe(new ReactorRecipe(new ResourceLocation(Elementaristics.MODID, "essence_life"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1 , Aspects.water.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence,1,  Aspects.earth.getId())), new ItemStack(ModItems.essence, 1, Aspects.life.getId())));
        ReactorRecipes.addRecipe(new ReactorRecipe(new ResourceLocation(Elementaristics.MODID, "essence_electricity"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.air.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.fire.getId())), new ItemStack(ModItems.essence, 1, Aspects.electricity.getId())));

        ReactorRecipes.addRecipe(new ReactorRecipe(new ResourceLocation(Elementaristics.MODID, "seeds_opium"), Ingredient.fromStacks(new ItemStack(Blocks.RED_FLOWER)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.mind.getId())), new ItemStack(ModItems.seed_herb)));
        ReactorRecipes.addRecipe(new ReactorRecipe(new ResourceLocation(Elementaristics.MODID, "sparks_living"), Ingredient.fromItem(Items.BLAZE_POWDER), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.fire.getId())), ModItems.sparks_living.getDefaultInstance()));
        ReactorRecipes.addRecipe(new ReactorRecipe(new ResourceLocation(Elementaristics.MODID, "sparks_brightest"), Ingredient.fromItem(Items.GLOWSTONE_DUST), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.light.getId())), ModItems.sparks_brightest.getDefaultInstance()));
        ReactorRecipes.addRecipe(new ReactorRecipe(new ResourceLocation(Elementaristics.MODID, "thoughts_battling"), Ingredient.fromStacks(new ItemStack(ModBlocks.fabric_passion)), Ingredient.fromStacks(new ItemStack(ModBlocks.fabric_reason)), ModItems.thoughts_battling.getDefaultInstance()));
        ReactorRecipes.addRecipe(new ReactorRecipe(new ResourceLocation(Elementaristics.MODID, "vacuum_selfsustaining"), Ingredient.fromStacks(new ItemStack(Items.BUCKET)), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.vacuum.getId())), ModItems.vacuum_selfsustaining.getDefaultInstance()));

    }

    private static void initPurifier() {
        PurifierRecipes.addRecipe(new PurifierRecipe(new ResourceLocation(Elementaristics.MODID, "essence_crystal"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.earth.getId())), 3, new ItemStack(ModItems.essence, 1, Aspects.crystal.getId()), 100));
        PurifierRecipes.addRecipe(new PurifierRecipe(new ResourceLocation(Elementaristics.MODID, "essence_order"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.aether.getId())), 3,new ItemStack(ModItems.essence, 1, Aspects.order.getId()), 200));

        PurifierRecipes.addRecipe(new PurifierRecipe(new ResourceLocation(Elementaristics.MODID, "water_purest"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.water.getId())), 2, ModItems.water_purest.getDefaultInstance(), 600));
    }

    private static void initEntropizer() {
        EntropizerRecipes.addRecipe(new EntropizerRecipe(new ResourceLocation(Elementaristics.MODID, "essence_chaos"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.aether.getId())), new ItemStack(ModItems.essence, 1, Aspects.chaos.getId())));
        EntropizerRecipes.addRecipe(new EntropizerRecipe(new ResourceLocation(Elementaristics.MODID, "essence_vacuum"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.air.getId())), new ItemStack(ModItems.essence, 1, Aspects.vacuum.getId())));

        EntropizerRecipes.addRecipe(new EntropizerRecipe(new ResourceLocation(Elementaristics.MODID, "matter_astral_body"), Ingredient.fromItem(ModItems.stardust), new ItemStack(ModItems.matter_astral_body)));
        EntropizerRecipes.addRecipe(new EntropizerRecipe(new ResourceLocation(Elementaristics.MODID, "dead_soul"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.soul.getId())), new ItemStack(ModItems.soul_dead)));
        EntropizerRecipes.addRecipe(new EntropizerRecipe(new ResourceLocation(Elementaristics.MODID, "stardust"), Ingredient.fromItem(Items.GLOWSTONE_DUST), new ItemStack(ModItems.stardust)));

    }

    private static void initTunneler() {
        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "essence_body"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.earth.getId())), new ItemStack(ModItems.essence, 1, Aspects.water.getId()), new ItemStack(ModItems.essence, 1, Aspects.air.getId()), new ItemStack(ModItems.essence, 1, Aspects.fire.getId()), new ItemStack(ModItems.essence, 1, Aspects.body.getId())));
        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "essence_mind"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.fire.getId())), new ItemStack(ModItems.essence, 1, Aspects.water.getId()), new ItemStack(ModItems.essence, 1, Aspects.air.getId()), new ItemStack(ModItems.essence, 1, Aspects.earth.getId()), new ItemStack(ModItems.essence, 1, Aspects.mind.getId())));

        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "essence_soul"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.magan.getId())), new ItemStack(ModItems.essence, 1, Aspects.order.getId()), new ItemStack(ModItems.essence, 1, Aspects.chaos.getId()), new ItemStack(ModItems.essence, 1, Aspects.aether.getId()), new ItemStack(ModItems.essence, 1, Aspects.soul.getId())));

        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "powder_soul"), Ingredient.fromItem(ModItems.soul_dead), new ItemStack(ModItems.essence, 1, Aspects.soul.getId()), new ItemStack(ModItems.matter_astral_body), new ItemStack(ModItems.essence, 1, Aspects.magan.getId()), new ItemStack(ModItems.sands_soul)));
        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "laudanum"), Ingredient.fromItem(ModItems.water_purest), new ItemStack(ModItems.petal_opium), new ItemStack(ModItems.seed_herb), new ItemStack(ModItems.essence, 1, Aspects.mind.getId()), new ItemStack(ModItems.opium_tincture)));
        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "catalyst_entropizing"), Ingredient.fromStacks(new ItemStack(Blocks.STONE)), new ItemStack(ModItems.chaotic_matter), new ItemStack(ModItems.essence, 1, Aspects.aether.getId()), new ItemStack(ModItems.gem_triangular), new ItemStack(ModItems.catalyst_entropizing)));
        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "catalyst_ordering"), Ingredient.fromStacks(new ItemStack(Blocks.OBSIDIAN)), new ItemStack(ModItems.sparks_brightest), new ItemStack(ModItems.essence, 1, Aspects.order.getId()), new ItemStack(ModItems.gem_triangular), new ItemStack(ModItems.catalyst_ordering)));

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



    }
}
