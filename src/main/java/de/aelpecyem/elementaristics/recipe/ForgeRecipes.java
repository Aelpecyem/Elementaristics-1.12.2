package de.aelpecyem.elementaristics.recipe;

import de.aelpecyem.elementaristics.recipe.base.ForgeRecipe;
import de.aelpecyem.elementaristics.recipe.base.PurifierRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ForgeRecipes {
    public static Map<ResourceLocation, ForgeRecipe> FORGE_RECIPES = new HashMap<>();

    public static void addRecipe(ForgeRecipe recipe) {
        FORGE_RECIPES.put(recipe.name, recipe);
    }

    public static ForgeRecipe getRecipeForInput(ItemStack input) {
        for (ForgeRecipe recipe : FORGE_RECIPES.values()) {
            if (recipe.input.apply(input)) {
                return recipe;
            }
        }
        return null;
    }
}
