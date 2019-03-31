package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
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
        initPedestal();
        initPurifier();
        initEntropizer();
        initTunneler();
        initConcentrator();
        initReactor();
    }

    private static void initForge() {
        ForgeRecipes.addRecipe(new ForgeRecipe(new ResourceLocation(Elementaristics.MODID, "thaumagral_iron"), Ingredient.fromStacks(new ItemStack(Blocks.IRON_BLOCK)), Ingredient.fromStacks(new ItemStack(Blocks.GOLD_BLOCK)), Ingredient.fromStacks(new ItemStack(Blocks.DIAMOND_BLOCK)), Ingredient.fromStacks(new ItemStack(Blocks.EMERALD_BLOCK)), Ingredient.fromStacks(new ItemStack(Blocks.LAPIS_BLOCK)),  ModItems.thaumagral_iron.getDefaultInstance()));
    }

    private static void initPedestal() {
        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "essence_aether"), Ingredient.fromStacks(new ItemStack(ModItems.cluster_aether)), new ItemStack(ModItems.essence, 1, Aspects.aether.getId())));
        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "essence_fire"), Ingredient.fromStacks(new ItemStack(ModItems.cluster_fire)), new ItemStack(ModItems.essence, 1, Aspects.fire.getId())));
        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "essence_air"), Ingredient.fromStacks(new ItemStack(ModItems.cluster_air)), new ItemStack(ModItems.essence, 1, Aspects.air.getId())));
        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "essence_water"), Ingredient.fromStacks(new ItemStack(ModItems.cluster_water)), new ItemStack(ModItems.essence, 1, Aspects.water.getId())));
        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "essence_earth"), Ingredient.fromStacks(new ItemStack(ModItems.cluster_earth)), new ItemStack(ModItems.essence, 1, Aspects.earth.getId())));

        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "vibrant_quartz"), Ingredient.fromStacks(new ItemStack(Items.QUARTZ)), new ItemStack(ModItems.vibrant_quartz)));
        PedestalRecipes.addRecipe(new PedestalRecipe(new ResourceLocation(Elementaristics.MODID, "chaotic_matter_pedestal"), Ingredient.fromStacks(new ItemStack(Blocks.STONE)), new ItemStack(ModItems.chaotic_matter)));
    }

    private static void initReactor() {
        ReactorRecipes.addRecipe(new ReactorRecipe(new ResourceLocation(Elementaristics.MODID, "essence_life"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1 , Aspects.water.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence,1,  Aspects.earth.getId())), new ItemStack(ModItems.essence, 1, Aspects.life.getId())));
        ReactorRecipes.addRecipe(new ReactorRecipe(new ResourceLocation(Elementaristics.MODID, "essence_electricity"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.air.getId())), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.fire.getId())), new ItemStack(ModItems.essence, 1, Aspects.electricity.getId())));
    }

    private static void initPurifier() {
        PurifierRecipes.addRecipe(new PurifierRecipe(new ResourceLocation(Elementaristics.MODID, "essence_crystal"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 3, Aspects.earth.getId())), new ItemStack(ModItems.essence, 1, Aspects.crystal.getId()), 100));
        PurifierRecipes.addRecipe(new PurifierRecipe(new ResourceLocation(Elementaristics.MODID, "essence_order"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 3, Aspects.aether.getId())), new ItemStack(ModItems.essence, 1, Aspects.order.getId()), 200));
    }

    private static void initEntropizer() {
        EntropizerRecipes.addRecipe(new EntropizerRecipe(new ResourceLocation(Elementaristics.MODID, "essence_chaos"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.aether.getId())), new ItemStack(ModItems.essence, 1, Aspects.chaos.getId())));
        EntropizerRecipes.addRecipe(new EntropizerRecipe(new ResourceLocation(Elementaristics.MODID, "essence_vacuum"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.air.getId())), new ItemStack(ModItems.essence, 1, Aspects.vacuum.getId())));

        EntropizerRecipes.addRecipe(new EntropizerRecipe(new ResourceLocation(Elementaristics.MODID, "matter_astral_body"), Ingredient.fromItem(ModItems.stardust), new ItemStack(ModItems.matter_astral_body)));
        EntropizerRecipes.addRecipe(new EntropizerRecipe(new ResourceLocation(Elementaristics.MODID, "dead_soul"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.soul.getId())), new ItemStack(ModItems.soul_dead)));
    }

    private static void initTunneler() {
        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "essence_body"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.earth.getId())), new ItemStack(ModItems.essence, 1, Aspects.water.getId()), new ItemStack(ModItems.essence, 1, Aspects.air.getId()), new ItemStack(ModItems.essence, 1, Aspects.fire.getId()), new ItemStack(ModItems.essence, 1, Aspects.body.getId())));
        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "essence_mind"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.fire.getId())), new ItemStack(ModItems.essence, 1, Aspects.water.getId()), new ItemStack(ModItems.essence, 1, Aspects.air.getId()), new ItemStack(ModItems.essence, 1, Aspects.earth.getId()), new ItemStack(ModItems.essence, 1, Aspects.mind.getId())));

        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "essence_soul"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.magan.getId())), new ItemStack(ModItems.essence, 1, Aspects.order.getId()), new ItemStack(ModItems.essence, 1, Aspects.chaos.getId()), new ItemStack(ModItems.essence, 1, Aspects.aether.getId()), new ItemStack(ModItems.essence, 1, Aspects.soul.getId())));

        TunnelerRecipes.addRecipe(new TunnelerRecipe(new ResourceLocation(Elementaristics.MODID, "powder_soul"), Ingredient.fromItem(ModItems.soul_dead), new ItemStack(ModItems.essence, 1, Aspects.soul.getId()), new ItemStack(ModItems.matter_astral_body), new ItemStack(ModItems.essence, 1, Aspects.magan.getId()), new ItemStack(ModItems.sands_soul)));
    }

    private static void initConcentrator() {
        ConcentratorRecipes.addRecipe(new ConcentratorRecipe(new ResourceLocation(Elementaristics.MODID, "essence_light"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.fire.getId())), new ItemStack(ModItems.essence, 1, Aspects.air.getId()), new ItemStack(ModItems.essence, 1, Aspects.light.getId())));
        ConcentratorRecipes.addRecipe(new ConcentratorRecipe(new ResourceLocation(Elementaristics.MODID, "essence_ice"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.water.getId())), new ItemStack(ModItems.essence, 1, Aspects.earth.getId()), new ItemStack(ModItems.essence, 1, Aspects.ice.getId())));

        ConcentratorRecipes.addRecipe(new ConcentratorRecipe(new ResourceLocation(Elementaristics.MODID, "dead_soul"), Ingredient.fromStacks(new ItemStack(ModItems.essence, 1, Aspects.soul.getId())), new ItemStack(ModItems.essence, 1, Aspects.mana.getId()), new ItemStack(ModItems.soul_dead)));
    }
}
