package de.aelpecyem.elementaristics.recipe;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModBlocks;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.init.RiteInit;
import de.aelpecyem.elementaristics.items.base.ItemEssence;
import de.aelpecyem.elementaristics.misc.elements.ElementInit;
import de.aelpecyem.elementaristics.misc.rites.RiteKnowledge;
import de.aelpecyem.elementaristics.recipe.base.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class InitRecipes {

    public static void init() {
        initForge();
        initPedestal();
        initPurifier();
        initEntropizer();
        initTunneler();
        initConcentrator();
        initReactor();
    }

    private static void initForge() {
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "thaumagral_iron"), Ingredient.fromStacks(new ItemStack(Blocks.IRON_BLOCK)), ModItems.thaumagral_iron.getDefaultInstance(),4));
    }

    private static void initPedestal() {
        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "essence_aether"), Ingredient.fromStacks(new ItemStack(ModItems.cluster_aether)), new ItemStack(ModItems.essence, 1, ElementInit.aether.getId())));
        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "essence_fire"), Ingredient.fromStacks(new ItemStack(ModItems.cluster_fire)), new ItemStack(ModItems.essence, 1, ElementInit.fire.getId())));
        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "essence_air"), Ingredient.fromStacks(new ItemStack(ModItems.cluster_air)), new ItemStack(ModItems.essence, 1, ElementInit.air.getId())));
        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "essence_water"), Ingredient.fromStacks(new ItemStack(ModItems.cluster_water)), new ItemStack(ModItems.essence, 1, ElementInit.water.getId())));
        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "essence_earth"), Ingredient.fromStacks(new ItemStack(ModItems.cluster_earth)), new ItemStack(ModItems.essence, 1, ElementInit.earth.getId())));

        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "vibrant_quartz"), Ingredient.fromStacks(new ItemStack(Items.QUARTZ)), new ItemStack(ModItems.vibrant_quartz)));
        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "chaotic_matter_pedestal"), Ingredient.fromStacks(new ItemStack(Blocks.STONE)), new ItemStack(ModItems.chaotic_matter)));
    }

    private static void initReactor() {
        ReactorRecipes.addRecipe(new ReactorRecipe(new ResourceLocation(Elementaristics.MODID, "essence_life"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1 ,ElementInit.water.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence,1,  ElementInit.earth.getId())), new ItemStack(ModItems.essence, 1,ElementInit.life.getId())));
        ReactorRecipes.addRecipe(new ReactorRecipe(new ResourceLocation(Elementaristics.MODID, "essence_electricity"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1,ElementInit.air.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, ElementInit.fire.getId())), new ItemStack(ModItems.essence, 1,ElementInit.electricity.getId())));
    }

    private static void initPurifier() {
        PurifierRecipes.addRecipe(new PurifierRecipe(new ResourceLocation(Elementaristics.MODID, "essence_crystal"), Ingredient.fromStacks(new ItemStack(ModItems.cluster_earth)), new ItemStack(ModItems.essence, 1, ElementInit.crystal.getId()), 100));
        PurifierRecipes.addRecipe(new PurifierRecipe(new ResourceLocation(Elementaristics.MODID, "essence_order"), Ingredient.fromStacks(new ItemStack(ModItems.cluster_aether)), new ItemStack(ModItems.essence, 1, ElementInit.order.getId()), 200));
    }

    private static void initEntropizer() {
        EntropizerRecipes.addRecipe(new EntropizerRecipe(new ResourceLocation(Elementaristics.MODID, "essence_chaos"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, ElementInit.aether.getId())), new ItemStack(ModItems.essence, 1, ElementInit.chaos.getId())));
        EntropizerRecipes.addRecipe(new EntropizerRecipe(new ResourceLocation(Elementaristics.MODID, "essence_vacuum"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, ElementInit.air.getId())), new ItemStack(ModItems.essence, 1, ElementInit.vacuum.getId())));
    }

    private static void initTunneler() {
        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "essence_body"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, ElementInit.earth.getId())), new ItemStack(ModItems.essence, 1, ElementInit.water.getId()), new ItemStack(ModItems.essence, 1, ElementInit.air.getId()), new ItemStack(ModItems.essence, 1, ElementInit.fire.getId()), new ItemStack(ModItems.essence, 1, ElementInit.body.getId())));
        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "essence_mind"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, ElementInit.fire.getId())), new ItemStack(ModItems.essence, 1, ElementInit.water.getId()), new ItemStack(ModItems.essence, 1, ElementInit.air.getId()), new ItemStack(ModItems.essence, 1, ElementInit.earth.getId()), new ItemStack(ModItems.essence, 1, ElementInit.mind.getId())));

        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "essence_soul"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, ElementInit.magan.getId())), new ItemStack(ModItems.essence, 1, ElementInit.order.getId()), new ItemStack(ModItems.essence, 1, ElementInit.chaos.getId()), new ItemStack(ModItems.essence, 1, ElementInit.chaos.getId()), new ItemStack(ModItems.essence, 1, ElementInit.soul.getId())));
    }

    private static void initConcentrator() {
        ConcentratorRecipes.addRecipe(new ConcentratorRecipe(new ResourceLocation(Elementaristics.MODID, "essence_light"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, ElementInit.fire.getId())), new ItemStack(ModItems.essence, 1, ElementInit.air.getId()), new ItemStack(ModItems.essence, 1, ElementInit.light.getId())));
        ConcentratorRecipes.addRecipe(new ConcentratorRecipe(new ResourceLocation(Elementaristics.MODID, "essence_ice"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, ElementInit.water.getId())), new ItemStack(ModItems.essence, 1, ElementInit.earth.getId()), new ItemStack(ModItems.essence, 1, ElementInit.ice.getId())));
    }
}
