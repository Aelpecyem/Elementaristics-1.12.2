package de.aelpecyem.elementaristics.recipe;

import de.aelpecyem.elementaristics.recipe.base.ReactorRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ReactorRecipes {
    public static Map<ResourceLocation, ReactorRecipe> REACTOR_RECIPES = new HashMap<>();

    public static void addRecipe(ReactorRecipe recipe) {
        REACTOR_RECIPES.put(recipe.name, recipe);
    }

    public static ReactorRecipe getRecipeForInputs(ItemStack input, ItemStack input2) {
        for (ReactorRecipe recipe : REACTOR_RECIPES.values()) {
            if (recipe.input.apply(input) && recipe.input2.apply(input2)) {
                return recipe;
            }
        }
        return null;
    }
}
