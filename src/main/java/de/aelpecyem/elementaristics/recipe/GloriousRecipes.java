package de.aelpecyem.elementaristics.recipe;

import de.aelpecyem.elementaristics.recipe.base.GloriousRecipe;
import de.aelpecyem.elementaristics.recipe.base.PurifierRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class GloriousRecipes {
    public static Map<ResourceLocation, GloriousRecipe> GLORIOUS_RECIPES = new HashMap<>();

    public static void addRecipe(GloriousRecipe recipe) {
        GLORIOUS_RECIPES.put(recipe.name, recipe);
    }

    public static GloriousRecipe getRecipeForInput(ItemStack input) {
        for (GloriousRecipe recipe : GLORIOUS_RECIPES.values()) {
            if (recipe.input.apply(input)) {
                return recipe;
            }
        }
        return null;
    }
}
