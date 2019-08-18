package de.aelpecyem.elementaristics.recipe;

import de.aelpecyem.elementaristics.recipe.base.PurifierRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class PurifierRecipes {
    public static final Map<ResourceLocation, PurifierRecipe> PURIFIER_RECIPES = new HashMap<>();

    public static void addRecipe(PurifierRecipe recipe) {
        PURIFIER_RECIPES.put(recipe.name, recipe);
    }

    public static PurifierRecipe getRecipeForInput(ItemStack input) {
        for (PurifierRecipe recipe : PURIFIER_RECIPES.values()) {
            if (recipe.input.apply(input)) {
                return recipe;
            }
        }
        return null;
    }
}
