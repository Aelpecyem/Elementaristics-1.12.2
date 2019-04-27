package de.aelpecyem.elementaristics.recipe;

import de.aelpecyem.elementaristics.recipe.base.ConcentratorRecipe;
import de.aelpecyem.elementaristics.recipe.base.InfusionRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class InfusionRecipes {
    public static Map<ResourceLocation, InfusionRecipe> INFUSION_RECIPES = new HashMap<>();

    public static void addRecipe(InfusionRecipe recipe) {
        INFUSION_RECIPES.put(recipe.name, recipe);
    }

    public static InfusionRecipe getRecipeForInput(ItemStack input) {
        for (InfusionRecipe recipe : INFUSION_RECIPES.values()) {
            if (recipe.input.apply(input)) {
                return recipe;
            }
        }
        return null;
    }
}
